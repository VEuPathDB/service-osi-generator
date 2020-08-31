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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IdSetTest
{
  private OrganismUtil mOrgUtil;

  private Validation mValidation;

  private long idSetId, collectionId, organismId, counterStart, createdBy;

  private String template;

  private int numIssued;

  private OffsetDateTime createdOn;

  @BeforeEach
  void setUp() throws Exception {
    mOrgUtil    = mock(OrganismUtil.class);
    mValidation = mock(Validation.class);

    var orgInst = OrganismUtil.class.getDeclaredField("instance");
    orgInst.setAccessible(true);
    orgInst.set(null, mOrgUtil);

    var valInst = Validation.class.getDeclaredField("instance");
    valInst.setAccessible(true);
    valInst.set(null, mValidation);

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

    doReturn(idSetId).when(mValidation).enforceOneMinimum(idSetId);
    doReturn(collectionId).when(mValidation).enforceOneMinimum(collectionId);
    doReturn(organismId).when(mValidation).enforceOneMinimum(organismId);
    doReturn(counterStart).when(mValidation).enforceOneMinimum(counterStart);
    doReturn(createdBy).when(mValidation).enforceOneMinimum(createdBy);
    doReturn(numIssued).when(mValidation).enforceZeroMinimum(numIssued);
    doReturn(template).when(mOrgUtil).enforceOrgPattern(template);
    doReturn(createdOn).when(mValidation).enforceNonNull(createdOn);

    doReturn(template).when(mOrgUtil).enforceOrgPattern(template);
  }

  @Test
  @DisplayName("constructor validates inputs")
  void constructor() {
    newIdSet();
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
    var target = newIdSet();
    assertEquals(idSetId, target.getId());
  }

  @Test
  @DisplayName("Collection ID getter returns expected value")
  void getCollectionId() {
    var target = newIdSet();
    assertEquals(collectionId, target.getCollectionId());
  }

  @Test
  @DisplayName("Organism ID getter returns expected value")
  void getOrganismId() {
    var target = newIdSet();
    assertEquals(organismId, target.getOrganismId());
  }

  @Test
  @DisplayName("Template getter returns expected value")
  void getTemplate() {
    var target = newIdSet();
    assertSame(template, target.getTemplate());
  }

  @Test
  @DisplayName("Counter start point getter returns expected value")
  void getCounterStart() {
    var target = newIdSet();
    assertEquals(counterStart, target.getCounterStart());
  }

  @Test
  @DisplayName("Issued gene ID counter getter returns expected value")
  void getNumIssued() {
    var target = newIdSet();
    assertEquals(numIssued, target.getNumIssued());
  }

  @Test
  @DisplayName("ID set record creation date getter returns expected value")
  void getCreatedOnId() {
    var target = newIdSet();
    assertSame(createdOn, target.getCreatedOn());
  }

  @Test
  @DisplayName("ID set record created by user getter returns expected value")
  void getCreatedBy() {
    var target = newIdSet();
    assertEquals(createdBy, target.getCreatedBy());
  }

  IdSet newIdSet() {
    return new IdSet(
      idSetId,
      collectionId,
      organismId,
      template,
      counterStart,
      numIssued,
      createdOn,
      createdBy
    );
  }
}
