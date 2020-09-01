package org.veupathdb.service.osi.service.genes;

import java.sql.ResultSet;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.veupathdb.lib.container.jaxrs.providers.LogProvider;
import org.veupathdb.service.osi.db.Schema.Osi.Genes;
import org.veupathdb.service.osi.generated.model.GeneratedTranscriptEntry;
import org.veupathdb.service.osi.generated.model.GeneratedTranscriptEntryImpl;
import org.veupathdb.service.osi.generated.model.IdSetResponse;
import org.veupathdb.service.osi.model.db.Gene;
import org.veupathdb.service.osi.model.db.Organism;

public class GeneUtil
{
  @SuppressWarnings("FieldMayBeFinal")
  private static GeneUtil instance = new GeneUtil();

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

  public static String getIdentifier(final ResultSet rs) throws Exception {
    return getInstance().parseIdentifier(rs);
  }

  public static Gene newGeneRow(final ResultSet rs) throws Exception {
    return getInstance().createGeneRow(rs);
  }

  public static GeneratedTranscriptEntry toEntry(
    final Gene row
  ) {
    return getInstance().geneToEntry(row);
  }


  public static Map < Long, GeneratedTranscriptEntry > toEntries(
    final Collection < Gene > rows,
    final Map < Long, IdSetResponse > sets
  ) {
    return getInstance().genesToEntries(rows, sets);
  }

  public static String[] expandGenes(
    final Organism org,
    final long start,
    final int count
  ) {
    return getInstance().expandGeneIds(org, start, count);
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

  public String parseIdentifier(final ResultSet rs) throws Exception {
    log.trace("GeneUtil#parseIdentifier(ResultSet)");
    return rs.getString(Genes.GENE_NAME);
  }

  public Gene createGeneRow(final ResultSet rs) throws Exception {
    log.trace("GeneUtil#createGeneRow(ResultSet)");
    return new Gene(
      rs.getLong(Genes.GENE_ID),
      rs.getLong(Genes.ID_SET_ID),
      rs.getString(Genes.GENE_NAME),
      rs.getObject(Genes.CREATED_ON, OffsetDateTime.class),
      rs.getLong(Genes.CREATED_BY)
    );
  }

  public Map < Long, GeneratedTranscriptEntry > genesToEntries(
    final Collection < Gene > rows,
    final Map < Long, IdSetResponse > idSets
  ) {
    log.trace("GeneUtil#genesToEntries(Collection, Map)");
    var out = new HashMap < Long, GeneratedTranscriptEntry >(rows.size());

    for (var g : rows) {
      var tmp = geneToEntry(g);
      out.put(g.getId(), tmp);
      idSets.get(g.getIdSetId()).getGeneratedIds().add(tmp);
    }

    return out;
  }

  public GeneratedTranscriptEntry geneToEntry(final Gene row) {
    log.trace("GeneUtil#geneToEntry(Gene)");
    var out = new GeneratedTranscriptEntryImpl();

    out.setGeneId(row.getGeneIdentifier());
    out.setProteins(new ArrayList <>());
    out.setTranscripts(new ArrayList <>());

    return out;
  }

  public String[] expandGeneIds(
    final Organism org,
    final long start,
    final int count
  ) {
    log.trace("GeneUtil#expandGeneIds(Organism, long, int)");
    final var out = new String[count];
    final var fom = org.getTemplate();

    for (long i = 0; i < count; i++)
         out[(int) i] = String.format(fom, start + i);

    return out;
  }
}
