package org.veupathdb.service.osi.service.genes;

import java.sql.ResultSet;
import java.time.OffsetDateTime;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.veupathdb.lib.container.jaxrs.providers.LogProvider;
import org.veupathdb.service.osi.model.db.Gene;
import org.veupathdb.service.osi.model.db.IdSet;
import org.veupathdb.service.osi.model.db.User;
import org.veupathdb.service.osi.model.db.raw.GeneRow;
import org.veupathdb.service.osi.repo.Schema.Osi.Genes;
import org.veupathdb.service.osi.service.user.UserUtil;

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

  public static Gene newGene(
    final ResultSet rs,
    final Map < Long, User > users,
    final Map < Long, IdSet > idSets
  ) throws Exception {
    return getInstance().createGene(rs, users, idSets);
  }

  public static Gene newGene(
    final ResultSet rs,
    final Map < Long, IdSet > idSets
  ) throws Exception {
    return getInstance().createGene(rs, idSets);
  }

  public static Gene newGene(
    final ResultSet rs,
    final IdSet idSet
  ) throws Exception {
    return getInstance().createGene(rs, idSet);
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

  public Gene createGene(
    final ResultSet rs,
    final Map < Long, User > users,
    final Map < Long, IdSet > idSets
  ) throws Exception {
    log.trace("GeneUtil#createGene(ResultSet, Map, Map)");
    return new Gene(
      rs.getLong(Genes.GENE_ID),
      idSets.get(rs.getLong(Genes.ID_SET_ID)),
      rs.getString(Genes.GENE_NAME),
      users.get(rs.getLong(Genes.CREATED_BY)),
      rs.getObject(Genes.CREATED_ON, OffsetDateTime.class)
    );
  }

  public Gene createGene(
    final ResultSet rs,
    final Map < Long, IdSet > idSets
  ) throws Exception {
    log.trace("GeneUtil#createGene(ResultSet, Map)");
    return new Gene(
      rs.getLong(Genes.GENE_ID),
      idSets.get(rs.getLong(Genes.ID_SET_ID)),
      rs.getString(Genes.GENE_NAME),
      UserUtil.newUser(rs),
      rs.getObject(Genes.CREATED_ON, OffsetDateTime.class)
    );
  }

  public Gene createGene(
    final ResultSet rs,
    final IdSet idSet
  ) throws Exception {
    log.trace("GeneUtil#createGene(ResultSet, IdSet)");
    return new Gene(
      rs.getLong(Genes.GENE_ID),
      idSet,
      rs.getString(Genes.GENE_NAME),
      UserUtil.newUser(rs),
      rs.getObject(Genes.CREATED_ON, OffsetDateTime.class)
    );
  }
}
