package test;

import test.auth.AuthUtil;

public class AuthTestBase extends TestBase
{
  protected UserRecord user;

  @Override
  protected void setUp() throws Exception {
    super.setUp();

    user = AuthUtil.getUser(AuthUtil.createUser(TestUtil.randStr(), TestUtil.randStr()));
  }

  public String authHeader() {
    return authHeader(user.getUserName(), user.getApiKey());
  }
}
