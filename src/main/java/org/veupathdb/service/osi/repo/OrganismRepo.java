package org.veupathdb.service.osi.repo;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.veupathdb.service.osi.model.OrganismQuery;
import org.veupathdb.service.osi.model.db.NewOrganism;
import org.veupathdb.service.osi.model.db.Organism;
import org.veupathdb.service.osi.service.DbMan;

public class OrganismRepo
{
  /**
   * Attempts to allocate <code>count</code> sequential gene id int values,
   * returning the first gene id value in the allocated block.
   *
   * @param count number of gene id int values to allocate
   *
   * @return first gene id int value in the allocated block.
   */
  public static int allocateGeneIds(int count) throws Exception {
    try (
      var cn = DbMan.connection();
      var ps = cn.prepareStatement(SQL.Update.Osi.Organisms.GENE_COUNTER)
    ) {
      ps.setInt(1, count);
      try (var rs = ps.executeQuery()) {
        rs.next();
        return rs.getInt(1);
      }
    }
  }

  /**
   * Attempts to allocate <code>count</code> sequential transcript id int
   * values, returning the first transcript id value in the allocated block.
   *
   * @param count number of transcript id int values to allocate
   *
   * @return first transcript id int value in the allocated block.
   */
  public static int allocateTranscriptIds(int count) throws Exception {
    try (
      var cn = DbMan.connection();
      var ps = cn.prepareStatement(SQL.Update.Osi.Organisms.TRANSCRIPT_COUNTER)
    ) {
      ps.setInt(1, count);
      try (var rs = ps.executeQuery()) {
        rs.next();
        return rs.getInt(1);
      }
    }
  }

  public static Organism insertOrganism(NewOrganism organism) throws Exception {
    try (
      var cn = DbMan.connection();
      var ps = cn.prepareStatement(SQL.Insert.Osi.ORGANISM)
    ) {
      ps.setString(1, organism.getTemplate());
      ps.setLong(2, organism.getGeneCounterStart());
      ps.setLong(3, organism.getGeneCounterStart());
      ps.setLong(4, organism.getTranscriptCounterStart());
      ps.setLong(5, organism.getTranscriptCounterStart());
      ps.setInt(6, organism.getCreatedBy().getUserId());

      try (var rs = ps.executeQuery()) {
        rs.next();

        return Utils.newOrganism(rs, organism);
      }
    }
  }

  public static Optional < Organism > selectOrganism(int organismId)
  throws Exception {
    try (
      var cn = DbMan.connection();
      var ps = cn.prepareStatement(SQL.Select.Osi.Organisms.BY_ID)
    ) {
      ps.setInt(1, organismId);

      try (var rs = ps.executeQuery()) {
        if (!rs.next())
          return Optional.empty();

        return Optional.of(Utils.newOrganism(rs));
      }
    }
  }

  /**
   * Searches for organisms in the database matching all of the criteria set in
   * the given {@link OrganismQuery} object.
   *
   * @param query Organism search criteria
   *
   * @return A list of zero or more matched organisms.
   */
  public static List < Organism > findOrganisms(OrganismQuery query)
  throws Exception {
    try (
      var cn = DbMan.connection();
      var ps = cn.prepareStatement(SQL.Select.Osi.Organisms.BY_QUERY)
    ) {
      if (query.getName() == null)
        ps.setNull(1, Types.VARCHAR);
      else
        ps.setString(1, query.getName());

      if (query.getStart() == null)
        ps.setNull(2, Types.TIMESTAMP_WITH_TIMEZONE);
      else
        ps.setObject(2, query.getStart());

      if (query.getEnd() == null)
        ps.setNull(3, Types.TIMESTAMP_WITH_TIMEZONE);
      else
        ps.setObject(3, query.getEnd());

      if (query.getCreatedById() == null)
        ps.setNull(4, Types.INTEGER);
      else
        ps.setInt(4, query.getCreatedById());

      if (query.getCreatedByName() == null)
        ps.setNull(5, Types.VARCHAR);
      else
        ps.setString(5, query.getCreatedByName());

      try (var rs = ps.executeQuery()) {
        final var out = new ArrayList<Organism>();

        while (rs.next()) {
          out.add(Utils.newOrganism(rs));
        }

        return out;
      }
    }
  }
}
