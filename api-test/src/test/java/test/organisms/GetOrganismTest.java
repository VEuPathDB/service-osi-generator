package test.organisms;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("GET /organisms/{id}")
public class GetOrganismTest
{
  @Nested
  @DisplayName("given an invalid organism name")
  class InvalidName
  {
    @Test
    @Disabled
    @DisplayName("returns a 404 error")
    void test1() {
    }
  }

  @Nested
  @DisplayName("given a valid organism name")
  class ValidName
  {
    @Nested
    @DisplayName("and invalid user credentials")
    class BadUser
    {
      @Test
      @Disabled
      @DisplayName("returns a 401 error")
      void test1() {
      }
    }

    @Nested
    @DisplayName("and no user credentials")
    class NoUser
    {
      @Test
      @Disabled
      @DisplayName("returns a 401 error")
      void test1() {
      }
    }

    @Nested
    @DisplayName("and valid user credentials")
    class GoodUser
    {
      @Test
      @Disabled
      @DisplayName("returns the requested record")
      void test1() {
      }
    }
  }

  @Nested
  @DisplayName("given an invalid organism id")
  class InvalidId
  {
    @Test
    @Disabled
    @DisplayName("returns a 404 error")
    void test1() {
    }
  }

  @Nested
  @DisplayName("given a valid organism id")
  class ValidId
  {
    @Nested
    @DisplayName("and invalid user credentials")
    class BadUser
    {
      @Test
      @Disabled
      @DisplayName("returns a 401 error")
      void test1() {
      }
    }

    @Nested
    @DisplayName("and no user credentials")
    class NoUser
    {
      @Test
      @Disabled
      @DisplayName("returns a 401 error")
      void test1() {
      }
    }

    @Nested
    @DisplayName("and valid user credentials")
    class GoodUser
    {
      @Test
      @Disabled
      @DisplayName("returns the requested record")
      void test1() {
      }
    }
  }
}
