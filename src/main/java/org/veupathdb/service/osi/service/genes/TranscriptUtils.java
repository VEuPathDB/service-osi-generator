package org.veupathdb.service.osi.service.genes;

import java.sql.ResultSet;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.glassfish.grizzly.utils.Pair;
import org.veupathdb.service.osi.model.db.Gene;
import org.veupathdb.service.osi.model.db.Transcript;
import org.veupathdb.service.osi.model.db.User;
import org.veupathdb.service.osi.model.db.raw.GeneRow;
import org.veupathdb.service.osi.model.db.raw.TranscriptRow;
import org.veupathdb.service.osi.repo.Schema.Osi.Transcripts;
import org.veupathdb.service.osi.service.user.UserManager;

public class TranscriptUtils
{
  public static final String
    PROTEIN_PAT    = "%s.P%d",
    TRANSCRIPT_PAT = "%s.P%d";

  private static final String
    ERR_GENE_TRAN_MISMATCH = "Attempted to expand a transcript record against"
    + " an incorrect gene record.";

  static TranscriptRow newTranscriptRow(ResultSet rs) throws Exception {
    return new TranscriptRow(
      rs.getLong(Transcripts.TRANSCRIPT_ID),
      rs.getLong(Transcripts.GENE_ID),
      rs.getLong(Transcripts.COUNTER_START),
      rs.getInt(Transcripts.NUM_ISSUED),
      rs.getObject(Transcripts.CREATED_ON, OffsetDateTime.class),
      rs.getLong(Transcripts.CREATED_BY)
    );
  }

  public static Transcript newTranscript(
    final ResultSet rs,
    final Map < Integer, User > users,
    final Map < Integer, Gene > genes
  ) throws Exception {
    return new Transcript(
      rs.getLong(Transcripts.TRANSCRIPT_ID),
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
      rs.getLong(Transcripts.TRANSCRIPT_ID),
      genes.get(rs.getInt(Transcripts.GENE_ID)),
      rs.getLong(Transcripts.COUNTER_START),
      rs.getInt(Transcripts.NUM_ISSUED),
      UserManager.getLocal(rs.getInt(Transcripts.CREATED_BY)).orElseThrow(),
      rs.getObject(Transcripts.CREATED_ON, OffsetDateTime.class)
    );
  }

  /**
   * Expands the given {@code TranscriptRow} into a list of transcript and
   * protein ids.
   *
   * @param gene Gene record to which the given transcript belongs.
   * @param tran Transcript record to expand.
   *
   * @return A list of {@link Pair} instances containing a matching pair of
   * transcript and protein ids.
   *
   * @throws IllegalArgumentException if the given transcript and gene record
   * do not match (If the transcript row is not for the given gene row).
   */
  public static List < Pair < String, String > > expandTranscript(
    final GeneRow gene,
    final TranscriptRow tran
  ) {
    if (gene.getId() != tran.getGeneId())
      throw new IllegalArgumentException(ERR_GENE_TRAN_MISMATCH);

    var out = new ArrayList < Pair < String, String > >(tran.getNumIssued());
    var pre = gene.getGeneIdentifier();
    var bas = tran.getCounterStart();

    for (long i = 0; i < tran.getNumIssued(); i++) {
      var id = bas + i;

      out.add(new Pair <>(
        String.format(TRANSCRIPT_PAT, pre, id),
        String.format(PROTEIN_PAT, pre, id)
      ));
    }

    return out;
  }
}
