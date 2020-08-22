package org.veupathdb.service.osi.service;

import java.sql.ResultSet;
import java.time.OffsetDateTime;
import java.util.Map;

import org.veupathdb.service.osi.model.db.IdSetCollection;
import org.veupathdb.service.osi.model.db.User;
import org.veupathdb.service.osi.model.db.raw.IdSetCollectionRow;
import org.veupathdb.service.osi.repo.Schema.Osi.Collections;

public class CollectionUtils
{
  public static IdSetCollectionRow newCollectionRow(final ResultSet rs)
  throws Exception {
    return new IdSetCollectionRow(
      rs.getInt(Collections.COLLECTION_ID),
      rs.getString(Collections.NAME),
      rs.getInt(Collections.CREATED_BY),
      rs.getObject(Collections.CREATED_ON, OffsetDateTime.class)
    );
  }

  public static IdSetCollection newCollection(
    final IdSetCollectionRow row,
    final Map < Integer, User > users
  ) {
    return new IdSetCollection(
      row.getCollectionId(),
      row.getName(),
      users.get(row.getCreatedBy()),
      row.getCreatedOn()
    );
  }

  public static IdSetCollection newCollection(ResultSet rs) throws Exception {
    return new IdSetCollection(
      rs.getInt(Collections.COLLECTION_ID),
      rs.getString(Collections.NAME),
      UserUtils.newUser(rs),
      rs.getObject(Collections.CREATED_ON, OffsetDateTime.class)
    );
  }
}
