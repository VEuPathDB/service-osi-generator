package org.veupathdb.service.osi.model.db;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.veupathdb.service.osi.service.organism.OrganismUtil;
import util.TestBase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@DisplayName("IdSet")
class IdSetTest extends TestBase
{
  private OrganismUtil mOrgUtil;

  private long idSetId, organismId, counterStart, createdBy;

  private String template, json;

  private int numIssued;

  private OffsetDateTime createdOn;

  private IdSet target;

  @BeforeEach
  public void setUp() throws Exception {
    super.setUp();

    mOrgUtil = mock(OrganismUtil.class);

    var orgInst = OrganismUtil.class.getDeclaredField("instance");
    orgInst.setAccessible(true);
    orgInst.set(null, mOrgUtil);

    idSetId      = random.nextLong();
    organismId   = random.nextLong();
    counterStart = random.nextLong();
    createdBy    = random.nextLong();
    template     = randomString();
    numIssued    = random.nextInt();
    createdOn    = randomDate();
    json         = randomString();

    doReturn(idSetId).when(mValidation).enforceOneMinimum(idSetId);
    doReturn(organismId).when(mValidation).enforceOneMinimum(organismId);
    doReturn(counterStart).when(mValidation).enforceOneMinimum(counterStart);
    doReturn(createdBy).when(mValidation).enforceOneMinimum(createdBy);
    doReturn(numIssued).when(mValidation).enforceZeroMinimum(numIssued);
    doReturn(template).when(mOrgUtil).enforceOrgPattern(template);
    doReturn(createdOn).when(mValidation).enforceNonNull(createdOn);

    doReturn(template).when(mOrgUtil).enforceOrgPattern(template);

    target = new IdSet(
      idSetId,
      organismId,
      template,
      counterStart,
      numIssued,
      createdOn,
      createdBy
    );

    doReturn(json).when(mJson).writeValueAsString(target);
  }

  @Test
  @DisplayName("constructor validates inputs")
  void constructor() {
    verify(mValidation).enforceOneMinimum(idSetId);
    verify(mValidation).enforceOneMinimum(organismId);
    verify(mOrgUtil).enforceOrgPattern(template);
    verify(mValidation).enforceOneMinimum(counterStart);
    verify(mValidation).enforceZeroMinimum(numIssued);
    verify(mValidation).enforceNonNull(createdOn);
    verify(mValidation).enforceOneMinimum(createdBy);
  }

  @Test
  @DisplayName("ID set ID getter returns expected value")
  void getId() {
    assertEquals(idSetId, target.getId());
  }

  @Test
  @DisplayName("Organism ID getter returns expected value")
  void getOrganismId() {
    assertEquals(organismId, target.getOrganismId());
  }

  @Test
  @DisplayName("Template getter returns expected value")
  void getTemplate() {
    assertSame(template, target.getTemplate());
  }

  @Test
  @DisplayName("Counter start point getter returns expected value")
  void getCounterStart() {
    assertEquals(counterStart, target.getCounterStart());
  }

  @Test
  @DisplayName("Issued gene ID count getter returns expected value")
  void getNumIssued() {
    assertEquals(numIssued, target.getNumIssued());
  }

  @Test
  @DisplayName("ID set record creation date getter returns expected value")
  void getCreatedOnId() {
    assertSame(createdOn, target.getCreatedOn());
  }

  @Test
  @DisplayName("ID set record created by user getter returns expected value")
  void getCreatedBy() {
    assertEquals(createdBy, target.getCreatedBy());
  }

  @Test
  @DisplayName("String serialization returns JSON")
  void stringify() throws Exception {
    assertSame(json, target.toString());
    verify(mJson).writeValueAsString(target);
  }
}
