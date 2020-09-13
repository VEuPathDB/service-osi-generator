package test;

import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class TestBase
{
  public static final String
    ADMIN_PASS    = System.getenv("ADMIN_PASS"),
    ADMIN_USER    = System.getenv("ADMIN_USER"),
    SERVICE_HOST  = "osi-service";

  public static final int
    SERVICE_PORT = 8080;

  @BeforeAll
  static void beforeAll() {
    DbUtil.init();
  }

  @BeforeEach
  protected void setUp() throws Exception {
    DbUtil.clearDB();
  }

  @AfterAll
  static void afterAll() {
    DbUtil.close();
  }

  public static String makeUrl(final String path) {
    return String.format("http://%s:%d%s", SERVICE_HOST, SERVICE_PORT, path);
  }

  public static String adminAuthHeader() {
    return authHeader(ADMIN_USER, ADMIN_PASS);
  }

  public static String authHeader(final String name, final String key) {
    return "Basic " + Base64.encodeBase64String((name + ":" + key).getBytes());
  }
}
