package org.veupathdb.service.osi.controller;

import java.util.List;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Request;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.veupathdb.service.osi.generated.model.OrganismPostRequest;
import org.veupathdb.service.osi.generated.model.OrganismPutRequest;
import org.veupathdb.service.osi.generated.model.OrganismResponse;
import org.veupathdb.service.osi.service.organism.OrganismService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("OrganismController")
class OrganismControllerTest
{
  private static Request mRequest;

  private static OrganismService mService;

  @BeforeAll
  static void beforeAll() throws Exception {
    mRequest = mock(Request.class);
    mService = mock(OrganismService.class);

    var inst = OrganismService.class.getDeclaredField("instance");
    inst.setAccessible(true);
    inst.set(null, mService);
  }

  @SuppressWarnings("rawtypes")
  @Nested
  @DisplayName("#getOrganisms(String, Long, Long, String)")
  class GetOrganisms
  {
    @Test
    @DisplayName("passes query parameters to service layer")
    void happyPath() {
      var mResponse = mock(List.class);
      var orgName   = "some organism name";
      var after     = 123456L;
      var before    = 654321L;
      var user      = "some user name or id";

      doReturn(mResponse).when(mService)
        .handleSearch(orgName, after, before, user, mRequest);

      var output = new OrganismController(mRequest)
        .getOrganisms(orgName, after, before, user);

      assertNotNull(output);
      assertSame(mResponse, ((GenericEntity) output.getEntity()).getEntity());
      verify(mService).handleSearch(orgName, after, before, user, mRequest);
    }
  }

  @Nested
  @DisplayName("#postOrganisms(OrganismPostRequest)")
  class PostOrganisms
  {
    @Test
    @DisplayName("passes request body to service layer")
    void happyPath() {
      var mResponse = 123456789L;
      var body      = mock(OrganismPostRequest.class);

      doReturn(mResponse).when(mService)
        .handleCreate(body, mRequest);

      var output = new OrganismController(mRequest).postOrganisms(body);

      assertNotNull(output);
      assertEquals(mResponse, output.getEntity());
      verify(mService).handleCreate(body, mRequest);
    }
  }

  @Nested
  @DisplayName("#getOrganismsByOrganismId(String)")
  class GetOrganismsByOrganismId
  {
    @Test
    @DisplayName("passes uri parameter to service layer")
    void happyPath() {
      var mResponse = mock(OrganismResponse.class);
      var uriParameter = "some organism name or id";

      doReturn(mResponse).when(mService).handleGet(uriParameter, mRequest);

      var output = new OrganismController(mRequest)
        .getOrganismsByOrganismId(uriParameter);

      assertNotNull(output);
      assertSame(mResponse, output.getEntity());
      verify(mService).handleGet(uriParameter, mRequest);
    }
  }

  @Nested
  @DisplayName("#putOrganismsByOrganismId(String, OrganismPutRequest)")
  class PutOrganismsByOrganismId
  {
    @Test
    @DisplayName("passes uri param and body to service layer")
    void happyPath() {
      var uriParam = "some organism name";
      var body     = mock(OrganismPutRequest.class);

      var output = new OrganismController(mRequest)
        .putOrganismsByOrganismId(uriParam, body);

      assertNotNull(output);
      verify(mService).handleUpdate(uriParam, body, mRequest);
    }
  }
}
