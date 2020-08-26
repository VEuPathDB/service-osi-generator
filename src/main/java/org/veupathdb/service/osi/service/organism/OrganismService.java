package org.veupathdb.service.osi.service.organism;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;

import org.veupathdb.lib.container.jaxrs.errors.UnprocessableEntityException;
import org.veupathdb.service.osi.generated.model.OrganismPostRequest;
import org.veupathdb.service.osi.generated.model.OrganismPutRequest;
import org.veupathdb.service.osi.generated.model.OrganismResponse;
import org.veupathdb.service.osi.model.RecordQuery;
import org.veupathdb.service.osi.model.db.User;
import org.veupathdb.service.osi.util.Errors;
import org.veupathdb.service.osi.util.Field;
import org.veupathdb.service.osi.util.Params;

import static org.veupathdb.service.osi.util.Params.or;
import static org.veupathdb.service.osi.util.Params.orStr;

public class OrganismService
{
  private static final String
    TEMPLATE_PATTERN = "^.*%[0-9,+\\- $.]*d.*$";
  private static final Pattern
    TEMPLATE_REGEX = Pattern.compile(TEMPLATE_PATTERN);

  // ╔════════════════════════════════════════════════════════════════════╗ //
  // ║                                                                    ║ //
  // ║    Organism Search Handling                                        ║ //
  // ║                                                                    ║ //
  // ╚════════════════════════════════════════════════════════════════════╝ //

  public static List < OrganismResponse > handleSearch(RecordQuery query) {
    try {
      return OrganismRepo.findOrganisms(Objects.requireNonNull(query))
        .stream()
        .map(OrganismUtils::org2OrgRes)
        .collect(Collectors.toList());
    } catch (Exception e) {
      throw new InternalServerErrorException(e);
    }
  }

  // ╔════════════════════════════════════════════════════════════════════╗ //
  // ║                                                                    ║ //
  // ║    Organism Lookup Handling                                        ║ //
  // ║                                                                    ║ //
  // ╚════════════════════════════════════════════════════════════════════╝ //

  public static OrganismResponse handleGet(String identifier) {
    try {
      var either = Params.stringOrInt(identifier);

      return (either.isLeft()
        ? OrganismRepo.selectByName(either.getLeft())
        : OrganismRepo.selectById(either.getRight()))
        .map(OrganismUtils::org2OrgRes)
        .orElseThrow(NotFoundException::new);
    } catch (Exception e) {
      throw Errors.wrapErr(e);
    }
  }

  // ╔════════════════════════════════════════════════════════════════════╗ //
  // ║                                                                    ║ //
  // ║    Organism Creation Handling                                      ║ //
  // ║                                                                    ║ //
  // ╚════════════════════════════════════════════════════════════════════╝ //

  public static int handleCreate(OrganismPostRequest req, User user) {
    if (req == null)
      throw new BadRequestException();

    validateOrgCreateRequest(req);

    try {
      var org = OrganismRepo.insert(OrganismUtils.orgReq2NewOrg(
        Objects.requireNonNull(req), Objects.requireNonNull(user)));
      return org.getId();
    } catch (Exception e) {
      throw Errors.wrapErr(e);
    }
  }

  private static final String
    VAL_BLANK_NAME = "The organism name field cannot be blank.",
    VAL_BLANK_TEMP = "The organism id template field cannot be blank.",
    VAL_BAD_TEMP   = "The organism id template field must match the regex"
      + " pattern \"" + TEMPLATE_PATTERN + "\".";

  private static void validateOrgCreateRequest(OrganismPostRequest req) {
    if (req == null)
      throw new BadRequestException();

    var errs = new HashMap < String, List < String > >();

    if (req.getOrganismName() == null || req.getOrganismName().isBlank())
      errs.put(Field.Organism.NAME, Collections.singletonList(VAL_BLANK_NAME));

    if (req.getTemplate() == null || req.getTemplate().isBlank())
      errs.put(Field.Organism.TEMPLATE,
        Collections.singletonList(VAL_BLANK_TEMP));
    else if (!TEMPLATE_REGEX.matcher(req.getTemplate()).matches())
      errs.put(Field.Organism.TEMPLATE,
        Collections.singletonList(VAL_BAD_TEMP));

    if (!errs.isEmpty())
      throw new UnprocessableEntityException(errs);
  }

  // ╔════════════════════════════════════════════════════════════════════╗ //
  // ║                                                                    ║ //
  // ║    Organism Update Handling                                        ║ //
  // ║                                                                    ║ //
  // ╚════════════════════════════════════════════════════════════════════╝ //

  public static void handleUpdate(String identifier, OrganismPutRequest req) {
    prevalidatePutReq(req);

    Params.stringOrInt(identifier)
      .ifLeft(s -> handleStrPutRequest(s, req))
      .ifRight(i -> handleIntPutRequest(i, req));
  }

  private static void handleIntPutRequest(int orgId, OrganismPutRequest req) {
    try {
      if (req.getGeneIntStart() == null && req.getTranscriptIntStart() == null)
        handleOrgTemplateUpdate(orgId, req.getTemplate());
      else
        handleOrgFullUpdate(orgId, req);
    } catch (Exception e) {
      throw Errors.wrapErr(e);
    }
  }

  private static void handleStrPutRequest(String name, OrganismPutRequest req) {
    try {
      if (req.getGeneIntStart() == null && req.getTranscriptIntStart() == null)
        handleOrgTemplateUpdate(name, req.getTemplate());
      else
        handleOrgFullUpdate(name, req);
    } catch (Exception e) {
      throw Errors.wrapErr(e);
    }
  }

  private static void handleOrgTemplateUpdate(String name, String template)
  throws Exception {
    try (
      var load = OrganismUpdater.begin();
      var upd  = load.loadOrganism(name, NotFoundException::new)
    ) {
      upd.update(template);
    }
  }

  private static void handleOrgTemplateUpdate(int id, String template)
  throws Exception {
    try (
      var load = OrganismUpdater.begin();
      var upd  = load.loadOrganism(id, NotFoundException::new)
    ) {
      upd.update(template);
    }
  }

  private static final String
    VAL_ORG_UP = "Cannot organism id counters after ids have been issued.";

  private static void handleOrgFullUpdate(String name, OrganismPutRequest req)
  throws Exception {
    try (
      var load = OrganismUpdater.begin();
      var upd  = load.loadOrganism(name, NotFoundException::new)
    ) {
      handleOrgFullUpdate(upd, req);
    }
  }

  private static void handleOrgFullUpdate(int id, OrganismPutRequest req)
  throws Exception {
    try (
      var load = OrganismUpdater.begin();
      var upd  = load.loadOrganism(id, NotFoundException::new)
    ) {
      handleOrgFullUpdate(upd, req);
    }
  }

  private static void handleOrgFullUpdate(
    OrganismUpdater up,
    OrganismPutRequest req
  ) throws Exception {
    if (!up.canUpdateCounters()) {
      var errs = new HashMap< String, List < String > >();
      if (req.getGeneIntStart() != null)
        errs.put(Field.Organism.GENE_INT_START, Collections.singletonList(
          VAL_ORG_UP));
      if (req.getTranscriptIntStart() != null)
        errs.put(Field.Organism.TRAN_INT_START, Collections.singletonList(
          VAL_ORG_UP));
      throw new UnprocessableEntityException(errs);
    }

    var org = up.getOrganism();

    up.update(
      orStr(req.getTemplate(), org.getTemplate()),
      or(req.getGeneIntStart(), org.getGeneCounterStart()),
      or(req.getTranscriptIntStart(), org.getTranscriptCounterStart())
    );
  }

  private static final String
    VAL_EMPTY_PUT = "Organism put requests must contain at least one value.";

  private static void prevalidatePutReq(OrganismPutRequest req) {
    if (req.getTemplate() == null
      && req.getGeneIntStart() == null
      && req.getTranscriptIntStart() == null
    )
      throw new UnprocessableEntityException(
        Collections.singletonList(VAL_EMPTY_PUT),
        Collections.emptyMap());
  }
}
