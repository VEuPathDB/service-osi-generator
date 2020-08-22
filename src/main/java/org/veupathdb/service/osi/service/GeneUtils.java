package org.veupathdb.service.osi.service;

import java.sql.ResultSet;
import java.time.OffsetDateTime;
import java.util.Map;

import org.veupathdb.service.osi.model.db.Gene;
import org.veupathdb.service.osi.model.db.IdSet;
import org.veupathdb.service.osi.model.db.User;
import org.veupathdb.service.osi.repo.Schema.Osi.Genes;

public class GeneUtils
{
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
}
