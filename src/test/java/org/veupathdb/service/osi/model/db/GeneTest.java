package org.veupathdb.service.osi.model.db;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Random;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.veupathdb.service.osi.util.Validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

class GeneTest
{
  private Validation mValidation;

  private long geneId, setId, userId;

  private String geneName;

  private OffsetDateTime createdOn;

  @BeforeEach
  void beforeEach() throws Exception {
    var random = new Random(System.currentTimeMillis());

    mValidation = mock(Validation.class);
    geneId = random.nextLong();
    setId  = random.nextLong();
    userId = random.nextLong();
    geneName = UUID.randomUUID().toString();
    createdOn = OffsetDateTime.ofInstant(
      Instant.ofEpochMilli(
        Math.abs(random.nextLong())),
      ZoneId.systemDefault());

    doReturn(geneId).when(mValidation).enforceOneMinimum(geneId);
    doReturn(setId).when(mValidation).enforceOneMinimum(setId);
    doReturn(userId).when(mValidation).enforceOneMinimum(userId);
    doReturn(geneName).when(mValidation).enforceNonEmpty(geneName);
    doReturn(createdOn).when(mValidation).enforceNonNull(createdOn);

    var inst = Validation.class.getDeclaredField("instance");
    inst.setAccessible(true);
    inst.set(null, mValidation);
  }

  @Test
  @DisplayName("Gene constructor validates inputs")
  void Gene() {
    var target = new Gene(geneId, setId, geneName, createdOn, userId);

    verify(mValidation).enforceOneMinimum(geneId);
    verify(mValidation).enforceOneMinimum(setId);
    verify(mValidation).enforceNonEmpty(geneName);
    verify(mValidation).enforceNonNull(createdOn);
    verify(mValidation).enforceOneMinimum(userId);
  }

  @Test
  @DisplayName("Gene ID getter returns expected value")
  void getId() {
    var target = new Gene(geneId, setId, geneName, createdOn, userId);

    assertEquals(geneId, target.getId());
  }

  @Test
  @DisplayName("ID Set ID getter returns expected value")
  void getIdSetId() {
    var target = new Gene(geneId, setId, geneName, createdOn, userId);

    assertEquals(setId, target.getIdSetId());
  }

  @Test
  @DisplayName("Gene name getter returns expected value")
  void getGeneIdentifier() {
    var target = new Gene(geneId, setId, geneName, createdOn, userId);

    assertSame(geneName, target.getGeneIdentifier());
  }

  @Test
  @DisplayName("Gene record creation date getter returns expected value")
  void getCreatedOn() {
    var target = new Gene(geneId, setId, geneName, createdOn, userId);

    assertSame(createdOn, target.getCreatedOn());
  }

  @Test
  @DisplayName("Gene record creation user getter returns expected value")
  void getCreatedBy() {
    var target = new Gene(geneId, setId, geneName, createdOn, userId);

    assertEquals(userId, target.getCreatedBy());
  }
}
