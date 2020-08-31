package org.veupathdb.service.osi.model.db;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.veupathdb.service.osi.service.organism.OrganismUtil;
import util.TestBase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrganismTest extends TestBase
{
  private OrganismUtil mOrgUtil;

  private long geneId, gCountStart, gCountCurr, tCountStart, tCountCurr, userId;

  private String name, template, json;

  private OffsetDateTime created, modified;

  private Organism target;

  @Override
  @BeforeEach
  public void setUp() throws Exception {
    super.setUp();

    geneId      = random.nextLong();
    name        = randomString();
    template    = randomString();
    gCountStart = random.nextLong();
    gCountCurr  = random.nextLong();
    tCountStart = random.nextLong();
    tCountCurr  = random.nextLong();
    created     = randomDate();
    modified    = randomDate();
    userId      = random.nextLong();
    json        = randomString();
    mOrgUtil    = mock(OrganismUtil.class);

    var inst = OrganismUtil.class.getDeclaredField("instance");
    inst.setAccessible(true);
    inst.set(null, mOrgUtil);

    doReturn(geneId).when(mValidation).enforceOneMinimum(geneId);
    doReturn(name).when(mValidation).enforceNonEmpty(name);
    doReturn(template).when(mOrgUtil).enforceOrgPattern(template);
    doReturn(gCountStart).when(mValidation).enforceOneMinimum(gCountStart);
    doReturn(gCountCurr).when(mValidation)
      .enforceMinimum(gCountStart, gCountCurr);
    doReturn(tCountStart).when(mValidation).enforceOneMinimum(tCountStart);
    doReturn(tCountCurr).when(mValidation)
      .enforceMinimum(tCountStart, tCountCurr);
    doReturn(created).when(mValidation).enforceNonNull(created);
    doReturn(modified).when(mValidation).enforceNonNull(modified);
    doReturn(userId).when(mValidation).enforceOneMinimum(userId);

    target = new Organism(
      geneId,
      name,
      template,
      gCountStart,
      gCountCurr,
      tCountStart,
      tCountCurr,
      created,
      modified,
      userId
    );

    doReturn(json).when(mJson).writeValueAsString(target);
  }

  @Test
  @DisplayName("Constructor validates inputs")
  void constructor() {
    verify(mValidation).enforceOneMinimum(geneId);
    verify(mValidation).enforceNonEmpty(name);
    verify(mOrgUtil).enforceOrgPattern(template);
    verify(mValidation).enforceOneMinimum(gCountStart);
    verify(mValidation).enforceMinimum(gCountStart, gCountCurr);
    verify(mValidation).enforceOneMinimum(tCountStart);
    verify(mValidation).enforceMinimum(tCountStart, tCountCurr);
    verify(mValidation).enforceNonNull(created);
    verify(mValidation).enforceNonNull(modified);
    verify(mValidation).enforceOneMinimum(userId);
  }

  @Test
  @DisplayName("Gene ID getter returns expected value")
  void getId() {
    assertEquals(geneId, target.getId());
  }

  @Test
  @DisplayName("Name getter returns expected value")
  void getName() {
    assertSame(name, target.getName());
  }

  @Test
  @DisplayName("Template getter returns expected value")
  void getTemplate() {
    assertSame(template, target.getTemplate());
  }

  @Test
  @DisplayName("Gene counter starting value getter returns expected value")
  void getGeneCounterStart() {
    assertEquals(gCountStart, target.getGeneCounterStart());
  }

  @Test
  @DisplayName("Gene counter current value getter returns expected value")
  void getGeneCounterCurrent() {
    assertEquals(gCountCurr, target.getGeneCounterCurrent());
  }

  @Test
  @DisplayName("Transcript counter starting value getter returns expected value")
  void getTranscriptCounterStart() {
    assertEquals(tCountStart, target.getTranscriptCounterStart());
  }

  @Test
  @DisplayName("Transcript counter current value getter returns expected value")
  void getTranscriptCounterCurrent() {
    assertEquals(tCountCurr, target.getTranscriptCounterCurrent());
  }

  @Test
  @DisplayName("Created date getter returns expected value")
  void getCreatedOn() {
    assertSame(created, target.getCreatedOn());
  }

  @Test
  @DisplayName("Last modified date getter returns expected value")
  void getModified() {
    assertSame(modified, target.getModified());
  }

  @Test
  @DisplayName("Transcript counter current value getter returns expected value")
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
