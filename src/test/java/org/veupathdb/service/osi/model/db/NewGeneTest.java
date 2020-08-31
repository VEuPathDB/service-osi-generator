package org.veupathdb.service.osi.model.db;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.TestBase;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

class NewGeneTest extends TestBase
{
  private IdSet mIdSet;

  private User mUser;

  private String name, json;

  private NewGene target;

  @BeforeEach
  public void setUp() throws Exception {
    super.setUp();

    mIdSet = mock(IdSet.class);
    mUser  = mock(User.class);
    name   = randomString();
    json   = randomString();

    doReturn(mIdSet).when(mValidation).enforceNonNull(mIdSet);
    doReturn(name).when(mValidation).enforceNonEmpty(name);
    doReturn(mUser).when(mValidation).enforceNonNull(mUser);

    target = new NewGene(mIdSet, name, mUser);

    doReturn(json).when(mJson).writeValueAsString(target);
  }

  @Test
  @DisplayName("Constructor validates inputs")
  void constructor() {
    verify(mValidation).enforceNonNull(mIdSet);
    verify(mValidation).enforceNonEmpty(name);
    verify(mValidation).enforceNonNull(mUser);
  }

  @Test
  @DisplayName("ID set getter returns expected value")
  void getIdSet() {
    assertSame(mIdSet, target.getIdSet());
  }

  @Test
  @DisplayName("Gene identifier getter returns expected value")
  void getIdentifier() {
    assertSame(name, target.getIdentifier());
  }

  @Test
  @DisplayName("User getter returns expected value")
  void getCreatedBy() {
    assertSame(mUser, target.getCreatedBy());
  }

  @Test
  @DisplayName("String serialization returns JSON")
  void stringify() throws Exception{
    assertSame(json, target.toString());
    verify(mJson).writeValueAsString(target);
  }
}
