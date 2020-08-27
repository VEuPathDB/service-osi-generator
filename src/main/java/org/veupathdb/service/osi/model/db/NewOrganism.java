package org.veupathdb.service.osi.model.db;

import org.veupathdb.service.osi.util.Errors;
import org.veupathdb.service.osi.util.Format;
import org.veupathdb.service.osi.util.InputValidationException;
import org.veupathdb.service.osi.util.Validation;

/**
 * Immutable representation of a partial Organism record.
 * <p>
 * This type is for use in the creation of a new Organism record where database
 * assigned fields such as ID and creation timestamp have not yet been assigned.
 * </p>
 */
public class NewOrganism
{
  private final String name;

  private final String template;

  private final long geneCounterStart;

  private final long transcriptCounterStart;

  private final User createdBy;

  /**
   * Creates a new {@code NewOrganism} instance with the given values.
   *
   * @param name             Unique identifying name of the Organism.
   * @param template         Unique template string from which gene ids will be
   *                         generated for this organism.
   * @param geneCounterStart Starting point for the gene id generation integer
   *                         component.
   * @param tranCounterStart Starting point for the transcript id generation
   *                         integer component.
   * @param createdBy        The user record for the user requesting this
   *                         organism record be created.
   *
   * @throws InputValidationException if any of the {@code name},
   * {@code template}, or {@code createdBy} parameters are null; if either of
   * the {@code name} or {@code template} parameters are blank/empty; or if
   * either of the {@code geneCounterStart} or {@code tranCounterStart} fields
   * are less than {@code 1}.
   */
  public NewOrganism(
    String name,
    String template,
    long geneCounterStart,
    long tranCounterStart,
    User createdBy
  ) {
    this.name                   = Validation.nonEmpty(name);
    this.template               = Validation.nonEmpty(template);
    this.geneCounterStart       = Validation.oneMinimum(geneCounterStart);
    this.transcriptCounterStart = Validation.oneMinimum(tranCounterStart);
    this.createdBy              = createdBy;
  }

  /**
   * Returns the name value for this record.
   *
   * @return the name value for this record.
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the template string for this record.
   *
   * @return the template string for this record.
   */
  public String getTemplate() {
    return template;
  }

  /**
   * Returns the gene id integer component starting point.
   *
   * @return the gene id integer component starting point.
   */
  public long getGeneCounterStart() {
    return geneCounterStart;
  }

  /**
   * Returns the transcript id integer component starting point.
   *
   * @return the transcript id integer component starting point.
   */
  public long getTranscriptCounterStart() {
    return transcriptCounterStart;
  }

  /**
   * Returns an immutable representation of the user record representing the
   * user that requested the creation of this organism record.
   *
   * @return the user record for the user that requested the creation of this
   * record.
   */
  public User getCreatedBy() {
    return createdBy;
  }

  @Override
  public String toString() {
    return Errors.toRuntime(this, Format.Json()::writeValueAsString);
  }
}
