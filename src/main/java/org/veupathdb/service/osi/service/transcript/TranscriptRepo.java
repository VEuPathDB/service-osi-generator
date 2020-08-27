package org.veupathdb.service.osi.service.transcript;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.vulpine.lib.query.util.basic.BasicPreparedListReadQuery;
import org.veupathdb.service.osi.model.db.Gene;
import org.veupathdb.service.osi.model.db.Transcript;
import org.veupathdb.service.osi.model.db.User;
import org.veupathdb.service.osi.model.db.raw.TranscriptRow;
import org.veupathdb.service.osi.repo.SQL.Select.Osi.Transcripts;
import org.veupathdb.service.osi.service.DbMan;
import org.veupathdb.service.osi.util.QueryUtil;

public class TranscriptRepo
{
  public static List < TranscriptRow > selectByGeneIds(long[] ids)
  throws Exception {
    return new BasicPreparedListReadQuery <>(
      Transcripts.BY_GENES,
      DbMan::connection,
      TranscriptUtils::newTranscriptRow,
      QueryUtil.idSet(ids)
    ).execute().getValue();
  }

  public static List < Transcript > selectByGeneIds(
    final long[] geneIds,
    final Map < Long, Gene > genes
  ) throws Exception {
    return new BasicPreparedListReadQuery<>(
      Transcripts.BY_GENES,
      DbMan::connection,
      rs -> TranscriptUtils.newTranscript(rs, genes),
      QueryUtil.idSet(geneIds)
    ).execute().getValue();
  }

  public static List < Transcript > selectByCollectionIds(
    final long[] collectionIds,
    final Map < Long, User > users,
    final Map < Long, Gene > genes
  ) throws Exception {
    return new BasicPreparedListReadQuery<>(
      Transcripts.BY_COLLECTIONS,
      DbMan::connection,
      rs -> TranscriptUtils.newTranscript(rs, users, genes),
      QueryUtil.idSet(collectionIds)
    ).execute().getValue();
  }

  public static List < Transcript > selectTranscriptsByCollection(
    final int collectionId,
    final Map < Long, User > users,
    final Map < Long, Gene > genes
  ) throws Exception {
    var out = new ArrayList<Transcript>();

    try (
      var cn = DbMan.connection();
      var ps = cn.prepareStatement(Transcripts.BY_COLLECTION)
    ) {
      ps.setInt(1, collectionId);

      try (var rs = ps.executeQuery()) {
        while (rs.next())
          out.add(TranscriptUtils.newTranscript(rs, users, genes));
      }
    }

    return out;
  }
}