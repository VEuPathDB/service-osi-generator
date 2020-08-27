package org.veupathdb.service.osi.service.genes;

import java.util.Map;

import io.vulpine.lib.query.util.basic.BasicPreparedMapReadQuery;
import org.apache.logging.log4j.Logger;
import org.veupathdb.lib.container.jaxrs.providers.LogProvider;
import org.veupathdb.service.osi.model.db.Gene;
import org.veupathdb.service.osi.model.db.IdSet;
import org.veupathdb.service.osi.model.db.User;
import org.veupathdb.service.osi.model.db.raw.GeneRow;
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

}
