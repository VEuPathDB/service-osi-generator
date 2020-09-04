package test.collections;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("GET /idSetCollections/{id}")
public class GetCollectionTest
{
  @Nested
  @DisplayName("by name")
  class ByName
  {
    @Nested
    @DisplayName("with an invalid value")
    class Invalid
    {
      @Test
      @Disabled
      @DisplayName("returns a 404 error")
      void test1() {
      }
    }

    @Nested
    @DisplayName("with a valid value")
    class Valid
    {
      @Nested
      @DisplayName("and invalid user credentials")
      class BadCreds
      {
        @Test
        @Disabled
        @DisplayName("returns a 401 error")
        void test1() {
        }
      }

      @Nested
      @DisplayName("and no user credentials")
      class NoCreds
      {
        @Test
        @Disabled
        @DisplayName("returns a 401 error")
        void test1() {
        }
      }

      @Nested
      @DisplayName("and valid user credentials")
      class GoodCreds
      {
        @Test
        @Disabled
        @DisplayName("returns the requested ID set collection")
        void test1() {
        }
      }
    }
  }

  @Nested
  @DisplayName("by id")
  class ById
  {
    @Nested
    @DisplayName("with an invalid value")
    class Invalid
    {
      @Test
      @Disabled
      @DisplayName("returns a 404 error")
      void test1() {
      }
    }

    @Nested
    @DisplayName("with a valid value")
    class Valid
    {
      @Nested
      @DisplayName("and invalid user credentials")
      class BadCreds
      {
        @Test
        @Disabled
        @DisplayName("returns a 401 error")
        void test1() {
        }
      }

      @Nested
      @DisplayName("and no user credentials")
      class NoCreds
      {
        @Test
        @Disabled
        @DisplayName("returns a 401 error")
        void test1() {
        }
      }

      @Nested
      @DisplayName("and valid user credentials")
      class GoodCreds
      {
        @Test
        @Disabled
        @DisplayName("returns the requested ID set collection")
        void test1() {
        }
      }
    }
  }
}
