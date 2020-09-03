package org.veupathdb.service.osi.service.user;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.Logger;
import org.veupathdb.lib.container.jaxrs.providers.LogProvider;
import org.veupathdb.service.osi.model.db.User;

public class UserManager
{
  @SuppressWarnings("FieldMayBeFinal")
  private static UserManager instance = new UserManager();

  private final Map < Long, User > byId;

  private final Map < String, User >  byName;

  private final Logger log;

  public UserManager() {
    byId   = new HashMap <>();
    byName = new HashMap <>();
    log    = LogProvider.logger(getClass());
  }

  // ╔════════════════════════════════════════════════════════════════════╗ //
  // ║                                                                    ║ //
  // ║    Mockable Instance Methods                                       ║ //
  // ║                                                                    ║ //
  // ╚════════════════════════════════════════════════════════════════════╝ //

  public synchronized Optional < User > getLocalUser(final long userId) {
    log.trace("UserManager#getLocalUser(long)");

    return Optional.ofNullable(byId.get(userId));
  }

  public synchronized Optional < User > getLocalUser(final String username) {
    log.trace("UserManager#getLocalUser(long)");

    return Optional.ofNullable(byName.get(username));
  }

  public synchronized void putLocalUser(final User user) {
    log.trace("UserManager#putLocalUser(User)");

    byId.put(user.getUserId(), user);
    byName.put(user.getUserName(), user);
  }

  public Optional < User > lookupUser(final long userId) throws Exception {
    log.trace("UserManager#lookupUser(String)");

    var tmp = getLocalUser(userId);

    if (tmp.isPresent())
      return tmp;

    tmp = UserRepo.select(userId);
    tmp.ifPresent(this::putLocalUser);

    return tmp;
  }

  public Optional < User > lookupUser(final String name) throws Exception {
    log.trace("UserManager#lookupUser(String)");

    var tmp = getLocalUser(name);

    if (tmp.isPresent())
      return tmp;

    tmp = UserRepo.select(name);
    tmp.ifPresent(this::putLocalUser);

    return tmp;
  }

  public Optional < User > lookupUser(final String name, final String token) throws Exception {
    log.trace("UserManager#lookupUser(String, String)");

    return lookupUser(name)
      .filter(u -> u.getApiKey().equals(token));
  }

  // ╔════════════════════════════════════════════════════════════════════╗ //
  // ║                                                                    ║ //
  // ║    Static Access Methods                                           ║ //
  // ║                                                                    ║ //
  // ╚════════════════════════════════════════════════════════════════════╝ //

  public static UserManager getInstance() {
    return instance;
  }

  public static Optional < User > lookup(final String userName, final String token)
  throws Exception {
    return getInstance().lookupUser(userName, token);
  }

  public static Optional < User > lookup(final String userName)
  throws Exception {
    return getInstance().lookupUser(userName);
  }

  public static Optional < User > lookup(final long userId)
  throws Exception {
    return getInstance().lookupUser(userId);
  }
}
