package org.veupathdb.service.osi.model.db;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.veupathdb.service.osi.util.Validation;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class NewGeneTest
{
  private Validation mValidation;

  private IdSet mIdSet;

  private User mUser;

  private String name;

  @BeforeEach
  void setUp() throws Exception {
    mIdSet = mock(IdSet.class);
    mUser  = mock(User.class);
    name   = UUID.randomUUID().toString();

    mValidation = mock(Validation.class);
    doReturn(mIdSet).when(mValidation).enforceNonNull(mIdSet);
    doReturn(name).when(mValidation).enforceNonEmpty(name);
    doReturn(mUser).when(mValidation).enforceNonNull(mUser);

    var inst = Validation.class.getDeclaredField("instance");
    inst.setAccessible(true);
    inst.set(null, mValidation);
  }

  @Test
  @DisplayName("Constructor validates inputs")
  void constructor() {
    new NewGene(mIdSet, name, mUser);
    verify(mValidation).enforceNonNull(mIdSet);
    verify(mValidation).enforceNonEmpty(name);
    verify(mValidation).enforceNonNull(mUser);
  }

  @Test
  @DisplayName("ID set getter returns expected value")
  void getIdSet() {
    var target = new NewGene(mIdSet, name, mUser);
    assertSame(mIdSet, target.getIdSet());
  }

  @Test
  @DisplayName("Gene identifier getter returns expected value")
  void getIdentifier() {
    var target = new NewGene(mIdSet, name, mUser);
    assertSame(name, target.getIdentifier());
  }

  @Test
  @DisplayName("User getter returns expected value")
  void getCreatedBy() {
    var target = new NewGene(mIdSet, name, mUser);
    assertSame(mUser, target.getCreatedBy());
  }
}
