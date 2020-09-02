package org.veupathdb.service.osi.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import io.vulpine.lib.query.util.RowParser;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("QueryUtil")
class QueryUtilTest
{
  private final QueryUtil target = QueryUtil.getInstance();

  private PreparedStatement mPrepStat;

  private ResultSet mResult;

  private RowParser < ? > mParser;

  @BeforeEach
  void setUp() throws Exception {
    mPrepStat = mock(PreparedStatement.class);
    mResult   = mock(ResultSet.class);
    mParser   = mock(RowParser.class);
  }

  @Nested
  @DisplayName("#prepSingleId(long)")
  class PrepSingleId
  {
    @Test
    @DisplayName("Prepares a single int64 value statement")
    void test() throws Exception {
      var fn = target.prepSingleId(1223);
      fn.prepare(mPrepStat);
      verify(mPrepStat).setLong(1, 1223);
      verifyNoMoreInteractions(mPrepStat);
    }
  }

  @Nested
  @DisplayName("#prepSingleString(String)")
  class PrepSingleString
  {
    @Test
    @DisplayName("Prepares a single string value statement")
    void test() throws Exception {
      var fn = target.prepSingleString("hello");
      fn.prepare(mPrepStat);
      verify(mPrepStat).setString(1, "hello");
      verifyNoMoreInteractions(mPrepStat);
    }
  }

  @Nested
  @DisplayName("#prepIdSet(long[])")
  class PrepIdSet
  {
    @Test
    @DisplayName("Prepares a single int64 array value statement")
    void test() throws Exception {
      var in = new long[]{1, 2, 3};
      var fn = target.prepIdSet(in);
      fn.prepare(mPrepStat);
      verify(mPrepStat).setObject(1, in);
      verifyNoMoreInteractions(mPrepStat);
    }
  }

  @Nested
  @DisplayName("#prepStringSet(String[])")
  class PrepStringSet
  {
    @Test
    @DisplayName("Prepares a single string array value statement")
    void test() throws Exception {
      var in = new String[]{"hi", "bye", "no"};
      var fn = target.prepStringSet(in);
      fn.prepare(mPrepStat);
      verify(mPrepStat).setObject(1, in);
      verifyNoMoreInteractions(mPrepStat);
    }
  }

  @Nested
  @DisplayName("#optionalResult(RowParser)")
  class OptionalResult
  {
    @Nested
    @DisplayName("When given an empty ResultSet")
    class Empty
    {
      @BeforeEach
      void setUp() throws Exception {
        doReturn(false).when(mResult).next();
      }

      @Test
      @DisplayName("Returns an empty option")
      void test1() throws Exception {
        var fn = target.optionalResult(mParser);
        var op = fn.parse(mResult);

        assertTrue(op.isEmpty());
        verifyZeroInteractions(mParser);
        verify(mResult).next();
        verifyNoMoreInteractions(mResult);
      }
    }

    @Nested
    @DisplayName("When given a populated ResultSet")
    class Full
    {
      private Object value;

      @BeforeEach
      void setUp() throws Exception {
        value = new Object();

        doReturn(true).when(mResult).next();
        doReturn(value).when(mParser).parse(mResult);
      }

      @Test
      @DisplayName("Returns a filled option")
      void test1() throws Exception {
        var fn = target.optionalResult(mParser);
        var op = fn.parse(mResult);

        verify(mResult).next();
        verify(mParser).parse(mResult);

        assertTrue(op.isPresent());
        assertSame(value, op.get());
      }
    }
  }

  @Nested
  @DisplayName("#requiredResult(RowParser)")
  class RequiredResult
  {
    @Nested
    @DisplayName("When given an empty ResultSet")
    class Empty
    {
      @BeforeEach
      void setUp() throws Exception {
        doReturn(false).when(mResult).next();
      }

      @Test
      @DisplayName("Throws an IllegalStateException")
      void test1() throws Exception {
        var fn = target.requiredResult(mParser);

        assertThrows(IllegalStateException.class, () -> fn.parse(mResult));
        verifyZeroInteractions(mParser);
        verify(mResult).next();
        verifyNoMoreInteractions(mResult);
      }
    }

    @Nested
    @DisplayName("When given a populated ResultSet")
    class Full
    {
      private Object value;

      @BeforeEach
      void setUp() throws Exception {
        value = new Object();

        doReturn(true).when(mResult).next();
        doReturn(value).when(mParser).parse(mResult);
      }

      @Test
      @DisplayName("Returns the expected value")
      void test1() throws Exception {
        var fn = target.requiredResult(mParser);

        assertSame(value, fn.parse(mResult));
        verify(mResult).next();
        verify(mParser).parse(mResult);
      }
    }
  }
}
