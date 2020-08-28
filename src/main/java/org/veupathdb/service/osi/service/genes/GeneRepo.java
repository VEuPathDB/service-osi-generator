package org.veupathdb.service.osi.service.genes;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import io.vulpine.lib.query.util.basic.BasicPreparedListReadQuery;
import io.vulpine.lib.query.util.basic.BasicPreparedMapReadQuery;
import org.apache.logging.log4j.Logger;
import org.veupathdb.lib.container.jaxrs.providers.LogProvider;
import org.veupathdb.service.osi.model.db.Gene;
import org.veupathdb.service.osi.model.db.IdSet;
import org.veupathdb.service.osi.model.db.User;
import org.veupathdb.service.osi.db.SQL;
import org.veupathdb.service.osi.db.SQL.Select.Osi.Genes;
import org.veupathdb.service.osi.service.DbMan;
import org.veupathdb.service.osi.util.QueryUtil;

public class GeneRepo
{
  private static final GeneRepo instance = new GeneRepo();

  private final Logger log = LogProvider.logger(getClass());

  // ╔════════════════════════════════════════════════════════════════════╗ //
  // ║                                                                    ║ //
  // ║    Static Access Methods                                           ║ //
  // ║                                                                    ║ //
  // ╚════════════════════════════════════════════════════════════════════╝ //

  public static GeneRepo getInstance() {
    return instance;
  }

  public static Map < Long, Gene > selectBySetIds(long[] ids)
  throws Exception {
    return getInstance().getBySetIds(ids);
  }

  public static Map < String, Gene > select(final String[] ids)
  throws Exception {
    return getInstance().getByIds(ids);
  }

  public static Map < String, Gene > select(
    final String[] ids,
    final Connection con
  ) throws Exception {
    return getInstance().getByIds(ids, con);
  }

  public static List < Gene > selectBySetId(
    final long id,
    final Connection con
  ) throws Exception {
    return getInstance().getBySetId(id, con);
  }

  public static Map < Long, Gene > selectByCollectionIds(
    final long[] collectionIds
  ) throws Exception {
    return getInstance().getByCollectionIds(collectionIds);
  }


  public static Map < Long, Gene > selectByCollectionId(
    final long collection
  ) throws Exception {
    return getInstance().getByCollectionId(collection);
  }

  public static void insert(
    final IdSet set,
    final String[] ids,
    final Connection con,
    final User user
  ) throws Exception {
    getInstance().insertGenes(set, ids, con, user);
  }

  // ╔════════════════════════════════════════════════════════════════════╗ //
  // ║                                                                    ║ //
  // ║    Mockable Instance Methods                                       ║ //
  // ║                                                                    ║ //
  // ╚════════════════════════════════════════════════════════════════════╝ //

  public Map < Long, Gene > getBySetIds(final long[] ids)
  throws Exception {
    log.trace("GeneRepo#getBySetIds(long[])");
    return new BasicPreparedMapReadQuery <>(
      Genes.BY_ID_SETS,
      DbMan::connection,
      GeneUtil.getInstance()::parseId,
      GeneUtil.getInstance()::createGeneRow,
      QueryUtil.idSet(ids)
    ).execute().getValue();
  }

  public Map < String, Gene > getByIds(final String[] ids)
  throws Exception {
    log.trace("GeneRepo#getByIds(String[])");
    try (final var con = DbMan.connection()) {
      return getByIds(ids, con);
    }
  }

  public Map < String, Gene > getByIds(
    final String[] ids,
    final Connection con
  ) throws Exception {
    log.trace("GeneRepo#getByIds(String[], Connection)");
    return new BasicPreparedMapReadQuery <>(
      Genes.BY_ID_SETS,
      con,
      GeneUtil::getIdentifier,
      GeneUtil.getInstance()::createGeneRow,
      QueryUtil.stringSet(ids)
    ).execute().getValue();
  }

  public List < Gene > getBySetId(final long id, final Connection con)
  throws Exception {
    log.trace("GeneRepo#getBySetIds(long)");
    return new BasicPreparedListReadQuery <>(
      Genes.BY_ID_SETS,
      con,
      GeneUtil.getInstance()::createGeneRow,
      QueryUtil.singleId(id)
    ).execute().getValue();
  }


  public Map < Long, Gene > getByCollectionIds(
    final long[] collectionIds
  ) throws Exception {
    log.trace("GeneRepo#getByCollectionIds(long[])");
    return new BasicPreparedMapReadQuery<>(
      Genes.BY_COLLECTIONS,
      DbMan::connection,
      GeneUtil.getInstance()::parseId,
      GeneUtil::newGeneRow,
      QueryUtil.idSet(collectionIds)
    ).execute().getValue();
  }

  public Map < Long, Gene > getByCollectionId(
    final long collection
  ) throws Exception {
    log.trace("GeneRepo#getByCollectionId(long)");
    return new BasicPreparedMapReadQuery<>(
      Genes.BY_COLLECTION,
      DbMan::connection,
      GeneUtil.getInstance()::parseId,
      GeneUtil::newGeneRow,
      QueryUtil.singleId(collection)
    ).execute().getValue();
  }

  public void insertGenes(
    final IdSet set,
    final String[] ids,
    final Connection con,
    final User user
  ) throws Exception {
    log.trace("GeneRepo#insertGenes(IdSetRow, String[], Connection)");

    final var si = user.getUserId();
    final var ui = set.getCreatedBy();

    try (final var ps = con.prepareStatement(SQL.Insert.Osi.GENE)) {
      for (var id : ids) {
        ps.setLong(1, si);
        ps.setString(2, id);
        ps.setLong(3, ui);
        ps.addBatch();
      }

      ps.executeBatch();
    }
  }
}
