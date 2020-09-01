package org.veupathdb.service.osi.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.veupathdb.service.osi.model.config.CliConfig;

public class DbMan implements AutoCloseable
{
  private static final String
    JDBC_TEMPLATE = "jdbc:postgresql://%s:%d/%s";

  /**
   * Class Error Messages
   */
  private static final String
    ERR_NO_INIT = "Attempted to retrieve DbMan instance before DbMan was"
      + "initialized.";

  private static DbMan instance;

  private final HikariDataSource ds;

  private DbMan(HikariDataSource ds) {
    this.ds = ds;
  }

  public Connection getConnection() throws SQLException {
    return ds.getConnection();
  }

  @Override
  public void close() {
    ds.close();
  }

  public static Connection connection() throws SQLException {
    return getInstance().getConnection();
  }

  public static DbMan getInstance() {
    if (instance == null)
      throw new RuntimeException(ERR_NO_INIT);
    return instance;
  }

  public static DbMan initialize(final CliConfig opts) {
    final var config = new HikariConfig();
    config.setDriverClassName("org.postgresql.Driver");
    config.setUsername(opts.getDbUsername());
    config.setPassword(opts.getDbPassword());
    config.setMaximumPoolSize(opts.getDbPoolSize());
    config.setJdbcUrl(String.format(
      JDBC_TEMPLATE,
      opts.getDbHost(),
      opts.getDbPort(),
      opts.getDbName()));

    instance = new DbMan(new HikariDataSource(config));
    return instance;
  }
}
