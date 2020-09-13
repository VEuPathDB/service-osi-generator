package test.sets;

import org.junit.jupiter.api.*;
import test.AuthTestBase;

@DisplayName("GET " + IdSetUtil.API_PATH)
public class SearchIdSetsTest extends AuthTestBase
{
  @Override
  @BeforeEach
  protected void setUp() throws Exception {
    super.setUp();
  }

  @Nested
  @DisplayName("when given")
  class When
  {
    @Nested
    @DisplayName("a created by filter that")
    class User
    {
      @Nested
      @DisplayName("should match records based on user name")
      class T1
      {
        @Test
        @Disabled
        @DisplayName("returns only the expected results")
        void test1() {
        }
      }

      @Nested
      @DisplayName("should not match any records")
      class T2
      {
        @Test
        @Disabled
        @DisplayName("returns no results")
        void test1() {
        }
      }

      @Nested
      @DisplayName("should match records based on user id")
      class T3
      {
        @Test
        @Disabled
        @DisplayName("returns only the expected results")
        void test1() {
        }
      }

      @Nested
      @DisplayName("should not match any records")
      class T4
      {
        @Test
        @Disabled
        @DisplayName("returns no results")
        void test1() {
        }
      }
    }

    @Nested
    @DisplayName("a created after filter that")
    class After
    {
      @Nested
      @DisplayName("should match records created after the given date")
      class T1
      {
        @Test
        @Disabled
        @DisplayName("returns only the expected results")
        void test1() {
        }
      }

      @Nested
      @DisplayName("should not match any records")
      class T2
      {
        @Test
        @Disabled
        @DisplayName("returns no results")
        void test1() {
        }
      }
    }

    @Nested
    @DisplayName("a created before filter that")
    class Before
    {
      @Nested
      @DisplayName("should match records created before the given date")
      class T1
      {
        @Test
        @Disabled
        @DisplayName("returns only the expected results")
        void test1() {
        }
      }

      @Nested
      @DisplayName("should not match any records")
      class T2
      {
        @Test
        @Disabled
        @DisplayName("returns no results")
        void test1() {
        }
      }
    }
  }
}
