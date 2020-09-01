package org.veupathdb.service.osi.service;

import java.sql.Connection;
import java.util.Random;
import java.util.UUID;

import org.glassfish.jersey.server.ContainerRequest;
import org.junit.jupiter.api.BeforeEach;
import org.veupathdb.service.osi.model.db.User;
import org.veupathdb.service.osi.service.collections.CollectionRepo;
import org.veupathdb.service.osi.service.genes.GeneRepo;
import org.veupathdb.service.osi.service.genes.GeneUtil;
import org.veupathdb.service.osi.service.idset.IdSetRepo;
import org.veupathdb.service.osi.service.idset.IdSetUtil;
import org.veupathdb.service.osi.service.organism.OrganismRepo;
import org.veupathdb.service.osi.service.organism.OrganismUtil;
import org.veupathdb.service.osi.service.user.UserService;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class ServiceTestBase
{
  protected ContainerRequest mRequest;

  protected OrganismRepo mOrgRepo;

  protected OrganismUtil mOrgUtil;

  protected GeneRepo mGeneRepo;

  protected GeneUtil mGeneUtil;

  protected CollectionRepo mCollRepo;

  protected UserService mUserService;

  protected IdSetRepo mIdRepo;

  protected IdSetUtil mIdUtil;

  protected User mUser;

  protected DbMan mDbMan;

  protected Connection mConn;

  protected Random random;

  @BeforeEach
  protected void setUp() throws Exception {
    random = new Random(System.currentTimeMillis());

    mRequest = mock(ContainerRequest.class);
    mUser    = mock(User.class);
    mConn    = mock(Connection.class);

    mGeneRepo    = mockSingleton(GeneRepo.class);
    mGeneUtil    = mockSingleton(GeneUtil.class);
    mOrgRepo     = mockSingleton(OrganismRepo.class);
    mOrgUtil     = mockSingleton(OrganismUtil.class);
    mUserService = mockSingleton(UserService.class);
    mCollRepo    = mockSingleton(CollectionRepo.class);
    mDbMan       = mockSingleton(DbMan.class);
    mIdRepo      = mockSingleton(IdSetRepo.class);
    mIdUtil      = mockSingleton(IdSetUtil.class);

    doReturn(mConn).when(mDbMan).getConnection();
  }

  protected int positiveInt() {
    return Math.abs(random.nextInt());
  }

  protected long positiveLong() {
    var val = Math.abs(random.nextLong());
    return val == 0 ? 1 : val;
  }

  protected String randString() {
    return UUID.randomUUID().toString();
  }

  protected < T > T mockSingleton(Class < T > cls) throws Exception {
    var mock = mock(cls);
    var inst = cls.getDeclaredField("instance");

    inst.setAccessible(true);
    inst.set(null, mock);

    return mock;
  }
}
