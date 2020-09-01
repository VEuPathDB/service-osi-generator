package org.veupathdb.service.osi.model.config;

import org.veupathdb.lib.container.jaxrs.config.Options;
import picocli.CommandLine.Option;

public class CliConfig extends Options
{
  public static final String
    DEFAULT_USERNAME = "osi_service",
    DEFAULT_DB_HOST  = "osi-db",
    DEFAULT_DB_NAME  = "postgres";

  public static final short
    DEFAULT_DB_PORT = 5432;

  public static final byte
    DEFAULT_DB_POOL_SIZE = 20;

  public static final String
    ENV_DB_USER      = "DB_USERNAME",
    ENV_DB_PASS      = "DB_PASSWORD",
    ENV_DB_PORT      = "DB_PORT",
    ENV_DB_POOL_SIZE = "DB_POOL_SIZE",
    ENV_DB_HOST      = "DB_HOST",
    ENV_DB_NAME      = "DB_NAME",
    ENV_ADMIN_USER   = "ADMIN_USER",
    ENV_ADMIN_PASS   = "ADMIN_PASS";

  public static final String
    ERR_NO_DB_PASS    = "Missing required environment variable " + ENV_DB_PASS,
    ERR_NO_ADMIN_USER = "Missing required environment variable "
      + ENV_ADMIN_USER,
    ERR_NO_ADMIN_PASS = "Missing required environment variable "
      + ENV_ADMIN_PASS;

  @Option(
    arity = "1",
    defaultValue = "${env:" + ENV_DB_USER + "}",
    names = "--db-username",
    description = "Postgres username.\n"
      + "May be set using the environment variable DB_USERNAME.\n"
      + "If not set, defaults to \"osi_service\"."
  )
  private String dbUsername;

  @Option(
    arity = "1",
    defaultValue = "${env:" + ENV_DB_PASS + "}",
    names = "--db-password-do-not-use",
    description = "Postgres password.\n"
      + "Must be set using the environment variable DB_PASSWORD."
      + "If not set, service will not start."
  )
  private String dbPassword;

  @Option(
    arity = "1",
    defaultValue = "${env:" + ENV_DB_PORT + "}",
    names = "--db-port",
    description = "Postgres port.\n"
      + "May be set using the environment variable DB_PORT."
      + "If not set, defaults to \"5432\"."
  )
  private Short dbPort;

  @Option(
    arity = "1",
    defaultValue = "${env:" + ENV_DB_POOL_SIZE + "}",
    names = "--db-pool-size",
    description = "Postgres connection pool size.\n"
      + "May be set using the environment variable DB_POOL_SIZE.\n"
      + "If not set, defaults to \"20\"."
  )
  private Byte dbPoolSize;

  @Option(
    arity = "1",
    defaultValue = "${env:" + ENV_DB_HOST + "}",
    names = "--db-host",
    description = "Postgres host address.\n"
      + "May be set using the environment variable DB_HOST.\n"
      + "If not set, defaults to \"postgres\"."
  )
  private String dbHost;

  @Option(
    arity = "1",
    defaultValue = "${env:" + ENV_DB_NAME + "}",
    names = "--db-name",
    description = "Postgres database name.\n"
      + "May be set using the environment variable DB_NAME.\n"
      + "If not set, defaults to \"postgres\""
  )
  private String dbName;

  @Option(
    arity = "1",
    defaultValue = "${env:" + ENV_ADMIN_USER + "}",
    names = "--admin-username",
    description = "Admin username, used exclusively to register new users with the service."
  )
  private String adminUser;

  @Option(
    arity = "1",
    defaultValue = "${env:" + ENV_ADMIN_PASS + "}",
    names = "--admin-password-do-not-use",
    description = "Admin password, used exclusively to register new users with the service."
  )
  private String adminPass;

  public String getDbUsername() {
    if (dbUsername == null)
      dbUsername = DEFAULT_USERNAME;

    return dbUsername;
  }

  public String getDbPassword() {
    if (dbPassword == null)
      throw new RuntimeException(ERR_NO_DB_PASS);

    return dbPassword;
  }

  public Short getDbPort() {
    if (dbPort == null)
      dbPort = DEFAULT_DB_PORT;

    return dbPort;
  }

  public Byte getDbPoolSize() {
    if (dbPoolSize == null)
      dbPoolSize = DEFAULT_DB_POOL_SIZE;

    return dbPoolSize;
  }

  public String getDbHost() {
    if (dbHost == null)
      dbHost = DEFAULT_DB_HOST;

    return dbHost;
  }

  public String getDbName() {
    if (dbName == null)
      dbName = DEFAULT_DB_NAME;

    return dbName;
  }

  public String getAdminUser() {
    if (adminUser == null)
      throw new RuntimeException(ERR_NO_ADMIN_USER);

    return adminUser;
  }

  public String getAdminPass() {
    if (adminPass == null)
      throw new RuntimeException(ERR_NO_ADMIN_PASS);

    return adminPass;
  }
}
