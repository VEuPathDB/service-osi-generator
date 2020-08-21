package org.veupathdb.service.osi.repo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.Map;

import org.veupathdb.service.osi.model.db.*;
import org.veupathdb.service.osi.repo.Schema.JoinPrefixes;
import org.veupathdb.service.osi.repo.Schema.Osi.*;

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
   *                      retrieve row data.
   */
  static User newUser(final ResultSet rs) throws SQLException {
    return newUser(rs, "");
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

  private static Organism newOrganism(ResultSet rs, Map <Integer, User> cache)
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
   * Constructs a new {@link Transcript} instance from the given
   * {@link ResultSet}.
   * <p>
   * This constructor was built around the joined transcript queries and expects
   * the column names to be prefixed with the standard values defined in
   * {@link JoinPrefixes}.
   * <p>
   * This method will construct the parent types all the way up to the top level
   * types {@link Organism} and {@link User}.
   *
   * @param rs ResultSet from which to pull the row data.
   *
   * @return A new Transcript instance.
   */
  static Transcript newTranscript(ResultSet rs)
  throws Exception {
    var tPref = JoinPrefixes.TRANSCRIPTS;

    return new Transcript(
      newGene(rs),
      rs.getLong(prefix(tPref, Transcripts.COUNTER_START)),
      rs.getInt(prefix(tPref, Transcripts.NUM_ISSUED)),
      newUser(rs, JoinPrefixes.TRANSCRIPT_USER),
      rs.getObject(prefix(tPref, Transcripts.CREATED_ON), OffsetDateTime.class)
    );
  }

  static Transcript newTranscript(ResultSet rs, Map < Integer, User > cache)
  throws Exception {
    var tPref = JoinPrefixes.TRANSCRIPTS;

    return new Transcript(
      newGene(rs, cache),
      rs.getLong(prefix(tPref, Transcripts.COUNTER_START)),
      rs.getInt(prefix(tPref, Transcripts.NUM_ISSUED)),
      cache.get(rs.getInt(prefix(tPref, Transcripts.CREATED_BY))),
      rs.getObject(prefix(tPref, Transcripts.CREATED_ON), OffsetDateTime.class)
    );
  }

  static Gene newGene(ResultSet rs) throws Exception {
    final var gPref = JoinPrefixes.GENES;
    final var uPref = JoinPrefixes.GENE_USER;

    return new Gene(
      rs.getInt(prefix(gPref, Genes.GENE_ID)),
      newIdSet(rs, true),
      rs.getString(prefix(gPref, Genes.GENE_NAME)),
      newUser(rs, uPref),
      rs.getObject(prefix(gPref, Genes.CREATED_ON), OffsetDateTime.class)
    );
  }

  static Gene newGene(ResultSet rs, Map < Integer, User > cache)
  throws Exception {
    final var gPref = JoinPrefixes.GENES;
    final var uPref = JoinPrefixes.GENE_USER;

    return new Gene(
      rs.getInt(prefix(gPref, Genes.GENE_ID)),
      newIdSet(rs, true),
      rs.getString(prefix(gPref, Genes.GENE_NAME)),
      cache.get(rs.getInt(prefix(gPref, Genes.CREATED_BY))),
      rs.getObject(prefix(gPref, Genes.CREATED_ON), OffsetDateTime.class)
    );
  }

  private static IdSet newIdSet(ResultSet rs, boolean joined)
  throws Exception {
    final var sPref = joined ? JoinPrefixes.ID_SET : "";
    final var uPref = joined ? JoinPrefixes.ID_SET_USER : "";

    return new IdSet(
      rs.getInt(prefix(sPref, IdSets.ID_SET_ID)),
      newIdSetCollection(rs, joined),
      newOrganism(rs, joined),
      rs.getString(prefix(sPref, IdSets.TEMPLATE)),
      newUser(rs, uPref),
      rs.getObject(prefix(sPref, IdSets.CREATED_ON), OffsetDateTime.class)
    );
  }

  private static IdSetCollection newIdSetCollection(
    ResultSet rs,
    boolean joined
  ) throws Exception {
    final var cPref = joined ? JoinPrefixes.ID_SET_COLLECTION : "";
    final var uPref = joined ? JoinPrefixes.ID_SET_COLLECTION_USER : "";

    return new IdSetCollection(
      rs.getInt(prefix(cPref, IdSetCollections.COLLECTION_ID)),
      rs.getString(prefix(cPref, IdSetCollections.NAME)),
      newUser(rs, uPref),
      rs.getObject(
        prefix(cPref, IdSetCollections.CREATED_ON),
        OffsetDateTime.class
      )
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
