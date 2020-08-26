package org.veupathdb.service.osi.service.idsets;

import java.sql.ResultSet;
import java.time.OffsetDateTime;
import java.util.Map;

import org.veupathdb.service.osi.model.db.IdSet;
import org.veupathdb.service.osi.model.db.IdSetCollection;
import org.veupathdb.service.osi.model.db.Organism;
import org.veupathdb.service.osi.model.db.User;
import org.veupathdb.service.osi.model.db.raw.IdSetRow;
import org.veupathdb.service.osi.repo.Schema.Osi.IdSets;

public class IdSetUtils
{
  public static IdSet rowToNode(
    final IdSetRow row,
    final Map < Integer, User > users,
    final Map < Integer, Organism > organisms,
    final Map < Integer, IdSetCollection > collections
  ) {
    return new IdSet(
      row.getIdSetId(),
      collections.get(row.getCollectionId()),
      organisms.get(row.getOrganismId()),
      row.getTemplate(),
      users.get(row.getCreatedBy()),
      row.getCounterStart(),
      row.getNumIssued(),
      row.getCreated()
    );
  }

  public static IdSetRow newIdSetRow(final ResultSet rs) throws Exception {
    return new IdSetRow(
      rs.getInt(IdSets.ID_SET_ID),
      rs.getInt(IdSets.COLLECTION_ID),
      rs.getInt(IdSets.ORGANISM_ID),
      rs.getString(IdSets.TEMPLATE),
      rs.getLong(IdSets.COUNTER_START),
      rs.getInt(IdSets.NUM_ISSUED),
      rs.getObject(IdSets.CREATED_ON, OffsetDateTime.class),
      rs.getInt(IdSets.CREATED_BY)
    );
  }

  public static IdSet newIdSet(
    final ResultSet rs,
    final Map < Integer, User > users,
    final Map < Integer, Organism > organisms,
    final Map < Integer, IdSetCollection > collections
  ) throws Exception {
    return newIdSet(rs, users, organisms,
      collections.get(rs.getInt(IdSets.COLLECTION_ID)));
  }

  public static IdSet newIdSet(
    final ResultSet rs,
    final Map < Integer, User > users,
    final Map < Integer, Organism > organisms,
    final IdSetCollection collection
  ) throws Exception {
    return new IdSet(
      rs.getInt(IdSets.ID_SET_ID),
      collection,
      organisms.get(rs.getInt(IdSets.ORGANISM_ID)),
      rs.getString(IdSets.TEMPLATE),
      users.get(rs.getInt(IdSets.CREATED_BY)),
      rs.getLong(IdSets.COUNTER_START),
      rs.getInt(IdSets.NUM_ISSUED),
      rs.getObject(IdSets.CREATED_ON, OffsetDateTime.class)
    );
  }
}
