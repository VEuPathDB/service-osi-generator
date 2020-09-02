package org.veupathdb.service.osi.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.TestBase;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("RecordQuery")
class RecordQueryTest extends TestBase
{
  private RecordQuery target;

  @BeforeEach
  public void setUp() throws Exception {
    super.setUp();

    target = new RecordQuery();
  }

  @Test
  @DisplayName("Name field getter returns the set value")
  void name() {
    var value = randomString();
    assertSame(target, target.setName(value));
    assertSame(value, target.getName());
  }

  @Test
  @DisplayName("Name field state getter returns the expected value")
  void hasName() {
    assertFalse(target.hasName());
    assertSame(target, target.setName(randomString()));
    assertTrue(target.hasName());
    assertSame(target, target.setName(null));
    assertFalse(target.hasName());
  }


  @Test
  @DisplayName("Start date field getter returns the set value")
  void start() {
    var value = randomDate();
    assertSame(target, target.setStart(value));
    assertSame(value, target.getStart());
  }

  @Test
  @DisplayName("Start date field state getter returns the expected value")
  void hasStart() {
    assertFalse(target.hasStart());
    assertSame(target, target.setStart(randomDate()));
    assertTrue(target.hasStart());
    assertSame(target, target.setStart(null));
    assertFalse(target.hasStart());
  }

  @Test
  @DisplayName("End date field getter returns the set value")
  void end() {
    var value = randomDate();
    assertSame(target, target.setEnd(value));
    assertSame(value, target.getEnd());
  }

  @Test
  @DisplayName("End date field state getter returns the expected value")
  void hasEnd() {
    assertFalse(target.hasEnd());
    assertSame(target, target.setEnd(randomDate()));
    assertTrue(target.hasEnd());
    assertSame(target, target.setEnd(null));
    assertFalse(target.hasEnd());
  }

  @Test
  @DisplayName("User ID field getter returns the set value")
  void createdById() {
    var value = Long.valueOf(random.nextLong());
    assertSame(target, target.setCreatedById(value));
    assertSame(value, target.getCreatedById());
  }

  @Test
  @DisplayName("User ID field state getter returns the expected value")
  void hasCreatedById() {
    assertFalse(target.hasCreatedById());
    assertSame(target, target.setCreatedById(random.nextLong()));
    assertTrue(target.hasCreatedById());
    assertSame(target, target.setCreatedById(null));
    assertFalse(target.hasCreatedById());
  }


  @Test
  @DisplayName("User Name field getter returns the set value")
  void createdByName() {
    var value = randomString();
    assertSame(target, target.setCreatedByName(value));
    assertSame(value, target.getCreatedByName());
  }

  @Test
  @DisplayName("User name field state getter returns the expected value")
  void hasCreatedByName() {
    assertFalse(target.hasCreatedByName());
    assertSame(target, target.setCreatedByName(randomString()));
    assertTrue(target.hasCreatedByName());
    assertSame(target, target.setCreatedByName(null));
    assertFalse(target.hasCreatedByName());
  }
}
