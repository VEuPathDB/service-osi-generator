package org.veupathdb.service.osi.service.idset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Request;

import org.apache.logging.log4j.Logger;
import org.veupathdb.lib.container.jaxrs.errors.UnprocessableEntityException;
import org.veupathdb.lib.container.jaxrs.providers.LogProvider;
import org.veupathdb.service.osi.generated.model.IdSetPatchEntry;
import org.veupathdb.service.osi.generated.model.IdSetPostRequest;
import org.veupathdb.service.osi.generated.model.IdSetResponse;
import org.veupathdb.service.osi.model.RecordQuery;
import org.veupathdb.service.osi.model.db.*;
import org.veupathdb.service.osi.service.DbMan;
import org.veupathdb.service.osi.service.genes.GeneRepo;
import org.veupathdb.service.osi.service.genes.GeneUtil;
import org.veupathdb.service.osi.service.organism.OrganismRepo;
import org.veupathdb.service.osi.service.transcript.TranscriptRepo;
import org.veupathdb.service.osi.service.transcript.TranscriptUtils;
import org.veupathdb.service.osi.service.user.UserService;
import org.veupathdb.service.osi.util.Errors;
import org.veupathdb.service.osi.util.Field;

import static java.util.Collections.singletonList;
import static java.util.Collections.singletonMap;

public class IdSetService
{
  @SuppressWarnings("FieldMayBeFinal")
  private static IdSetService instance = new IdSetService();

  private final Logger log = LogProvider.logger(getClass());

  // ╔════════════════════════════════════════════════════════════════════╗ //
  // ║                                                                    ║ //
  // ║    Static Access Methods                                           ║ //
  // ║                                                                    ║ //
  // ╚════════════════════════════════════════════════════════════════════╝ //

  /**
   * @see #handleSearch(Long, Long, String, Request)
   */
  public static List<IdSetResponse> search(
    final Long start,
    final Long end,
    final String user,
    final Request request
  ) {
    return getInstance().handleSearch(start, end, user, request);
  }

  /**
   * @see #handleCreate(IdSetPostRequest, Request)
   */
  public static IdSetResponse create(final IdSetPostRequest body, final Request req) {
    return getInstance().handleCreate(body, req);
  }

  /**
   * @see #handleGet(long, Request)
   */
  public static IdSetResponse get(final long id, final Request req) {
    return getInstance().handleGet(id, req);
  }

  /**
   * @see #handleUpdate(long, List, Request)
   */
  public static IdSetResponse update(
    final long idSetId,
    final List<IdSetPatchEntry> entries,
    final Request req
  ) {
    return getInstance().handleUpdate(idSetId, entries, req);
  }

  public static IdSetService getInstance() {
    return instance;
  }

  // ╔════════════════════════════════════════════════════════════════════╗ //
  // ║                                                                    ║ //
  // ║    ID Set Search Handling                                          ║ //
  // ║                                                                    ║ //
  // ╚════════════════════════════════════════════════════════════════════╝ //

  public List<IdSetResponse> handleSearch(
    final Long start,
    final Long end,
    final String user,
    final Request request
  ) {
    log.trace("IdSetService#handleSearch(Long, Long, String)");
    UserService.requireUser(request);

    var query = new RecordQuery()
      .setStart(start)
      .setEnd(end)
      .setCreatedBy(user);

    try {
      var sets = IdSetRepo.select(query)
        .stream()
        .map(IdSetUtil::setToRes)
        .collect(Collectors.toMap(
          IdSetResponse::getIdSetId,
          Function.identity()
        ));

      return populateIdSets(sets);
    } catch (Exception e) {
      throw Errors.wrapErr(e);
    }
  }

  // ╔════════════════════════════════════════════════════════════════════╗ //
  // ║                                                                    ║ //
  // ║    ID Set Creation Handling                                        ║ //
  // ║                                                                    ║ //
  // ╚════════════════════════════════════════════════════════════════════╝ //

  private static final String
    VAL_BAD_ORG_ID = "Invalid '" + Field.IdSet.ORGANISM_ID + "' value.",
    VAL_GEN_COUNT  = "Invalid '" + Field.IdSet.GENERATE_GENES + "' value.";

  /**
   * Attempts to create a new ID set from the given request.
   * <p>
   * Possible race conditions:
   * <ul>
   *   <li>
   *     Simultaneous creations on the same organism may behave weirdly?
   *     Maybe the organism table should be locked for the duration of the
   *     transaction to prevent conflicting ids?
   *   </li>
   * </ul>
   * <p>
   * Other possible issues:
   * <ul>
   *   <li>
   *     If the insert fails after the gene ids have been allocated, that block
   *     of ids may not be usable anymore.
   *     </li>
   * </ul>
   *
   * @param body Client request body
   * @param req  Raw servlet request
   *
   * @return the newly created ID Set in a client compatible format.
   *
   * @throws BadRequestException          if the input body is null.
   * @throws UnprocessableEntityException if the input body fails validation.
   * @throws InternalServerErrorException if any other errors occur while
   *                                      attempting to process this request.
   */
  public IdSetResponse handleCreate(final IdSetPostRequest body, final Request req) {
    log.trace("IdSetService#handleCreate(IdSetPostRequest, User)");

    if (body == null)
      throw new BadRequestException();

    final var user = UserService.requireUser(req);

    validateNewIdSetRequest(body);

    try {
      final var errs = new HashMap<String, List<String>>();
      final var oOrg = OrganismRepo.selectById(body.getOrganismId());

      if (oOrg.isEmpty())
        errs.put(Field.IdSet.ORGANISM_ID, singletonList(VAL_BAD_ORG_ID));
      if (!errs.isEmpty())
        throw new UnprocessableEntityException(errs);

      try (final var con = DbMan.connection()) {
        con.setAutoCommit(false);

        final var count = OrganismRepo.allocateGeneIds(
          body.getOrganismId(),
          body.getGenerateGenes(),
          con
        );

        final var set = IdSetRepo.insert(
          new NewIdSet(
            oOrg.orElseThrow(),
            oOrg.get().getTemplate(),
            count,
            body.getGenerateGenes(),
            user
          ),
          con
        );

        log.debug("Inserted new ID set record with id " + set.getId());

        final var geneIds = GeneUtil.expandGenes(
          oOrg.get(),
          count,
          body.getGenerateGenes()
        );

        GeneRepo.insert(set, geneIds, con, user);

        log.debug("Inserted {} new gene records", geneIds.length);

        final var out = IdSetUtil.setToRes(set);

        GeneRepo.selectBySetId(set.getId(), con)
          .stream()
          .map(GeneUtil::toEntry)
          .forEach(out.getGeneratedIds()::add);

        con.commit();

        return out;
      }
    } catch (Exception e) {
      throw Errors.wrapErr(e);
    }
  }

  private void validateNewIdSetRequest(final IdSetPostRequest req) {
    log.trace("IdSetService#validateNewIdSetRequest(IdSetPostRequest)");

    final var errs = new HashMap<String, List<String>>();

    if (req.getOrganismId() < 1)
      errs.put(Field.IdSet.ORGANISM_ID, singletonList(VAL_BAD_ORG_ID));
    if (req.getGenerateGenes() < 0)
      errs.put(Field.IdSet.GENERATE_GENES, singletonList(VAL_GEN_COUNT));

    if (!errs.isEmpty())
      throw new UnprocessableEntityException(errs);
  }

  // ╔════════════════════════════════════════════════════════════════════╗ //
  // ║                                                                    ║ //
  // ║    ID Set Lookup Handling                                          ║ //
  // ║                                                                    ║ //
  // ╚════════════════════════════════════════════════════════════════════╝ //

  public IdSetResponse handleGet(final long id, final Request req) {
    log.trace("IdSetService#handleGet(int)");
    UserService.requireUser(req);
    try {
      return populateIdSets(
        singletonMap(id, IdSetUtil.setToRes(IdSetRepo.select(id)
          .orElseThrow(NotFoundException::new)))).get(0);
    } catch (Exception e) {
      throw Errors.wrapErr(e);
    }
  }

  // ╔════════════════════════════════════════════════════════════════════╗ //
  // ║                                                                    ║ //
  // ║    ID Set Update Handling                                          ║ //
  // ║                                                                    ║ //
  // ╚════════════════════════════════════════════════════════════════════╝ //

  private static final String
    VAL_EMPTY = "Field cannot be blank.",
    VAL_LZERO = "Field cannot be less than 0.",
    VAL_ENTRY = "Invalid patch on record %d.";

  public IdSetResponse handleUpdate(
    final long idSetId,
    final List<IdSetPatchEntry> entries,
    final Request req
  ) {
    log.trace("IdSetService#handleSearch(List, User)");

    if (entries == null)
      throw new BadRequestException();

    // Retrieve request user.
    final var user = UserService.requireUser(req);

    // Perform light validation (checks not requiring a db connection)
    validatePatchEntries(entries);

    try {
      // Lookup id set by id, throw a 404 if it wasn't found.
      var idSet = IdSetRepo.select(idSetId)
        .orElseThrow(NotFoundException::new);

      var geneIds              = new String[entries.size()];
      var totalTranscriptCount = 0;

      // For each patch entry, record the gene ID into the geneIds array and
      // sum the total new transcript IDs to allocate.
      for (var i = 0; i < entries.size(); i++) {
        var entry = entries.get(i);

        geneIds[i] = entry.getGeneId();
        totalTranscriptCount += entry.getTranscripts();
      }

      // Select known genes from the array of patch entry gene ids.
      final var genes = GeneRepo.select(idSetId, geneIds);
      // Allocate the new transcript IDs.
      final var start = OrganismRepo.allocateTranscriptIds(
        idSet.getOrganismId(),
        totalTranscriptCount
      );

      // Insert any genes that were not previously known to the OSID service.
      doGeneInsert(idSet, entries, geneIds, genes, start, user);

      // Convert the ID set to an external type.
      var out = IdSetUtil.setToRes(idSet);

      populateIdSets(
        singletonMap(idSetId, out),
        genes.values().stream().collect(Collectors.toMap(
          Gene::getId,
          Function.identity()
        )),
        singletonList(out)
      );

      return out;
    } catch (Exception e) {
      throw Errors.wrapErr(e);
    }
  }

  /**
   * Insert new Gene and transcript IDs. (TODO: why is this one function?)
   *
   * New Gene IDs are those that appear in the {@code geneIds} array but do
   * not also appear in the {@code genes} map.
   *
   * Additionally, this method mutates the {@code genes} map by appending the
   * newly created genes to the map.
   *
   * @param idSet    Parent {@code IdSet} for the new gene and transcript ids.
   * @param entries  Patch request entries.
   * @param geneIds  Array of Gene IDs referenced in the patch request.
   * @param genes    Mapping of known gene IDs.
   * @param startPos Transcript ID counter start position.
   * @param user     User making the patch request.
   */
  void doGeneInsert(
    final IdSet idSet,
    final List<IdSetPatchEntry> entries,
    final String[] geneIds,
    final Map<String, Gene> genes,
    final long startPos,
    final User user
  ) throws Exception {
    log.trace("IdSetService#doGeneInsert(IdSet, List, String[] Map, long, User)");

    try (final var con = DbMan.connection()) {
      if (genes.size() != entries.size()) {
        log.debug("Unrecognized gene ids in patch.  Inserting new genes.");

        final var toInsert = entries.stream()
          .map(IdSetPatchEntry::getGeneId)
          .filter(Predicate.not(genes::containsKey))
          .toArray(String[]::new);

        GeneRepo.insert(idSet, toInsert, con, user);
        genes.clear();
        genes.putAll(GeneRepo.select(idSet.getId(), geneIds, con));

        if (genes.size() != entries.size())
          throw new IllegalStateException(); // TODO: error message
      }

      final var newTranscripts = new NewTranscript[entries.size()];

      var pos = startPos;
      for (var i = 0; i < entries.size(); i++) {
        final var entry = entries.get(i);

        newTranscripts[i] = new NewTranscript(
          genes.get(entry.getGeneId()),
          pos,
          entry.getTranscripts(),
          user
        );

        pos += entry.getTranscripts();
      }

      TranscriptRepo.insert(newTranscripts, con);
    }
  }

  void validatePatchEntries(final List<IdSetPatchEntry> entries) {
    log.trace("IdSetService#validatePatchEntries(List)");
    for (var i = 0; i < entries.size(); i++) {
      try {
        validatePatchEntry(entries.get(i));
      } catch (UnprocessableEntityException e) {
        throw new UnprocessableEntityException(
          singletonList(String.format(VAL_ENTRY, i)),
          e.getByKey()
        );
      }
    }
  }

  void validatePatchEntry(final IdSetPatchEntry entry) {
    log.trace("IdSetService#validatePatchEntry(IdSetPatchEntry)");
    final var errs = new HashMap<String, List<String>>();

    if (entry.getGeneId() == null || entry.getGeneId().isBlank())
      errs.put(Field.Gene.ID, singletonList(VAL_EMPTY));
    if (entry.getTranscripts() < 0)
      errs.put(Field.Gene.ID, singletonList(VAL_LZERO));

    if (!errs.isEmpty())
      throw new UnprocessableEntityException(errs);
  }

  // ╔════════════════════════════════════════════════════════════════════╗ //
  // ║                                                                    ║ //
  // ║    Common Utilities                                                ║ //
  // ║                                                                    ║ //
  // ╚════════════════════════════════════════════════════════════════════╝ //

  public List<IdSetResponse> populateIdSets(
    final Map<Long, IdSetResponse> sets
  ) throws Exception {
    log.trace("IdSetService#populateIdSets(Map)");

    final var ids = new long[sets.size()];
    final var out = new ArrayList<IdSetResponse>(sets.size());

    var i = 0;
    for (final var s : sets.values()) {
      ids[i++] = s.getIdSetId();
      out.add(s);
    }

    final var genes = GeneRepo.selectBySetIds(ids);

    return populateIdSets(sets, genes, out);
  }

  public List<IdSetResponse> populateIdSets(
    final Map<Long, IdSetResponse> sets,
    final Map<Long, Gene> genes,
    final List<IdSetResponse> out
  ) throws Exception {
    log.trace("IdSetService#populateIdSets(Map, Map, long[], List)");

    var outGenes = GeneUtil.toEntries(genes.values(), sets);

    TranscriptUtils.assign(
      TranscriptRepo.selectByGeneIds(genes.keySet()
        .stream()
        .mapToLong(Long::longValue)
        .toArray()),
      genes,
      outGenes
    );

    return out;
  }
}
