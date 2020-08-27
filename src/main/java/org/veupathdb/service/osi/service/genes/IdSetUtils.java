package org.veupathdb.service.osi.service.genes;

import java.sql.ResultSet;
import java.time.OffsetDateTime;
import java.util.*;

import org.veupathdb.service.osi.generated.model.GeneratedTranscriptEntry;
import org.veupathdb.service.osi.generated.model.GeneratedTranscriptEntryImpl;
import org.veupathdb.service.osi.generated.model.IdSetResponse;
import org.veupathdb.service.osi.generated.model.IdSetResponseImpl;
import org.veupathdb.service.osi.model.db.IdSet;
import org.veupathdb.service.osi.model.db.IdSetCollection;
import org.veupathdb.service.osi.model.db.Organism;
import org.veupathdb.service.osi.model.db.User;
import org.veupathdb.service.osi.model.db.raw.GeneRow;
import org.veupathdb.service.osi.model.db.raw.IdSetRow;
import org.veupathdb.service.osi.model.db.raw.TranscriptRow;
import org.veupathdb.service.osi.repo.Schema.Osi.IdSets;

public class IdSetUtils
{
  public static IdSet rowToNode(
    final IdSetRow row,
    final Map < Integer, User > users,
    final Map < Integer, Organism > organisms,
    final Map < Integer, IdSetCollection > collections
  ) {
    return new IdSet(
      row.getId(),
      collections.get(row.getCollectionId()),
      organisms.get(row.getOrganismId()),
      row.getTemplate(),
      users.get(row.getCreatedBy()),
      row.getCounterStart(),
      row.getNumIssued(),
      row.getCreatedOn()
    );
  }

  public static IdSetRow newIdSetRow(final ResultSet rs) throws Exception {
    return new IdSetRow(
      rs.getInt(IdSets.ID_SET_ID),
      rs.getInt(IdSets.COLLECTION_ID),
      rs.getInt(IdSets.ORGANISM_ID),
      rs.getString(IdSets.TEMPLATE),
      rs.getLong(IdSets.COUNTER_START),
      rs.getInt(IdSets.NUM_ISSUED),
      rs.getObject(IdSets.CREATED_ON, OffsetDateTime.class),
      rs.getInt(IdSets.CREATED_BY)
    );
  }

  public static IdSet newIdSet(
    final ResultSet rs,
    final Map < Integer, User > users,
    final Map < Integer, Organism > organisms,
    final Map < Integer, IdSetCollection > collections
  ) throws Exception {
    return newIdSet(rs, users, organisms,
      collections.get(rs.getInt(IdSets.COLLECTION_ID)));
  }

  public static IdSet newIdSet(
    final ResultSet rs,
    final Map < Integer, User > users,
    final Map < Integer, Organism > organisms,
    final IdSetCollection collection
  ) throws Exception {
    return new IdSet(
      rs.getInt(IdSets.ID_SET_ID),
      collection,
      organisms.get(rs.getInt(IdSets.ORGANISM_ID)),
      rs.getString(IdSets.TEMPLATE),
      users.get(rs.getInt(IdSets.CREATED_BY)),
      rs.getLong(IdSets.COUNTER_START),
      rs.getInt(IdSets.NUM_ISSUED),
      rs.getObject(IdSets.CREATED_ON, OffsetDateTime.class)
    );
  }

  public static IdSetResponse setToRes(IdSetRow set) {
    var out = new IdSetResponseImpl();

    out.setIdSetId(set.getId());
    out.setCollectionId(set.getCollectionId());
    out.setTemplate(set.getTemplate());
    out.setGeneIntStart(set.getCounterStart());
    out.setGeneratedGeneCount(set.getNumIssued());
    out.setCreatedBy(set.getCreatedBy());
    out.setCreatedOn(Date.from(set.getCreatedOn().toInstant()));
    out.setGeneratedIds(new ArrayList <>());

    return out;
  }

  public static void assignTranscripts(
    Map < Long, IdSetResponse > idSets,
    Map < Long, GeneRow > genes,
    List < TranscriptRow > transcripts
  ) {
    var out = new HashMap<Integer, GeneratedTranscriptEntry>(genes.size());
    genes.forEach((k, gene) -> {
      var entry = gene2Entry(gene);
      out.put(k, entry);
      idSets.get(gene.getIdSetId()).getGeneratedIds().add(entry);
    });
    transcripts.forEach(t -> {
      var ids =
      out.get(t.getGeneId())
    });

    return out;
  }

  private static Map < Integer, GeneratedTranscriptEntry > genesToEntries(
    Map < Integer, GeneRow > rows
  ) {
  }

  private static GeneratedTranscriptEntry gene2Entry(GeneRow row) {
    var out = new GeneratedTranscriptEntryImpl();
    out.setGeneId(row.getGeneIdentifier());
    out.setTranscripts(new ArrayList <>());
    out.setProteins(new ArrayList <>());
    return out;
  }
}
