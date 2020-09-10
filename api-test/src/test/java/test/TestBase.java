package test;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class TestBase
{
  public static final String
    ADMIN_PASS    = System.getenv("ADMIN_PASS"),
    ADMIN_USER    = System.getenv("ADMIN_USER"),
    DB_ADMIN_USER = "postgres",
    DB_ADMIN_PASS = System.getenv("POSTGRES_PASSWORD"),
    DB_PASSWORD   = System.getenv("DB_PASSWORD"),
    DB_USERNAME   = "osi_service",
    DB_HOST       = "osi-db",
    DB_NAME       = "postgres",
    SERVICE_HOST  = "osi-service";

  public static final int
    DB_PORT      = 5432,
    SERVICE_PORT = 8080;

  public static HikariDataSource dataSource;

  public static HikariDataSource adminDataSource;

  @BeforeAll
  static void beforeAll() throws Exception {
    dataSource = new HikariDataSource(getDbConfig());
    adminDataSource = new HikariDataSource(getAdminDbConfig());
  }

  @BeforeEach
  protected void setUp() throws Exception {
    clearDB();
  }

  @AfterAll
  static void afterAll() {
    dataSource.close();
  }

  protected static void clearDB() throws Exception {
    try (
      var con = adminDataSource.getConnection();
      var stmt = con.createStatement()
    ) {
      // Truncating users will also wipe all other tables as they all have a
      // foreign key to auth.users.user_id.
      stmt.execute("TRUNCATE TABLE auth.users CASCADE;");
    }
  }

  protected static HikariConfig getDbConfig() {
    var out = new HikariConfig();
    out.setJdbcUrl(makeJdbcUrl());
    out.setUsername(DB_USERNAME);
    out.setPassword(DB_PASSWORD);
    out.setMaximumPoolSize(1);
    return out;
  }

  protected static HikariConfig getAdminDbConfig() {
    var out = new HikariConfig();
    out.setJdbcUrl(makeJdbcUrl());
    out.setUsername(DB_ADMIN_USER);
    out.setPassword(DB_ADMIN_PASS);
    out.setMaximumPoolSize(1);
    return out;
  }

  public static String makeJdbcUrl() {
    return String.format("jdbc:postgresql://%s:%d/%s", DB_HOST, 5432, DB_NAME);
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
