package org.veupathdb.service.osi.service.collections;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Request;

import org.veupathdb.service.osi.generated.model.CollectionResponse;
import org.veupathdb.service.osi.generated.model.IdSetCollectionPostRequest;
import org.veupathdb.service.osi.model.RecordQuery;
import org.veupathdb.service.osi.model.db.NewIdSetCollection;
import org.veupathdb.service.osi.service.idset.IdSetRepo;
import org.veupathdb.service.osi.service.genes.GeneRepo;
import org.veupathdb.service.osi.service.genes.GeneUtil;
import org.veupathdb.service.osi.service.idset.IdSetUtil;
import org.veupathdb.service.osi.service.transcript.TranscriptRepo;
import org.veupathdb.service.osi.service.transcript.TranscriptUtils;
import org.veupathdb.service.osi.service.user.UserService;
import org.veupathdb.service.osi.util.Errors;
import org.veupathdb.service.osi.util.Field;
import org.veupathdb.service.osi.util.Params;
import org.veupathdb.service.osi.util.RequestValidation;

import static java.util.Collections.singletonMap;

public class CollectionService
{
  private static final CollectionService instance = new CollectionService();

  // ╔════════════════════════════════════════════════════════════════════╗ //
  // ║                                                                    ║ //
  // ║    Static Access Methods                                           ║ //
  // ║                                                                    ║ //
  // ╚════════════════════════════════════════════════════════════════════╝ //

  public static CollectionService getInstance() {
    return instance;
  }

  public static CollectionResponse lookup(final long id, final Request req) {
    return getInstance().getCollection(id, req);
  }

  public static List < CollectionResponse > search(
    final String  orgName,
    final Long    start,
    final Long    end,
    final String  user,
    final Request req
  ) {
    return getInstance().findCollections(orgName, start, end, user, req);
  }

  public static CollectionResponse create(
    final IdSetCollectionPostRequest body,
    final Request res
  ) {
    return getInstance().newCollection(body, res);
  }

  // ╔════════════════════════════════════════════════════════════════════╗ //
  // ║                                                                    ║ //
  // ║    Mockable Instance Methods                                       ║ //
  // ║                                                                    ║ //
  // ╚════════════════════════════════════════════════════════════════════╝ //

  public CollectionResponse getCollection(final long id, final Request req) {
    UserService.requireRequestUser(req);

    try {
      var res = CollectionUtil.toCollectionResponse(CollectionRepo.select(id)
        .orElseThrow(NotFoundException::new));
      var genes = GeneRepo.selectByCollectionId(id);
      var outGenes = GeneUtil.toEntries(
        genes.values(),
        IdSetUtil.setsToResponses(
          IdSetRepo.selectByCollectionId(id),
          singletonMap(id, res)));

      TranscriptUtils.assign(
        TranscriptRepo.selectByCollectionId(id),
        genes,
        outGenes
      );

      return res;
    } catch (Exception e) {
      throw Errors.wrapErr(e);
    }
  }

  public List < CollectionResponse > findCollections(
    final String  orgName,
    final Long    start,
    final Long    end,
    final String  user,
    final Request req
  ) {
    UserService.requireRequestUser(req);

    var query = new RecordQuery()
      .setName(orgName)
      .setStart(Params.nullableTimestamp(start))
      .setEnd(Params.nullableTimestamp(end));

    Params.stringOrLong(user)
      .ifLeft(query::setCreatedByName)
      .ifRight(query::setCreatedById);

    try {
      var res = CollectionUtil.toCollectionResponse(
        CollectionRepo.select(query));
      var out = new ArrayList< CollectionResponse >(res.size());
      var ids = new long[res.size()];

      int i = 0;
      for (var c : res.values()) {
        out.set(i, c);
        ids[i++] = c.getCollectionId();
      }

      var genes    = GeneRepo.selectByCollectionIds(ids);
      var outGenes = GeneUtil.toEntries(
        genes.values(),
        IdSetUtil.setsToResponses(
          IdSetRepo.selectByCollectionIds(ids),
          res));

      TranscriptUtils.assign(
        TranscriptRepo.selectByCollectionIds(ids),
        genes,
        outGenes
      );

      return out;
    } catch (Exception e) {
      throw Errors.wrapErr(e);
    }
  }

  public CollectionResponse newCollection(
    final IdSetCollectionPostRequest body,
    final Request req
  ) {
    if (body == null)
      throw new BadRequestException();

    final var user = UserService.requireRequestUser(req);

    RequestValidation.notEmpty(Field.Collection.NAME, body.getName());

    try {
      return CollectionUtil.toCollectionResponse(
        CollectionRepo.insert(new NewIdSetCollection(
          body.getName(),
          user)));
    } catch (Exception e) {
      throw Errors.wrapErr(e);
    }
  }
}
