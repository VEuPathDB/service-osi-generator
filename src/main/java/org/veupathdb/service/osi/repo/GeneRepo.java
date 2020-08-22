package org.veupathdb.service.osi.repo;

import java.util.HashMap;
import java.util.Map;

import org.veupathdb.service.osi.model.db.Gene;
import org.veupathdb.service.osi.model.db.IdSet;
import org.veupathdb.service.osi.model.db.User;
import org.veupathdb.service.osi.service.DbMan;
import org.veupathdb.service.osi.service.GeneUtils;

public class GeneRepo
{
  public static Map < Integer, Gene > selectGenesByCollections(
    final int[] collectionIds,
    final Map < Integer, User > users,
    final Map < Integer, IdSet > idSets
  ) throws Exception {
    var out = new HashMap < Integer, Gene >();

    try (
      var cn = DbMan.connection();
      var ps = cn.prepareStatement(SQL.Select.Osi.Genes.BY_BULK_COLLECTION)
    ) {
      ps.setObject(1, collectionIds);

      try (var rs = ps.executeQuery()) {
        while (rs.next()) {
          var row = GeneUtils.newGene(rs, users, idSets);
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
      var ps = cn.prepareStatement(SQL.Select.Osi.Genes.BY_COLLECTION)
    ) {
      ps.setInt(1, collection);

      try (var rs = ps.executeQuery()) {
        while (rs.next()) {
          var row = GeneUtils.newGene(rs, users, idSets);
          out.put(row.getGeneId(), row);
        }
      }
    }

    return out;
  }
}
