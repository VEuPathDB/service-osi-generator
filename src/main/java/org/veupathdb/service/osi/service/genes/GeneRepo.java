package org.veupathdb.service.osi.service.genes;

import java.util.HashMap;
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

  public static Map < Integer, Gene > selectGenesByCollections(
    final int[] collectionIds,
    final Map < Integer, User > users,
    final Map < Integer, IdSet > idSets
  ) throws Exception {
    var out = new HashMap < Integer, Gene >();

    try (
      var cn = DbMan.connection();
      var ps = cn.prepareStatement(Genes.BY_COLLECTIONS)
    ) {
      ps.setObject(1, collectionIds);

      try (var rs = ps.executeQuery()) {
        while (rs.next()) {
          var row = GeneUtil.newGene(rs, users, idSets);
          out.put(row.getGeneId(), row);
        }
      }
    }

    return out;
  }

  public static Map < Integer, Gene > selectGenesByCollection(
    final int collection,
    final Map < Integer, User > users,
    final Map < Integer, IdSet > idSets
  ) throws Exception {
    var out = new HashMap < Integer, Gene >();

    try (
      var cn = DbMan.connection();
      var ps = cn.prepareStatement(Genes.BY_COLLECTION)
    ) {
      ps.setInt(1, collection);

      try (var rs = ps.executeQuery()) {
        while (rs.next()) {
          var row = GeneUtil.newGene(rs, users, idSets);
          out.put(row.getGeneId(), row);
        }
      }
    }

    return out;
  }
}
