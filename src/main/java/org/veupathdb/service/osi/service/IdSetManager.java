package org.veupathdb.service.osi.service;

import java.util.*;

import javax.ws.rs.InternalServerErrorException;

import org.apache.logging.log4j.Logger;
import org.veupathdb.lib.container.jaxrs.providers.LogProvider;
import org.veupathdb.service.osi.generated.model.GeneratedTranscriptEntry;
import org.veupathdb.service.osi.generated.model.GeneratedTranscriptEntryImpl;
import org.veupathdb.service.osi.generated.model.IdSetResponse;
import org.veupathdb.service.osi.generated.model.IdSetResponseImpl;
import org.veupathdb.service.osi.model.RecordQuery;
import org.veupathdb.service.osi.model.db.Gene;
import org.veupathdb.service.osi.model.db.IdSet;
import org.veupathdb.service.osi.model.db.Transcript;
import org.veupathdb.service.osi.repo.IdSetRepo;

public class IdSetManager
{
  private static final Logger log = LogProvider.logger(IdSetManager.class);

  public static List < IdSetResponse > findIdSets(RecordQuery query) {
    try {
      var rows = IdSetRepo.findIdSets(query);

      var orgs = new ArrayList<Integer>();
      var 

    } catch (Exception e) {
      throw new InternalServerErrorException(e);
    }
  }

  public static IdSetResponse buildTree(
    IdSet idSet,
    Map < Integer, Gene > genes,
    Collection < Transcript > transcripts
  ) {
    log.trace("IdSetManager#buildTree");

    var out = new IdSetResponseImpl();
    out.setIdSetId(idSet.getIdSetId());
    out.setCollectionId(idSet.getCollection().getCollectionId());
    out.setTemplate(idSet.getTemplate());
    out.setGeneIntStart(idSet.getCounterStart());
    out.setGeneratedGeneCount(idSet.getNumIssued());
    out.setCreated(idSet.getCreatedOn().toEpochSecond());
    out.setCreatedBy(idSet.getCreatedBy().getUserName());

    var entries = new ArrayList<GeneratedTranscriptEntry>(genes.size());
    var lookup = new HashMap<Gene, GeneratedTranscriptEntry>(genes.size());

    genes.values()
      .stream()
      .filter(g -> g.getIdSet().getIdSetId() == idSet.getIdSetId())
      .forEach(g -> {
        var row = newEntry(g);
        lookup.put(g, row);
        entries.add(row);
      });

    transcripts.stream()
      .filter(t -> lookup.containsKey(t.getGene()))
      .forEach(t -> {
        var entry = lookup.get(t.getGene());
        entry.getProteins().addAll(TranscriptUtils.expandProteinIds(t));
        entry.getTranscripts().addAll(TranscriptUtils.expandTranscriptIds(t));
      });

    out.setGeneratedIds(entries);

    return out;
  }

  public static List < IdSetResponse > buildTrees(
    Map < Integer, IdSet > idSets,
    Map < Integer, Gene > genes,
    Collection < Transcript > transcripts
  ) {
    var out = new ArrayList<IdSetResponse>(idSets.size());
    var sLookup = new HashMap < Integer, IdSetResponse >(idSets.size());

    for (var s : idSets.values()) {
      var row = new IdSetResponseImpl();
      row.setIdSetId(s.getIdSetId());
      row.setCollectionId(s.getCollection().getCollectionId());
      row.setTemplate(s.getTemplate());
      row.setGeneIntStart(s.getCounterStart());
      row.setGeneratedGeneCount(s.getNumIssued());
      row.setCreated(s.getCreatedOn().toEpochSecond());
      row.setCreatedBy(s.getCreatedBy().getUserName());
      row.setGeneratedIds(new ArrayList <>());

      out.add(row);
      sLookup.put(s.getIdSetId(), row);
    }

    var gLookup = new HashMap<Gene, GeneratedTranscriptEntry>(genes.size());

    for (var g : genes.values()) {
      var row = newEntry(g);

      gLookup.put(g, row);
      sLookup.get(g.getIdSet().getIdSetId())
        .getGeneratedIds()
        .add(row);
    }

    for (Transcript t : transcripts) {
      var entry = gLookup.get(t.getGene());
      entry.getProteins().addAll(TranscriptUtils.expandProteinIds(t));
      entry.getTranscripts().addAll(TranscriptUtils.expandTranscriptIds(t));
    }

    return out;
  }

  private static GeneratedTranscriptEntry newEntry(Gene g) {
    var out = new GeneratedTranscriptEntryImpl();
    out.setGeneId(g.getIdentifier());
    out.setProteins(new ArrayList <>());
    out.setTranscripts(new ArrayList <>());
    return out;
  }
}
