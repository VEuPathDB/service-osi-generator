package org.veupathdb.service.osi.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import io.vulpine.lib.query.util.RowParser;
import io.vulpine.lib.query.util.StatementPreparer;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("QueryUtil")
class QueryUtilTest
{
  private QueryUtil target;

  private PreparedStatement mPrepStat;

  private ResultSet mResult;

  private RowParser < ? > mParser;

  @BeforeEach
  void setUp() throws Exception {
    mPrepStat = mock(PreparedStatement.class);
    mResult   = mock(ResultSet.class);
    mParser   = mock(RowParser.class);
    target    = new QueryUtil();

    var inst = QueryUtil.class.getDeclaredField("instance");
    inst.setAccessible(true);
    inst.set(null, target);
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
    @DisplayName("When given a null parser")
    class Null
    {
      @Test
      @DisplayName("Throws a NullPointerException")
      void test1() {
        assertThrows(
          NullPointerException.class,
          () -> target.optionalResult(null)
        );
      }
    }

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
    @DisplayName("When given a null parser")
    class Null
    {
      @Test
      @DisplayName("Throws a NullPointerException")
      void test1() {
        assertThrows(
          NullPointerException.class,
          () -> target.requiredResult(null)
        );
      }
    }

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

  @Nested
  @DisplayName("Static Access Methods")
  class Statics
  {
    private QueryUtil mQueryUtil;

    private StatementPreparer mPrep;

    private RowParser < ? > mParse;

    @BeforeEach
    void setUp() throws Exception {
      mQueryUtil = mock(QueryUtil.class);
      mPrep      = mock(StatementPreparer.class);
      mParse     = mock(RowParser.class);

      var inst = QueryUtil.class.getDeclaredField("instance");
      inst.setAccessible(true);
      inst.set(null, mQueryUtil);
    }

    @Test
    @DisplayName("#singleId(long) calls #prepSingleId(long)")
    void test1() {
      doReturn(mPrep).when(mQueryUtil).prepSingleId(123456);
      assertSame(mPrep, QueryUtil.singleId(123456));
      verify(mQueryUtil).prepSingleId(123456);
      verifyNoMoreInteractions(mQueryUtil);
    }

    @Test
    @DisplayName("#singleString(String) calls #prepSingleString(String)")
    void test2() {
      doReturn(mPrep).when(mQueryUtil).prepSingleString("hello");
      assertSame(mPrep, QueryUtil.singleString("hello"));
      verify(mQueryUtil).prepSingleString("hello");
      verifyNoMoreInteractions(mQueryUtil);
    }

    @Test
    @DisplayName("#idSet(long[]) calls #prepIdSet(long[])")
    void test3() {
      var set = new long[] { 1, 2, 3, 4, 5, 6 };

      doReturn(mPrep).when(mQueryUtil).prepIdSet(set);
      assertSame(mPrep, QueryUtil.idSet(set));
      verify(mQueryUtil).prepIdSet(set);
      verifyNoMoreInteractions(mQueryUtil);
    }

    @Test
    @DisplayName("#stringSet(String[]) calls #prepStringSet(String[])")
    void test4() {
      var set = new String[] { "1", "2", "3", "4", "5", "6" };

      doReturn(mPrep).when(mQueryUtil).prepStringSet(set);
      assertSame(mPrep, QueryUtil.stringSet(set));
      verify(mQueryUtil).prepStringSet(set);
      verifyNoMoreInteractions(mQueryUtil);
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("#option(RowParser) calls #optionalResult(RowParser)")
    void test5() {
      var in = mock(RowParser.class);

      doReturn(mParse).when(mQueryUtil).optionalResult(in);
      assertSame(mParse, QueryUtil.option(in));
      verify(mQueryUtil).optionalResult(in);
      verifyNoMoreInteractions(mQueryUtil);
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("#must(RowParser) calls #requiredResult(RowParser)")
    void test6() {
      var in = mock(RowParser.class);

      doReturn(mParse).when(mQueryUtil).requiredResult(in);
      assertSame(mParse, QueryUtil.must(in));
      verify(mQueryUtil).requiredResult(in);
      verifyNoMoreInteractions(mQueryUtil);
    }
  }
}
