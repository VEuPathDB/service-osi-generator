package org.veupathdb.service.osi.service.collections;

import java.sql.ResultSet;
import java.time.OffsetDateTime;
import java.util.*;

import org.veupathdb.service.osi.generated.model.CollectionResponse;
import org.veupathdb.service.osi.generated.model.CollectionResponseImpl;
import org.veupathdb.service.osi.model.db.IdSetCollection;
import org.veupathdb.service.osi.model.db.NewIdSetCollection;
import org.veupathdb.service.osi.db.Schema.Osi.Collections;

public class CollectionUtils
{
  private static final CollectionUtils instance = new CollectionUtils();

  // ╔════════════════════════════════════════════════════════════════════╗ //
  // ║                                                                    ║ //
  // ║    Static Access Methods                                           ║ //
  // ║                                                                    ║ //
  // ╚════════════════════════════════════════════════════════════════════╝ //

  public static CollectionUtils getInstance() {
    return instance;
  }

  public static IdSetCollection newCollectionRow(
    final ResultSet rs,
    final NewIdSetCollection base
  ) throws Exception {
    return getInstance().createCollectionRow(rs, base);
  }

  public static IdSetCollection newCollectionRow(final ResultSet rs)
  throws Exception {
    return getInstance().createCollectionRow(rs);
  }

  public static long getCollectionId(final ResultSet rs) throws Exception {
    return getInstance().parseCollectionId(rs);
  }

  public static CollectionResponse toCollectionResponse(IdSetCollection col) {
    return getInstance().collectionToResponse(col);
  }

  public static Map < Long, CollectionResponse > toCollectionResponse(
    final Collection < IdSetCollection > rows
  ) {
    return getInstance().collectionsToResponse(rows);
  }


  // ╔════════════════════════════════════════════════════════════════════╗ //
  // ║                                                                    ║ //
  // ║    Mockable Instance Methods                                       ║ //
  // ║                                                                    ║ //
  // ╚════════════════════════════════════════════════════════════════════╝ //

  public long parseCollectionId(final ResultSet rs) throws Exception {
    return rs.getLong(Collections.COLLECTION_ID);
  }

  public IdSetCollection createCollectionRow(
    final ResultSet rs,
    final NewIdSetCollection base
  ) throws Exception {
    return new IdSetCollection(
      rs.getLong(Collections.COLLECTION_ID),
      base.getName(),
      base.getCreatedBy().getUserId(),
      rs.getObject(Collections.CREATED_ON, OffsetDateTime.class)
    );
  }

  public IdSetCollection createCollectionRow(final ResultSet rs)
  throws Exception {
    return new IdSetCollection(
      rs.getLong(Collections.COLLECTION_ID),
      rs.getString(Collections.NAME),
      rs.getLong(Collections.CREATED_BY),
      rs.getObject(Collections.CREATED_ON, OffsetDateTime.class)
    );
  }

  public CollectionResponse collectionToResponse(IdSetCollection col) {
    var out = new CollectionResponseImpl();

    out.setCollectionId(col.getId());
    out.setName(col.getName());
    out.setIdSets(new ArrayList <>());
    out.setCreatedOn(Date.from(col.getCreatedOn().toInstant()));
    out.setCreatedBy(col.getCreatedBy());

    return out;
  }

  public Map < Long, CollectionResponse > collectionsToResponse(
    final Collection < IdSetCollection > rows
  ) {
    var out = new HashMap< Long, CollectionResponse >(rows.size());

    for (var r : rows)
      out.put(r.getId(), collectionToResponse(r));

    return out;
  }

}
