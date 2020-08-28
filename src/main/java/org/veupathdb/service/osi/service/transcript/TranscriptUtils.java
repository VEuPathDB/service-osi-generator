package org.veupathdb.service.osi.service.transcript;

import java.sql.ResultSet;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.glassfish.grizzly.utils.Pair;
import org.veupathdb.lib.container.jaxrs.providers.LogProvider;
import org.veupathdb.service.osi.generated.model.GeneratedTranscriptEntry;
import org.veupathdb.service.osi.model.db.Gene;
import org.veupathdb.service.osi.model.db.Transcript;
import org.veupathdb.service.osi.repo.Schema.Osi.Transcripts;

public class TranscriptUtils
{
  private static final TranscriptUtils instance = new TranscriptUtils();

  public static final String
    PROTEIN_PAT    = "%s.P%d",
    TRANSCRIPT_PAT = "%s.P%d";

  private static final String
    ERR_GENE_TRAN_MISMATCH = "Attempted to expand a transcript record against"
    + " an incorrect gene record.";

  private final Logger log = LogProvider.logger(getClass());

  // ╔════════════════════════════════════════════════════════════════════╗ //
  // ║                                                                    ║ //
  // ║    Static Access Methods                                           ║ //
  // ║                                                                    ║ //
  // ╚════════════════════════════════════════════════════════════════════╝ //

  public static TranscriptUtils getInstance() {
    return instance;
  }

  public static long getId(final ResultSet rs) throws Exception {
    return getInstance().parseId(rs);
  }

  public static Transcript newTranscriptRow(final ResultSet rs)
  throws Exception {
    return getInstance().createTranscriptRow(rs);
  }

  /**
   * @see #expandTranscriptIds(Gene, Transcript)
   */
  public static List < Pair < String, String > > expandTranscript(
    final Gene gene,
    final Transcript tran
  ) {
    return getInstance().expandTranscriptIds(gene, tran);
  }

  public static void assign(
    final Collection < Transcript > rows,
    final Map < Long, Gene > genes,
    final Map < Long, GeneratedTranscriptEntry > entries
  ) {
    getInstance().assignTranscripts(rows, genes, entries);
  }

  // ╔════════════════════════════════════════════════════════════════════╗ //
  // ║                                                                    ║ //
  // ║    Mockable Instance Methods                                       ║ //
  // ║                                                                    ║ //
  // ╚════════════════════════════════════════════════════════════════════╝ //

  public long parseId(final ResultSet rs) throws Exception {
    log.trace("TranscriptUtils#parseId(ResultSet)");
    return rs.getLong(Transcripts.TRANSCRIPT_ID);
  }

  public Transcript createTranscriptRow(ResultSet rs) throws Exception {
    log.trace("TranscriptUtils#createTranscriptRow(ResultSet)");
    return new Transcript(
      rs.getLong(Transcripts.TRANSCRIPT_ID),
      rs.getLong(Transcripts.GENE_ID),
      rs.getLong(Transcripts.COUNTER_START),
      rs.getInt(Transcripts.NUM_ISSUED),
      rs.getObject(Transcripts.CREATED_ON, OffsetDateTime.class),
      rs.getLong(Transcripts.CREATED_BY)
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
  public List < Pair < String, String > > expandTranscriptIds(
    final Gene gene,
    final Transcript tran
  ) {
    log.trace("TranscriptUtils#expandTranscriptIds(GeneRow, TranscriptRow)");
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

  public void assignTranscripts(
    final Collection < Transcript > rows,
    final Map < Long, Gene > genes,
    final Map < Long, GeneratedTranscriptEntry > entries
  ) {
    for (var t : rows) {
      var gene = genes.get(t.getGeneId());
      var exp  = TranscriptUtils.expandTranscript(gene, t);
      var out  = entries.get(t.getGeneId());
      for (var p : exp) {
        out.getTranscripts().add(p.getFirst());
        out.getProteins().add(p.getSecond());
      }
    }
  }
}
