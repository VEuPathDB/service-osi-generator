package org.veupathdb.service.osi.service.idset;

import java.sql.ResultSet;
import java.time.OffsetDateTime;
import java.util.*;

import org.veupathdb.service.osi.db.Schema.Osi.IdSets;
import org.veupathdb.service.osi.generated.model.IdSetResponse;
import org.veupathdb.service.osi.generated.model.IdSetResponseImpl;
import org.veupathdb.service.osi.model.db.IdSet;
import org.veupathdb.service.osi.model.db.NewIdSet;

public class IdSetUtil
{
  @SuppressWarnings("FieldMayBeFinal")
  private static IdSetUtil instance = new IdSetUtil();

  // ╔════════════════════════════════════════════════════════════════════╗ //
  // ║                                                                    ║ //
  // ║    Static Access Methods                                           ║ //
  // ║                                                                    ║ //
  // ╚════════════════════════════════════════════════════════════════════╝ //

  public static IdSetUtil getInstance() {
    return instance;
  }

  public static long getId(final ResultSet rs) throws Exception {
    return getInstance().parseId(rs);
  }

  public static IdSet newIdSet(final ResultSet rs) throws Exception {
    return getInstance().parseIdSet(rs);
  }

  public static IdSet newIdSet(final ResultSet rs, final NewIdSet  ids)
  throws Exception {
    return getInstance().parseIdSet(rs, ids);
  }

  public static IdSetResponse setToRes(final IdSet set) {
    return getInstance().setToResponse(set);
  }

  // ╔════════════════════════════════════════════════════════════════════╗ //
  // ║                                                                    ║ //
  // ║    Mockable Instance Methods                                       ║ //
  // ║                                                                    ║ //
  // ╚════════════════════════════════════════════════════════════════════╝ //

  public long parseId(final ResultSet rs) throws Exception {
    return rs.getLong(IdSets.ID_SET_ID);
  }

  public IdSet parseIdSet(final ResultSet rs) throws Exception {
    return new IdSet(
      rs.getLong(IdSets.ID_SET_ID),
      rs.getLong(IdSets.ORGANISM_ID),
      rs.getString(IdSets.TEMPLATE),
      rs.getLong(IdSets.COUNTER_START),
      rs.getInt(IdSets.NUM_ISSUED),
      rs.getObject(IdSets.CREATED_ON, OffsetDateTime.class),
      rs.getLong(IdSets.CREATED_BY)
    );
  }
  public IdSet parseIdSet(
    final ResultSet rs,
    final NewIdSet  ids
  ) throws Exception {
    return new IdSet(
      rs.getInt(IdSets.ID_SET_ID),
      ids.getOrganism().getId(),
      ids.getOrganism().getTemplate(),
      ids.getCounterStart(),
      ids.getNumIssued(),
      rs.getObject(IdSets.CREATED_ON, OffsetDateTime.class),
      ids.getCreatedBy().getUserId()
    );
  }

  public IdSetResponse setToResponse(final IdSet set) {
    var out = new IdSetResponseImpl();

    out.setIdSetId(set.getId());
    out.setOrganismId(set.getOrganismId());
    out.setTemplate(set.getTemplate());
    out.setGeneIntStart(set.getCounterStart());
    out.setGeneratedGeneCount(set.getNumIssued());
    out.setCreatedBy(set.getCreatedBy());
    out.setCreatedOn(Date.from(set.getCreatedOn().toInstant()));
    out.setGeneratedIds(new ArrayList <>());

    return out;
  }
}
