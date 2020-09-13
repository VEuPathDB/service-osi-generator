package test;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DbUtil
{
  private static final String
    DB_ADMIN_USER = "postgres",
    DB_ADMIN_PASS = System.getenv("POSTGRES_PASSWORD"),
    DB_PASSWORD   = System.getenv("DB_PASSWORD"),
    DB_USERNAME   = "osi_service",
    DB_HOST       = "osi-db",
    DB_NAME       = "postgres";

  private static final int DB_PORT = 5432;

  private static HikariDataSource dataSource;

  private static HikariDataSource adminDataSource;

  public static void init() {
    dataSource      = new HikariDataSource(getDbConfig());
    adminDataSource = new HikariDataSource(getAdminDbConfig());
  }

  public static void close() {
    dataSource.close();
    adminDataSource.close();
  }

  public static DataSource getServiceDataSource() {
    return dataSource;
  }

  public static DataSource getAdminDataSource() {
    return adminDataSource;
  }

  public static void clearDB() throws Exception {
    try (
      var con = adminDataSource.getConnection();
      var stmt = con.createStatement()
    ) {
      // Truncating users will also wipe all other tables as they all have a
      // foreign key to auth.users.user_id.
      stmt.execute("TRUNCATE TABLE auth.users CASCADE;");
    }
  }

  public static HikariConfig getDbConfig() {
    var out = new HikariConfig();
    out.setJdbcUrl(makeJdbcUrl());
    out.setUsername(DB_USERNAME);
    out.setPassword(DB_PASSWORD);
    out.setMaximumPoolSize(1);
    return out;
  }

  public static HikariConfig getAdminDbConfig() {
    var out = new HikariConfig();
    out.setJdbcUrl(makeJdbcUrl());
    out.setUsername(DB_ADMIN_USER);
    out.setPassword(DB_ADMIN_PASS);
    out.setMaximumPoolSize(1);
    return out;
  }

  public static String makeJdbcUrl() {
    return String.format("jdbc:postgresql://%s:%d/%s", DB_HOST, DB_PORT, DB_NAME);
  }
}
