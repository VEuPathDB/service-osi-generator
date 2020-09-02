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

@DisplayName("Gene")
class GeneTest extends TestBase
{
  private long geneId, setId, userId;

  private String geneName, json;

  private OffsetDateTime createdOn;

  private Gene target;

  @BeforeEach
  public void setUp() throws Exception {
    super.setUp();

    json      = "some json string";
    geneId    = random.nextLong();
    setId     = random.nextLong();
    userId    = random.nextLong();
    geneName  = randomString();
    createdOn = randomDate();

    doReturn(geneId).when(mValidation).enforceOneMinimum(geneId);
    doReturn(setId).when(mValidation).enforceOneMinimum(setId);
    doReturn(userId).when(mValidation).enforceOneMinimum(userId);
    doReturn(geneName).when(mValidation).enforceNonEmpty(geneName);
    doReturn(createdOn).when(mValidation).enforceNonNull(createdOn);

    target = new Gene(
      geneId,
      setId,
      geneName,
      createdOn,
      userId
    );

    doReturn(json).when(mJson).writeValueAsString(target);
  }

  @Test
  @DisplayName("Gene constructor validates inputs")
  void Gene() {
    verify(mValidation).enforceOneMinimum(geneId);
    verify(mValidation).enforceOneMinimum(setId);
    verify(mValidation).enforceNonEmpty(geneName);
    verify(mValidation).enforceNonNull(createdOn);
    verify(mValidation).enforceOneMinimum(userId);
  }

  @Test
  @DisplayName("Gene ID getter returns expected value")
  void getId() {
    assertEquals(geneId, target.getId());
  }

  @Test
  @DisplayName("ID Set ID getter returns expected value")
  void getIdSetId() {
    assertEquals(setId, target.getIdSetId());
  }

  @Test
  @DisplayName("Gene name getter returns expected value")
  void getGeneIdentifier() {
    assertSame(geneName, target.getGeneIdentifier());
  }

  @Test
  @DisplayName("Gene record creation date getter returns expected value")
  void getCreatedOn() {
    assertSame(createdOn, target.getCreatedOn());
  }

  @Test
  @DisplayName("Gene record creation user getter returns expected value")
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
