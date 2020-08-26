package org.veupathdb.service.osi.repo;

import java.sql.Types;
import java.time.OffsetDateTime;
import java.util.*;

import org.veupathdb.service.osi.model.RecordQuery;
import org.veupathdb.service.osi.model.db.IdSetCollection;
import org.veupathdb.service.osi.model.db.NewIdSetCollection;
import org.veupathdb.service.osi.repo.Schema.Osi.Collections;
import org.veupathdb.service.osi.service.collections.CollectionUtils;
import org.veupathdb.service.osi.service.DbMan;

public class CollectionRepo
{
  public static IdSetCollection insertCollection(NewIdSetCollection coll)
  throws Exception {
    try (
      var cn = DbMan.connection();
      var ps = cn.prepareStatement(SQL.Insert.Osi.COLLECTION)
    ) {
      ps.setString(1, coll.getName());
      ps.setInt(2, coll.getCreatedBy().getUserId());

      try (var rs = ps.executeQuery()) {
        rs.next();

        return new IdSetCollection(
          rs.getInt(Collections.COLLECTION_ID),
          rs.getObject(Collections.CREATED_ON, OffsetDateTime.class),
          coll);
      }
    }
  }

  public static Map < Integer, IdSetCollection > selectCollections(int[] ids)
  throws Exception {
    var out = new HashMap<Integer, IdSetCollection>(ids.length);

    try (
      var cn = DbMan.connection();
      var ps = cn.prepareStatement(SQL.Select.Osi.Collections.BY_IDS)
    ) {
      ps.setObject(1, ids);

      try (var rs = ps.executeQuery()) {
        while (rs.next()) {
          var row = CollectionUtils.newCollection(rs);
          out.put(row.getCollectionId(), row);
        }
      }
    }

    return out;
  }

  public static Optional < IdSetCollection > selectCollection(int id)
  throws Exception {
    try (
      var cn = DbMan.connection();
      var ps = cn.prepareStatement(SQL.Select.Osi.Collections.BY_ID)
    ) {
      ps.setInt(1, id);

      try (var rs = ps.executeQuery()) {
        if (!rs.next())
          return Optional.empty();

        return Optional.of(CollectionUtils.newCollection(rs));
      }
    }
  }

  public static List < IdSetCollection > findCollectionRows(
    final RecordQuery query
  ) throws Exception {
    try (
      var cn = DbMan.connection();
      var ps = cn.prepareStatement(SQL.Select.Osi.Collections.BY_QUERY)
    ) {
      if (query.hasName())
        ps.setString(1, query.getName());
      else
        ps.setNull(1, Types.VARCHAR);

      if (query.hasStart())
        ps.setObject(2, query.getStart());
      else
        ps.setNull(2, Types.TIMESTAMP_WITH_TIMEZONE);

      if (query.hasEnd())
        ps.setObject(3, query.getEnd());
      else
        ps.setNull(3, Types.TIMESTAMP_WITH_TIMEZONE);

      if (query.hasCreatedById())
        ps.setInt(4, query.getCreatedById());
      else
        ps.setNull(4, Types.INTEGER);

      if (query.hasCreatedByName())
        ps.setString(5, query.getCreatedByName());
      else
        ps.setNull(5, Types.VARCHAR);

      try (var rs = ps.executeQuery()) {
        var out = new ArrayList <IdSetCollection>();

        while (rs.next())
          out.add(CollectionUtils.newCollection(rs));

        return out;
      }
    }
  }
}
