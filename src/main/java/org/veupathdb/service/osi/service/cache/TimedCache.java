package org.veupathdb.service.osi.service.cache;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class TimedCache < K, V >
{
  private static final ScheduledExecutorService cleaner;
  private static final Set <TimedCache<?, ?>> caches = new HashSet <>();

  static {
    cleaner = Executors.newSingleThreadScheduledExecutor();
    cleaner.scheduleAtFixedRate(TimedCache::doCleanup, 5, 5, TimeUnit.MINUTES);
  }

  protected final Map < K, V > values;
  protected final Map < K, Long > times;

  private final long timeout;

  public TimedCache(long timeout) {
    this.timeout = timeout;

    values = new HashMap <>();
    times = new HashMap <>();
  }

  public void put(K key, V value) {
    synchronized (values) {
      values.put(key, value);
      times.put(key, System.currentTimeMillis());
    }
  }

  public Optional<V> get(K key) {
    synchronized (values) {
      return Optional.ofNullable(values.get(key));
    }
  }

  protected void cleanup() {
    var threshold = System.currentTimeMillis() - timeout;
    var keys = new ArrayList<K>();

    synchronized (values) {
      for (var e : times.entrySet()) {
        if (e.getValue() < threshold)
          keys.add(e.getKey());
      }

      for (var k : keys) {
        values.remove(k);
        times.remove(k);
      }
    }
  }

  private static void doCleanup() {
    synchronized (caches) {
      caches.forEach(TimedCache::cleanup);
    }
  }
}
