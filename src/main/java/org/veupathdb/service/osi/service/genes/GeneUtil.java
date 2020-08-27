package org.veupathdb.service.osi.service.genes;

import java.sql.ResultSet;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.veupathdb.lib.container.jaxrs.providers.LogProvider;
import org.veupathdb.service.osi.generated.model.GeneratedTranscriptEntry;
import org.veupathdb.service.osi.generated.model.GeneratedTranscriptEntryImpl;
import org.veupathdb.service.osi.generated.model.IdSetResponse;
import org.veupathdb.service.osi.model.db.raw.GeneRow;
import org.veupathdb.service.osi.repo.Schema.Osi.Genes;

public class GeneUtil
{
  private static final GeneUtil instance = new GeneUtil();

  private final Logger log = LogProvider.logger(getClass());

  // ╔════════════════════════════════════════════════════════════════════╗ //
  // ║                                                                    ║ //
  // ║    Static Access Methods                                           ║ //
  // ║                                                                    ║ //
  // ╚════════════════════════════════════════════════════════════════════╝ //

  public static GeneUtil getInstance() {
    return instance;
  }

  public static long getId(final ResultSet rs) throws Exception {
    return getInstance().parseId(rs);
  }

  public static GeneRow newGeneRow(final ResultSet rs) throws Exception {
    return getInstance().createGeneRow(rs);
  }

  public static Map < Long, GeneratedTranscriptEntry > toEntries(
    final Collection < GeneRow > rows,
    final Map < Long, IdSetResponse > sets
  ) {
    return getInstance().genesToEntries(rows, sets);
  }

  // ╔════════════════════════════════════════════════════════════════════╗ //
  // ║                                                                    ║ //
  // ║    Mockable Instance Methods                                       ║ //
  // ║                                                                    ║ //
  // ╚════════════════════════════════════════════════════════════════════╝ //

  public long parseId(final ResultSet rs) throws Exception {
    log.trace("GeneUtil#parseId(ResultSet)");
    return rs.getLong(Genes.GENE_ID);
  }

  public GeneRow createGeneRow(final ResultSet rs) throws Exception {
    return new GeneRow(
      rs.getLong(Genes.GENE_ID),
      rs.getLong(Genes.ID_SET_ID),
      rs.getString(Genes.GENE_NAME),
      rs.getObject(Genes.CREATED_ON, OffsetDateTime.class),
      rs.getLong(Genes.CREATED_BY)
    );
  }

  public Map < Long, GeneratedTranscriptEntry > genesToEntries(
    final Collection < GeneRow > rows,
    final Map < Long, IdSetResponse > idSets
  ) {
    var out = new HashMap< Long, GeneratedTranscriptEntry >(rows.size());

    for (var g : rows) {
      var tmp = geneToEntry(g);
      out.put(g.getId(), tmp);
      idSets.get(g.getIdSetId()).getGeneratedIds().add(tmp);
    }

    return out;
  }

  public GeneratedTranscriptEntry geneToEntry(final GeneRow row) {
    var out = new GeneratedTranscriptEntryImpl();

    out.setGeneId(row.getGeneIdentifier());
    out.setProteins(new ArrayList <>());
    out.setTranscripts(new ArrayList <>());

    return out;
  }
}
