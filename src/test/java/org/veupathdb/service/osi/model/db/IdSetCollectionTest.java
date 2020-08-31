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

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class IdSetCollectionTest
{
  private Validation mValidation;

  private long collectionId, createdBy;

  private String name;

  private OffsetDateTime createdOn;

  @BeforeEach
  void setUp() throws Exception {
    var rand = new Random(System.currentTimeMillis());
    collectionId = rand.nextLong();
    name         = UUID.randomUUID().toString();
    createdOn    = OffsetDateTime.ofInstant(Instant.ofEpochMilli(
      Math.abs(rand.nextLong())), ZoneId.systemDefault());
    createdBy    = rand.nextLong();

    mValidation = mock(Validation.class);
    doReturn(collectionId).when(mValidation).enforceOneMinimum(collectionId);
    doReturn(name).when(mValidation).enforceNonEmpty(name);
    doReturn(createdOn).when(mValidation).enforceNonNull(createdOn);
    doReturn(createdBy).when(mValidation).enforceOneMinimum(createdBy);

    var inst = Validation.class.getDeclaredField("instance");
    inst.setAccessible(true);
    inst.set(null, mValidation);
  }

  @Test
  @DisplayName("Constructor validates inputs")
  void constructor() {
    new IdSetCollection(collectionId, name, createdOn, createdBy);
    verify(mValidation).enforceOneMinimum(collectionId);
    verify(mValidation).enforceNonEmpty(name);
    verify(mValidation).enforceNonNull(createdOn);
    verify(mValidation).enforceOneMinimum(createdBy);
  }

  @Test
  @DisplayName("Collection ID getter returns expected value")
  void getId() {
    var target = new IdSetCollection(collectionId, name, createdOn, createdBy);
    assertEquals(collectionId, target.getId());
  }

  @Test
  @DisplayName("Collection name getter returns expected value")
  void getName() {
    var target = new IdSetCollection(collectionId, name, createdOn, createdBy);
    assertSame(name, target.getName());
  }

  @Test
  @DisplayName("Collection record creation date getter returns expected value")
  void getCreatedOn() {
    var target = new IdSetCollection(collectionId, name, createdOn, createdBy);
    assertSame(createdOn, target.getCreatedOn());
  }

  @Test
  @DisplayName("Collection ID getter returns expected value")
  void getCreatedBy() {
    var target = new IdSetCollection(collectionId, name, createdOn, createdBy);
    assertEquals(createdBy, target.getCreatedBy());
  }
}
