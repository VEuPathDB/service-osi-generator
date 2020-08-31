package org.veupathdb.service.osi.service.collections;

import java.sql.ResultSet;
import java.time.OffsetDateTime;
import java.util.*;

import org.apache.logging.log4j.Logger;
import org.veupathdb.lib.container.jaxrs.providers.LogProvider;
import org.veupathdb.service.osi.db.Schema.Osi.Collections;
import org.veupathdb.service.osi.generated.model.CollectionResponse;
import org.veupathdb.service.osi.generated.model.CollectionResponseImpl;
import org.veupathdb.service.osi.model.db.IdSetCollection;
import org.veupathdb.service.osi.model.db.NewIdSetCollection;

public class CollectionUtil
{
  private static final CollectionUtil instance = new CollectionUtil();

  private final Logger log = LogProvider.logger(getClass());

  // ╔════════════════════════════════════════════════════════════════════╗ //
  // ║                                                                    ║ //
  // ║    Static Access Methods                                           ║ //
  // ║                                                                    ║ //
  // ╚════════════════════════════════════════════════════════════════════╝ //

  public static CollectionUtil getInstance() {
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
    log.trace("CollectionUtil#parseCollectionId(ResultSet)");
    return rs.getLong(Collections.COLLECTION_ID);
  }

  public IdSetCollection createCollectionRow(
    final ResultSet rs,
    final NewIdSetCollection base
  ) throws Exception {
    log.trace("CollectionUtil#parseCollectionId(ResultSet, NewIdSetCollection)");
    return new IdSetCollection(
      rs.getLong(Collections.COLLECTION_ID),
      base.getName(),
      rs.getObject(Collections.CREATED_ON, OffsetDateTime.class), base.getCreatedBy().getUserId()
    );
  }

  public IdSetCollection createCollectionRow(final ResultSet rs)
  throws Exception {
    log.trace("CollectionUtil#createCollectionRow(ResultSet)");
    return new IdSetCollection(
      rs.getLong(Collections.COLLECTION_ID),
      rs.getString(Collections.NAME),
      rs.getObject(Collections.CREATED_ON, OffsetDateTime.class), rs.getLong(Collections.CREATED_BY)
    );
  }

  public CollectionResponse collectionToResponse(final IdSetCollection col) {
    log.trace("CollectionUtil#collectionsToResponse(IdSetCollection)");
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
    log.trace("CollectionUtil#collectionsToResponse(Collection)");

    var out = new HashMap< Long, CollectionResponse >(rows.size());

    for (var r : rows)
      out.put(r.getId(), collectionToResponse(r));

    return out;
  }

}
