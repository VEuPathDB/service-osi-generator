package org.veupathdb.service.osi.model.db;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Random;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.veupathdb.service.osi.service.organism.OrganismUtil;
import org.veupathdb.service.osi.util.Validation;
import util.TestBase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IdSetTest extends TestBase
{
  private OrganismUtil mOrgUtil;

  private long idSetId, collectionId, organismId, counterStart, createdBy;

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

    var rand = new Random(System.currentTimeMillis());

    idSetId      = rand.nextLong();
    collectionId = rand.nextLong();
    organismId   = rand.nextLong();
    counterStart = rand.nextLong();
    createdBy    = rand.nextLong();
    template     = UUID.randomUUID().toString();
    numIssued    = rand.nextInt();
    createdOn    = OffsetDateTime.ofInstant(Instant.ofEpochMilli(
      Math.abs(rand.nextLong())), ZoneId.systemDefault());
    json = "some json string";

    doReturn(idSetId).when(mValidation).enforceOneMinimum(idSetId);
    doReturn(collectionId).when(mValidation).enforceOneMinimum(collectionId);
    doReturn(organismId).when(mValidation).enforceOneMinimum(organismId);
    doReturn(counterStart).when(mValidation).enforceOneMinimum(counterStart);
    doReturn(createdBy).when(mValidation).enforceOneMinimum(createdBy);
    doReturn(numIssued).when(mValidation).enforceZeroMinimum(numIssued);
    doReturn(template).when(mOrgUtil).enforceOrgPattern(template);
    doReturn(createdOn).when(mValidation).enforceNonNull(createdOn);

    doReturn(template).when(mOrgUtil).enforceOrgPattern(template);

    target = new IdSet(
      idSetId,
      collectionId,
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
    verify(mValidation).enforceOneMinimum(collectionId);
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
  @DisplayName("Collection ID getter returns expected value")
  void getCollectionId() {
    assertEquals(collectionId, target.getCollectionId());
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
  void stringify() throws Exception{
    assertSame(json, target.toString());
    verify(mJson).writeValueAsString(target);
  }
}
