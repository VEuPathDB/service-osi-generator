package org.veupathdb.service.osi.model.db;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.veupathdb.service.osi.service.organism.OrganismUtil;
import util.TestBase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

class NewOrganismTest extends TestBase
{
  private OrganismUtil mOrgUtil;

  private String name, template, json;

  private long geneCountStart, transCountStart;

  private User mUser;

  private NewOrganism target;

  @Override
  @BeforeEach
  public void setUp() throws Exception {
    super.setUp();

    name            = randomString();
    template        = randomString();
    json            = randomString();
    geneCountStart  = random.nextLong();
    transCountStart = random.nextLong();

    mUser = mock(User.class);

    mOrgUtil = mock(OrganismUtil.class);
    var inst = OrganismUtil.class.getDeclaredField("instance");
    inst.setAccessible(true);
    inst.set(null, mOrgUtil);

    doReturn(template).when(mOrgUtil).enforceOrgPattern(template);

    doReturn(name).when(mValidation).enforceNonEmpty(name);
    doReturn(geneCountStart).when(mValidation)
      .enforceOneMinimum(geneCountStart);
    doReturn(transCountStart).when(mValidation)
      .enforceOneMinimum(transCountStart);
    doReturn(mUser).when(mValidation).enforceNonNull(mUser);

    target = new NewOrganism(
      name,
      template,
      geneCountStart,
      transCountStart,
      mUser
    );

    doReturn(json).when(mJson).writeValueAsString(target);
  }

  @Test
  @DisplayName("Constructor validates inputs")
  void constructor() {
    verify(mValidation).enforceNonEmpty(name);
    verify(mOrgUtil).enforceOrgPattern(template);
    verify(mValidation).enforceOneMinimum(geneCountStart);
    verify(mValidation).enforceOneMinimum(transCountStart);
    verify(mValidation).enforceNonNull(mUser);
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
  @DisplayName("Gene counter start getter returns expected value")
  void getGeneCounterStart() {
    assertEquals(geneCountStart, target.getGeneCounterStart());
  }

  @Test
  @DisplayName("Transcript counter start getter returns expected value")
  void getTranscriptCounterStart() {
    assertEquals(transCountStart, target.getTranscriptCounterStart());
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
