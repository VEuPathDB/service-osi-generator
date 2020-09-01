package org.veupathdb.service.osi.model.db;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.veupathdb.service.osi.util.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserTest extends NewUserTest
{
  private long userId;

  private OffsetDateTime created;

  protected User sTarget;

  @Override
  @BeforeEach
  public void setUp() throws Exception {
    super.setUp();

    System.out.println("RESET");

    userId   = random.nextLong();
    created  = randomDate();

    doReturn(userId).when(mValidation).enforceOneMinimum(userId);
    doReturn(created).when(mValidation).enforceNonNull(created);
    doReturn(mNode).when(mNode).put(anyString(), anyLong());

    target = sTarget = new User(userId, name, key, created);
  }

  @Test
  @DisplayName("Constructor validates inputs")
  void constructor() {
    verify(mValidation).enforceOneMinimum(userId);
    verify(mValidation).enforceNonNull(created);
  }

  @Test
  @DisplayName("Second constructor validates inputs")
  void constructor2() throws Exception {
    reset(mValidation);

    new User(userId, created, new NewUser(name, key));
    verify(mValidation).enforceOneMinimum(userId);
    verify(mValidation).enforceNonNull(created);
    verify(mValidation).enforceNonEmpty(name);
    verify(mValidation).enforceNonEmpty(key);
  }

  @Test
  @DisplayName("User ID getter returns expected value")
  void getUserId() {
    assertEquals(userId, sTarget.getUserId());
  }

  @Test
  @DisplayName("User ID getter returns expected value")
  void getIssued() {
    assertSame(created, sTarget.getIssued());
  }

  @Test
  @Override
  @DisplayName("String serializer returns a non-null value.")
  void stringer() {
    assertNotNull(target.toString());
    verify(mNode).put(Field.User.ID, userId);
    verify(mNode).put(Field.User.ISSUED, created
      .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
  }
}
