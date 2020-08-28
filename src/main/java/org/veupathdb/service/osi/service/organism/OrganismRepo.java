package org.veupathdb.service.osi.service.organism;

import java.sql.Connection;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

import io.vulpine.lib.query.util.basic.BasicPreparedListReadQuery;
import io.vulpine.lib.query.util.basic.BasicPreparedReadQuery;
import io.vulpine.lib.query.util.basic.BasicPreparedWriteQuery;
import org.veupathdb.service.osi.model.RecordQuery;
import org.veupathdb.service.osi.model.db.NewOrganism;
import org.veupathdb.service.osi.model.db.Organism;
import org.veupathdb.service.osi.repo.SQL;
import org.veupathdb.service.osi.repo.SQL.Select.Osi.Organisms;
import org.veupathdb.service.osi.service.DbMan;
import org.veupathdb.service.osi.util.QueryUtil;

public class OrganismRepo
{
  private static final OrganismRepo instance = new OrganismRepo();

  // ╔════════════════════════════════════════════════════════════════════╗ //
  // ║                                                                    ║ //
  // ║    Static Access Methods                                           ║ //
  // ║                                                                    ║ //
  // ╚════════════════════════════════════════════════════════════════════╝ //

  public static OrganismRepo getInstance() {
    return instance;
  }

  /**
   * @see #incrementGeneCounter(long, int, Connection)
   */
  public static long allocateGeneIds(
    final long organismId,
    final int incrementBy,
    final Connection con
  ) throws Exception {
    return getInstance().incrementGeneCounter(organismId, incrementBy, con);
  }

  /**
   * @see #incrementTranscriptCounter(long, int)
   */
  public static long allocateTranscriptIds(long id, int count)
  throws Exception {
    return getInstance().incrementTranscriptCounter(id, count);
  }

  public static Organism insert(NewOrganism organism) throws Exception {
    return getInstance().create(organism);
  }

  public static Optional < Organism > selectById(long organismId)
  throws Exception {
    return getInstance().getById(organismId);
  }

  public static Optional < Organism > selectById(long orgId, Connection con)
  throws Exception {
    return getInstance().getById(orgId, con);
  }

  public static Optional < Organism > selectByName(String name)
  throws Exception {
    return getInstance().getByName(name);
  }

  public static Optional < Organism > selectByName(String name, Connection con)
  throws Exception {
    return getInstance().getByName(name, con);
  }

  /**
   * @see #getByQuery(RecordQuery)
   */
  public static List < Organism > search(RecordQuery query)
  throws Exception {
    return getInstance().getByQuery(query);
  }

  public static void update(
    long orgId,
    String template,
    Connection con
  ) throws Exception {
    getInstance().updateTemplate(orgId, template, con);
  }

  public static void update(
    long orgId,
    String template,
    long geneCounter,
    long tranCounter,
    Connection con
  ) throws Exception {
    getInstance().updateOrganism(
      orgId,
      template,
      geneCounter,
      tranCounter,
      con
    );
  }

  // ╔════════════════════════════════════════════════════════════════════╗ //
  // ║                                                                    ║ //
  // ║    Mockable Instance Methods                                       ║ //
  // ║                                                                    ║ //
  // ╚════════════════════════════════════════════════════════════════════╝ //

  /**
   * Attempts to allocate <code>count</code> sequential gene id int values,
   * returning the first gene id value in the allocated block.
   *
   * @param id    organism id
   * @param count number of gene id int values to allocate
   *
   * @return first gene id int value in the allocated block.
   */
  public long incrementGeneCounter(
    final long id,
    final int count,
    final Connection con
  ) throws Exception {
    return new BasicPreparedReadQuery <>(
      SQL.Update.Osi.Organisms.GENE_COUNTER,
      con,
      QueryUtil.must(OrganismUtil::parseGeneCounter),
      ps -> {
        ps.setInt(1, count);
        ps.setLong(2, id);
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
  public long incrementTranscriptCounter(long id, int count) throws Exception {
    return new BasicPreparedReadQuery <>(
      SQL.Update.Osi.Organisms.TRANSCRIPT_COUNTER,
      DbMan::connection,
      QueryUtil.must(OrganismUtil::parseTranscriptCounter),
      ps -> {
        ps.setInt(1, count);
        ps.setLong(2, id);
      }
    ).execute().getValue();
  }

  public Organism create(NewOrganism organism) throws Exception {
    return new BasicPreparedReadQuery <>(
      SQL.Insert.Osi.ORGANISM,
      DbMan::connection,
      QueryUtil.must(rs -> OrganismUtil.newOrganismRow(rs, organism)),
      ps -> {
        ps.setString(1, organism.getTemplate());
        ps.setLong(2, organism.getGeneCounterStart());
        ps.setLong(3, organism.getGeneCounterStart());
        ps.setLong(4, organism.getTranscriptCounterStart());
        ps.setLong(5, organism.getTranscriptCounterStart());
        ps.setLong(6, organism.getCreatedBy().getUserId());
      }
    ).execute().getValue();
  }

  public Optional < Organism > getById(long organismId)
  throws Exception {
    try (var con = DbMan.connection()) {
      return getById(organismId, con);
    }
  }

  public Optional < Organism > getById(long orgId, Connection con)
  throws Exception {
    return new BasicPreparedReadQuery <>(
      Organisms.BY_ID,
      con,
      QueryUtil.option(OrganismUtil::newOrganismRow),
      QueryUtil.singleId(orgId)
    ).execute().getValue();
  }

  public Optional < Organism > getByName(String name)
  throws Exception {
    try (var con = DbMan.connection()) {
      return selectByName(name, con);
    }
  }

  public Optional < Organism > getByName(String name, Connection con)
  throws Exception {
    return new BasicPreparedReadQuery <>(
      Organisms.BY_NAME,
      con,
      QueryUtil.option(OrganismUtil::newOrganismRow),
      QueryUtil.singleString(name)
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
  public List < Organism > getByQuery(RecordQuery query)
  throws Exception {
    return new BasicPreparedListReadQuery <>(
      Organisms.BY_QUERY,
      DbMan::connection,
      OrganismUtil::newOrganismRow,
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
          ps.setNull(4, Types.BIGINT);
        else
          ps.setLong(4, query.getCreatedById());

        if (query.getCreatedByName() == null)
          ps.setNull(5, Types.VARCHAR);
        else
          ps.setString(5, query.getCreatedByName());
      }
    ).execute().getValue();
  }

  public void updateTemplate(
    long orgId,
    String template,
    Connection con
  ) throws Exception {
    new BasicPreparedWriteQuery(
      SQL.Update.Osi.Organisms.TEMPLATE,
      con,
      ps -> {
        ps.setLong(1, orgId);
        ps.setString(2, template);
      }
    ).execute();
  }

  public void updateOrganism(
    long orgId,
    String template,
    long geneCounter,
    long tranCounter,
    Connection con
  ) throws Exception {
    new BasicPreparedWriteQuery(
      SQL.Update.Osi.Organisms.FULL_RECORD,
      con,
      ps -> {
        ps.setLong(1, orgId);
        ps.setString(2, template);
        ps.setLong(3, geneCounter);
        ps.setLong(4, tranCounter);
      }
    ).execute();
  }
}
