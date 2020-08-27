package org.veupathdb.service.osi.service.genes;

import java.util.Map;

import io.vulpine.lib.query.util.basic.BasicPreparedMapReadQuery;
import org.veupathdb.service.osi.model.db.Gene;
import org.veupathdb.service.osi.model.db.IdSet;
import org.veupathdb.service.osi.model.db.User;
import org.veupathdb.service.osi.model.db.raw.GeneRow;
import org.veupathdb.service.osi.repo.SQL.Select.Osi.Genes;
import org.veupathdb.service.osi.service.DbMan;
import org.veupathdb.service.osi.util.QueryUtil;

public class GeneRepo
{
  public static Map < Long, GeneRow > selectBySetIds(long[] ids)
  throws Exception {
    return new BasicPreparedMapReadQuery <>(
      Genes.BY_ID_SETS,
      DbMan::connection,
      GeneUtil.getInstance()::parseId,
      GeneUtil.getInstance()::createGeneRow,
      QueryUtil.idSet(ids)
    ).execute().getValue();
  }

  public static Map < Long, Gene > selectByIdSets(
    final long[] setIds,
    final Map < Long, IdSet > idSets
  ) throws Exception {
    return new BasicPreparedMapReadQuery<>(
      Genes.BY_ID_SETS,
      DbMan::connection,
      GeneUtil.getInstance()::parseId,
      rs -> GeneUtil.newGene(rs, idSets),
      QueryUtil.idSet(setIds)
    ).execute().getValue();
  }

  public static Map < Long, Gene > selectByCollectionIds(
    final long[] collectionIds,
    final Map < Long, User > users,
    final Map < Long, IdSet > idSets
  ) throws Exception {
    return new BasicPreparedMapReadQuery<>(
      Genes.BY_COLLECTIONS,
      DbMan::connection,
      GeneUtil.getInstance()::parseId,
      rs -> GeneUtil.newGene(rs, users, idSets),
      QueryUtil.idSet(collectionIds)
    ).execute().getValue();
  }

  public static Map < Long, Gene > selectByCollectionId(
    final long collection,
    final Map < Long, User > users,
    final Map < Long, IdSet > idSets
  ) throws Exception {
    return new BasicPreparedMapReadQuery<>(
      Genes.BY_COLLECTION,
      DbMan::connection,
      GeneUtil.getInstance()::parseId,
      rs -> GeneUtil.newGene(rs, users, idSets),
      QueryUtil.singleId(collection)
    ).execute().getValue();
  }
}
