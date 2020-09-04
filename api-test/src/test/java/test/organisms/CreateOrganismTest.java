package test.organisms;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("POST /organisms")
public class CreateOrganismTest
{
  @Nested
  @DisplayName("given a null request body")
  class Null
  {
    @Test
    @Disabled
    @DisplayName("returns a 400 error")
    void test1() {
    }
  }

  @Nested
  @DisplayName("given an invalid request body")
  class Invalid
  {
    @Nested
    @DisplayName("with a name that is")
    class BadName
    {
      @Nested
      @DisplayName("omitted")
      class T1
      {
        @Test
        @Disabled
        @DisplayName("returns a 422 error")
        void test1() {
        }
      }

      @Nested
      @DisplayName("null")
      class T2
      {
        @Test
        @Disabled
        @DisplayName("returns a 422 error")
        void test1() {
        }
      }

      @Nested
      @DisplayName("too short")
      class T3
      {
        @Test
        @Disabled
        @DisplayName("returns a 422 error")
        void test1() {
        }
      }

      @Nested
      @DisplayName("empty")
      class T4
      {
        @Test
        @Disabled
        @DisplayName("returns a 422 error")
        void test1() {
        }
      }

      @Nested
      @DisplayName("blank")
      class T5
      {
        @Test
        @Disabled
        @DisplayName("returns a 422 error")
        void test1() {
        }
      }
    }

    @Nested
    @DisplayName("with a template that is")
    class BadTemplate
    {
      @Nested
      @DisplayName("omitted")
      class T1
      {
        @Test
        @Disabled
        @DisplayName("returns a 422 error")
        void test1() {
        }
      }

      @Nested
      @DisplayName("null")
      class T2
      {
        @Test
        @Disabled
        @DisplayName("returns a 422 error")
        void test1() {
        }
      }

      @Nested
      @DisplayName("too short")
      class T3
      {
        @Test
        @Disabled
        @DisplayName("returns a 422 error")
        void test1() {
        }
      }

      @Nested
      @DisplayName("empty")
      class T4
      {
        @Test
        @Disabled
        @DisplayName("returns a 422 error")
        void test1() {
        }
      }

      @Nested
      @DisplayName("blank")
      class T5
      {
        @Test
        @Disabled
        @DisplayName("returns a 422 error")
        void test1() {
        }
      }

      @Nested
      @DisplayName("not valid according to the template pattern requirements")
      class T6
      {
        @Test
        @Disabled
        @DisplayName("returns a 422 error")
        void test1() {
        }
      }
    }

    @Nested
    @DisplayName("with a gene counter starting value that is")
    class BadGeneCounter
    {
      @Nested
      @DisplayName("omitted")
      class T1
      {
        @Test
        @Disabled
        @DisplayName("returns a 422 error")
        void test1() {
        }
      }

      @Nested
      @DisplayName("null")
      class T2
      {
        @Test
        @Disabled
        @DisplayName("returns a 422 error")
        void test1() {
        }
      }

      @Nested
      @DisplayName("below 1")
      class T3
      {
        @Test
        @Disabled
        @DisplayName("returns a 422 error")
        void test1() {
        }
      }
    }

    @Nested
    @DisplayName("with a transcript counter starting value that is")
    class BadTranscriptCounter
    {
      @Nested
      @DisplayName("omitted")
      class T1
      {
        @Test
        @Disabled
        @DisplayName("returns a 422 error")
        void test1() {
        }
      }

      @Nested
      @DisplayName("null")
      class T2
      {
        @Test
        @Disabled
        @DisplayName("returns a 422 error")
        void test1() {
        }
      }

      @Nested
      @DisplayName("below 1")
      class T3
      {
        @Test
        @Disabled
        @DisplayName("returns a 422 error")
        void test1() {
        }
      }
    }
  }

  @Nested
  @DisplayName("given a valid request body")
  class Valid
  {
    @Nested
    @DisplayName("and no user credentials")
    class T1
    {
      @Test
      @Disabled
      @DisplayName("returns a 401 error")
      void test1() {
      }
    }

    @Nested
    @DisplayName("and invalid user credentials")
    class T2
    {
      @Test
      @Disabled
      @DisplayName("returns a 401 error")
      void test1() {
      }
    }

    @Nested
    @DisplayName("and valid user credentials")
    class T3
    {
      @Test
      @Disabled
      @DisplayName("returns the id of the new record")
      void test1() {
      }
    }
  }
}
