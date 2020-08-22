package org.veupathdb.service.osi.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.veupathdb.service.osi.model.db.NewUser;
import org.veupathdb.service.osi.model.db.User;
import org.veupathdb.service.osi.repo.UserRepo;

public class UserManager
{
  private static UserManager instance;

  private final Map < Integer, User > byId;
  private final Map < String, User >  byName;

  public UserManager() {
    byId = new HashMap <>();
    byName = new HashMap <>();
  }

  public static User create(String username) throws Exception {
    return getInstance().createUser(new NewUser(
      username,
      UUID.randomUUID().toString().replaceAll("-", "")
    ));
  }

  public User createUser(NewUser user) throws Exception {
    var tmp = UserRepo.insertNewUser(user);
    byId.put(tmp.getUserId(), tmp);
    byName.put(tmp.getUserName(), tmp);
    return tmp;
  }

  public static Optional < User > lookup(int userId) throws Exception {
    return getInstance().lookupUser(userId);
  }

  public Optional < User > lookupUser(int id) throws Exception {
    var tmp = byId.get(id);

    return tmp == null ? UserRepo.selectUser(id) : Optional.of(tmp);
  }

  public static Optional < User > lookup(String userName) throws Exception {
    return getInstance().lookupUser(userName);
  }

  public Optional < User > lookupUser(String name) throws Exception {
    var tmp = byName.get(name);

    return tmp == null ? UserRepo.selectUser(name) : Optional.of(tmp);
  }

  public static Optional < User > lookup(String userName, String token)
  throws Exception {
    return getInstance().lookupUser(userName, token);
  }

  public Optional < User > lookupUser(String name, String token)
  throws Exception {
    var tmp = byName.get(name);

    return tmp == null
      ? UserRepo.selectUser(name, token)
      : Optional.of(tmp).filter(u -> u.getApiKey().equals(token));
  }

  public static UserManager getInstance() {
    if (instance == null)
      instance = new UserManager();
    return instance;
  }
}
