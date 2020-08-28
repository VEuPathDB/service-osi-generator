package org.veupathdb.service.osi.service.organism;

import java.sql.Connection;
import java.util.function.Supplier;

import io.vulpine.lib.query.util.basic.BasicStatementVoidQuery;
import org.veupathdb.service.osi.model.db.Organism;
import org.veupathdb.service.osi.repo.SQL;
import org.veupathdb.service.osi.service.DbMan;

import static org.veupathdb.service.osi.service.organism.OrganismRepo.selectById;
import static org.veupathdb.service.osi.service.organism.OrganismRepo.selectByName;

class OrganismUpdater implements AutoCloseable
{
  interface ErrProvider extends Supplier < RuntimeException > {
    RuntimeException get();
  }

  private final Connection con;

  private final Organism org;

  private OrganismUpdater(Connection con, Organism org) {
    this.con = con;
    this.org = org;
  }

  public boolean canUpdateCounters() {
    return org.getGeneCounterCurrent() == org.getGeneCounterStart()
      && org.getTranscriptCounterCurrent() == org.getTranscriptCounterStart();
  }

  public Organism getOrganism() {
    return org;
  }

  public void update(String template) throws Exception {
    OrganismRepo.update(org.getId(), template, con);
    con.commit();
  }

  public void update(String template, long geneCounter, long tranCounter)
  throws Exception {
    if (!canUpdateCounters())
      throw new IllegalStateException();

    OrganismRepo.update(org.getId(), template, geneCounter, tranCounter, con);
    con.commit();
  }

  @Override
  public void close() throws Exception {
    con.close();
  }

  static OrganismLoader begin() throws Exception {
    var con = DbMan.getInstance().getConnection();
    con.setAutoCommit(false);
    new BasicStatementVoidQuery(SQL.Lock.Osi.ORGANISMS, con).execute();
    return new OrganismLoader(con);
  }

  static class OrganismLoader implements AutoCloseable {
    private final Connection con;

    private OrganismLoader(Connection con) {
      this.con = con;
    }

    @Override
    public void close() throws Exception {
      con.close();
    }

    public OrganismUpdater loadOrganism(final long id, ErrProvider err)
    throws Exception {
      return new OrganismUpdater(con,
        selectById(id, con).orElseThrow(err));
    }

    public OrganismUpdater loadOrganism(final String name, ErrProvider err)
    throws Exception {
      return new OrganismUpdater(con,
        selectByName(name, con).orElseThrow(err));
    }
  }
}
