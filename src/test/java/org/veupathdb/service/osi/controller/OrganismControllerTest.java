package org.veupathdb.service.osi.controller;

import javax.ws.rs.core.Request;

import org.junit.jupiter.api.BeforeAll;
import org.veupathdb.service.osi.service.organism.OrganismService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class OrganismControllerTest
{
  private static Request mRequest;

  private static OrganismService mService;

  @BeforeAll
  static void beforeAll() throws Exception {
    mRequest = mock(Request.class);
    mService = mock(OrganismService.class);


  }
}
