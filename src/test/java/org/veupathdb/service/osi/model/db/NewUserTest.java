package org.veupathdb.service.osi.model.db;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.veupathdb.service.osi.util.Field;
import util.TestBase;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

class NewUserTest extends TestBase
{
  private String name, key, json;

  private NewUser target;

  private ObjectNode mNode;

  @BeforeEach
  public void setUp() throws Exception {
    super.setUp();

    mNode = mock(ObjectNode.class);
    name  = randomString();
    key   = randomString();
    json  = randomString();

    doReturn(name).when(mValidation).enforceNonEmpty(name);
    doReturn(key).when(mValidation).enforceNonEmpty(key);

    target = new NewUser(name, key);

    doReturn(mNode).when(mJson).createObjectNode();
    doReturn(mNode).when(mNode).put(anyString(), anyString());
    doReturn(json).when(mNode).toString();
  }

  @Test
  @DisplayName("Constructor validates inputs")
  void constructor() {
    verify(mValidation).enforceNonEmpty(name);
    verify(mValidation).enforceNonEmpty(key);
  }

  @Test
  @DisplayName("User name getter returns expected value")
  void getUserName() {
    assertSame(name, target.getUserName());
  }

  @Test
  @DisplayName("API key getter returns expected value")
  void getApiKey() {
    assertSame(key, target.getApiKey());
  }


  @Test
  @DisplayName("String serializer returns a non-null value.")
  void stringer() {
    assertSame(json, target.toString());
    verify(mJson).createObjectNode();
    verify(mNode).put(Field.User.USERNAME, name);
    verify(mNode).put(Field.User.API_KEY, "********");
  }
}
