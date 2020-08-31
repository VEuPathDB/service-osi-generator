package org.veupathdb.service.osi.model.db;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.TestBase;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

class NewIdSetCollectionTest extends TestBase
{
  private String name, json;

  private User mUser;

  private NewIdSetCollection target;

  @Override
  @BeforeEach
  public void setUp() throws Exception {
    super.setUp();

    json  = randomString();
    name  = randomString();
    mUser = mock(User.class);

    doReturn(name).when(mValidation).enforceNonEmpty(name);
    doReturn(mUser).when(mValidation).enforceNonNull(mUser);

    target = new NewIdSetCollection(name, mUser);

    doReturn(json).when(mJson).writeValueAsString(target);
  }

  @Test
  @DisplayName("Constructor validates inputs")
  void constructor() {
    verify(mValidation).enforceNonEmpty(name);
    verify(mValidation).enforceNonNull(mUser);
  }

  @Test
  @DisplayName("Name getter returns expected value")
  void getName() {
    assertSame(name, target.getName());
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
