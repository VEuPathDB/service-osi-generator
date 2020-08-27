package org.veupathdb.service.osi.service.collections;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Request;

import org.veupathdb.service.osi.generated.model.CollectionResponse;
import org.veupathdb.service.osi.repo.CollectionRepo;
import org.veupathdb.service.osi.repo.IdSetRepo;
import org.veupathdb.service.osi.service.genes.GeneRepo;
import org.veupathdb.service.osi.service.genes.GeneUtil;
import org.veupathdb.service.osi.service.genes.IdSetUtils;
import org.veupathdb.service.osi.service.transcript.TranscriptRepo;
import org.veupathdb.service.osi.service.transcript.TranscriptUtils;
import org.veupathdb.service.osi.service.user.UserService;
import org.veupathdb.service.osi.util.Errors;

import static java.util.Collections.singletonMap;

public class CollectionService
{
  private static final CollectionService instance = new CollectionService();

  public static CollectionService getInstance() {
    return instance;
  }

  public static CollectionResponse lookupCollection(long id, Request req) {
    UserService.requireRequestUser(req);

    try {
      var res = CollectionUtils.toCollectionResponse(CollectionRepo.select(id)
        .orElseThrow(NotFoundException::new));
      var genes = GeneRepo.selectByCollectionId(id);
      var outGenes = GeneUtil.toEntries(
        genes.values(),
        IdSetUtils.setsToResponses(
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
}
