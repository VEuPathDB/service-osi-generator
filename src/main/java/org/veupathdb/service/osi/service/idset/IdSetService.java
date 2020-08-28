package org.veupathdb.service.osi.service.idset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Request;

import org.apache.logging.log4j.Logger;
import org.veupathdb.lib.container.jaxrs.errors.UnprocessableEntityException;
import org.veupathdb.lib.container.jaxrs.providers.LogProvider;
import org.veupathdb.service.osi.generated.model.IdSetPatchEntry;
import org.veupathdb.service.osi.generated.model.IdSetPostRequest;
import org.veupathdb.service.osi.generated.model.IdSetResponse;
import org.veupathdb.service.osi.model.RecordQuery;
import org.veupathdb.service.osi.model.db.NewIdSet;
import org.veupathdb.service.osi.model.db.User;
import org.veupathdb.service.osi.repo.CollectionRepo;
import org.veupathdb.service.osi.repo.IdSetRepo;
import org.veupathdb.service.osi.service.DbMan;
import org.veupathdb.service.osi.service.genes.GeneRepo;
import org.veupathdb.service.osi.service.genes.GeneUtil;
import org.veupathdb.service.osi.service.organism.OrganismRepo;
import org.veupathdb.service.osi.service.transcript.TranscriptRepo;
import org.veupathdb.service.osi.service.transcript.TranscriptUtils;
import org.veupathdb.service.osi.service.user.UserService;
import org.veupathdb.service.osi.util.Errors;
import org.veupathdb.service.osi.util.Field;
import org.veupathdb.service.osi.util.Params;

import static java.util.Collections.singletonList;

public class IdSetService
{
  private static final IdSetService instance = new IdSetService();

  private final Logger log = LogProvider.logger(getClass());

  // ╔════════════════════════════════════════════════════════════════════╗ //
  // ║                                                                    ║ //
  // ║    Static Access Methods                                           ║ //
  // ║                                                                    ║ //
  // ╚════════════════════════════════════════════════════════════════════╝ //

  public static
  List < IdSetResponse > search(Long start, Long end, String user) {
    return getInstance().handleSearch(start, end, user);
  }

  public static
  IdSetResponse create(IdSetPostRequest body, Request req) {
    return getInstance().handleCreate(body, req);
  }

  public static
  IdSetResponse get(int id) {
    return getInstance().handleGet(id);
  }

  public static
  IdSetResponse update(
    final List < IdSetPatchEntry > entries,
    final User user
  ) {
    return getInstance().handleUpdate(entries, user);
  }

  public static
  IdSetService getInstance() {
    return instance;
  }

  // ╔════════════════════════════════════════════════════════════════════╗ //
  // ║                                                                    ║ //
  // ║    ID Set Search Handling                                          ║ //
  // ║                                                                    ║ //
  // ╚════════════════════════════════════════════════════════════════════╝ //

  public
  List < IdSetResponse > handleSearch(Long start, Long end, String user) {
    log.trace("IdSetService#handleSearch(Long, Long, String)");

    var query = new RecordQuery()
      .setStart(Params.nullableTimestamp(start))
      .setEnd(Params.nullableTimestamp(end));
    Params.stringOrLong(user)
      .ifLeft(query::setCreatedByName)
      .ifRight(query::setCreatedById);

    try {
      var sets = IdSetRepo.select(query)
        .stream()
        .map(IdSetUtils::setToRes)
        .collect(Collectors.toMap(
          IdSetResponse::getIdSetId,
          Function.identity()));

      var ids = new long[sets.size()];
      var out = new ArrayList <IdSetResponse>(sets.size());

      var i = 0;
      for (var s : sets.values()) {
        ids[i] = s.getIdSetId();
        out.set(i++, s);
      }

      var genes = GeneRepo.selectBySetIds(ids);
      var outGenes = GeneUtil.toEntries(genes.values(), sets);

      TranscriptUtils.assign(
        TranscriptRepo.selectByGeneIds(genes.keySet()
          .stream()
          .mapToLong(Long::longValue)
          .toArray()),
        genes,
        outGenes);

      return out;
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
    VAL_BAD_COL_ID = "Invalid '" + Field.IdSet.COLLECTION_ID + "' value.",
    VAL_GEN_COUNT  = "Invalid '" + Field.IdSet.GENERATE_GENES + "' value.";

  public IdSetResponse handleCreate(
    final IdSetPostRequest body,
    final Request          req
    ) {
    log.trace("IdSetService#handleCreate(IdSetPostRequest, User)");

    if (body == null)
      throw new BadRequestException();

    final var user = UserService.requireRequestUser(req);

    validateNewIdSetRequest(body);

    try {
      final var errs = new HashMap< String, List < String > >();
      final var oOrg = OrganismRepo.selectById(body.getOrganismId());
      final var oCol = CollectionRepo.select(body.getCollectionId());

      if (oOrg.isEmpty())
        errs.put(Field.IdSet.ORGANISM_ID, singletonList(VAL_BAD_ORG_ID));
      if (oCol.isEmpty())
        errs.put(Field.IdSet.COLLECTION_ID, singletonList(VAL_BAD_COL_ID));
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
            oCol.get(),
            oOrg.get(),
            oOrg.get().getTemplate(),
            user,
            count,
            body.getGenerateGenes()
          ),
          con);

        final var geneIds = GeneUtil.expandGenes(
          oOrg.get(),
          count,
          body.getGenerateGenes()
        );

        GeneRepo.insert(set, geneIds, con);

        final var out = IdSetUtils.setToRes(set);

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

  final void validateNewIdSetRequest(final IdSetPostRequest req) {
    log.trace("IdSetService#validateNewIdSetRequest(IdSetPostRequest)");

    final var errs = new HashMap< String, List < String > >();

    if (req.getOrganismId() < 1)
      errs.put(Field.IdSet.ORGANISM_ID, singletonList(VAL_BAD_ORG_ID));
    if (req.getCollectionId() < 1)
      errs.put(Field.IdSet.COLLECTION_ID, singletonList(VAL_BAD_COL_ID));
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

  public
  IdSetResponse handleGet(int id) {
    log.trace("IdSetService#handleGet(int)");
    return null;
  }

  // ╔════════════════════════════════════════════════════════════════════╗ //
  // ║                                                                    ║ //
  // ║    ID Set Update Handling                                          ║ //
  // ║                                                                    ║ //
  // ╚════════════════════════════════════════════════════════════════════╝ //

  public
  IdSetResponse handleUpdate(List < IdSetPatchEntry > entries, User user) {
    log.trace("IdSetService#handleSearch(List, User)");
    return null;
  }
}
