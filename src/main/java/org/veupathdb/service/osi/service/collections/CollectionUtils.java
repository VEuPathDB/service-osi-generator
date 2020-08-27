package org.veupathdb.service.osi.service.collections;

import java.sql.ResultSet;
import java.time.OffsetDateTime;
import java.util.*;

import org.veupathdb.service.osi.generated.model.CollectionResponse;
import org.veupathdb.service.osi.generated.model.CollectionResponseImpl;
import org.veupathdb.service.osi.model.db.IdSetCollection;
import org.veupathdb.service.osi.model.db.User;
import org.veupathdb.service.osi.model.db.raw.IdSetCollectionRow;
import org.veupathdb.service.osi.repo.Schema.Osi.Collections;
import org.veupathdb.service.osi.service.user.UserManager;

public class CollectionUtils
{
  private static final CollectionUtils instance = new CollectionUtils();

  public static CollectionUtils getInstance() {
    return instance;
  }

  public static IdSetCollectionRow newCollectionRow(final ResultSet rs)
  throws Exception {
    return new IdSetCollectionRow(
      rs.getInt(Collections.COLLECTION_ID),
      rs.getString(Collections.NAME),
      rs.getInt(Collections.CREATED_BY),
      rs.getObject(Collections.CREATED_ON, OffsetDateTime.class)
    );
  }

  public static IdSetCollection newCollection(ResultSet rs) throws Exception {
    return new IdSetCollection(
      rs.getInt(Collections.COLLECTION_ID),
      rs.getString(Collections.NAME),
      UserManager.getInstance()
        .getOrCreateUser(rs.getInt(Collections.CREATED_BY), rs),
      rs.getObject(Collections.CREATED_ON, OffsetDateTime.class)
    );
  }

  public static long getCollectionId(final ResultSet rs) throws Exception {
    return rs.getLong(Collections.COLLECTION_ID);
  }

  public static CollectionResponse toCollectionResponse(IdSetCollection col) {
    return getInstance().collectionToResponse(col);
  }

  public static Map < Long, CollectionResponse > toCollectionResponse(
    final Collection < IdSetCollectionRow > rows
  ) {
    return getInstance().collectionsToResponse(rows);
  }

  public CollectionResponse collectionToResponse(IdSetCollection col) {
    var out = new CollectionResponseImpl();

    out.setCollectionId(col.getCollectionId());
    out.setName(col.getName());
    out.setIdSets(new ArrayList <>());
    out.setCreatedOn(Date.from(col.getCreatedOn().toInstant()));
    out.setCreatedBy(col.getCreatedBy().getUserId());

    return out;
  }

  public CollectionResponse collectionToResponse(IdSetCollectionRow col) {
    var out = new CollectionResponseImpl();

    out.setCollectionId(col.getCollectionId());
    out.setName(col.getName());
    out.setIdSets(new ArrayList <>());
    out.setCreatedOn(Date.from(col.getCreatedOn().toInstant()));
    out.setCreatedBy(col.getCreatedBy());

    return out;
  }

  public Map < Long, CollectionResponse > collectionsToResponse(
    final Collection < IdSetCollectionRow > rows
  ) {
    var out = new HashMap< Long, CollectionResponse >(rows.size());

    for (var r : rows)
      out.put(r.getCollectionId(), collectionToResponse(r));

    return out;
  }
}
