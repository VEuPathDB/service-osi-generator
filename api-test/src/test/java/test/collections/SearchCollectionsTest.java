package test.collections;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("GET /idSetCollections")
public class SearchCollectionsTest
{
  @Nested
  @DisplayName("With a name query")
  class Name
  {
    @Nested
    @DisplayName("containing a wildcard")
    class Wildcard
    {
      @Test
      @Disabled
      @DisplayName("returns values matching the pattern")
      void test1() throws Exception {}

      @Test
      @Disabled
      @DisplayName("returns nothing if no records match the pattern")
      void test2() throws Exception {}
    }

    @Nested
    @DisplayName("containing multiple wildcards")
    class Wildcards
    {
      @Test
      @Disabled
      @DisplayName("returns values matching the pattern")
      void test1() throws Exception {}

      @Test
      @Disabled
      @DisplayName("returns nothing if no records match the pattern")
      void test2() throws Exception {}
    }

    @Nested
    @DisplayName("containing no wildcards")
    class NoWildcard
    {
      @Test
      @Disabled
      @DisplayName("returns values with the exact name")
      void test1() throws Exception {}
    }

    @Test
    @Disabled
    @DisplayName("returns nothing if no records have the exact name")
    void test2() throws Exception {}
  }

  @Nested
  @DisplayName("With a created after query")
  class CreatedAfter
  {
    @Test
    @Disabled
    @DisplayName("returns only values created after the set date")
    void test1() throws Exception {}

    @Test
    @Disabled
    @DisplayName("returns nothing if no records were created after the set date")
    void test2() throws Exception {}
  }

  @Nested
  @DisplayName("With a created before query")
  class CreatedBefore
  {
    @Test
    @Disabled
    @DisplayName("returns only values created before the set date")
    void test1() throws Exception {}

    @Test
    @Disabled
    @DisplayName("returns nothing if no records were created before the set date")
    void test2() throws Exception {}
  }

  @Nested
  @DisplayName("With a user query")
  class User
  {
    @Nested
    @DisplayName("using the user's name")
    class Name
    {
      @Test
      @Disabled
      @DisplayName("returns only values created by the user with the given name")
      void test1() throws Exception {}

      @Test
      @Disabled
      @DisplayName("returns nothing if no records were created by the given user")
      void test2() throws Exception {}
    }

    @Nested
    @DisplayName("using the user's id")
    class ID
    {
      @Test
      @Disabled
      @DisplayName("returns only values created by the user with the given id")
      void test1() throws Exception {}

      @Test
      @Disabled
      @DisplayName("returns nothing if no records were created by the given user")
      void test2() throws Exception {}
    }
  }
}
