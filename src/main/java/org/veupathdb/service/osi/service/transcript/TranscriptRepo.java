package org.veupathdb.service.osi.service.transcript;

import java.sql.Connection;
import java.util.List;

import io.vulpine.lib.query.util.basic.BasicPreparedListReadQuery;
import org.veupathdb.service.osi.db.SQL;
import org.veupathdb.service.osi.db.SQL.Select.Osi.Transcripts;
import org.veupathdb.service.osi.model.db.NewTranscript;
import org.veupathdb.service.osi.model.db.Transcript;
import org.veupathdb.service.osi.service.DbMan;
import org.veupathdb.service.osi.util.QueryUtil;

public class TranscriptRepo
{
  public static void insert(
    final NewTranscript[] newTranscripts,
    final Connection con
  ) throws Exception {
    try (final var ps = con.prepareStatement(SQL.Insert.Osi.TRANSCRIPT)) {
      for (final var t : newTranscripts) {
        ps.setLong(1, t.getGene().getId());
        ps.setLong(2, t.getCounterStart());
        ps.setInt (3, t.getNumIssued());
        ps.setLong(4, t.getCreatedBy().getUserId());
        ps.addBatch();
      }
      ps.executeBatch();
    }
  }

  public static List < Transcript > selectByGeneIds(long[] ids)
  throws Exception {
    return new BasicPreparedListReadQuery <>(
      Transcripts.BY_GENES,
      DbMan::connection,
      TranscriptUtils::newTranscriptRow,
      QueryUtil.idSet(ids)
    ).execute().getValue();
  }
}
