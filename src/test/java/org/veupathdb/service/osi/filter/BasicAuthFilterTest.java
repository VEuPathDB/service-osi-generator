package org.veupathdb.service.osi.filter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Stream;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.veupathdb.lib.container.jaxrs.Globals;
import org.veupathdb.service.osi.model.db.User;
import org.veupathdb.service.osi.service.user.UserManager;
import org.veupathdb.service.osi.util.InputValidationException;
import org.veupathdb.service.osi.util.Validation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@DisplayName("BasicAuthFilter")
class BasicAuthFilterTest
{
  private static final String
    adminUser = "someusername",
    adminPass = "44a73c66b380495197f09ea35ebd77bd";

  /**
   * Base64 encoded credentials
   */
  private static final String
    // user:c6c17c7eff284dfa98e0a9302de329ac (username too short)
    badUsername = "Basic dXNlcjpjNmMxN2M3ZWZmMjg0ZGZhOThlMGE5MzAyZGUzMjlhYw==",
  // someusername:123456789 (password too short)
  badPassword = "Basic c29tZXVzZXJuYW1lOjEyMzQ1Njc4OQ==",
  // someusername:44a73c66b380495197f09ea35ebd77bd
  goodCredentials = "Basic c29tZXVzZXJuYW1lOjQ0YTczYzY2YjM4MDQ5NTE5N2YwOWVhMzVlYmQ3N2Jk";

  private static final String[]
    NO_AUTH_ENDPOINTS = {"health", "metrics", "api"},
    ADMIN_ENDPOINTS   = {"auth", "auth/123", "auth/username"},
    AUTH_ENDPOINTS    = {
      "organisms", "organisms/123", "organisms/456",
      "idSetCollections", "idSetCollections/123",
      "idSets", "idSets/123"
    };

  private ContainerRequestContext mRequest;

  private MultivaluedMap < String, String > mHeaders;

  private UriInfo mUriInfo;

  private UserManager mUserMan;

  private BasicAuthFilter target;


  @BeforeEach
  @SuppressWarnings("unchecked")
  void setUp() throws Exception {
    mRequest = mock(ContainerRequestContext.class);
    mHeaders = mock(MultivaluedMap.class);
    mUriInfo = mock(UriInfo.class);
    mUserMan = mock(UserManager.class);
    target   = new BasicAuthFilter(adminUser, adminPass);

    var inst = UserManager.class.getDeclaredField("instance");
    inst.setAccessible(true);
    inst.set(null, mUserMan);

    // Reset validation class to real implementation (other tests mess with this)
    inst = Validation.class.getDeclaredField("instance");
    inst.setAccessible(true);
    inst.set(null, new Validation());
  }

  @Nested
  @DisplayName("#filter(ContainerRequestContext)")
  class Filter
  {
    @Nested
    @DisplayName("Without Credentials")
    class NoCreds
    {
      @ParameterizedTest(name = "Requests to /{0} are permitted")
      @ArgumentsSource(NoAuthProvider.class)
      void builtins(final String endpoint) {
        doReturn(mHeaders).when(mRequest).getHeaders();
        doReturn(mUriInfo).when(mRequest).getUriInfo();
        doReturn(endpoint).when(mUriInfo).getPath();

        assertDoesNotThrow(() -> target.filter(mRequest));
      }

      @ParameterizedTest(name = "Requests to /{0} are rejected")
      @ArgumentsSource(AdminProvider.class)
      void admin(final String endpoint) {
        doReturn(mHeaders).when(mRequest).getHeaders();
        doReturn(mUriInfo).when(mRequest).getUriInfo();
        doReturn(endpoint).when(mUriInfo).getPath();

        assertThrows(
          NotAuthorizedException.class,
          () -> target.filter(mRequest)
        );
      }

      @ParameterizedTest(name = "Requests to /{0} are rejected")
      @ArgumentsSource(AuthProvider.class)
      void normal(final String endpoint) {
        doReturn(mHeaders).when(mRequest).getHeaders();
        doReturn(mUriInfo).when(mRequest).getUriInfo();
        doReturn(endpoint).when(mUriInfo).getPath();

        assertThrows(
          NotAuthorizedException.class,
          () -> target.filter(mRequest)
        );
      }
    }

    @Nested
    @DisplayName("With Valid Admin Credentials")
    class AdminCreds
    {
      @ParameterizedTest(name = "Requests to /{0} are permitted")
      @ArgumentsSource(NoAuthProvider.class)
      void builtins(final String endpoint) {
        doReturn(mHeaders).when(mRequest).getHeaders();
        doReturn(mUriInfo).when(mRequest).getUriInfo();
        doReturn(endpoint).when(mUriInfo).getPath();

        assertDoesNotThrow(() -> target.filter(mRequest));
      }

      @ParameterizedTest(name = "Requests to /{0} are permitted")
      @ArgumentsSource(AdminProvider.class)
      void admin(final String endpoint) throws Exception {
        doReturn(mHeaders).when(mRequest).getHeaders();

        doReturn(true).when(mHeaders).containsKey("Authorization");
        doReturn(Collections.singletonList(goodCredentials)).when(mHeaders)
          .get("Authorization");
        doReturn(goodCredentials).when(mHeaders).getFirst("Authorization");

        doReturn(mUriInfo).when(mRequest).getUriInfo();
        doReturn(endpoint).when(mUriInfo).getPath();
        doReturn(Optional.empty()).when(mUserMan)
          .lookupUser(anyString(), anyString());

        assertDoesNotThrow(() -> target.filter(mRequest));
      }

      @ParameterizedTest(name = "Requests to /{0} are rejected")
      @ArgumentsSource(AuthProvider.class)
      void normal(final String endpoint) throws Exception {
        doReturn(mHeaders).when(mRequest).getHeaders();

        doReturn(true).when(mHeaders).containsKey("Authorization");
        doReturn(Collections.singletonList(goodCredentials)).when(mHeaders)
          .get("Authorization");
        doReturn(goodCredentials).when(mHeaders).getFirst("Authorization");

        doReturn(mUriInfo).when(mRequest).getUriInfo();
        doReturn(endpoint).when(mUriInfo).getPath();
        doReturn(Optional.empty()).when(mUserMan)
          .lookupUser(anyString(), anyString());

        assertThrows(
          NotAuthorizedException.class,
          () -> target.filter(mRequest)
        );
      }
    }

    @Nested
    @DisplayName("With Invalid Admin Credentials")
    class BadAdminCreds
    {
      @ParameterizedTest(name = "Requests to /{0} are permitted")
      @ArgumentsSource(NoAuthProvider.class)
      void builtins(final String endpoint) {
        doReturn(mHeaders).when(mRequest).getHeaders();
        doReturn(mUriInfo).when(mRequest).getUriInfo();
        doReturn(endpoint).when(mUriInfo).getPath();

        assertDoesNotThrow(() -> target.filter(mRequest));
      }

      @ParameterizedTest(name = "Requests to /{0} are rejected")
      @ArgumentsSource(AdminProvider.class)
      void admin(final String endpoint) throws Exception {
        doReturn(mHeaders).when(mRequest).getHeaders();

        doReturn(true).when(mHeaders).containsKey("Authorization");
        doReturn(Collections.singletonList(badUsername)).when(mHeaders)
          .get("Authorization");
        doReturn(badUsername).when(mHeaders).getFirst("Authorization");

        doReturn(mUriInfo).when(mRequest).getUriInfo();
        doReturn(endpoint).when(mUriInfo).getPath();
        doReturn(Optional.empty()).when(mUserMan)
          .lookupUser(anyString(), anyString());

        assertThrows(NotAuthorizedException.class,
          () -> target.filter(mRequest));
      }

      @ParameterizedTest(name = "Requests to /{0} are rejected")
      @ArgumentsSource(AuthProvider.class)
      void normal(final String endpoint) throws Exception {
        doReturn(mHeaders).when(mRequest).getHeaders();

        doReturn(true).when(mHeaders).containsKey("Authorization");
        doReturn(Collections.singletonList(badUsername)).when(mHeaders)
          .get("Authorization");
        doReturn(badUsername).when(mHeaders).getFirst("Authorization");

        doReturn(mUriInfo).when(mRequest).getUriInfo();
        doReturn(endpoint).when(mUriInfo).getPath();
        doReturn(Optional.empty()).when(mUserMan)
          .lookupUser(anyString(), anyString());

        assertThrows(
          NotAuthorizedException.class,
          () -> target.filter(mRequest)
        );
      }
    }

    @Nested
    @DisplayName("With Bad Auth Header")
    class BadHeader
    {
      @ParameterizedTest(name = "Requests to /{0} are permitted")
      @ArgumentsSource(NoAuthProvider.class)
      void builtins(final String endpoint) {
        doReturn(mHeaders).when(mRequest).getHeaders();
        doReturn(mUriInfo).when(mRequest).getUriInfo();
        doReturn(endpoint).when(mUriInfo).getPath();

        assertDoesNotThrow(() -> target.filter(mRequest));
      }

      @ParameterizedTest(name = "Requests to /{0} are rejected")
      @ArgumentsSource(AdminProvider.class)
      void admin(final String endpoint) throws Exception {
        doReturn(mHeaders).when(mRequest).getHeaders();

        doReturn(true).when(mHeaders).containsKey("Authorization");
        doReturn(Collections.singletonList("something")).when(mHeaders)
          .get("Authorization");
        doReturn("something").when(mHeaders).getFirst("Authorization");

        doReturn(mUriInfo).when(mRequest).getUriInfo();
        doReturn(endpoint).when(mUriInfo).getPath();
        doReturn(Optional.empty()).when(mUserMan)
          .lookupUser(anyString(), anyString());

        assertThrows(
          BadRequestException.class,
          () -> target.filter(mRequest));
      }

      @ParameterizedTest(name = "Requests to /{0} are rejected")
      @ArgumentsSource(AuthProvider.class)
      void normal(final String endpoint) throws Exception {
        doReturn(mHeaders).when(mRequest).getHeaders();

        doReturn(true).when(mHeaders).containsKey("Authorization");
        doReturn(Collections.singletonList("something")).when(mHeaders)
          .get("Authorization");
        doReturn("something").when(mHeaders).getFirst("Authorization");

        doReturn(mUriInfo).when(mRequest).getUriInfo();
        doReturn(endpoint).when(mUriInfo).getPath();
        doReturn(Optional.empty()).when(mUserMan)
          .lookupUser(anyString(), anyString());

        assertThrows(
          BadRequestException.class,
          () -> target.filter(mRequest)
        );
      }
    }

    @Nested
    @DisplayName("With Valid User Credentials")
    class UserCreds
    {
      @ParameterizedTest(name = "Requests to /{0} are permitted")
      @ArgumentsSource(NoAuthProvider.class)
      void builtins(final String endpoint) {
        doReturn(mHeaders).when(mRequest).getHeaders();
        doReturn(mUriInfo).when(mRequest).getUriInfo();
        doReturn(endpoint).when(mUriInfo).getPath();

        assertDoesNotThrow(() -> target.filter(mRequest));
      }

      @ParameterizedTest(name = "Requests to /{0} are rejected")
      @ArgumentsSource(AdminProvider.class)
      void admin(final String endpoint) throws Exception {
        doReturn(mHeaders).when(mRequest).getHeaders();

        doReturn(true).when(mHeaders).containsKey("Authorization");
        doReturn(Collections.singletonList(badPassword)).when(mHeaders)
          .get("Authorization");
        doReturn(badPassword).when(mHeaders).getFirst("Authorization");

        doReturn(mUriInfo).when(mRequest).getUriInfo();
        doReturn(endpoint).when(mUriInfo).getPath();
        doReturn(Optional.empty()).when(mUserMan)
          .lookupUser(anyString(), anyString());

        assertThrows(
          NotAuthorizedException.class,
          () -> target.filter(mRequest));
        verify(mRequest, times(0)).setProperty(anyString(), any());
      }

      @ParameterizedTest(name = "Requests to /{0} are permitted")
      @ArgumentsSource(AuthProvider.class)
      void normal(final String endpoint) throws Exception {
        var mUser = mock(User.class);

        doReturn(mHeaders).when(mRequest).getHeaders();

        doReturn(true).when(mHeaders).containsKey("Authorization");
        doReturn(Collections.singletonList(badPassword)).when(mHeaders)
          .get("Authorization");
        doReturn(badPassword).when(mHeaders).getFirst("Authorization");

        doReturn(mUriInfo).when(mRequest).getUriInfo();
        doReturn(endpoint).when(mUriInfo).getPath();
        doReturn(Optional.of(mUser)).when(mUserMan)
          .lookupUser(anyString(), anyString());

        assertDoesNotThrow(() -> target.filter(mRequest));
        verify(mRequest).setProperty(Globals.REQUEST_USER, mUser);
      }
    }

    @Nested
    @DisplayName("With Database Connection Errors")
    class BadDB
    {
      @ParameterizedTest(name = "Requests to /{0} are permitted")
      @ArgumentsSource(NoAuthProvider.class)
      void builtins(final String endpoint) {
        doReturn(mHeaders).when(mRequest).getHeaders();
        doReturn(mUriInfo).when(mRequest).getUriInfo();
        doReturn(endpoint).when(mUriInfo).getPath();

        assertDoesNotThrow(() -> target.filter(mRequest));
      }

      @ParameterizedTest(name = "Requests to /{0} throw 500")
      @ArgumentsSource(AdminProvider.class)
      void admin(final String endpoint) throws Exception {
        doReturn(mHeaders).when(mRequest).getHeaders();

        doReturn(true).when(mHeaders).containsKey("Authorization");
        doReturn(Collections.singletonList(badPassword)).when(mHeaders)
          .get("Authorization");
        doReturn(badPassword).when(mHeaders).getFirst("Authorization");

        doReturn(mUriInfo).when(mRequest).getUriInfo();
        doReturn(endpoint).when(mUriInfo).getPath();
        doThrow(Exception.class).when(mUserMan)
          .lookupUser(anyString(), anyString());

        assertThrows(
          InternalServerErrorException.class,
          () -> target.filter(mRequest));
        verify(mRequest, times(0)).setProperty(anyString(), any());
      }

      @ParameterizedTest(name = "Requests to /{0} are permitted")
      @ArgumentsSource(AuthProvider.class)
      void normal(final String endpoint) throws Exception {
        doReturn(mHeaders).when(mRequest).getHeaders();

        doReturn(true).when(mHeaders).containsKey("Authorization");
        doReturn(Collections.singletonList(badPassword)).when(mHeaders)
          .get("Authorization");
        doReturn(badPassword).when(mHeaders).getFirst("Authorization");

        doReturn(mUriInfo).when(mRequest).getUriInfo();
        doReturn(endpoint).when(mUriInfo).getPath();
        doThrow(Exception.class).when(mUserMan)
          .lookupUser(anyString(), anyString());

        assertThrows(InternalServerErrorException.class,
          () -> target.filter(mRequest));
        verify(mRequest, times(0)).setProperty(anyString(), any());
      }
    }
  }

  @Nested
  @DisplayName("#this(String, String)")
  class Constructor
  {
    @Nested
    @DisplayName("When the admin username is too short")
    class BadUsername
    {
      @Test
      @DisplayName("Throws Exception")
      void test() {
        assertThrows(InputValidationException.class,
          () -> new BasicAuthFilter("1234567", adminPass));
      }
    }

    @Nested
    @DisplayName("When the admin password is too short")
    class BadPassword
    {
      @Test
      @DisplayName("Throws Exception")
      void test() {
        assertThrows(InputValidationException.class,
          () -> new BasicAuthFilter(adminUser, "1234567891234567891234567891"));
      }
    }

    @Nested
    @DisplayName("When the admin username is null")
    class NullUsername
    {
      @Test
      @DisplayName("Throws Exception")
      void test() {
        assertThrows(InputValidationException.class,
          () -> new BasicAuthFilter(null, adminPass));
      }
    }

    @Nested
    @DisplayName("When the admin password is null")
    class NullPassword
    {
      @Test
      @DisplayName("Throws Exception")
      void test() {
        assertThrows(InputValidationException.class,
          () -> new BasicAuthFilter(adminUser, null));
      }
    }
  }

  static class NoAuthProvider implements ArgumentsProvider
  {
    @Override
    public Stream < ? extends Arguments > provideArguments(ExtensionContext context) {
      return Arrays.stream(NO_AUTH_ENDPOINTS).map(Arguments::of);
    }
  }

  static class AdminProvider implements ArgumentsProvider
  {
    @Override
    public Stream < ? extends Arguments > provideArguments(ExtensionContext context) {
      return Arrays.stream(ADMIN_ENDPOINTS).map(Arguments::of);
    }
  }

  static class AuthProvider implements ArgumentsProvider
  {
    @Override
    public Stream < ? extends Arguments > provideArguments(ExtensionContext context) {
      return Arrays.stream(AUTH_ENDPOINTS).map(Arguments::of);
    }
  }

  //  @Test
//  void filterValid() throws Exception {
//    Mockito.when(ctx.getHeaders())
//      .thenReturn(new MultivaluedHashMap <>(){{
//        put("Authorization", Collections.singletonList("Basic " + testAuthValue));
//      }});
//    Mockito.when(man.lookupUser("test_username", "test_password"))
//      .thenReturn(Optional.of(user));
//    Mockito.verify(ctx).setProperty(Globals.REQUEST_USER, user);
//
//    assertDoesNotThrow(() -> new BasicAuthFilter().filter(ctx));
//  }
}
