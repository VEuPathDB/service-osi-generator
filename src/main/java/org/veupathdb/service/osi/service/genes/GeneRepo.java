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

  public static Map < Long, Gene > selectByIdSets(
    final long[] setIds,
    final Map < Long, IdSet > idSets
  ) throws Exception {
    return getInstance().getByIdSetIds(setIds, idSets);
  }

  public static Map < Long, Gene > selectByCollectionIds(
    final long[] collectionIds,
    final Map < Long, User > users,
    final Map < Long, IdSet > idSets
  ) throws Exception {
    return getInstance().getByCollectionIds(collectionIds, users, idSets);
  }

  public static Map < Long, Gene > selectByCollectionId(
    final long collection,
    final Map < Long, User > users,
    final Map < Long, IdSet > idSets
  ) throws Exception {
    return getInstance().getByCollectionId(collection, users, idSets);
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

  public Map < Long, Gene > getByIdSetIds(
    final long[] setIds,
    final Map < Long, IdSet > idSets
  ) throws Exception {
    log.trace("GeneRepo#getBySetIds(long[], Map)");
    return new BasicPreparedMapReadQuery<>(
      Genes.BY_ID_SETS,
      DbMan::connection,
      GeneUtil.getInstance()::parseId,
      rs -> GeneUtil.newGene(rs, idSets),
      QueryUtil.idSet(setIds)
    ).execute().getValue();
  }

  public Map < Long, Gene > getByCollectionIds(
    final long[] collectionIds,
    final Map < Long, User > users,
    final Map < Long, IdSet > idSets
  ) throws Exception {
    log.trace("GeneRepo#getByCollectionIds(long[], Map, Map)");
    return new BasicPreparedMapReadQuery<>(
      Genes.BY_COLLECTIONS,
      DbMan::connection,
      GeneUtil.getInstance()::parseId,
      rs -> GeneUtil.newGene(rs, users, idSets),
      QueryUtil.idSet(collectionIds)
    ).execute().getValue();
  }

  public Map < Long, Gene > getByCollectionId(
    final long collection,
    final Map < Long, User > users,
    final Map < Long, IdSet > idSets
  ) throws Exception {
    log.trace("GeneRepo#getByCollectionId(long, Map, Map)");
    return new BasicPreparedMapReadQuery<>(
      Genes.BY_COLLECTION,
      DbMan::connection,
      GeneUtil.getInstance()::parseId,
      rs -> GeneUtil.newGene(rs, users, idSets),
      QueryUtil.singleId(collection)
    ).execute().getValue();
  }
}
