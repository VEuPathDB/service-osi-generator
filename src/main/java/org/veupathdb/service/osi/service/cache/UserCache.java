package org.veupathdb.service.osi.service.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.veupathdb.service.osi.model.db.User;

public class UserCache extends TimedCache < Integer, User >
{
  /**
   * Timeout user records after 5 minutes.
   */
  private static final long USER_TIMEOUT = 5 * 60 * 1000;

  private final Map < String, User > byName;

  public UserCache() {
    super(USER_TIMEOUT);

    byName = new HashMap <>();
  }

  @Override
  public void put(Integer key, User value) {
    synchronized (values) {
      values.put(key, value);
      byName.put(value.getUserName(), value);
      times.put(key, System.currentTimeMillis());
    }
  }

  public void put(User value) {
    put(value.getUserId(), value);
  }

  @Override
  public Optional < User > get(Integer key) {
    return super.get(key);
  }

  public Optional<User> get(String name) {
    synchronized (values) {
      return Optional.ofNullable(byName.get(name));
    }
  }

  @Override
  protected void cleanup() {
    var threshold = System.currentTimeMillis() - USER_TIMEOUT;
    var keys = new ArrayList <User>();

    synchronized (values) {
      for (var e : times.entrySet()) {
        if (e.getValue() < threshold)
          keys.add(values.get(e.getKey()));
      }

      for (var k : keys) {
        values.remove(k.getUserId());
        times.remove(k.getUserId());
        byName.remove(k.getUserName());
      }
    }
  }
}
