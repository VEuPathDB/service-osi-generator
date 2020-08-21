package org.veupathdb.service.osi.repo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

import org.veupathdb.service.osi.model.db.NewOrganism;
import org.veupathdb.service.osi.model.db.Organism;
import org.veupathdb.service.osi.model.db.User;

class Utils
{
  /**
   * Constructs a User record instance from the current cursor position of the
   * given result set.
   *
   * @param rs ResultSet from which to pull data to populate the user record.
   *
   * @return A new user record instance from the given ResultSet.
   *
   * @throws SQLException Thrown if a database error occurs while attempting to
   * retrieve row data.
   */
  static User userFromRs(final ResultSet rs) throws SQLException {
    return new User(
      rs.getInt(Schema.Auth.Users.USER_ID),
      rs.getString(Schema.Auth.Users.COLUMN_USER_EMAIL),
      rs.getString(Schema.Auth.Users.COLUMN_API_KEY),
      rs.getObject(Schema.Auth.Users.COLUMN_ISSUED, OffsetDateTime.class)
    );
  }

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
   * retrieve row data.
   */
  static Organism newOrganism(final ResultSet rs) throws SQLException {
    return new Organism(
      rs.getInt(Schema.Osi.Organisms.ORGANISM_ID),
      rs.getString(Schema.Osi.Organisms.TEMPLATE),
      rs.getLong(Schema.Osi.Organisms.GENE_COUNTER_START),
      rs.getLong(Schema.Osi.Organisms.GENE_COUNTER_CURRENT),
      rs.getLong(Schema.Osi.Organisms.TRANSCRIPT_COUNTER_START),
      rs.getLong(Schema.Osi.Organisms.TRANSCRIPT_COUNTER_CURRENT),
      userFromRs(rs),
      rs.getObject(Schema.Osi.Organisms.CREATED_ON, OffsetDateTime.class),
      rs.getObject(Schema.Osi.Organisms.LAST_MODIFIED, OffsetDateTime.class)
    );
  }

  static Organism newOrganism(ResultSet rs, NewOrganism org) throws SQLException {
    return new Organism(
      rs.getInt(Schema.Osi.Organisms.ORGANISM_ID),
      org.getGeneCounterStart(),
      org.getTranscriptCounterStart(),
      rs.getObject(Schema.Osi.Organisms.CREATED_ON, OffsetDateTime.class),
      rs.getObject(Schema.Osi.Organisms.LAST_MODIFIED, OffsetDateTime.class),
      org
    );
  }
}
