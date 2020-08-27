package org.veupathdb.service.osi.repo;

import io.vulpine.lib.sql.load.SqlLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.veupathdb.service.osi.repo.Schema.Table;

import java.util.function.Supplier;

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
        COLLECTION = insert(
        Table.ID_SET_COLLECTIONS,
        "new-collection"
      ),
        ORGANISM   = insert(Table.ORGANISMS, "new-organism");
    }
  }

  interface Lock {
    interface Osi {
      String ORGANISMS = load(Mode.LOCK, "osi.organisms");
    }
  }

  interface Select
  {
    interface Auth
    {
      interface Users
      {
        String
          BY_ID          = select(Table.USERS, "by-id"),
          BY_TOKEN       = select(Table.USERS, "by-token"),
          BY_EMAIL       = select(Table.USERS, "by-name"),
          BY_COLLECTION  = select(Table.USERS, "by-collection"),
          BY_IDS         = select(Table.USERS, "by-ids"),
          BY_COLLECTIONS = select(Table.USERS, "by-collections"),
          BY_ID_SETS     = select(Table.USERS, "by-id-sets");
      }
    }

    interface Osi
    {
      interface Collections
      {
        String
          BY_ID    = select(Table.ID_SET_COLLECTIONS, "by-id"),
          BY_IDS   = select(Table.ID_SET_COLLECTIONS, "by-ids"),
          BY_QUERY = select(Table.ID_SET_COLLECTIONS, "find-collections");
      }

      interface Genes
      {
        String
          BY_COLLECTION  = select(Table.GENES, "by-collection"),
          BY_COLLECTIONS = select(Table.GENES, "by-collections"),
          BY_ID_SET      = select(Table.GENES, "by-id-set"),
          BY_ID_SETS     = select(Table.GENES, "by-id-sets");
      }

      interface IdSets
      {
        String
          BY_ID          = select(Table.ID_SETS, "by-id"),
          BY_QUERY       = select(Table.ID_SETS, "by-query"),
          BY_COLLECTION  = select(Table.ID_SETS, "by-collection"),
          BY_COLLECTIONS = select(Table.ID_SETS, "by-collections");
      }

      interface Organisms
      {
        String
          BY_COLLECTIONS = select(Table.ORGANISMS, "by-collections"),
          BY_ID          = select(Table.ORGANISMS, "by-id"),
          BY_IDS         = select(Table.ORGANISMS, "by-ids"),
          BY_NAME        = select(Table.ORGANISMS, "by-name"),
          BY_QUERY       = select(Table.ORGANISMS, "by-query");
      }

      interface Transcripts
      {
        String
          BY_GENES       = select(Table.TRANSCRIPTS, "by-genes"),
          BY_COLLECTION  = select(Table.TRANSCRIPTS, "by-collection"),
          BY_COLLECTIONS = select(Table.TRANSCRIPTS, "by-collections");
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


  private static String delete(final String table, final String file) {
    log.trace("Loading delete query " + file + " for table " + table);
    return load(Mode.DELETE, String.format(PATH_FORMAT, table, file));
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
      case LOCK ->   LOADER.rawSql("lock."+path).orElseThrow(error(mode, path));
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
