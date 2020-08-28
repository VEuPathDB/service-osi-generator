package org.veupathdb.service.osi.service.organism;

import java.sql.ResultSet;
import java.time.OffsetDateTime;
import java.util.Date;

import org.veupathdb.service.osi.generated.model.OrganismPostRequest;
import org.veupathdb.service.osi.generated.model.OrganismResponse;
import org.veupathdb.service.osi.generated.model.OrganismResponseImpl;
import org.veupathdb.service.osi.model.db.NewOrganism;
import org.veupathdb.service.osi.model.db.Organism;
import org.veupathdb.service.osi.model.db.User;
import org.veupathdb.service.osi.repo.Schema.Osi.Organisms;

public class OrganismUtil
{
  public static long parseId(final ResultSet rs) throws Exception {
    return rs.getLong(Organisms.ORGANISM_ID);
  }

  public static long parseGeneCounter(final ResultSet rs) throws Exception {
    return rs.getLong(Organisms.GENE_COUNTER_CURRENT);
  }

  public static long parseTranscriptCounter(final ResultSet rs)
  throws Exception {
    return rs.getLong(Organisms.TRANSCRIPT_COUNTER_CURRENT);
  }

  static Organism newOrganismRow(final ResultSet rs) throws Exception {
    return new Organism(
      rs.getLong(Organisms.ORGANISM_ID),
      rs.getString(Organisms.NAME),
      rs.getString(Organisms.TEMPLATE),
      rs.getLong(Organisms.GENE_COUNTER_START),
      rs.getLong(Organisms.GENE_COUNTER_CURRENT),
      rs.getLong(Organisms.TRANSCRIPT_COUNTER_START),
      rs.getLong(Organisms.TRANSCRIPT_COUNTER_CURRENT),
      rs.getLong(Organisms.CREATED_BY),
      rs.getObject(Organisms.CREATED_ON, OffsetDateTime.class),
      rs.getObject(Organisms.LAST_MODIFIED, OffsetDateTime.class)
    );
  }

  static Organism newOrganismRow(
    final ResultSet rs,
    final NewOrganism org
  ) throws Exception {
    return new Organism(
      rs.getLong(Organisms.ORGANISM_ID),
      org.getName(),
      org.getTemplate(),
      org.getGeneCounterStart(),
      org.getGeneCounterStart(),
      org.getTranscriptCounterStart(),
      org.getTranscriptCounterStart(),
      org.getCreatedBy().getUserId(),
      rs.getObject(Organisms.CREATED_ON, OffsetDateTime.class),
      rs.getObject(Organisms.LAST_MODIFIED, OffsetDateTime.class)
    );
  }

  static OrganismResponse org2OrgRes(Organism org) {
    var tmp = new OrganismResponseImpl();

    tmp.setOrganismId(org.getId());
    tmp.setOrganismName(org.getName());
    tmp.setTemplate(org.getTemplate());
    tmp.setGeneIntStart(org.getGeneCounterStart());
    tmp.setGeneIntCurrent(org.getGeneCounterCurrent());
    tmp.setTranscriptIntStart(org.getTranscriptCounterStart());
    tmp.setTranscriptIntCurrent(org.getTranscriptCounterCurrent());
    tmp.setCreatedOn(Date.from(org.getCreatedOn().toInstant()));
    tmp.setCreatedBy(org.getCreatedBy());

    return tmp;
  }

  static NewOrganism orgReq2NewOrg(OrganismPostRequest req, User user) {
    return new NewOrganism(
      req.getOrganismName(),
      req.getTemplate(),
      req.getGeneIntStart(),
      req.getTranscriptIntStart(),
      user
    );
  }
}
