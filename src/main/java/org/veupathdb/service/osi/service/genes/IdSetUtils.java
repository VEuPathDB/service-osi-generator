package org.veupathdb.service.osi.service.genes;

import java.sql.ResultSet;
import java.time.OffsetDateTime;
import java.util.*;

import org.veupathdb.service.osi.generated.model.*;
import org.veupathdb.service.osi.model.db.IdSet;
import org.veupathdb.service.osi.model.db.IdSetCollection;
import org.veupathdb.service.osi.model.db.Organism;
import org.veupathdb.service.osi.model.db.User;
import org.veupathdb.service.osi.model.db.raw.GeneRow;
import org.veupathdb.service.osi.model.db.raw.IdSetRow;
import org.veupathdb.service.osi.model.db.raw.TranscriptRow;
import org.veupathdb.service.osi.repo.Schema.Osi.IdSets;

public class IdSetUtils
{
  public static long getId(final ResultSet rs) throws Exception {
    return rs.getLong(IdSets.ID_SET_ID);
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
    final Map < Long, User > users,
    final Map < Long, Organism > organisms,
    final Map < Long, IdSetCollection > collections
  ) throws Exception {
    return newIdSet(rs, users, organisms,
      collections.get(rs.getLong(IdSets.COLLECTION_ID)));
  }

  public static IdSet newIdSet(
    final ResultSet rs,
    final Map < Long, User > users,
    final Map < Long, Organism > organisms,
    final IdSetCollection collection
  ) throws Exception {
    return new IdSet(
      rs.getInt(IdSets.ID_SET_ID),
      collection,
      organisms.get(rs.getLong(IdSets.ORGANISM_ID)),
      rs.getString(IdSets.TEMPLATE),
      users.get(rs.getLong(IdSets.CREATED_BY)),
      rs.getLong(IdSets.COUNTER_START),
      rs.getInt(IdSets.NUM_ISSUED),
      rs.getObject(IdSets.CREATED_ON, OffsetDateTime.class)
    );
  }

  public static Map < Long, IdSetResponse > setsToResponses(
    final Collection < IdSetRow > rows,
    final Map < Long, CollectionResponse > colls
  ) {
    var out = new HashMap< Long, IdSetResponse >(rows.size());

    for (var s : rows) {
      var tmp = setToRes(s);
      out.put(s.getId(), tmp);
      colls.get(s.getCollectionId()).getIdSets().add(tmp);
    }

    return out;
  }

  public static IdSetResponse setToRes(IdSetRow set) {
    var out = new IdSetResponseImpl();

    out.setIdSetId(set.getId());
    out.setCollectionId(set.getCollectionId());
    out.setTemplate(set.getTemplate());
    out.setGeneIntStart(set.getCounterStart());
    out.setGeneratedGeneCount(set.getNumIssued());
    out.setCreatedBy(set.getCreatedBy());
    out.setCreatedOn(Date.from(set.getCreatedOn().toInstant()));
    out.setGeneratedIds(new ArrayList <>());

    return out;
  }
}
