package org.veupathdb.service.osi.service.organism;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Request;

import org.apache.logging.log4j.Logger;
import org.veupathdb.lib.container.jaxrs.errors.UnprocessableEntityException;
import org.veupathdb.lib.container.jaxrs.providers.LogProvider;
import org.veupathdb.service.osi.generated.model.OrganismPostRequest;
import org.veupathdb.service.osi.generated.model.OrganismPutRequest;
import org.veupathdb.service.osi.generated.model.OrganismResponse;
import org.veupathdb.service.osi.model.RecordQuery;
import org.veupathdb.service.osi.service.user.UserService;
import org.veupathdb.service.osi.util.*;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonList;
import static org.veupathdb.service.osi.service.organism.OrganismUtil.TEMPLATE_PATTERN;
import static org.veupathdb.service.osi.service.organism.OrganismUtil.TEMPLATE_REGEX;
import static org.veupathdb.service.osi.util.Params.or;
import static org.veupathdb.service.osi.util.Params.orStr;

public class OrganismService
{
  private static final int NAME_MIN_LEN = 3;

  @SuppressWarnings("FieldMayBeFinal")
  private static OrganismService instance = new OrganismService();

  private final Logger log = LogProvider.logger(getClass());

  // ╔════════════════════════════════════════════════════════════════════╗ //
  // ║                                                                    ║ //
  // ║    Static Access Methods                                           ║ //
  // ║                                                                    ║ //
  // ╚════════════════════════════════════════════════════════════════════╝ //

  public static OrganismService getInstance() {
    return instance;
  }

  /**
   * @see #handleSearch(String, Long, Long, String, Request)
   */
  public static List < OrganismResponse > search(
    final String organismName,
    final Long createdAfter,
    final Long createdBefore,
    final String createdBy,
    final Request req
  ) {
    return getInstance()
      .handleSearch(organismName, createdAfter, createdBefore, createdBy, req);
  }

  /**
   * @see #handleGet(String, Request)
   */
  public static OrganismResponse get(
    final String identifier,
    final Request req
  ) {
    return getInstance().handleGet(identifier, req);
  }

  /**
   * @see #handleCreate(OrganismPostRequest, Request)
   */
  public static long create(
    final OrganismPostRequest body,
    final Request req
  ) {
    return getInstance().handleCreate(body, req);
  }

  /**
   * @see #handleUpdate(String, OrganismPutRequest, Request)
   */
  public static void update(
    final String organismId,
    final OrganismPutRequest body,
    final Request req
  ) {
    getInstance().handleUpdate(organismId, body, req);
  }

  // ╔════════════════════════════════════════════════════════════════════╗ //
  // ║                                                                    ║ //
  // ║    Organism Search Handling                                        ║ //
  // ║                                                                    ║ //
  // ╚════════════════════════════════════════════════════════════════════╝ //

  public List < OrganismResponse > handleSearch(
    final String organismName,
    final Long createdAfter,
    final Long createdBefore,
    final String createdBy,
    final Request req
  ) {
    log.trace("OrganismService#handleSearch(String, Long, Long, String, Request)");

    try {
      UserService.requireUser(req);

      var query = new RecordQuery()
        .setStart(createdAfter)
        .setEnd(createdBefore)
        .setName(organismName)
        .setCreatedBy(createdBy);

      return OrganismRepo.search(Objects.requireNonNull(query))
        .stream()
        .map(OrganismUtil::org2OrgRes)
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

  public OrganismResponse handleGet(
    final String identifier,
    final Request req
  ) {
    log.trace("OrganismService#handleGet(String, Request)");

    UserService.requireUser(req);
    try {
      var either = Params.stringOrLong(identifier);

      return (
        either.isLeft()
          ? OrganismRepo.selectByName(either.getLeft())
          : OrganismRepo.selectById(either.getRight())
      )
        .map(OrganismUtil::org2OrgRes)
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

  /**
   * Attempts to create a new organism record.
   * <p>
   * Possible race conditions:
   * <ul>
   *   <li>
   *     Two requests come in simultaneously to create the same organism.
   *     The result of this will be whichever request hits the database second
   *     will throw a 500 error.  The only way to prevent this would be to lock
   *     the table before inserting and verify there are no conflicts before
   *     attempting the insert.
   *   </li>
   * </ul>
   *
   * @param body Organism creation request payload
   * @param req  Raw Jersey request object
   *
   * @return Primary key ID of the created organism record.
   */
  public long handleCreate(
    final OrganismPostRequest body,
    final Request req
  ) {
    log.trace("OrganismService#handleCreate(OrganismPostRequest, Request)");

    var user = UserService.requireUser(req);

    if (req == null)
      throw new BadRequestException();

    validateOrgCreateRequest(body);

    try {
      return OrganismRepo.insert(OrganismUtil.orgReq2NewOrg(body, user))
        .getId();
    } catch (Exception e) {
      throw Errors.wrapErr(e);
    }
  }

  private static final String
    VAL_BAD_TEMP = "Value must match the regex pattern \"" + TEMPLATE_PATTERN + "\".";

  void validateOrgCreateRequest(final OrganismPostRequest req) {
    log.trace("OrganismService#validateOrgCreateRequest(OrganismPostRequest)");

    if (req == null)
      throw new BadRequestException();

    var errs = new HashMap < String, List < String > >();

    RequestValidation.minLength(Field.Organism.NAME, req.getOrganismName(), NAME_MIN_LEN, errs);

    if (RequestValidation.notEmpty(Field.Organism.TEMPLATE, req.getTemplate(), errs))
      if (!TEMPLATE_REGEX.matcher(req.getTemplate()).matches())
        errs.put(Field.Organism.TEMPLATE, singletonList(VAL_BAD_TEMP));

    RequestValidation.atLeast(Field.Organism.GENE_INT_START, req.getGeneIntStart(), 1, errs);
    RequestValidation.atLeast(
      Field.Organism.TRAN_INT_START,
      req.getTranscriptIntStart(),
      req.getGeneIntStart(),
      errs
    );

    if (!errs.isEmpty())
      throw new UnprocessableEntityException(errs);
  }

  // ╔════════════════════════════════════════════════════════════════════╗ //
  // ║                                                                    ║ //
  // ║    Organism Update Handling                                        ║ //
  // ║                                                                    ║ //
  // ╚════════════════════════════════════════════════════════════════════╝ //

  /**
   * PUT request validation error messages.
   */
  private static final String
    VAL_NO_VALUES = "PUT requests to this endpoint require at least one value to overwrite.",
    VAL_ORG_UP    = "Cannot change the organism id counters after ids have been issued.";

  public void handleUpdate(
    final String identifier,
    final OrganismPutRequest body,
    final Request req
  ) {
    log.trace("OrganismService#handleUpdate(String, OrganismPutRequest, Request)");

    UserService.requireUser(req);

    prevalidatePutReq(body);

    Params.stringOrLong(identifier)
      .ifLeft(s -> handleStrPutRequest(s, body))
      .ifRight(i -> handleIntPutRequest(i, body));
  }

  private void handleIntPutRequest(
    final long orgId,
    final OrganismPutRequest req
  ) {
    log.trace("OrganismService#handleIntPutRequest(long, OrganismPutRequest)");

    try {
      if (req.getGeneIntStart() == null && req.getTranscriptIntStart() == null)
        handleOrgTemplateUpdate(orgId, req.getTemplate());
      else
        handleOrgFullUpdate(orgId, req);
    } catch (Exception e) {
      throw Errors.wrapErr(e);
    }
  }

  private void handleStrPutRequest(
    final String name,
    final OrganismPutRequest req
  ) {
    log.trace("OrganismService#handleStrPutRequest(String, OrganismPutRequest)");

    try {
      if (req.getGeneIntStart() == null && req.getTranscriptIntStart() == null)
        handleOrgTemplateUpdate(name, req.getTemplate());
      else
        handleOrgFullUpdate(name, req);
    } catch (Exception e) {
      throw Errors.wrapErr(e);
    }
  }

  private void handleOrgTemplateUpdate(
    final String name,
    final String template
  ) throws Exception {
    log.trace("OrganismService#handleOrgTemplateUpdate(String, String)");

    try (
      var load = OrganismUpdater.begin();
      var upd = load.loadOrganism(name, NotFoundException::new)
    ) {
      upd.update(template);
    }
  }

  private void handleOrgTemplateUpdate(
    final long id,
    final String template
  ) throws Exception {
    log.trace("OrganismService#handleOrgTemplateUpdate(long, String)");

    try (
      var load = OrganismUpdater.begin();
      var upd = load.loadOrganism(id, NotFoundException::new)
    ) {
      upd.update(template);
    }
  }

  private void handleOrgFullUpdate(
    final String name,
    final OrganismPutRequest req
  ) throws Exception {
    log.trace("OrganismService#handleOrgFullUpdate(String, OrganismPutRequest)");
    try (
      var load = OrganismUpdater.begin();
      var upd = load.loadOrganism(name, NotFoundException::new)
    ) {
      handleOrgFullUpdate(upd, req);
    }
  }

  private void handleOrgFullUpdate(long id, OrganismPutRequest req)
  throws Exception {
    log.trace("OrganismService#handleOrgFullUpdate(long, OrganismPutRequest)");

    try (
      var load = OrganismUpdater.begin();
      var upd = load.loadOrganism(id, NotFoundException::new)
    ) {
      handleOrgFullUpdate(upd, req);
    }
  }

  /**
   * Attempts to update one or more of an organism record's properties.
   *
   * @param up  Organism updater instance containing the record to update and
   *            various details about the record that will assist in validating
   *            then performing the update
   * @param req The user request body containing the properties the user wants
   *            to overwrite.
   *
   * @throws UnprocessableEntityException if the user is attempting to change
   * the gene or transcript counters on an organism record that has already been
   * used to generate new gene or transcript ids.
   *
   * @throws Exception if any other process failure occurs when attempting to
   * write the updates to the database.
   */
  private void handleOrgFullUpdate(
    final OrganismUpdater up,
    final OrganismPutRequest req
  ) throws Exception {
    log.trace("OrganismService#handleOrgFullUpdate(OrganismUpdater, OrganismPutRequest)");

    // If we cannot update the counters due to IDs already having been issued,
    // and the request is attempting to change those counter values, throw a
    // user error and stop here.
    if (!up.canUpdateCounters()) {
      var errs = new HashMap < String, List < String > >();
      if (req.getGeneIntStart() != null)
        errs.put(Field.Organism.GENE_INT_START, singletonList(VAL_ORG_UP));
      if (req.getTranscriptIntStart() != null)
        errs.put(Field.Organism.TRAN_INT_START, singletonList(VAL_ORG_UP));
      throw new UnprocessableEntityException(errs);
    }

    var org = up.getOrganism();

    up.update(
      orStr(req.getTemplate(), org.getTemplate()),
      or(req.getGeneIntStart(), org.getGeneCounterStart()),
      or(req.getTranscriptIntStart(), org.getTranscriptCounterStart())
    );
  }

  /**
   * Performs lightweight validations against the input that do not require
   * access to the database.
   * <p>
   * If the <code>template</code> property is set, ensures that it is not blank
   * and it validates against the template string regex.
   * <p>
   * If the <code>geneIntStart</code> property is set, ensures that it is
   * greater than 0.
   * <p>
   * If the <code>transcriptIntStart</code> property is set, ensures that it is
   * greater than 0.
   *
   * @param req Request to validate.
   *
   * @throws UnprocessableEntityException if the input fails one or more of the
   * validations described above.
   */
  private void prevalidatePutReq(final OrganismPutRequest req) {
    log.trace("OrganismService#prevalidatePutReq(OrganismPutRequest)");

    var errs        = new HashMap < String, List < String > >();
    var hasTemplate = req.getTemplate() != null;
    var hasGene     = req.getGeneIntStart() != null;
    var hasTrans    = req.getTranscriptIntStart() != null;

    // If we have a template in the input validate it
    if (hasTemplate) {
      log.debug("Validating new organism template.");

      RequestValidation.notEmpty(Field.Organism.TEMPLATE, req.getTemplate(), errs);
      if (!TEMPLATE_REGEX.matcher(req.getTemplate()).matches())
        errs.put(Field.Organism.TEMPLATE, singletonList(VAL_BAD_TEMP));
    }

    // if we have a gene-start value in the input, validate it
    if (hasGene) {
      log.debug("Validating new gene start value.");

      RequestValidation.atLeast(Field.Organism.GENE_INT_START, req.getGeneIntStart(), 1, errs);
    }

    // if we have a transcript-start value in the input, validate it
    if (hasTrans) {
      log.debug("Validating new transcript start value.");

      RequestValidation.atLeast(
        Field.Organism.TRAN_INT_START,
        req.getTranscriptIntStart(),
        1,
        errs
      );
    }

    // verify that _something_ was set in the input
    if (!hasTemplate && !hasGene && !hasTrans)
      throw new UnprocessableEntityException(singletonList(VAL_NO_VALUES), emptyMap());

    if (!errs.isEmpty())
      throw new UnprocessableEntityException(errs);
  }
}
