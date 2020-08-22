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
    byId   = new HashMap <>();
    byName = new HashMap <>();
  }

  public Optional < User > getLocalUser(int userId) {
    synchronized (byId) {
      return Optional.ofNullable(byId.get(userId));
    }
  }

  public User putLocalUser(User user) {
    synchronized (byId) {
      byId.put(user.getUserId(), user);
      byName.put(user.getUserName(), user);
    }

    return user;
  }

  public User createUser(NewUser user) throws Exception {
    var tmp = UserRepo.insertNewUser(user);
    synchronized (byId) {
      byId.put(tmp.getUserId(), tmp);
      byName.put(tmp.getUserName(), tmp);
    }
    return tmp;
  }

  public Optional < User > lookupUser(int id) throws Exception {
    User tmp;

    synchronized (byId) {
      tmp = byId.get(id);
    }

    if (tmp != null)
      return Optional.of(tmp);

    tmp = UserRepo.selectUser(id).orElse(null);

    if (tmp != null)
      putLocalUser(tmp);

    return Optional.ofNullable(tmp);
  }

  public Optional < User > lookupUser(String name) throws Exception {
    User tmp;

    synchronized (byId) {
      tmp = byName.get(name);
    }

    if (tmp != null)
      return Optional.of(tmp);

    tmp = UserRepo.selectUser(name).orElse(null);

    if (tmp != null)
      putLocalUser(tmp);

    return Optional.ofNullable(tmp);
  }

  public Optional < User > lookupUser(String name, String token)
  throws Exception {
    return lookupUser(name)
      .filter(u -> u.getApiKey().equals(token));
  }

  public static UserManager getInstance() {
    if (instance == null)
      instance = new UserManager();
    return instance;
  }

  public static Optional < User > getLocal(int userId) {
    return getInstance().getLocalUser(userId);
  }

  public static User putLocal(User user) {
    return getInstance().putLocalUser(user);
  }

  public static User create(String username) throws Exception {
    return getInstance().createUser(new NewUser(
      username,
      UUID.randomUUID().toString().replaceAll("-", "")
    ));
  }

  public static Optional < User > lookup(int userId) throws Exception {
    return getInstance().lookupUser(userId);
  }

  public static Optional < User > lookup(String userName) throws Exception {
    return getInstance().lookupUser(userName);
  }

  public static Optional < User > lookup(String userName, String token)
  throws Exception {
    return getInstance().lookupUser(userName, token);
  }
}
