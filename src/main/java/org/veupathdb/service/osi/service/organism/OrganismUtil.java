package org.veupathdb.service.osi.service.organism;

import java.sql.ResultSet;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.regex.Pattern;

import org.veupathdb.service.osi.db.Schema.Osi.Organisms;
import org.veupathdb.service.osi.generated.model.OrganismPostRequest;
import org.veupathdb.service.osi.generated.model.OrganismResponse;
import org.veupathdb.service.osi.generated.model.OrganismResponseImpl;
import org.veupathdb.service.osi.model.db.NewOrganism;
import org.veupathdb.service.osi.model.db.Organism;
import org.veupathdb.service.osi.model.db.User;
import org.veupathdb.service.osi.util.InputValidationException;
import org.veupathdb.service.osi.util.Validation;

public class OrganismUtil
{
  @SuppressWarnings("FieldMayBeFinal")
  private static OrganismUtil instance = new OrganismUtil();

  public static final String
    TEMPLATE_PATTERN = "^.*%[0-9,+\\- $.]*d.*$";
  public static final Pattern
    TEMPLATE_REGEX   = Pattern.compile(TEMPLATE_PATTERN);

  private static String
    ERR_BAD_PATTERN = "The organism id template field must match the regex"
    + " pattern \"" + TEMPLATE_PATTERN + "\".";

  // ╔════════════════════════════════════════════════════════════════════╗ //
  // ║                                                                    ║ //
  // ║    Static Access Methods                                           ║ //
  // ║                                                                    ║ //
  // ╚════════════════════════════════════════════════════════════════════╝ //

  public static OrganismUtil getInstance() {
    return instance;
  }

  public static String validateTemplate(final String pattern) {
    return getInstance().enforceOrgPattern(pattern);
  }

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

  // ╔════════════════════════════════════════════════════════════════════╗ //
  // ║                                                                    ║ //
  // ║    Mockable Instance Methods                                       ║ //
  // ║                                                                    ║ //
  // ╚════════════════════════════════════════════════════════════════════╝ //

  static Organism newOrganismRow(final ResultSet rs) throws Exception {
    return new Organism(
      rs.getLong(Organisms.ORGANISM_ID),
      rs.getString(Organisms.NAME),
      rs.getString(Organisms.TEMPLATE),
      rs.getLong(Organisms.GENE_COUNTER_START),
      rs.getLong(Organisms.GENE_COUNTER_CURRENT),
      rs.getLong(Organisms.TRANSCRIPT_COUNTER_START),
      rs.getLong(Organisms.TRANSCRIPT_COUNTER_CURRENT),
      rs.getObject(Organisms.CREATED_ON, OffsetDateTime.class),
      rs.getObject(Organisms.LAST_MODIFIED, OffsetDateTime.class),
      rs.getLong(Organisms.CREATED_BY)
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
      rs.getObject(Organisms.CREATED_ON, OffsetDateTime.class),
      rs.getObject(Organisms.LAST_MODIFIED, OffsetDateTime.class),
      org.getCreatedBy().getUserId()
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

  public String enforceOrgPattern(final String template) {
    Validation.nonEmpty(template);

    if (!TEMPLATE_REGEX.matcher(template).matches())
      throw new InputValidationException(template, ERR_BAD_PATTERN);

    return template;
  }
}
