package org.veupathdb.service.osi.service.user;

import java.util.Optional;

import org.junit.jupiter.api.*;
import org.veupathdb.service.osi.model.db.User;
import util.TestUtil;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("UserManager")
@SuppressWarnings("ResultOfMethodCallIgnored")
class UserManagerTest
{
  @Nested
  @DisplayName("#getLocalUser(long)")
  class GetLocalUser1
  {
    // TODO
  }

  @Nested
  @DisplayName("#getLocalUser(String)")
  class GetLocalUser2
  {
    // TODO
  }

  @Nested
  @DisplayName("#putLocalUser(User)")
  class PutLocalUser
  {
    // TODO
  }

  @Nested
  @DisplayName("#lookupUser(String)")
  class LookupUser1
  {
    private UserRepo mUserRepo;
    private User     mUser;

    @BeforeEach
    void setUp() throws Exception {
      mUserRepo = mock(UserRepo.class);
      mUser     = mock(User.class);

      var inst = UserRepo.class.getDeclaredField("instance");
      inst.setAccessible(true);
      inst.set(null, mUserRepo);
    }

    @AfterEach
    void tearDown() throws Exception {
      var inst = UserRepo.class.getDeclaredField("instance");
      inst.setAccessible(true);
      inst.set(null, new UserRepo());
    }

    @Nested
    @DisplayName("When user record is already cached")
    class Cached
    {
      @Test
      @DisplayName("Does not attempt to load from the database")
      void test1() throws Exception {
        var target = spy(new UserManager());
        var name   = TestUtil.randStr();

        doReturn(Optional.of(mUser)).when(target).getLocalUser(name);

        var out = target.lookupUser(name);

        assertTrue(out.isPresent());
        assertSame(mUser, out.get());

        verify(target).getLocalUser(name);
        verifyNoInteractions(mUserRepo);
      }
    }

    @Nested
    @DisplayName("When user record is not cached")
    class NotCached
    {
      @Test
      @DisplayName("Attempts to loads the user from the database")
      void test1() throws Exception {
        var target = spy(new UserManager());
        var name   = TestUtil.randStr();

        target.lookupUser(name);

        verify(target).getLocalUser(name);
        verify(mUserRepo).selectUser(name);
      }

      @Nested
      @DisplayName("and the database has no such user")
      class NoUser
      {
        @Test
        @DisplayName("returns an empty option")
        void test1() throws Exception {
          var target = spy(new UserManager());
          var name   = TestUtil.randStr();

          doReturn(Optional.empty()).when(mUserRepo).selectUser(name);

          assertTrue(target.lookupUser(name).isEmpty());

          verify(target).getLocalUser(name);
          verify(mUserRepo).selectUser(name);
        }
      }

      @Nested
      @DisplayName("and the database has a matching user")
      class WithUser
      {
        @Test
        @DisplayName("returns a filled option")
        void test1() throws Exception {
          var target = spy(new UserManager());
          var name   = TestUtil.randStr();

          doReturn(Optional.of(mUser)).when(mUserRepo).selectUser(name);

          var out = target.lookupUser(name);

          assertTrue(out.isPresent());
          assertSame(mUser, out.get());

          verify(target).getLocalUser(name);
          verify(mUserRepo).selectUser(name);
        }

        @Test
        @DisplayName("caches the value")
        void test2() throws Exception {
          var target = spy(new UserManager());
          var name   = TestUtil.randStr();

          doReturn(name).when(mUser).getUserName();
          doReturn(Optional.of(mUser)).when(mUserRepo).selectUser(name);

          target.lookupUser(name);

          verify(target).putLocalUser(mUser);

          var cached = target.getLocalUser(name);
          assertTrue(cached.isPresent());
          assertSame(mUser, cached.get());

          verify(target, times(2)).getLocalUser(name);
          verify(mUserRepo).selectUser(name);
        }
      }
    }
  }

  @Nested
  @DisplayName("#lookupUser(String, String)")
  class LookupUser2
  {
    private UserRepo mUserRepo;
    private User     mUser;

    @BeforeEach
    void setUp() throws Exception {
      mUserRepo = mock(UserRepo.class);
      mUser     = mock(User.class);

      var inst = UserRepo.class.getDeclaredField("instance");
      inst.setAccessible(true);
      inst.set(null, mUserRepo);
    }

    @AfterEach
    void tearDown() throws Exception {
      var inst = UserRepo.class.getDeclaredField("instance");
      inst.setAccessible(true);
      inst.set(null, new UserRepo());
    }

    @Nested
    @DisplayName("When user record is already cached")
    class Cached
    {
      @Test
      @DisplayName("Does not attempt to load from the database")
      void test1() throws Exception {
        var target = spy(new UserManager());
        var name   = TestUtil.randStr();
        var token  = TestUtil.randStr();

        doReturn(token).when(mUser).getApiKey();
        doReturn(Optional.of(mUser)).when(target).getLocalUser(name);

        var out = target.lookupUser(name, token);

        assertTrue(out.isPresent());
        assertSame(mUser, out.get());

        verify(target).getLocalUser(name);
        verifyNoInteractions(mUserRepo);
      }

      @Test
      @DisplayName("Does not return a result if the token does not match")
      void test2() throws Exception {
        var target = spy(new UserManager());
        var name   = TestUtil.randStr();
        var pass1  = TestUtil.randStr();
        var pass2  = TestUtil.randStr();

        assertNotEquals(pass1, pass2);

        doReturn(pass2).when(mUser).getApiKey();
        doReturn(Optional.of(mUser)).when(target).getLocalUser(name);

        assertTrue(target.lookupUser(name, pass1).isEmpty());

        verify(target).getLocalUser(name);
        verifyNoInteractions(mUserRepo);
      }

      @Test
      @DisplayName("Returns a result if the name and token match")
      void test3() throws Exception {
        var target = spy(new UserManager());
        var name   = TestUtil.randStr();
        var pass   = TestUtil.randStr();

        doReturn(pass).when(mUser).getApiKey();
        doReturn(Optional.of(mUser)).when(target).getLocalUser(name);

        var out = target.lookupUser(name, pass);
        assertTrue(out.isPresent());
        assertSame(mUser, out.get());

        verify(target).getLocalUser(name);
        verifyNoInteractions(mUserRepo);
      }
    }

    @Nested
    @DisplayName("When user record is not cached")
    class NotCached
    {
      @Test
      @DisplayName("Attempts to loads the user from the database")
      void test1() throws Exception {
        var target = spy(new UserManager());
        var name   = TestUtil.randStr();
        var token  = TestUtil.randStr();

        target.lookupUser(name, token);

        verify(target).getLocalUser(name);
        verify(mUserRepo).selectUser(name);
      }

      @Nested
      @DisplayName("and the database has no such user")
      class NoUser
      {
        @Test
        @DisplayName("returns an empty option")
        void test1() throws Exception {
          var target = spy(new UserManager());
          var name   = TestUtil.randStr();
          var token  = TestUtil.randStr();

          doReturn(Optional.empty()).when(mUserRepo).selectUser(name);

          assertTrue(target.lookupUser(name, token).isEmpty());

          verify(target).getLocalUser(name);
          verify(mUserRepo).selectUser(name);
        }
      }

      @Nested
      @DisplayName("and the database has a matching user")
      class WithUser
      {
        @Test
        @DisplayName("returns a filled option if the token matches")
        void test1() throws Exception {
          var target = spy(new UserManager());
          var name   = TestUtil.randStr();
          var token  = TestUtil.randStr();

          doReturn(token).when(mUser).getApiKey();
          doReturn(Optional.of(mUser)).when(mUserRepo).selectUser(name);

          var out = target.lookupUser(name, token);

          assertTrue(out.isPresent());
          assertSame(mUser, out.get());

          verify(target).getLocalUser(name);
          verify(mUserRepo).selectUser(name);
        }

        @Test
        @DisplayName("returns a filled option if the token does not match")
        void test2() throws Exception {
          var target = spy(new UserManager());
          var name   = TestUtil.randStr();
          var token  = TestUtil.randStr();
          var bad    = TestUtil.randStr();

          doReturn(token).when(mUser).getApiKey();
          doReturn(Optional.of(mUser)).when(mUserRepo).selectUser(name);

          assertTrue(target.lookupUser(name, bad).isEmpty());

          verify(target).getLocalUser(name);
          verify(mUserRepo).selectUser(name);
        }

        @Test
        @DisplayName("caches the value")
        void test3() throws Exception {
          var target = spy(new UserManager());
          var name   = TestUtil.randStr();
          var token  = TestUtil.randStr();

          doReturn(name).when(mUser).getUserName();
          doReturn(token).when(mUser).getApiKey();
          doReturn(Optional.of(mUser)).when(mUserRepo).selectUser(name);

          target.lookupUser(name, token);

          verify(target).putLocalUser(mUser);

          var cached = target.getLocalUser(name);
          assertTrue(cached.isPresent());
          assertSame(mUser, cached.get());

          verify(target, times(2)).getLocalUser(name);
          verify(mUserRepo).selectUser(name);
        }
      }
    }
  }

  @Nested
  @DisplayName("#lookupUser(long)")
  class LookupUser3
  {
    private UserRepo mUserRepo;
    private User     mUser;

    @BeforeEach
    void setUp() throws Exception {
      mUserRepo = mock(UserRepo.class);
      mUser     = mock(User.class);

      var inst = UserRepo.class.getDeclaredField("instance");
      inst.setAccessible(true);
      inst.set(null, mUserRepo);
    }

    @AfterEach
    void tearDown() throws Exception {
      var inst = UserRepo.class.getDeclaredField("instance");
      inst.setAccessible(true);
      inst.set(null, new UserRepo());
    }

    @Nested
    @DisplayName("When user record is already cached")
    class Cached
    {
    }

    @Nested
    @DisplayName("When user record is not cached")
    class NotCached
    {
      @Test
      @DisplayName("Attempts to loads the user from the database")
      void test1() throws Exception {
        var target = spy(new UserManager());
        var id     = TestUtil.randLong();

        target.lookupUser(id);

        verify(target).getLocalUser(id);
        verify(mUserRepo).selectUser(id);
      }

      @Nested
      @DisplayName("and the database has no such user")
      class NoUser
      {
        @Test
        @DisplayName("returns an empty option")
        void test1() throws Exception {
          var target = spy(new UserManager());
          var id     = TestUtil.randLong();

          doReturn(Optional.empty()).when(mUserRepo).selectUser(id);

          assertTrue(target.lookupUser(id).isEmpty());

          verify(target).getLocalUser(id);
          verify(mUserRepo).selectUser(id);
        }
      }

      @Nested
      @DisplayName("and the database has a matching user")
      class WithUser
      {
        @Test
        @DisplayName("returns a filled option")
        void test1() throws Exception {
          var target = spy(new UserManager());
          var id     = TestUtil.randLong();

          doReturn(Optional.of(mUser)).when(mUserRepo).selectUser(id);

          var out = target.lookupUser(id);

          assertTrue(out.isPresent());
          assertSame(mUser, out.get());

          verify(target).getLocalUser(id);
          verify(mUserRepo).selectUser(id);
        }

        @Test
        @DisplayName("caches the value")
        void test2() throws Exception {
          var target = spy(new UserManager());
          var id     = TestUtil.randLong();

          doReturn(id).when(mUser).getUserId();
          doReturn(Optional.of(mUser)).when(mUserRepo).selectUser(id);

          target.lookupUser(id);

          verify(target).putLocalUser(mUser);

          var cached = target.getLocalUser(id);
          assertTrue(cached.isPresent());
          assertSame(mUser, cached.get());

          verify(target, times(2)).getLocalUser(id);
          verify(mUserRepo).selectUser(id);
        }
      }
    }
  }

  @Nested
  @DisplayName("Static Access")
  class Statics
  {
    private UserManager mUserMan;

    @BeforeEach
    void setUp() throws Exception {
      mUserMan = mock(UserManager.class);

      var instance = UserManager.class.getDeclaredField("instance");
      instance.setAccessible(true);
      instance.set(null, mUserMan);
    }

    @AfterEach
    void tearDown() throws Exception {
      var instance = UserManager.class.getDeclaredField("instance");
      instance.setAccessible(true);
      instance.set(null, new UserManager());
    }

    @Test
    @DisplayName("#getInstance()")
    void test1() {
      assertSame(mUserMan, UserManager.getInstance());
    }

    @Test
    @DisplayName("#lookup(String, String)")
    void test2() throws Exception {
      var in1 = TestUtil.randStr();
      var in2 = TestUtil.randStr();
      var out = mock(User.class);

      doReturn(Optional.of(out)).when(mUserMan).lookupUser(in1, in2);

      assertSame(out, UserManager.lookup(in1, in2).get());
      verify(mUserMan).lookupUser(in1, in2);
      verifyNoMoreInteractions(mUserMan);
    }
  }
}
