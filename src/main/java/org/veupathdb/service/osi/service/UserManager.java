package org.veupathdb.service.osi.service;

import java.util.Optional;

import org.veupathdb.service.osi.model.db.NewUser;
import org.veupathdb.service.osi.model.db.User;
import org.veupathdb.service.osi.repo.UserRepo;
import org.veupathdb.service.osi.service.cache.UserCache;

public class UserManager
{
  private static UserManager instance;

  private UserCache cache;

  public UserManager() {
    this.cache = new UserCache();
  }

  public static User create(NewUser user) throws Exception {
    return getInstance().createUser(user);
  }

  public User createUser(NewUser user) throws Exception {
    var tmp = UserRepo.insertNewUser(user);
    cache.put(tmp);
    return tmp;
  }

  public static Optional<User> lookup(int userId) throws Exception {
    return getInstance().lookupUser(userId);
  }

  public Optional < User > lookupUser(int id) throws Exception {
    var tmp = cache.get(id);

    return tmp.isEmpty() ? UserRepo.selectUser(id) : tmp;
  }

  public static Optional<User> lookup(String userName) throws Exception {
    return getInstance().lookupUser(userName);
  }

  public Optional < User > lookupUser(String name) throws Exception {
    var tmp = cache.get(name);

    return tmp.isEmpty() ? UserRepo.selectUser(name) : tmp;
  }

  public static Optional<User> lookup(String userName, String token)
  throws Exception {
    return getInstance().lookupUser(userName, token);
  }

  public Optional < User > lookupUser(String name, String token)
  throws Exception {
    var tmp = cache.get(name);

    return tmp.isEmpty()
      ? UserRepo.selectUser(name, token)
      : tmp.filter(u -> u.getApiKey().equals(token));
  }

  public static UserManager getInstance() {
    if (instance == null)
      instance = new UserManager();
    return instance;
  }
}
