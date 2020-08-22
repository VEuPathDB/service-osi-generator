package org.veupathdb.service.osi.service;

import java.sql.ResultSet;
import java.time.OffsetDateTime;
import java.util.Map;

import org.veupathdb.service.osi.model.db.Organism;
import org.veupathdb.service.osi.model.db.User;
import org.veupathdb.service.osi.model.db.raw.OrganismRow;
import org.veupathdb.service.osi.repo.Schema;
import org.veupathdb.service.osi.repo.Schema.Osi.Organisms;

public class OrganismUtils
{
  public static Organism newOrganism(
    final ResultSet rs,
    final Map < Integer, User > users
  ) throws Exception {
    return new Organism(
      rs.getInt(Organisms.ORGANISM_ID),
      rs.getString(Organisms.TEMPLATE),
      rs.getLong(Organisms.GENE_COUNTER_START),
      rs.getLong(Organisms.GENE_COUNTER_CURRENT),
      rs.getLong(Organisms.TRANSCRIPT_COUNTER_START),
      rs.getLong(Organisms.TRANSCRIPT_COUNTER_CURRENT),
      users.get(rs.getInt(Organisms.CREATED_BY)),
      rs.getObject(Organisms.CREATED_ON, OffsetDateTime.class),
      rs.getObject(Organisms.LAST_MODIFIED, OffsetDateTime.class)
    );
  }
}
