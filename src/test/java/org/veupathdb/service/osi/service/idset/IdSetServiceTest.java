package org.veupathdb.service.osi.service.idset;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotAuthorizedException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.veupathdb.lib.container.jaxrs.errors.UnprocessableEntityException;
import org.veupathdb.service.osi.generated.model.IdSetPostRequest;
import org.veupathdb.service.osi.generated.model.IdSetResponse;
import org.veupathdb.service.osi.model.db.IdSet;
import org.veupathdb.service.osi.model.db.IdSetCollection;
import org.veupathdb.service.osi.model.db.NewIdSet;
import org.veupathdb.service.osi.model.db.Organism;
import org.veupathdb.service.osi.service.DbMan;
import org.veupathdb.service.osi.service.ServiceTestBase;
import org.veupathdb.service.osi.service.genes.GeneRepo;
import org.veupathdb.service.osi.util.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("IdSetService")
class IdSetServiceTest extends ServiceTestBase
{
  private IdSetService target;

  private IdSet mSet;

  @Override
  @BeforeEach
  public void setUp() throws Exception {
    super.setUp();

    mSet = mock(IdSet.class);

    target = new IdSetService();

    doReturn(mUser).when(mUserService).requireRequestUser(mRequest);
  }

  @Nested
  @DisplayName("#handleCreate(IdSetPostRequest, Request)")
  class HandleCreate
  {
    private IdSetPostRequest mPost;

    private Organism mOrg;

    private IdSetCollection mColl;

    private long orgId;

    private long collId;

    private int genCount;

    @BeforeEach
    void setUp() throws Exception {
      mPost    = mock(IdSetPostRequest.class);
      mOrg     = mock(Organism.class);
      mColl    = mock(IdSetCollection.class);
      orgId    = positiveLong();
      collId   = positiveLong();
      genCount = positiveInt();
    }

    void prepBody() {
      doReturn(orgId).when(mPost).getOrganismId();
      doReturn(collId).when(mPost).getCollectionId();
      doReturn(genCount).when(mPost).getGenerateGenes();
    }

    @Test
    @DisplayName("throws 400 error if post body is null")
    void err400() {
      assertThrows(
        BadRequestException.class,
        () -> target.handleCreate(null, mRequest)
      );

      verifyZeroInteractions(mUserService);
    }

    @Test
    @DisplayName("throws 401 error if request had no user attached")
    void err401() {
      reset(mUserService);
      doThrow(NotAuthorizedException.class).when(mUserService)
        .requireRequestUser(mRequest);

      assertThrows(
        NotAuthorizedException.class,
        () -> target.handleCreate(mPost, mRequest)
      );

      verify(mUserService, atLeastOnce()).requireRequestUser(mRequest);
    }

    @Test
    @DisplayName("throws 422 error if organism id value is < 1")
    void err422_1() {
      doReturn(0L).when(mPost).getOrganismId();
      doReturn(collId).when(mPost).getCollectionId();
      doReturn(genCount).when(mPost).getGenerateGenes();

      var excep = assertThrows(
        UnprocessableEntityException.class,
        () -> target.handleCreate(mPost, mRequest)
      );
      assertTrue(excep.getByKey().containsKey(Field.IdSet.ORGANISM_ID));
      verify(mUserService).requireRequestUser(mRequest);
      verify(mPost, atLeastOnce()).getOrganismId();
      verify(mPost, atLeastOnce()).getCollectionId();
      verify(mPost, atLeastOnce()).getGenerateGenes();
    }

    @Test
    @DisplayName("throws 422 error if collection id value is < 1")
    void err422_2() {
      doReturn(orgId).when(mPost).getOrganismId();
      doReturn(0L).when(mPost).getCollectionId();
      doReturn(genCount).when(mPost).getGenerateGenes();

      var excep = assertThrows(
        UnprocessableEntityException.class,
        () -> target.handleCreate(mPost, mRequest)
      );
      assertTrue(excep.getByKey().containsKey(Field.IdSet.COLLECTION_ID));
      verify(mUserService).requireRequestUser(mRequest);
      verify(mPost, atLeastOnce()).getOrganismId();
      verify(mPost, atLeastOnce()).getCollectionId();
      verify(mPost, atLeastOnce()).getGenerateGenes();
    }

    @Test
    @DisplayName("throws 422 error if generate gene id count value is < 0")
    void err422_3() {
      doReturn(orgId).when(mPost).getOrganismId();
      doReturn(collId).when(mPost).getCollectionId();
      doReturn(-1).when(mPost).getGenerateGenes();

      var excep = assertThrows(
        UnprocessableEntityException.class,
        () -> target.handleCreate(mPost, mRequest)
      );
      assertTrue(excep.getByKey().containsKey(Field.IdSet.GENERATE_GENES));
      verify(mUserService).requireRequestUser(mRequest);
      verify(mPost, atLeastOnce()).getOrganismId();
      verify(mPost, atLeastOnce()).getCollectionId();
      verify(mPost, atLeastOnce()).getGenerateGenes();
    }

    @Test
    @DisplayName("throws 422 error if the given organism id is invalid")
    void err422_4() throws Exception {
      prepBody();

      doReturn(Optional.empty()).when(mOrgRepo).getById(orgId);
      doReturn(Optional.of(mColl)).when(mCollRepo).selectById(collId);

      var excep = assertThrows(
        UnprocessableEntityException.class,
        () -> target.handleCreate(mPost, mRequest)
      );
      assertTrue(excep.getByKey().containsKey(Field.IdSet.ORGANISM_ID));
      verify(mOrgRepo).getById(orgId);
    }

    @Test
    @DisplayName("throws 422 error if the given collection id is invalid")
    void err422_5() throws Exception {
      prepBody();

      doReturn(Optional.of(mOrg)).when(mOrgRepo).getById(orgId);
      doReturn(Optional.empty()).when(mCollRepo).selectById(collId);

      var excep = assertThrows(
        UnprocessableEntityException.class,
        () -> target.handleCreate(mPost, mRequest)
      );
      assertTrue(excep.getByKey().containsKey(Field.IdSet.COLLECTION_ID));
      verify(mOrgRepo).getById(orgId);
      verify(mCollRepo).selectById(collId);
    }

    @Test
    @DisplayName("throws 500 error if the db connection cannot be retrieved")
    void err500_1() throws Exception {
      prepBody();

      doReturn(Optional.of(mOrg)).when(mOrgRepo).getById(orgId);
      doReturn(Optional.of(mColl)).when(mCollRepo)
        .selectById(collId);

      reset(mDbMan);
      doThrow(SQLException.class).when(mDbMan).getConnection();

      assertThrows(
        InternalServerErrorException.class,
        () -> target.handleCreate(mPost, mRequest)
      );
      verify(mOrgRepo).getById(orgId);
      verify(mCollRepo).selectById(collId);
      verify(mDbMan).getConnection();
    }


    @Test
    @DisplayName(
      "throws 500 error if the db connection cannot have commit disabled")
    void err500_2() throws Exception {
      prepBody();

      doReturn(Optional.of(mOrg)).when(mOrgRepo).getById(orgId);
      doReturn(Optional.of(mColl)).when(mCollRepo).selectById(collId);
      doThrow(SQLException.class).when(mConn).setAutoCommit(false);

      assertThrows(
        InternalServerErrorException.class,
        () -> target.handleCreate(mPost, mRequest)
      );
      verify(mDbMan).getConnection();
      verify(mConn).setAutoCommit(false);
    }

    @Test
    @DisplayName("throws 500 error if allocating gene ids fails")
    void err500_3() throws Exception {
      prepBody();

      doReturn(Optional.of(mOrg)).when(mOrgRepo).getById(orgId);
      doReturn(Optional.of(mColl)).when(mCollRepo).selectById(collId);
      doThrow(Exception.class).when(mOrgRepo)
        .incrementGeneCounter(orgId, genCount, mConn);

      assertThrows(
        InternalServerErrorException.class,
        () -> target.handleCreate(mPost, mRequest)
      );
      verify(mConn).setAutoCommit(false);
      verify(mOrgRepo).incrementGeneCounter(orgId, genCount, mConn);
    }

    @Test
    @DisplayName("throws 500 error if inserting an ID set fails")
    void err500_4() throws Exception {
      var incStart = positiveLong();
      var newCapt  = ArgumentCaptor.forClass(NewIdSet.class);
      var template = randString();

      prepBody();

      //noinspection ResultOfMethodCallIgnored
      doReturn(template).when(mOrg).getTemplate();

      doReturn(Optional.of(mOrg)).when(mOrgRepo).getById(orgId);
      doReturn(Optional.of(mColl)).when(mCollRepo).selectById(collId);

      doReturn(incStart).when(mOrgRepo)
        .incrementGeneCounter(orgId, genCount, mConn);
      doThrow(Exception.class).when(mIdRepo).insertRow(newCapt.capture(),
        ArgumentMatchers.same(mConn));

      assertThrows(
        InternalServerErrorException.class,
        () -> target.handleCreate(mPost, mRequest)
      );
      assertSame(mOrg, newCapt.getValue().getOrganism());
      assertSame(mColl, newCapt.getValue().getCollection());
      assertSame(template, newCapt.getValue().getTemplate());
      assertEquals(incStart, newCapt.getValue().getCounterStart());
      assertEquals(genCount, newCapt.getValue().getNumIssued());
      assertSame(mUser, newCapt.getValue().getCreatedBy());

      verify(mOrgRepo).incrementGeneCounter(orgId, genCount, mConn);
      verify(mIdRepo).insertRow(newCapt.getValue(), mConn);
    }

    @Test
    @DisplayName("throws 500 error if inserting new genes fails")
    void err500_5() throws Exception {
      var incStart = positiveLong();
      var newCapt  = ArgumentCaptor.forClass(NewIdSet.class);
      var template = randString();
      var geneIds  = new String[] { randString(), randString() };

      prepBody();

      //noinspection ResultOfMethodCallIgnored
      doReturn(template).when(mOrg).getTemplate();

      doReturn(Optional.of(mOrg)).when(mOrgRepo).getById(orgId);
      doReturn(Optional.of(mColl)).when(mCollRepo).selectById(collId);

      doReturn(incStart).when(mOrgRepo)
        .incrementGeneCounter(orgId, genCount, mConn);
      doReturn(mSet).when(mIdRepo).insertRow(newCapt.capture(),
        ArgumentMatchers.same(mConn));

      doReturn(geneIds).when(mGeneUtil).expandGeneIds(mOrg, incStart, genCount);
      doThrow(Exception.class).when(mGeneRepo)
        .insertGenes(mSet, geneIds, mConn, mUser);

      assertThrows(
        InternalServerErrorException.class,
        () -> target.handleCreate(mPost, mRequest)
      );

      verify(mIdRepo).insertRow(newCapt.getValue(), mConn);
      verify(mGeneUtil).expandGeneIds(mOrg, incStart, genCount);
      verify(mGeneRepo).insertGenes(mSet, geneIds, mConn, mUser);
    }

    @Test
    @DisplayName("throws 500 error if selecting inserted genes fails")
    void err500_6() throws Exception {
      var incStart = positiveLong();
      var template = randString();
      var geneIds  = new String[] { randString(), randString() };
      var mRes     = mock(IdSetResponse.class);
      var setId    = positiveLong();

      prepBody();

      //noinspection ResultOfMethodCallIgnored
      doReturn(template).when(mOrg).getTemplate();

      doReturn(Optional.of(mOrg)).when(mOrgRepo).getById(orgId);
      doReturn(Optional.of(mColl)).when(mCollRepo).selectById(collId);

      doReturn(incStart).when(mOrgRepo)
        .incrementGeneCounter(orgId, genCount, mConn);
      doReturn(mSet).when(mIdRepo).insertRow(any(), any());

      doReturn(geneIds).when(mGeneUtil).expandGeneIds(mOrg, incStart, genCount);
      doReturn(mRes).when(mIdUtil).setToResponse(mSet);

      //noinspection ResultOfMethodCallIgnored
      doReturn(setId).when(mSet).getId();

      doThrow(Exception.class).when(mGeneRepo).getBySetId(setId, mConn);

      assertThrows(
        InternalServerErrorException.class,
        () -> target.handleCreate(mPost, mRequest)
      );

      verify(mGeneUtil).expandGeneIds(mOrg, incStart, genCount);
      verify(mGeneRepo).insertGenes(mSet, geneIds, mConn, mUser);
      verify(mIdUtil).setToResponse(mSet);
      verify(mSet, atLeastOnce()).getId();
      verify(mGeneRepo).getBySetId(setId, mConn);
    }


    @Test
    @DisplayName("throws 500 error if committing the transaction fails")
    void err500_7() throws Exception {
      var incStart = positiveLong();
      var template = randString();
      var geneIds  = new String[] { randString(), randString() };
      var mRes     = mock(IdSetResponse.class);
      var setId    = positiveLong();
      var mGenes   = mock(List.class);

      prepBody();

      //noinspection ResultOfMethodCallIgnored
      doReturn(template).when(mOrg).getTemplate();

      doReturn(Optional.of(mOrg)).when(mOrgRepo).getById(orgId);
      doReturn(Optional.of(mColl)).when(mCollRepo).selectById(collId);

      doReturn(incStart).when(mOrgRepo)
        .incrementGeneCounter(orgId, genCount, mConn);
      doReturn(mSet).when(mIdRepo).insertRow(any(), any());

      doReturn(geneIds).when(mGeneUtil).expandGeneIds(mOrg, incStart, genCount);
      doReturn(mRes).when(mIdUtil).setToResponse(mSet);

      //noinspection ResultOfMethodCallIgnored
      doReturn(setId).when(mSet).getId();

      doReturn(mGenes).when(mGeneRepo).getBySetId(setId, mConn);
      doThrow(SQLException.class).when(mConn).commit();

      assertThrows(
        InternalServerErrorException.class,
        () -> target.handleCreate(mPost, mRequest)
      );

      verify(mIdUtil).setToResponse(mSet);
      verify(mSet).getId();
      verify(mGeneRepo).getBySetId(setId, mConn);
      verify(mConn).commit();
    }

    @Test
    @DisplayName("returns expected value on successful gene insert")
    void err200() throws Exception {
      var incStart = positiveLong();
      var template = randString();
      var geneIds  = new String[] { randString(), randString() };
      var mRes     = mock(IdSetResponse.class);
      var setId    = positiveLong();
      var mGenes   = mock(List.class);

      prepBody();

      //noinspection ResultOfMethodCallIgnored
      doReturn(template).when(mOrg).getTemplate();
      doReturn(Optional.of(mOrg)).when(mOrgRepo).getById(orgId);
      doReturn(Optional.of(mColl)).when(mCollRepo).selectById(collId);
      doReturn(incStart).when(mOrgRepo)
        .incrementGeneCounter(orgId, genCount, mConn);
      doReturn(mSet).when(mIdRepo).insertRow(any(), any());
      doReturn(geneIds).when(mGeneUtil).expandGeneIds(mOrg, incStart, genCount);
      doReturn(mRes).when(mIdUtil).setToResponse(mSet);
      //noinspection ResultOfMethodCallIgnored
      doReturn(setId).when(mSet).getId();
      doReturn(mGenes).when(mGeneRepo).getBySetId(setId, mConn);

      assertSame(mRes, target.handleCreate(mPost, mRequest));

      verify(mConn).commit();
    }
  }
}
