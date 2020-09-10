package org.veupathdb.service.osi.service.organism;

import java.sql.Connection;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

import io.vulpine.lib.query.util.basic.BasicPreparedListReadQuery;
import io.vulpine.lib.query.util.basic.BasicPreparedReadQuery;
import io.vulpine.lib.query.util.basic.BasicPreparedWriteQuery;
import org.apache.logging.log4j.Logger;
import org.veupathdb.lib.container.jaxrs.providers.LogProvider;
import org.veupathdb.service.osi.db.SQL;
import org.veupathdb.service.osi.db.SQL.Select.Osi.Organisms;
import org.veupathdb.service.osi.model.RecordQuery;
import org.veupathdb.service.osi.model.db.NewOrganism;
import org.veupathdb.service.osi.model.db.Organism;
import org.veupathdb.service.osi.service.DbMan;
import org.veupathdb.service.osi.util.QueryUtil;

public class OrganismRepo
{
  @SuppressWarnings("FieldMayBeFinal")
  private static OrganismRepo instance = new OrganismRepo();

  private final Logger log = LogProvider.logger(getClass());

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
  public static long allocateTranscriptIds(final long id, final int count)
  throws Exception {
    return getInstance().incrementTranscriptCounter(id, count);
  }

  /**
   * @see #create(NewOrganism)
   */
  public static Organism insert(final NewOrganism organism) throws Exception {
    return getInstance().create(organism);
  }

  /**
   * @see #selectById(long)
   */
  public static Optional < Organism > selectById(final long organismId)
  throws Exception {
    return getInstance().getById(organismId);
  }

  /**
   * @see #selectById(long, Connection)
   */
  public static Optional < Organism > selectById(
    final long orgId,
    final Connection con
  ) throws Exception {
    return getInstance().getById(orgId, con);
  }

  /**
   * @see #selectByName(String)
   */
  public static Optional < Organism > selectByName(final String name)
  throws Exception {
    return getInstance().getByName(name);
  }

  /**
   * @see #selectByName(String, Connection)
   */
  public static Optional < Organism > selectByName(
    final String name,
    final Connection con
  ) throws Exception {
    return getInstance().getByName(name, con);
  }

  /**
   * @see #getByQuery(RecordQuery)
   */
  public static List < Organism > search(RecordQuery query)
  throws Exception {
    return getInstance().getByQuery(query);
  }

  /**
   * @see #updateTemplate(long, String, Connection)
   */
  public static void update(
    final long orgId,
    final String template,
    final Connection con
  ) throws Exception {
    getInstance().updateTemplate(orgId, template, con);
  }

  /**
   * @see #updateOrganism(long, String, long, long, Connection)
   */
  public static void update(
    final long orgId,
    final String template,
    final long geneCounter,
    final long tranCounter,
    final Connection con
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
    log.trace("OrganismRepo#incrementGeneCounter(long, int, Connection)");

    return new BasicPreparedReadQuery <>(
      SQL.Update.Osi.Organisms.GENE_COUNTER,
      con,
      QueryUtil.must(OrganismUtil::parseGeneCounter),
      ps -> {
        log.trace("OrganismRepo#incrementGeneCounter$prep(PreparedStatement)");
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
  public long incrementTranscriptCounter(final long id, final int count)
  throws Exception {
    log.trace("OrganismRepo#incrementTranscriptCounter(long, int)");

    return new BasicPreparedReadQuery <>(
      SQL.Update.Osi.Organisms.TRANSCRIPT_COUNTER,
      DbMan::connection,
      QueryUtil.must(OrganismUtil::parseTranscriptCounter),
      ps -> {
        log.trace("OrganismRepo#incrementTranscriptCounter$prep(PreparedStatement)");
        ps.setInt(1, count);
        ps.setLong(2, id);
      }
    ).execute().getValue();
  }

  public Organism create(final NewOrganism organism) throws Exception {
    log.trace("OrganismRepo#create(NewOrganism)");

    return new BasicPreparedReadQuery <>(
      SQL.Insert.Osi.ORGANISM,
      DbMan::connection,
      QueryUtil.must(rs -> OrganismUtil.newOrganismRow(rs, organism)),
      ps -> {
        log.trace("OrganismRepo#create$prep(PreparedStatement)");
        ps.setString(1, organism.getName());
        ps.setString(2, organism.getTemplate());
        ps.setLong(3, organism.getGeneCounterStart());
        ps.setLong(4, organism.getGeneCounterStart());
        ps.setLong(5, organism.getTranscriptCounterStart());
        ps.setLong(6, organism.getTranscriptCounterStart());
        ps.setLong(7, organism.getCreatedBy().getUserId());
      }
    ).execute().getValue();
  }

  public Optional < Organism > getById(final long organismId)
  throws Exception {
    log.trace("OrganismRepo#getById(long)");

    try (var con = DbMan.connection()) {
      return getById(organismId, con);
    }
  }

  public Optional < Organism > getById(final long orgId, final Connection con)
  throws Exception {
    log.trace("OrganismRepo#getById(long, Connection)");

    return new BasicPreparedReadQuery <>(
      Organisms.BY_ID,
      con,
      QueryUtil.option(OrganismUtil::newOrganismRow),
      QueryUtil.singleId(orgId)
    ).execute().getValue();
  }

  public Optional < Organism > getByName(final String name)
  throws Exception {
    log.trace("OrganismRepo#getByName(String)");

    try (var con = DbMan.connection()) {
      return selectByName(name, con);
    }
  }

  public Optional < Organism > getByName(
    final String name,
    final Connection con
  ) throws Exception {
    log.trace("OrganismRepo#getByName(String, Connection)");

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
  public List < Organism > getByQuery(final RecordQuery query)
  throws Exception {
    log.trace("OrganismRepo#getByQuery(RecordQuery)");

    return new BasicPreparedListReadQuery <>(
      Organisms.BY_QUERY,
      DbMan::connection,
      OrganismUtil::newOrganismRow,
      ps -> {
        log.trace("OrganismRepo#getByQuery$prep(PreparedStatement)");

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
    final long orgId,
    final String template,
    final Connection con
  ) throws Exception {
    log.trace("OrganismRepo#updateTemplate(long, String, Connection)");

    new BasicPreparedWriteQuery(
      SQL.Update.Osi.Organisms.TEMPLATE,
      con,
      ps -> {
        log.trace("OrganismRepo#updateTemplate$prep(PreparedStatement)");
        ps.setString(1, template);
        ps.setLong(2, orgId);
      }
    ).execute();
  }

  public void updateOrganism(
    final long orgId,
    final String template,
    final long geneCounter,
    final long tranCounter,
    final Connection con
  ) throws Exception {
    log.trace("OrganismRepo#updateOrganism(long, String, long, long, Connection)");

    new BasicPreparedWriteQuery(
      SQL.Update.Osi.Organisms.FULL_RECORD,
      con,
      ps -> {
        log.trace("OrganismRepo#updateOrganism$prep(PreparedStatement)");
        ps.setLong(1, orgId);
        ps.setString(2, template);
        ps.setLong(3, geneCounter);
        ps.setLong(4, tranCounter);
      }
    ).execute();
  }
}
