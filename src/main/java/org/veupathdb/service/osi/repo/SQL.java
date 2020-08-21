package org.veupathdb.service.osi.repo;

import io.vulpine.lib.sql.load.SqlLoader;

import java.util.function.Supplier;

interface SQL
{
  enum Mode
  {
    DELETE,
    INSERT,
    SELECT,
    UPDATE
  }

  String
    ERR_NOT_FOUND = "Could not load required SQL file resources.sql.%s.%s.sql",
    PATH_FORMAT   = "%s.%s";

  SqlLoader LOADER = new SqlLoader();

  interface Insert
  {
    interface Auth
    {
      String NEW_USER = insert(Schema.Auth.TABLE_USERS, "new-user");
    }
  }

  interface Select
  {
    interface Auth
    {
      interface Users
      {
        String BY_TOKEN = select(Schema.Auth.TABLE_USERS, "lookup-user");
      }
    }

    interface Osi
    {
      interface Organisms
      {
        String
          BY_ID = select(Schema.Osi.TABLE_ORGANISMS, "by-id");
      }
    }
  }

  interface Update
  {
    interface Osi
    {
      interface Organisms
      {
        String COUNTER = update(Schema.Osi.TABLE_ORGANISMS, "counter");
      }
    }
  }


  private static String delete(final String table, final String file) {
    return load(Mode.DELETE, String.format(PATH_FORMAT, table, file));
  }

  private static String insert(final String table, final String file) {
    return load(Mode.INSERT, String.format(PATH_FORMAT, table, file));
  }

  private static String select(final String table, final String file) {
    return load(Mode.SELECT, String.format(PATH_FORMAT, table, file));
  }

  private static String update(final String table, final String file) {
    return load(Mode.UPDATE, String.format(PATH_FORMAT, table, file));
  }

  private static String load(final Mode mode, final String path) {
    return switch (mode) {
      case INSERT -> LOADER.insert(path).orElseThrow(error(mode, path));
      case SELECT -> LOADER.select(path).orElseThrow(error(mode, path));
      case UPDATE -> LOADER.udpate(path).orElseThrow(error(mode, path));
      case DELETE -> LOADER.delete(path).orElseThrow(error(mode, path));
    };
  }

  private static Supplier < RuntimeException > error(Mode mode, String path) {
    return () -> new RuntimeException(String.format(ERR_NOT_FOUND,
      mode.name().toLowerCase(), path
    ));
  }
}
