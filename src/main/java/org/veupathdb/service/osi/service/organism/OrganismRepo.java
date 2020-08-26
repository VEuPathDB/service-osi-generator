package org.veupathdb.service.osi.service.organism;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;

import io.vulpine.lib.query.util.basic.BasicPreparedListReadQuery;
import io.vulpine.lib.query.util.basic.BasicPreparedMapReadQuery;
import io.vulpine.lib.query.util.basic.BasicPreparedReadQuery;
import io.vulpine.lib.query.util.basic.BasicPreparedWriteQuery;
import org.veupathdb.service.osi.model.RecordQuery;
import org.veupathdb.service.osi.model.db.NewOrganism;
import org.veupathdb.service.osi.model.db.Organism;
import org.veupathdb.service.osi.model.db.User;
import org.veupathdb.service.osi.repo.SQL;
import org.veupathdb.service.osi.repo.SQL.Select;
import org.veupathdb.service.osi.repo.Schema;
import org.veupathdb.service.osi.service.DbMan;

class OrganismRepo
{
  /**
   * Attempts to allocate <code>count</code> sequential gene id int values,
   * returning the first gene id value in the allocated block.
   *
   * @param count number of gene id int values to allocate
   *
   * @return first gene id int value in the allocated block.
   */
  static int allocateGeneIds(int id, int count) throws Exception {
    return new BasicPreparedReadQuery <>(
      SQL.Update.Osi.Organisms.GENE_COUNTER,
      DbMan::connection,
      OrganismRepo::rsToSingleInt,
      ps -> {
        ps.setInt(1, count);
        ps.setInt(2, id);
      }
    ).execute().getValue();
  }

  /**
   * Attempts to allocate <code>count</code> sequential transcript id int
   * values, returning the first transcript id value in the allocated block.
   *
   * @param count number of transcript id int values to allocate
   *
   * @return first transcript id int value in the allocated block.
   */
  static int allocateTranscriptIds(int id, int count) throws Exception {
    return new BasicPreparedReadQuery <>(
      SQL.Update.Osi.Organisms.TRANSCRIPT_COUNTER,
      DbMan::connection,
      OrganismRepo::rsToSingleInt,
      ps -> {
        ps.setInt(1, count);
        ps.setInt(2, id);
      }
    ).execute().getValue();
  }

  static Map < Integer, Organism > selectByCollectionIds(
    final int[] collectionIds,
    final Map < Integer, User > users
  ) throws Exception {
    return new BasicPreparedMapReadQuery <>(
      Select.Osi.Organisms.BY_COLLECTIONS,
      DbMan::connection,
      OrganismRepo::getOrgId,
      rs -> OrganismUtils.newOrganism(rs, users),
      ps -> ps.setObject(1, collectionIds)
    ).execute().getValue();
  }

  static Map < Integer, Organism > selectByCollectionId(
    final int collectionId,
    final Map < Integer, User > users
  ) throws Exception {
    return new BasicPreparedMapReadQuery <>(
      Select.Osi.Organisms.BY_COLLECTIONS,
      DbMan::connection,
      OrganismRepo::getOrgId,
      rs -> OrganismUtils.newOrganism(rs, users),
      ps -> ps.setInt(1, collectionId)
    ).execute().getValue();
  }

  static Organism insert(NewOrganism organism) throws Exception {
    return new BasicPreparedReadQuery <>(
      SQL.Insert.Osi.ORGANISM,
      DbMan::connection,
      rs -> {
        rs.next();
        return OrganismUtils.newOrganism(rs, organism);
      },
      ps -> {
        ps.setString(1, organism.getTemplate());
        ps.setLong(2, organism.getGeneCounterStart());
        ps.setLong(3, organism.getGeneCounterStart());
        ps.setLong(4, organism.getTranscriptCounterStart());
        ps.setLong(5, organism.getTranscriptCounterStart());
        ps.setInt(6, organism.getCreatedBy().getUserId());
      }
    ).execute().getValue();
  }

  static Optional < Organism > selectById(int organismId)
  throws Exception {
    try (var con = DbMan.connection()) {
      return selectById(organismId, con);
    }
  }

  static Optional < Organism > selectById(int orgId, Connection con)
  throws Exception {
    return new BasicPreparedReadQuery <>(
      Select.Osi.Organisms.BY_ID,
      con,
      OrganismRepo::rsToOptOrg,
      ps -> ps.setInt(1, orgId)
    ).execute().getValue();
  }

  static Map < Integer, Organism > selectByIds(int[] organismIds)
  throws Exception {
    return new BasicPreparedMapReadQuery<>(
      Select.Osi.Organisms.BY_IDS,
      DbMan::connection,
      OrganismRepo::getOrgId,
      OrganismUtils::newOrganism,
      ps -> ps.setObject(1, organismIds)
    ).execute().getValue();
  }

  static Optional < Organism > selectByName(String name)
  throws Exception {
    try (var con = DbMan.connection()) {
      return selectByName(name, con);
    }
  }

  static Optional < Organism > selectByName(String name, Connection con)
  throws Exception {
    return new BasicPreparedReadQuery<>(
      Select.Osi.Organisms.BY_NAME,
      con,
      OrganismRepo::rsToOptOrg,
      ps -> ps.setString(1, name)
    ).execute().getValue();
  }

  /**
   * Searches for organisms in the database matching all of the criteria set in
   * the given {@link RecordQuery} object.
   *
   * @param query Organism search criteria
   *
   * @return A list of zero or more matched organisms.
   */
  static List < Organism > findOrganisms(RecordQuery query)
  throws Exception {
    return new BasicPreparedListReadQuery<>(
      Select.Osi.Organisms.BY_QUERY,
      DbMan::connection,
      OrganismUtils::newOrganism,
      ps -> {
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
      }
    ).execute().getValue();
  }

  static void updateTemplate(
    int orgId,
    String template,
    Connection con
  ) throws Exception {
    new BasicPreparedWriteQuery(
      SQL.Update.Osi.Organisms.TEMPLATE,
      con,
      ps -> {
        ps.setInt(1, orgId);
        ps.setString(2, template);
      }
    ).execute();
  }

  static void updateOrganism(
    int orgId,
    String template,
    long geneCounter,
    long tranCounter,
    Connection con
  ) throws Exception {
    new BasicPreparedWriteQuery(
      SQL.Update.Osi.Organisms.FULL_RECORD,
      con,
      ps -> {
        ps.setInt(1, orgId);
        ps.setString(2, template);
        ps.setLong(3, geneCounter);
        ps.setLong(4, tranCounter);
      }
    ).execute();
  }

  private static int rsToSingleInt(ResultSet rs) throws SQLException {
    rs.next();
    return rs.getInt(1);
  }

  private static int getOrgId(ResultSet rs) throws SQLException {
    rs.next();
    return rs.getInt(Schema.Osi.Organisms.ORGANISM_ID);
  }

  private static Optional < Organism > rsToOptOrg(ResultSet rs)
  throws Exception {
    return rs.next()
      ? Optional.of(OrganismUtils.newOrganism(rs))
      : Optional.empty();
  }
}
