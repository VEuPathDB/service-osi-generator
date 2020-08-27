package org.veupathdb.service.osi.service.collections;

import java.util.Collection;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Request;

import org.veupathdb.service.osi.generated.model.CollectionResponse;
import org.veupathdb.service.osi.repo.CollectionRepo;
import org.veupathdb.service.osi.repo.IdSetRepo;
import org.veupathdb.service.osi.service.user.UserService;
import org.veupathdb.service.osi.util.Errors;

public class CollectionService
{
  private static final CollectionService instance = new CollectionService();

  public static CollectionService getInstance() {
    return instance;
  }

  public static CollectionResponse lookupCollection(long id, Request req) {
    UserService.requireRequestUser(req);

    try {
      var coll = CollectionRepo.select(id).orElseThrow(NotFoundException::new);
      var sets = IdSetRepo.selectIdSetsByCollectionId(coll.getCollectionId());

      var res = CollectionUtils.toCollectionResponse(col);
    } catch (Exception e) {
      throw Errors.wrapErr(e);
    }
  }
}
