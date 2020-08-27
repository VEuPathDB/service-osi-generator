package org.veupathdb.service.osi.service.collections;

import java.sql.ResultSet;
import java.time.OffsetDateTime;
import java.util.*;

import org.veupathdb.service.osi.generated.model.CollectionResponse;
import org.veupathdb.service.osi.generated.model.CollectionResponseImpl;
import org.veupathdb.service.osi.model.db.IdSetCollection;
import org.veupathdb.service.osi.model.db.NewIdSetCollection;
import org.veupathdb.service.osi.model.db.raw.IdSetCollectionRow;
import org.veupathdb.service.osi.repo.Schema.Osi.Collections;

public class CollectionUtils
{
  private static final CollectionUtils instance = new CollectionUtils();

  public static CollectionUtils getInstance() {
    return instance;
  }

  public static IdSetCollectionRow newCollectionRow(
    final ResultSet rs,
    final NewIdSetCollection base
  ) throws Exception {
    return new IdSetCollectionRow(
      rs.getLong(Collections.COLLECTION_ID),
      base.getName(),
      base.getCreatedBy().getUserId(),
      rs.getObject(Collections.CREATED_ON, OffsetDateTime.class)
    );
  }

  public static IdSetCollectionRow newCollectionRow(final ResultSet rs)
  throws Exception {
    return new IdSetCollectionRow(
      rs.getLong(Collections.COLLECTION_ID),
      rs.getString(Collections.NAME),
      rs.getLong(Collections.CREATED_BY),
      rs.getObject(Collections.CREATED_ON, OffsetDateTime.class)
    );
  }

  public static long getCollectionId(final ResultSet rs) throws Exception {
    return rs.getLong(Collections.COLLECTION_ID);
  }

  public static CollectionResponse toCollectionResponse(IdSetCollectionRow col) {
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
