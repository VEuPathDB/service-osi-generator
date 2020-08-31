package org.veupathdb.service.osi.controller;

import java.util.List;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Request;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.veupathdb.service.osi.generated.model.CollectionResponse;
import org.veupathdb.service.osi.generated.model.IdSetCollectionPostRequest;
import org.veupathdb.service.osi.service.collections.CollectionService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CollectionControllerTest
{
  private static Request mRequest;

  private static CollectionService mService;

  @BeforeAll
  static void beforeAll() throws Exception {
    mRequest = mock(Request.class);
    mService = mock(CollectionService.class);

    var inst = CollectionService.class.getDeclaredField("instance");
    inst.setAccessible(true);
    inst.set(null, mService);
  }

  @Nested
  @DisplayName(
    "CollectionController#getIdSetCollections(Long, Long, String, String)")
  class GetIdSetCollections
  {
    @Test
    @DisplayName("passes query params to service layer")
    void happyPath() {
      var mResponse = mock(List.class);
      var after     = 1234L;
      var before    = 4321L;
      var by        = "some user";
      var name      = "some collection name";


      doReturn(mResponse).when(mService)
        .findCollections(name, after, before, by, mRequest);

      var output = new CollectionController(mRequest)
        .getIdSetCollections(after, before, by, name);

      assertNotNull(output);
      assertSame(mResponse, ((GenericEntity) output.getEntity()).getEntity());
      verify(mService).findCollections(name, after, before, by, mRequest);
    }
  }

  @Nested
  @DisplayName(
    "CollectionController#postIdSetCollections(IdSetCollectionPostRequest")
  class PostIdSetCollections
  {
    @Test
    @DisplayName("passes post body to service layer")
    void happyPath() {
      var body      = mock(IdSetCollectionPostRequest.class);
      var mResponse = mock(CollectionResponse.class);

      doReturn(mResponse).when(mService).newCollection(body, mRequest);

      var output = new CollectionController(mRequest)
        .postIdSetCollections(body);

      assertNotNull(output);
      assertSame(mResponse, output.getEntity());
      verify(mService).newCollection(body, mRequest);
    }
  }

  @Nested
  @DisplayName("CollectionController#getIdSetCollectionsByCollectionId(Long)")
  class GetIdSetCollectionsByCollectionId
  {
    @Test
    @DisplayName("passes uri param to service layer")
    void happyPath() {
      var uriParam  = 123456L;
      var mResponse = mock(CollectionResponse.class);

      doReturn(mResponse).when(mService).getCollection(uriParam, mRequest);

      var output = new CollectionController(mRequest)
        .getIdSetCollectionsByCollectionId(uriParam);

      assertNotNull(output);
      assertSame(mResponse, output.getEntity());
      verify(mService).getCollection(uriParam, mRequest);
    }
  }
}
