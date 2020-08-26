package org.veupathdb.service.osi.service;

import java.sql.ResultSet;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.veupathdb.service.osi.Patterns;
import org.veupathdb.service.osi.model.db.Gene;
import org.veupathdb.service.osi.model.db.Transcript;
import org.veupathdb.service.osi.model.db.User;
import org.veupathdb.service.osi.repo.Schema.Osi.Transcripts;
import org.veupathdb.service.osi.service.user.UserManager;

public class TranscriptUtils
{
  public static Transcript newTranscript(
    final ResultSet rs,
    final Map < Integer, User > users,
    final Map < Integer, Gene > genes
  ) throws Exception {
    return new Transcript(
      genes.get(rs.getInt(Transcripts.GENE_ID)),
      rs.getLong(Transcripts.COUNTER_START),
      rs.getInt(Transcripts.NUM_ISSUED),
      users.get(rs.getInt(Transcripts.CREATED_BY)),
      rs.getObject(Transcripts.CREATED_ON, OffsetDateTime.class)
    );
  }

  public static Transcript newTranscript(
    final ResultSet rs,
    final Map < Integer, Gene > genes
  ) throws Exception {
    return new Transcript(
      genes.get(rs.getInt(Transcripts.GENE_ID)),
      rs.getLong(Transcripts.COUNTER_START),
      rs.getInt(Transcripts.NUM_ISSUED),
      UserManager.getLocal(rs.getInt(Transcripts.CREATED_BY)).orElseThrow(),
      rs.getObject(Transcripts.CREATED_ON, OffsetDateTime.class)
    );
  }

  public static List < String > expandTranscriptIds(Transcript transcript) {
    var out = new ArrayList <String>(transcript.getNumIssued());

    for (var i = 0; i < transcript.getNumIssued(); i++)
      out.set(i, String.format(
        Patterns.TRANSCRIPT_PATTERN,
        transcript.getGene().getIdentifier(),
        transcript.getCounterStart() + i
      ));

    return out;
  }

  public static List < String > expandProteinIds(Transcript transcript) {
    var out = new ArrayList <String>(transcript.getNumIssued());

    for (var i = 0; i < transcript.getNumIssued(); i++)
         out.set(i, String.format(
           Patterns.PROTEIN_PATTERN,
           transcript.getGene().getIdentifier(),
           transcript.getCounterStart() + i
         ));

    return out;
  }
}
