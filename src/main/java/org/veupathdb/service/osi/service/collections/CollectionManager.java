package org.veupathdb.service.osi.service.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;

import org.veupathdb.service.osi.generated.model.IdSetCollection;
import org.veupathdb.service.osi.generated.model.IdSetCollectionImpl;
import org.veupathdb.service.osi.model.RecordQuery;
import org.veupathdb.service.osi.model.db.NewIdSetCollection;
import org.veupathdb.service.osi.model.db.User;
import org.veupathdb.service.osi.repo.*;
import org.veupathdb.service.osi.service.genes.GeneRepo;
import org.veupathdb.service.osi.service.genes.IdSetManager;
import org.veupathdb.service.osi.service.genes.TranscriptRepo;
import org.veupathdb.service.osi.service.organism.OrganismRepo;
import org.veupathdb.service.osi.service.user.UserRepo;

public class CollectionManager
{
  private static final String
    ERR_SEARCH_FAILED = "Failed to run collections search",
    ERR_LOOKUP_FAILED = "Failed to retrieve collection by id",
    ERR_INSERT_FAILED = "Failed to create new collection";

  public static IdSetCollection createCollection(
    final String name,
    final User   user
  ) {
    try {
      return toExternal(CollectionRepo.insert(new NewIdSetCollection(
        name,
        user
      )));
    } catch (Exception e) {
      throw new InternalServerErrorException(ERR_INSERT_FAILED, e);
    }
  }

  public static IdSetCollection getCollection(final int collectionId) {
    try {
      var collection = CollectionRepo.select(collectionId)
        .orElseThrow(NotFoundException::new);

      var users = UserRepo.selectUsersByCollection(collectionId);

      var idSets = IdSetRepo.selectIdSetsByCollection(
        collection,
        users,
        OrganismRepo.selectByCollectionId(collectionId, users));

      var genes = GeneRepo.selectGenesByCollection(
        collectionId, users, idSets);

      var out = toExternal(collection);

      IdSetManager.buildTrees(idSets, genes,
        TranscriptRepo.selectTranscriptsByCollection(collectionId, users, genes))
        .forEach(out.getIdSets()::add);

      return out;
    } catch (WebApplicationException e) {
      throw e;
    } catch (Exception e) {
      throw new InternalServerErrorException(ERR_LOOKUP_FAILED, e);
    }
  }

  public static List < IdSetCollection > findCollections(RecordQuery query) {
    try {
      var collections = CollectionRepo.select(query);

      var collIds = collections.stream()
        .mapToInt(org.veupathdb.service.osi.model.db.IdSetCollection::getCollectionId)
        .toArray();

      var users = UserRepo.selectUsersByCollections(collIds);

      var idSets = IdSetRepo.selectIdSetsByCollections(
        collIds,
        users,
        OrganismRepo.selectByCollectionIds(
          collIds,
          users
        ),
        collections
      );

      var genes = GeneRepo.selectGenesByCollections(
        collIds,
        users,
        idSets
      );

      var out = new ArrayList <IdSetCollection>(collIds.length);
      var cLookup = new HashMap < Integer, IdSetCollection > (collIds.length);

      collections.values()
        .stream()
        .map(CollectionManager::toExternal)
        .forEach(row -> {
          out.add(row);
          cLookup.put(row.getCollectionId(), row);
        });

      IdSetManager.buildTrees(
        idSets,
        genes,
        TranscriptRepo.selectTranscriptsByCollections(
          collIds,
          users,
          genes))
        .forEach(row -> cLookup.get(row.getCollectionId())
          .getIdSets()
          .add(row));

      return out;
    } catch (Exception e) {
      throw new InternalServerErrorException(ERR_SEARCH_FAILED, e);
    }
  }

  private static IdSetCollection toExternal(org.veupathdb.service.osi.model.db.IdSetCollection coll) {
    var out = new IdSetCollectionImpl();

    out.setCollectionId(coll.getCollectionId());
    out.setName(coll.getName());
    out.setCreated(coll.getCreatedOn().toEpochSecond());
    out.setCreatedBy(coll.getCreatedBy().getUserName());
    out.setIdSets(new ArrayList <>());

    return out;
  }
}
