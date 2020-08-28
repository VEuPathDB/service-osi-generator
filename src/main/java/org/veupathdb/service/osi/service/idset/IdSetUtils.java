package org.veupathdb.service.osi.service.idset;

import java.sql.ResultSet;
import java.time.OffsetDateTime;
import java.util.*;

import org.veupathdb.service.osi.generated.model.*;
import org.veupathdb.service.osi.model.db.NewIdSet;
import org.veupathdb.service.osi.model.db.raw.IdSetRow;
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

  public static IdSetRow newIdSetRow(
    final ResultSet rs,
    final NewIdSet  ids
  ) throws Exception {
    return new IdSetRow(
      rs.getInt(IdSets.ID_SET_ID),
      ids.getCollection().getId(),
      ids.getOrganism().getId(),
      ids.getOrganism().getTemplate(),
      ids.getCounterStart(),
      ids.getNumIssued(),
      rs.getObject(IdSets.CREATED_ON, OffsetDateTime.class),
      ids.getCreatedBy().getUserId()
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
