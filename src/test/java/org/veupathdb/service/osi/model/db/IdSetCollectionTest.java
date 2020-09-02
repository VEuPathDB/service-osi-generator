package org.veupathdb.service.osi.model.db;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.TestBase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@DisplayName("IdSetCollection")
public class IdSetCollectionTest extends TestBase
{
  private long collectionId, createdBy;

  private String name, json;

  private OffsetDateTime createdOn;

  private IdSetCollection target;

  @BeforeEach
  public void setUp() throws Exception {
    super.setUp();

    collectionId = random.nextLong();
    name         = randomString();
    createdOn    = randomDate();
    createdBy    = random.nextLong();
    json         = randomString();

    doReturn(collectionId).when(mValidation).enforceOneMinimum(collectionId);
    doReturn(name).when(mValidation).enforceNonEmpty(name);
    doReturn(createdOn).when(mValidation).enforceNonNull(createdOn);
    doReturn(createdBy).when(mValidation).enforceOneMinimum(createdBy);

    target = new IdSetCollection(
      collectionId,
      name,
      createdOn,
      createdBy
    );

    doReturn(json).when(mJson).writeValueAsString(target);
  }

  @Test
  @DisplayName("Constructor validates inputs")
  void constructor() {
    verify(mValidation).enforceOneMinimum(collectionId);
    verify(mValidation).enforceNonEmpty(name);
    verify(mValidation).enforceNonNull(createdOn);
    verify(mValidation).enforceOneMinimum(createdBy);
  }

  @Test
  @DisplayName("Collection ID getter returns expected value")
  void getId() {
    assertEquals(collectionId, target.getId());
  }

  @Test
  @DisplayName("Collection name getter returns expected value")
  void getName() {
    assertSame(name, target.getName());
  }

  @Test
  @DisplayName("Collection record creation date getter returns expected value")
  void getCreatedOn() {
    assertSame(createdOn, target.getCreatedOn());
  }

  @Test
  @DisplayName("Collection ID getter returns expected value")
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
