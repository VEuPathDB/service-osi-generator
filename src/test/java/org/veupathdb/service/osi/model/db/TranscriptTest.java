package org.veupathdb.service.osi.model.db;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.TestBase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TranscriptTest extends TestBase
{
  private long transcriptId, geneId, countStart, userId;

  private int numIssued;

  private OffsetDateTime created;

  private String json;

  private Transcript target;

  @Override
  @BeforeEach
  public void setUp() throws Exception {
    super.setUp();

    transcriptId = random.nextLong();
    geneId       = random.nextLong();
    countStart   = random.nextLong();
    numIssued    = random.nextInt();
    created      = randomDate();
    userId       = random.nextLong();
    json         = randomString();

    doReturn(transcriptId).when(mValidation).enforceOneMinimum(transcriptId);
    doReturn(geneId).when(mValidation).enforceOneMinimum(geneId);
    doReturn(countStart).when(mValidation).enforceOneMinimum(countStart);
    doReturn(numIssued).when(mValidation).enforceZeroMinimum(numIssued);
    doReturn(created).when(mValidation).enforceNonNull(created);
    doReturn(userId).when(mValidation).enforceOneMinimum(userId);

    target = new Transcript(
      transcriptId,
      geneId,
      countStart,
      numIssued,
      created,
      userId
    );

    doReturn(json).when(mJson).writeValueAsString(target);
  }

  @Test
  @DisplayName("Constructor validates inputs")
  void constructor() {
    verify(mValidation).enforceOneMinimum(transcriptId);
    verify(mValidation).enforceOneMinimum(geneId);
    verify(mValidation).enforceOneMinimum(countStart);
    verify(mValidation).enforceZeroMinimum(numIssued);
    verify(mValidation).enforceNonNull(created);
    verify(mValidation).enforceOneMinimum(userId);
  }

  @Test
  @DisplayName("Transcript ID getter returns expected value")
  void getTranscriptId() {
    assertEquals(transcriptId, target.getTranscriptId());
  }

  @Test
  @DisplayName("Gene ID getter returns expected value")
  void getGeneId() {
    assertEquals(geneId, target.getGeneId());
  }

  @Test
  @DisplayName("Transcript counter start value getter returns expected value")
  void getCounterStart() {
    assertEquals(countStart, target.getCounterStart());
  }

  @Test
  @DisplayName(
    "Transcript ID issued count start value getter returns expected value")
  void getNumIssued() {
    assertEquals(numIssued, target.getNumIssued());
  }

  @Test
  @DisplayName("Creation date getter returns expected value")
  void getCreatedOn() {
    assertSame(created, target.getCreatedOn());
  }

  @Test
  @DisplayName("User ID getter returns expected value")
  void getCreatedBy() {
    assertEquals(userId, target.getCreatedBy());
  }

  @Test
  @DisplayName("String serializer returns a non-null value.")
  void stringer() throws Exception {
    assertSame(json, target.toString());
    verify(mJson).writeValueAsString(target);
  }
}
