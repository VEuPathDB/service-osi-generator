package org.veupathdb.service.osi.service.genes;

import java.sql.ResultSet;
import java.time.OffsetDateTime;
import java.util.Map;

import org.veupathdb.service.osi.model.db.Gene;
import org.veupathdb.service.osi.model.db.IdSet;
import org.veupathdb.service.osi.model.db.User;
import org.veupathdb.service.osi.model.db.raw.GeneRow;
import org.veupathdb.service.osi.repo.Schema.Osi.Genes;
import org.veupathdb.service.osi.service.user.UserUtils;

public class GeneUtils
{
  public static GeneRow newGeneRow(final ResultSet rs) throws Exception {
    return new GeneRow(
      rs.getInt(Genes.GENE_ID),
      rs.getInt(Genes.ID_SET_ID),
      rs.getString(Genes.GENE_NAME),
      rs.getObject(Genes.CREATED_ON, OffsetDateTime.class),
      rs.getInt(Genes.CREATED_BY)
    );
  }

  public static Gene newGene(
    final ResultSet rs,
    final Map < Integer, User > users,
    final Map < Integer, IdSet > idSets
  ) throws Exception {
    return new Gene(
      rs.getInt(Genes.GENE_ID),
      idSets.get(rs.getInt(Genes.ID_SET_ID)),
      rs.getString(Genes.GENE_NAME),
      users.get(rs.getInt(Genes.CREATED_BY)),
      rs.getObject(Genes.CREATED_ON, OffsetDateTime.class)
    );
  }

  public static Gene newGene(
    final ResultSet rs,
    final Map < Integer, IdSet > idSets
  ) throws Exception {
    return new Gene(
      rs.getInt(Genes.GENE_ID),
      idSets.get(rs.getInt(Genes.ID_SET_ID)),
      rs.getString(Genes.GENE_NAME),
      UserUtils.newUser(rs),
      rs.getObject(Genes.CREATED_ON, OffsetDateTime.class)
    );
  }

  public static Gene newGene(
    final ResultSet rs,
    final IdSet idSet
  ) throws Exception {
    return new Gene(
      rs.getInt(Genes.GENE_ID),
      idSet,
      rs.getString(Genes.GENE_NAME),
      UserUtils.newUser(rs),
      rs.getObject(Genes.CREATED_ON, OffsetDateTime.class)
    );
  }
}
