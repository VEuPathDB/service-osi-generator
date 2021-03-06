package org.veupathdb.service.osi.db;

import java.util.function.Supplier;

import io.vulpine.lib.sql.load.SqlLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.veupathdb.service.osi.db.Schema.Table;

public interface SQL
{
  Logger log = LogManager.getLogger(SQL.class);

  enum Mode
  {
    DELETE,
    INSERT,
    LOCK,
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
      String NEW_USER = insert(Table.USERS, "new-user");
    }

    interface Osi
    {
      String
        GENE       = insert(Table.GENES, "new-gene"),
        ID_SET     = insert(Table.ID_SETS, "new-id-set"),
        ORGANISM   = insert(Table.ORGANISMS, "new-organism"),
        TRANSCRIPT = insert(Table.TRANSCRIPTS, "new-transcript");
    }
  }

  interface Lock
  {
    interface Osi
    {
      String ORGANISMS = load(Mode.LOCK, "osi.organisms");
    }
  }

  interface Select
  {
    interface Auth
    {
      interface Users
      {
        String BY_ID   = select(Table.USERS, "by-id");
        String BY_NAME = select(Table.USERS, "by-name");
      }
    }

    interface Osi
    {
      interface Genes
      {
        String
          BY_ID_SET      = select(Table.GENES, "by-id-set"),
          BY_ID_SETS     = select(Table.GENES, "by-id-sets"),
          BY_IDENTIFIERS = select(Table.GENES, "by-identifiers");
      }

      interface IdSets
      {
        String
          BY_ID    = select(Table.ID_SETS, "by-id"),
          BY_QUERY = select(Table.ID_SETS, "by-query");
      }

      interface Organisms
      {
        String
          BY_ID    = select(Table.ORGANISMS, "by-id"),
          BY_NAME  = select(Table.ORGANISMS, "by-name"),
          BY_QUERY = select(Table.ORGANISMS, "by-query");
      }

      interface Transcripts
      {
        String BY_GENES = select(Table.TRANSCRIPTS, "by-genes");
      }
    }
  }

  interface Update
  {
    interface Osi
    {
      interface Organisms
      {
        String
          FULL_RECORD        = update(Table.ORGANISMS, "update-full"),
          GENE_COUNTER       = update(Table.ORGANISMS, "gene-counter"),
          TEMPLATE           = update(Table.ORGANISMS, "update-template"),
          TRANSCRIPT_COUNTER = update(Table.ORGANISMS, "transcript-counter");
      }

    }
  }

  private static String insert(final String table, final String file) {
    log.trace("Loading insert query " + file + " for table " + table);
    return load(Mode.INSERT, String.format(PATH_FORMAT, table, file));
  }

  private static String select(final String table, final String file) {
    log.trace("Loading select query " + file + " for table " + table);
    return load(Mode.SELECT, String.format(PATH_FORMAT, table, file));
  }

  private static String update(final String table, final String file) {
    log.trace("Loading update query " + file + " for table " + table);
    return load(Mode.UPDATE, String.format(PATH_FORMAT, table, file));
  }

  private static String load(final Mode mode, final String path) {
    return switch (mode) {
      case INSERT -> LOADER.insert(path).orElseThrow(error(mode, path));
      case SELECT -> LOADER.select(path).orElseThrow(error(mode, path));
      case LOCK -> LOADER.rawSql("lock." + path).orElseThrow(error(mode, path));
      case UPDATE -> LOADER.udpate(path).orElseThrow(error(mode, path));
      case DELETE -> LOADER.delete(path).orElseThrow(error(mode, path));
    };
  }

  private static Supplier<RuntimeException> error(Mode mode, String path) {
    return () -> new RuntimeException(String.format(ERR_NOT_FOUND,
      mode.name().toLowerCase(), path
    ));
  }
}
