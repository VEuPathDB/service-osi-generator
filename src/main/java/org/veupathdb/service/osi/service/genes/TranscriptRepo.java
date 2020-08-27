package org.veupathdb.service.osi.service.genes;

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

public class TranscriptRepo
{
  public static List < TranscriptRow > selectByGeneIds(int[] ids)
  throws Exception {
    return new BasicPreparedListReadQuery <>(
      Transcripts.BY_GENES,
      DbMan.connection(),
      TranscriptUtils::newTranscriptRow,
      ps -> ps.setObject(1, ids)
    ).execute().getValue();
  }

  public static List < Transcript > selectTranscriptsByGenes(
    final int[] geneIds,
    final Map < Integer, Gene > genes
  ) throws Exception {
    var out = new ArrayList<Transcript>();

    try (
      var cn = DbMan.connection();
      var ps = cn.prepareStatement(Transcripts.BY_GENES)
    ) {
      ps.setObject(1, geneIds);

      try (var rs = ps.executeQuery()) {
        while (rs.next())
          out.add(TranscriptUtils.newTranscript(rs, genes));
      }
    }

    return out;
  }

  public static List < Transcript > selectTranscriptsByCollections(
    final int[] collectionIds,
    final Map < Integer, User > users,
    final Map < Integer, Gene > genes
  ) throws Exception {
    var out = new ArrayList<Transcript>();

    try (
      var cn = DbMan.connection();
      var ps = cn.prepareStatement(Transcripts.BY_COLLECTIONS)
    ) {
      ps.setObject(1, collectionIds);

      try (var rs = ps.executeQuery()) {
        while (rs.next())
          out.add(TranscriptUtils.newTranscript(rs, users, genes));
      }
    }

    return out;
  }

  public static List < Transcript > selectTranscriptsByCollection(
    final int collectionId,
    final Map < Integer, User > users,
    final Map < Integer, Gene > genes
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
