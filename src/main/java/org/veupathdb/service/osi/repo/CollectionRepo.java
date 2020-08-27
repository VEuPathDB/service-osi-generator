package org.veupathdb.service.osi.repo;

import java.sql.Types;
import java.time.OffsetDateTime;
import java.util.*;

import io.vulpine.lib.query.util.basic.BasicPreparedMapReadQuery;
import io.vulpine.lib.query.util.basic.BasicPreparedReadQuery;
import org.veupathdb.service.osi.model.RecordQuery;
import org.veupathdb.service.osi.model.db.IdSetCollection;
import org.veupathdb.service.osi.model.db.NewIdSetCollection;
import org.veupathdb.service.osi.model.db.raw.IdSetCollectionRow;
import org.veupathdb.service.osi.repo.Schema.Osi.Collections;
import org.veupathdb.service.osi.service.collections.CollectionUtils;
import org.veupathdb.service.osi.service.DbMan;
import org.veupathdb.service.osi.util.QueryUtil;

public class CollectionRepo
{
  public static IdSetCollection insert(NewIdSetCollection coll)
  throws Exception {
    return new BasicPreparedReadQuery <>(
      SQL.Insert.Osi.COLLECTION,
      DbMan::connection,
      QueryUtil.must(rs -> new IdSetCollection(
        rs.getInt(Collections.COLLECTION_ID),
        rs.getObject(Collections.CREATED_ON, OffsetDateTime.class),
        coll
      )),
      ps -> {
        ps.setString(1, coll.getName());
        ps.setLong(2, coll.getCreatedBy().getUserId());
      }
    ).execute().getValue();
  }

  public static Map < Long, IdSetCollection > select(long[] ids)
  throws Exception {
    return new BasicPreparedMapReadQuery <>(
      SQL.Select.Osi.Collections.BY_IDS,
      DbMan::connection,
      CollectionUtils::getCollectionId,
      CollectionUtils::newCollection,
      QueryUtil.idSet(ids)
    ).execute().getValue();
  }

  public static Optional < IdSetCollection > select(long id)
  throws Exception {
    return new BasicPreparedReadQuery<>(
      SQL.Select.Osi.Collections.BY_ID,
      DbMan::connection,
      QueryUtil.option(CollectionUtils::newCollection),
      QueryUtil.singleId(id)
    ).execute().getValue();
  }

  public static List < IdSetCollectionRow > select(
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
        ps.setLong(4, query.getCreatedById());
      else
        ps.setNull(4, Types.INTEGER);

      if (query.hasCreatedByName())
        ps.setString(5, query.getCreatedByName());
      else
        ps.setNull(5, Types.VARCHAR);

      try (var rs = ps.executeQuery()) {
        var out = new ArrayList <IdSetCollectionRow>();

        while (rs.next())
          out.add(CollectionUtils.newCollectionRow(rs));

        return out;
      }
    }
  }
}
