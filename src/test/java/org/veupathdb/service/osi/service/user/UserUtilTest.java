package org.veupathdb.service.osi.service.user;

import java.sql.ResultSet;
import java.time.OffsetDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.veupathdb.service.osi.db.Schema;
import org.veupathdb.service.osi.generated.model.NewUserResponse;
import org.veupathdb.service.osi.model.db.NewUser;
import org.veupathdb.service.osi.model.db.User;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("UserUtil")
class UserUtilTest
{
  private ResultSet mResult;

  @BeforeEach
  void setUp() throws Exception {
    mResult = mock(ResultSet.class);

    var inst = UserUtil.class.getDeclaredField("instance");
    inst.setAccessible(true);
    inst.set(null, new UserUtil());
  }

  @Nested
  @DisplayName("#createUser(ResultSet)")
  class CreateUser1
  {
    @Test
    @DisplayName("accurately copies data from the input ResultSet")
    void test1() throws Exception {
      var id     = 123456L;
      var name   = "some user name";
      var key    = "some user api key";
      var issued = OffsetDateTime.now();

      doReturn(id).when(mResult).getLong(Schema.Auth.Users.USER_ID);
      doReturn(name).when(mResult).getString(Schema.Auth.Users.USER_NAME);
      doReturn(key).when(mResult).getString(Schema.Auth.Users.API_KEY);
      doReturn(issued).when(mResult)
        .getObject(Schema.Auth.Users.ISSUED, OffsetDateTime.class);

      var user = UserUtil.getInstance().createUser(mResult);

      assertEquals(id, user.getUserId());
      assertEquals(name, user.getUserName());
      assertEquals(key, user.getApiKey());
      assertSame(issued, user.getIssued());
    }
  }

  @Nested
  @DisplayName("#createUser(ResultSet, NewUser)")
  class CreateUser2
  {
    private NewUser mUser;

    @BeforeEach
    void setUp() {
      mUser   = mock(NewUser.class);
    }

    @Test
    @DisplayName("accurately copies data from the input ResultSet")
    void test1() throws Exception {
      var id     = 123456L;
      var name   = "some user name";
      var key    = "some user api key";
      var issued = OffsetDateTime.now();

      doReturn(id).when(mResult).getLong(Schema.Auth.Users.USER_ID);
      doReturn(issued).when(mResult)
        .getObject(Schema.Auth.Users.ISSUED, OffsetDateTime.class);

      doReturn(name).when(mUser).getUserName();
      doReturn(key).when(mUser).getApiKey();

      var user = UserUtil.getInstance().createUser(mResult, mUser);

      assertEquals(id, user.getUserId());
      assertEquals(name, user.getUserName());
      assertEquals(key, user.getApiKey());
      assertSame(issued, user.getIssued());
    }
  }

  @Nested
  @DisplayName("#userToResponse(User)")
  class UserToNewUserResponse
  {
    private User user;

    @BeforeEach
    void setUp() {
      user = new User(
        123456,
        "some user",
        "some api key",
        OffsetDateTime.now()
      );
    }

    @Test
    @DisplayName("accurately copies the given user instance")
    void test1() {
      var out = UserUtil.getInstance().userToResponse(user);

      assertEquals(user.getUserId(), out.getUserId());
      assertSame(user.getUserName(), out.getUsername());
      assertSame(user.getApiKey(), out.getApiKey());
      assertEquals(
        user.getIssued().getYear(),
        out.getIssued().getYear() + 1900
      );
      assertEquals(
        user.getIssued().getMonth().getValue(),
        out.getIssued().getMonth() + 1
      );
      assertEquals(user.getIssued().getDayOfMonth(), out.getIssued().getDate());
      assertEquals(user.getIssued().getHour(), out.getIssued().getHours());
      assertEquals(user.getIssued().getMinute(), out.getIssued().getMinutes());
      assertEquals(user.getIssued().getSecond(), out.getIssued().getSeconds());
    }
  }

  @Nested
  @DisplayName("Static Access")
  class Statics
  {
    private UserUtil mUtil;

    private User mUser;

    @BeforeEach
    void setUp() throws Exception {
      mUtil = mock(UserUtil.class);
      mUser = mock(User.class);

      var inst = UserUtil.class.getDeclaredField("instance");
      inst.setAccessible(true);
      inst.set(null, mUtil);
    }

    @Test
    @DisplayName("#newUser(ResultSet) calls #createUser(ResultSet)")
    void test1() throws Exception {
      doReturn(mUser).when(mUtil).createUser(mResult);

      assertSame(mUser, UserUtil.newUser(mResult));
      verify(mUtil).createUser(mResult);
      verifyNoMoreInteractions(mUtil);
    }

    @Test
    @DisplayName("#newUser(ResultSet, NewUser) calls #createUser(ResultSet, NewUser)")
    void test2() throws Exception {
      var mnu = mock(NewUser.class);

      doReturn(mUser).when(mUtil).createUser(mResult, mnu);

      assertSame(mUser, UserUtil.newUser(mResult, mnu));
      verify(mUtil).createUser(mResult, mnu);
      verifyNoMoreInteractions(mUtil);
    }

    @Test
    @DisplayName("#userToRes(User) calls #userToResponse(User)")
    void test3() throws Exception {
      var mre = mock(NewUserResponse.class);

      doReturn(mre).when(mUtil).userToResponse(mUser);

      assertSame(mre, UserUtil.userToRes(mUser));
      verify(mUtil).userToResponse(mUser);
      verifyNoMoreInteractions(mUtil);
    }
  }
}
