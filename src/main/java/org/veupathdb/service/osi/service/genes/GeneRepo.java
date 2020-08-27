package org.veupathdb.service.osi.service.genes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.vulpine.lib.query.util.basic.BasicPreparedListReadQuery;
import io.vulpine.lib.query.util.basic.BasicPreparedMapReadQuery;
import org.veupathdb.service.osi.model.db.Gene;
import org.veupathdb.service.osi.model.db.IdSet;
import org.veupathdb.service.osi.model.db.User;
import org.veupathdb.service.osi.model.db.raw.GeneRow;
import org.veupathdb.service.osi.repo.SQL;
import org.veupathdb.service.osi.repo.Schema;
import org.veupathdb.service.osi.service.DbMan;

public class GeneRepo
{
  public static Map < Integer, GeneRow > selectBySetIds(int[] ids)
  throws Exception {
    return new BasicPreparedMapReadQuery <>(
      SQL.Select.Osi.Genes.BY_ID_SETS,
      DbMan.connection(),
      rs -> rs.getInt(Schema.Osi.Transcripts.GENE_ID),
      GeneUtils::newGeneRow,
      ps -> ps.setObject(1, ids)
    ).execute().getValue();
  }

  public static Map < Integer, Gene > selectGenesByIdSet(IdSet set)
  throws Exception {
    var out = new HashMap < Integer, Gene >();

    try (
      var cn = DbMan.connection();
      var ps = cn.prepareStatement(SQL.Select.Osi.Genes.BY_ID_SET)
    ) {
      ps.setInt(1, set.getId());

      try (var rs = ps.executeQuery()) {
        while (rs.next()) {
          var row = GeneUtils.newGene(rs, set);
          out.put(row.getGeneId(), row);
        }
      }
    }

    return out;
  }

  public static Map < Integer, Gene > selectGenesByIdSets(
    final int[] setIds,
    final Map < Integer, IdSet > idSets
  ) throws Exception {
    var out = new HashMap < Integer, Gene >();

    try (
      var cn = DbMan.connection();
      var ps = cn.prepareStatement(SQL.Select.Osi.Genes.BY_ID_SETS)
    ) {
      ps.setObject(1, setIds);

      try (var rs = ps.executeQuery()) {
        while (rs.next()) {
          var row = GeneUtils.newGene(rs, idSets);
          out.put(row.getGeneId(), row);
        }
      }
    }

    return out;
  }

  public static Map < Integer, Gene > selectGenesByCollections(
    final int[] collectionIds,
    final Map < Integer, User > users,
    final Map < Integer, IdSet > idSets
  ) throws Exception {
    var out = new HashMap < Integer, Gene >();

    try (
      var cn = DbMan.connection();
      var ps = cn.prepareStatement(SQL.Select.Osi.Genes.BY_COLLECTIONS)
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
