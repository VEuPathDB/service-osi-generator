package org.veupathdb.service.osi.service.genes;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.logging.log4j.Logger;
import org.veupathdb.lib.container.jaxrs.providers.LogProvider;
import org.veupathdb.service.osi.generated.model.IdSetPatchEntry;
import org.veupathdb.service.osi.generated.model.IdSetPostRequest;
import org.veupathdb.service.osi.generated.model.IdSetResponse;
import org.veupathdb.service.osi.model.RecordQuery;
import org.veupathdb.service.osi.model.db.User;
import org.veupathdb.service.osi.repo.IdSetRepo;
import org.veupathdb.service.osi.service.transcript.TranscriptRepo;
import org.veupathdb.service.osi.util.Errors;
import org.veupathdb.service.osi.util.Params;

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
  IdSetResponse create(IdSetPostRequest req, User user) {
    return getInstance().handleCreate(req, user);
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
    Params.stringOrInt(user)
      .ifLeft(query::setCreatedByName)
      .ifRight(query::setCreatedById);

    try {
      var sets = IdSetRepo.select(query)
        .stream()
        .map(IdSetUtils::setToRes)
        .collect(Collectors.toMap(
          IdSetResponse::getIdSetId,
          Function.identity()
        ));

      var genes = GeneRepo.selectBySetIds(sets.values()
        .stream()
        .mapToInt(IdSetResponse::getIdSetId)
        .toArray());

      var transcripts = TranscriptRepo.selectByGeneIds(genes.keySet()
        .stream()
        .mapToInt(Integer::intValue)
        .toArray())
        .stream()
        .map(t -> );

    } catch (Exception e) {
      throw Errors.wrapErr(e);
    }

    return null;
  }

  // ╔════════════════════════════════════════════════════════════════════╗ //
  // ║                                                                    ║ //
  // ║    ID Set Creation Handling                                        ║ //
  // ║                                                                    ║ //
  // ╚════════════════════════════════════════════════════════════════════╝ //

  public
  IdSetResponse handleCreate(IdSetPostRequest req, User user) {
    log.trace("IdSetService#handleCreate(IdSetPostRequest, User)");
    return null;
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
