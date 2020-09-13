package test;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.databind.JsonNode;

import static org.junit.jupiter.api.Assertions.*;

public class Assert
{
  public static void datesSimilar(final OffsetDateTime a, final OffsetDateTime b) {
    assertEquals(a.getYear(), b.getYear());
    assertEquals(a.getMonth(), b.getMonth());
    assertEquals(a.getDayOfMonth(), b.getDayOfMonth());
    assertEquals(a.getHour(), b.getHour());
    assertTrue(Math.abs(a.getMinute() - b.getMinute()) < 3);
  }

  public static class Json {
    public static void stringNotEmpty(final String name, final JsonNode node) {
      assertFalse(node.textValue().isBlank(), "JSON field " + name + " must not be blank.");
    }

    public static void contains(final JsonNode node, final String key) {
      assertTrue(node.has(key), "Response object missing required field " + key);
    }

    public static void isString(final String name, final JsonNode node) {
      assertTrue(node.isTextual(), "Response field " + name + " must be a string value.");
    }

    public static void isIsoDate(final String name, final JsonNode node) {
      assertDoesNotThrow(() -> OffsetDateTime.parse(node.textValue()),
        "Response field " + name + " must be a valid ISO datetime value.");
    }

    public static void isIntegral(final String name, final JsonNode node) {
      assertTrue(node.isIntegralNumber(), "Response field " + name + " must be an integral value.");
    }

    public static void isGreaterThan(final String name, final JsonNode node, final long min) {
      assertTrue(node.asLong() > min, "Response field " + name + " must be greater than " + min);
    }

    public static void stringMinLength(final String name, final JsonNode node, final int min) {
      assertTrue(node.textValue().length() >= min,
        "Response field " + name + " must be at least " + min + " characters in length");
    }
  }
}
