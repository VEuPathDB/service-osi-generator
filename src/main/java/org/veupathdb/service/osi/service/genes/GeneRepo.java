package org.veupathdb.service.osi.service.genes;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.vulpine.lib.query.util.basic.*;
import org.apache.logging.log4j.Logger;
import org.veupathdb.lib.container.jaxrs.providers.LogProvider;
import org.veupathdb.service.osi.model.db.raw.GeneRow;
import org.veupathdb.service.osi.model.db.raw.IdSetRow;
import org.veupathdb.service.osi.repo.SQL;
import org.veupathdb.service.osi.repo.SQL.Select.Osi.Genes;
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

  public static Map < Long, GeneRow > selectBySetIds(long[] ids)
  throws Exception {
    return getInstance().getBySetIds(ids);
  }

  public static List < GeneRow > selectBySetId(
    final long id,
    final Connection con
  ) throws Exception {
    return getInstance().getBySetId(id, con);
  }

  public static Map < Long, GeneRow > selectByCollectionIds(
    final long[] collectionIds
  ) throws Exception {
    return getInstance().getByCollectionIds(collectionIds);
  }


  public static Map < Long, GeneRow > selectByCollectionId(
    final long collection
  ) throws Exception {
    return getInstance().getByCollectionId(collection);
  }

  public static void insert(
    final IdSetRow set,
    final String[] ids,
    final Connection con
  ) throws Exception {
    getInstance().insertGenes(set, ids, con);
  }

  // ╔════════════════════════════════════════════════════════════════════╗ //
  // ║                                                                    ║ //
  // ║    Mockable Instance Methods                                       ║ //
  // ║                                                                    ║ //
  // ╚════════════════════════════════════════════════════════════════════╝ //

  public Map < Long, GeneRow > getBySetIds(long[] ids)
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

  public List < GeneRow > getBySetId(final long id, final Connection con)
  throws Exception {
    log.trace("GeneRepo#getBySetIds(long)");
    return new BasicPreparedListReadQuery <>(
      Genes.BY_ID_SETS,
      con,
      GeneUtil.getInstance()::createGeneRow,
      QueryUtil.singleId(id)
    ).execute().getValue();
  }


  public Map < Long, GeneRow > getByCollectionIds(
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

  public Map < Long, GeneRow > getByCollectionId(
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
    final IdSetRow set,
    final String[] ids,
    final Connection con
  ) throws Exception {
    log.trace("GeneRepo#insertGenes(IdSetRow, String[], Connection)");

    final var si = set.getId();
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
