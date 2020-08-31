package org.veupathdb.service.osi.model.db;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.TestBase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NewTranscriptTest extends TestBase
{
  private Gene mGene;

  private User mUser;

  private long countStart;

  private int numIssued;

  private String json;

  private NewTranscript target;

  @Override
  @BeforeEach
  public void setUp() throws Exception {
    super.setUp();

    mGene      = mock(Gene.class);
    mUser      = mock(User.class);
    countStart = random.nextLong();
    numIssued  = random.nextInt();
    json       = randomString();

    doReturn(mGene).when(mValidation).enforceNonNull(mGene);
    doReturn(mUser).when(mValidation).enforceNonNull(mUser);
    doReturn(countStart).when(mValidation).enforceOneMinimum(countStart);
    doReturn(numIssued).when(mValidation).enforceZeroMinimum(numIssued);

    target = new NewTranscript(mGene, countStart, numIssued, mUser);

    doReturn(json).when(mJson).writeValueAsString(target);
  }

  @Test
  @DisplayName("Constructor validates inputs")
  void constructor() {
    verify(mValidation).enforceNonNull(mGene);
    verify(mValidation).enforceOneMinimum(countStart);
    verify(mValidation).enforceZeroMinimum(numIssued);
    verify(mValidation).enforceNonNull(mUser);
  }

  @Test
  @DisplayName("Gene getter returns expected value")
  void getGene() {
    assertSame(mGene, target.getGene());
  }

  @Test
  @DisplayName("Counter start getter returns expected value")
  void getCounterStart() {
    assertEquals(countStart, target.getCounterStart());
  }

  @Test
  @DisplayName("Issued ID count getter returns expected value")
  void getNumIssued() {
    assertEquals(numIssued, target.getNumIssued());
  }

  @Test
  @DisplayName("User getter returns expected value")
  void getCreatedBy() {
    assertSame(mUser, target.getCreatedBy());
  }

  @Test
  @DisplayName("String serializer returns a non-null value.")
  void stringer() throws Exception {
    assertSame(json, target.toString());
    verify(mJson).writeValueAsString(target);
  }
}
