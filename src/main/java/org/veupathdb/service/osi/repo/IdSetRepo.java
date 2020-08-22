package org.veupathdb.service.osi.repo;

import java.sql.Types;
import java.util.*;

import org.veupathdb.service.osi.model.RecordQuery;
import org.veupathdb.service.osi.model.db.IdSet;
import org.veupathdb.service.osi.model.db.IdSetCollection;
import org.veupathdb.service.osi.model.db.Organism;
import org.veupathdb.service.osi.model.db.User;
import org.veupathdb.service.osi.model.db.raw.IdSetRow;
import org.veupathdb.service.osi.service.DbMan;
import org.veupathdb.service.osi.service.IdSetUtils;

public class IdSetRepo
{
  public static List < IdSetRow > findIdSets(RecordQuery query)
  throws Exception {
    var out = new ArrayList < IdSetRow >();

    try (
      var cn = DbMan.connection();
      var ps = cn.prepareStatement(SQL.Select.Osi.IdSets.BY_QUERY)
    ) {
      if (query.hasStart())
        ps.setObject(1, query.getStart());
      else
        ps.setNull(1, Types.TIMESTAMP_WITH_TIMEZONE);

      if (query.hasEnd())
        ps.setObject(2, query.getEnd());
      else
        ps.setNull(2, Types.TIMESTAMP_WITH_TIMEZONE);

      if (query.hasCreatedById())
        ps.setInt(3, query.getCreatedById());
      else
        ps.setNull(3, Types.INTEGER);

      if (query.hasCreatedByName())
        ps.setString(4, query.getCreatedByName());
      else
        ps.setNull(4, Types.VARCHAR);

      try (var rs = ps.executeQuery()) {
        while (rs.next()) {
          out.add(IdSetUtils.newIdSetRow(rs));
        }
      }
    }

    return out;
  }

  public static Map < Integer, IdSet > selectIdSetsByCollections(
    final int[] collectionIds,
    final Map < Integer, User > users,
    final Map < Integer, Organism > organisms,
    final Map < Integer, IdSetCollection > collections
  ) throws Exception {
    var out = new HashMap < Integer, IdSet >(collectionIds.length);

    try (
      var cn = DbMan.connection();
      var ps = cn.prepareStatement(SQL.Select.Osi.IdSets.BY_COLLECTIONS)
    ) {
      ps.setObject(1, collectionIds);

      try (var rs = ps.executeQuery()) {
        while (rs.next()) {
          var row = IdSetUtils.newIdSet(rs, users, organisms, collections);
          out.put(row.getIdSetId(), row);
        }
      }
    }

    return out;
  }

  public static Map < Integer, IdSet > selectIdSetsByCollection(
    final IdSetCollection collection,
    final Map < Integer, User > users,
    final Map < Integer, Organism > organisms
  ) throws Exception {
    var out = new HashMap < Integer, IdSet >();

    try (
      var cn = DbMan.connection();
      var ps = cn.prepareStatement(SQL.Select.Osi.IdSets.BY_COLLECTIONS)
    ) {
      ps.setInt(1, collection.getCollectionId());

      try (var rs = ps.executeQuery()) {
        while (rs.next()) {
          var row = IdSetUtils.newIdSet(rs, users, organisms, collection);
          out.put(row.getIdSetId(), row);
        }
      }
    }

    return out;
  }
}
