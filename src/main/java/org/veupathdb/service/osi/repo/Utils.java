package org.veupathdb.service.osi.repo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

import org.veupathdb.service.osi.model.db.*;
import org.veupathdb.service.osi.repo.Schema.JoinPrefixes;
import org.veupathdb.service.osi.repo.Schema.Osi.*;

class Utils
{

  /**
   * Constructs an Organism record instance from the current cursor position of
   * the given result set.
   *
   * @param rs ResultSet from which to pull data to populate the organism
   *           record.
   *
   * @return A new organism record instance from the given ResultSet.
   *
   * @throws SQLException Thrown if a database error occurs while attempting to
   *                      retrieve row data.
   */
  static Organism newOrganism(ResultSet rs) throws SQLException {
    return newOrganism(rs, false);
  }

  static Organism newOrganism(ResultSet rs, NewOrganism org)
  throws SQLException {
    return new Organism(
      rs.getInt(Organisms.ORGANISM_ID),
      org.getGeneCounterStart(),
      org.getTranscriptCounterStart(),
      rs.getObject(Organisms.CREATED_ON, OffsetDateTime.class),
      rs.getObject(Organisms.LAST_MODIFIED, OffsetDateTime.class),
      org
    );
  }

  private static Organism newOrganism(ResultSet rs, boolean joined)
  throws SQLException {
    var oPref = joined ? JoinPrefixes.ORGANISMS : "";
    var uPref = joined ? JoinPrefixes.ORGANISM_USER : "";

    return new Organism(
      rs.getInt(prefix(oPref, Organisms.ORGANISM_ID)),
      rs.getString(prefix(oPref, Organisms.TEMPLATE)),
      rs.getLong(prefix(oPref, Organisms.GENE_COUNTER_START)),
      rs.getLong(prefix(oPref, Organisms.GENE_COUNTER_CURRENT)),
      rs.getLong(prefix(oPref, Organisms.TRANSCRIPT_COUNTER_START)),
      rs.getLong(prefix(oPref, Organisms.TRANSCRIPT_COUNTER_CURRENT)),
      newUser(rs, uPref),
      rs.getObject(prefix(oPref, Organisms.CREATED_ON), OffsetDateTime.class),
      rs.getObject(prefix(oPref, Organisms.LAST_MODIFIED), OffsetDateTime.class)
    );
  }

  /**
   * Constructs a new User instance from the given {@link ResultSet}.
   * <p>
   * A prefix may be provided to distinguish between multiple user instances in
   * a single result row.
   *
   * @param rs     ResultSet from which the user data should be pulled.
   * @param prefix Prefix to prepend onto the standard user column names when
   *               fetching data from the result row.
   *
   * @return a new User instance.
   */
  private static User newUser(ResultSet rs, String prefix) throws SQLException {
    return new User(
      rs.getInt(prefix(prefix, Schema.Auth.Users.USER_ID)),
      rs.getString(prefix(prefix, Schema.Auth.Users.COLUMN_USER_EMAIL)),
      rs.getString(prefix(prefix, Schema.Auth.Users.COLUMN_API_KEY)),
      rs.getObject(
        prefix(prefix, Schema.Auth.Users.COLUMN_ISSUED),
        OffsetDateTime.class
      )
    );
  }

  private static String prefix(String pref, String col) {
    return pref + col;
  }
}
