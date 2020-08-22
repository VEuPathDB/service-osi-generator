package org.veupathdb.service.osi.repo;

import java.sql.Types;
import java.util.*;

import org.veupathdb.service.osi.model.RecordQuery;
import org.veupathdb.service.osi.model.db.NewOrganism;
import org.veupathdb.service.osi.model.db.Organism;
import org.veupathdb.service.osi.model.db.User;
import org.veupathdb.service.osi.service.DbMan;
import org.veupathdb.service.osi.service.OrganismUtils;

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

  public static Map < Integer, Organism > organismsByCollectionIds(
    final int[] collectionIds,
    final Map < Integer, User > users
  ) throws Exception {
    var out = new HashMap<Integer, Organism>();

    try (
      var cn = DbMan.connection();
      var ps = cn.prepareStatement(SQL.Select.Osi.Organisms.BY_COLLECTIONS)
    ) {
      ps.setObject(1, collectionIds);

      try (var rs = ps.executeQuery()) {
        var row = OrganismUtils.newOrganism(rs, users);
        out.put(row.getOrganismId(), row);
      }
    }

    return out;
  }

  public static Map < Integer, Organism > organismsByCollectionId(
    final int collectionId,
    final Map < Integer, User > users
  ) throws Exception {
    var out = new HashMap<Integer, Organism>();

    try (
      var cn = DbMan.connection();
      var ps = cn.prepareStatement(SQL.Select.Osi.Organisms.BY_COLLECTIONS)
    ) {
      ps.setInt(1, collectionId);

      try (var rs = ps.executeQuery()) {
        var row = OrganismUtils.newOrganism(rs, users);
        out.put(row.getOrganismId(), row);
      }
    }

    return out;
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

  public static Map < Integer, Organism > selectOrganisms(int[] organismIds)
  throws Exception {
    var out = new HashMap<Integer, Organism>(organismIds.length);

    try (
      var cn = DbMan.connection();
      var ps = cn.prepareStatement(SQL.Select.Osi.Organisms.BY_IDS)
    ) {
      ps.setObject(1, organismIds);

      try (var rs = ps.executeQuery()) {
        while (rs.next()) {
          var row = OrganismUtils.newOrganism(rs);
          out.put(row.getOrganismId(), row);
        }
      }
    }

    return out;
  }


  /**
   * Searches for organisms in the database matching all of the criteria set in
   * the given {@link RecordQuery} object.
   *
   * @param query Organism search criteria
   *
   * @return A list of zero or more matched organisms.
   */
  public static List < Organism > findOrganisms(RecordQuery query)
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
