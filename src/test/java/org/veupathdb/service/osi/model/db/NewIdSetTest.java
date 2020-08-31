package org.veupathdb.service.osi.model.db;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.TestBase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

class NewIdSetTest extends TestBase
{
  private IdSetCollection mCollection;

  private Organism mOrganism;

  private User mUser;

  private String template, json;

  private long counterStart;

  private int numIssued;

  private NewIdSet target;

  @BeforeEach
  public void setUp() throws Exception {
    super.setUp();

    template     = randomString();
    counterStart = random.nextLong();
    numIssued    = random.nextInt();
    json         = randomString();
    mCollection  = mock(IdSetCollection.class);
    mOrganism    = mock(Organism.class);
    mUser        = mock(User.class);

    doReturn(mCollection).when(mValidation).enforceNonNull(mCollection);
    doReturn(mOrganism).when(mValidation).enforceNonNull(mOrganism);
    doReturn(template).when(mValidation).enforceNonEmpty(template);
    doReturn(counterStart).when(mValidation).enforceOneMinimum(counterStart);
    doReturn(numIssued).when(mValidation).enforceZeroMinimum(numIssued);
    doReturn(mUser).when(mValidation).enforceNonNull(mUser);

    target = new NewIdSet(
      mCollection,
      mOrganism,
      template,
      counterStart,
      numIssued,
      mUser
    );

    doReturn(json).when(mJson).writeValueAsString(target);
  }

  @Test
  @DisplayName("Constructor validates inputs")
  void constructor() {
    verify(mValidation).enforceNonNull(mCollection);
    verify(mValidation).enforceNonNull(mOrganism);
    verify(mValidation).enforceNonEmpty(template);
    verify(mValidation).enforceOneMinimum(counterStart);
    verify(mValidation).enforceZeroMinimum(numIssued);
    verify(mValidation).enforceNonNull(mUser);
  }

  @Test
  @DisplayName("Collection getter returns expected value")
  void getCollection() {
    assertSame(mCollection, target.getCollection());
  }

  @Test
  @DisplayName("Organism getter returns expected value")
  void getOrganism() {
    assertSame(mOrganism, target.getOrganism());
  }

  @Test
  @DisplayName("Template getter returns expected value")
  void getTemplate() {
    assertSame(template, target.getTemplate());
  }

  @Test
  @DisplayName("User getter returns expected value")
  void getCreatedBy() {
    assertSame(mUser, target.getCreatedBy());
  }

  @Test
  @DisplayName("Counter Start getter returns expected value")
  void getCounterStart() {
    assertEquals(counterStart, target.getCounterStart());
  }

  @Test
  @DisplayName("Issued count getter returns expected value")
  void getNumIssued() {
    assertEquals(numIssued, target.getNumIssued());
  }

  @Test
  @DisplayName("String serializer returns a non-null value.")
  void stringer() throws Exception {
    assertSame(json, target.toString());
    verify(mJson).writeValueAsString(target);
  }
}
