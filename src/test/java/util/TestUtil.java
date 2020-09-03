package util;

import java.util.Random;
import java.util.UUID;

public class TestUtil
{
  private static final Random random = new Random(System.currentTimeMillis());

  public static String randStr() {
    return UUID.randomUUID().toString();
  }

  public static long randLong() {
    return random.nextLong();
  }

  public static int randInt() {
    return random.nextInt();
  }
}
