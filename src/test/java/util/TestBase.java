package util;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Random;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.veupathdb.service.osi.util.Format;
import org.veupathdb.service.osi.util.Validation;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class TestBase
{
  protected Format mFormat;

  protected ObjectMapper mJson;

  protected Validation mValidation;

  protected Random random;

  @BeforeEach
  @SuppressWarnings("ResultOfMethodCallIgnored")
  public void setUp() throws Exception {
    random = new Random(System.currentTimeMillis());

    mValidation = mock(Validation.class);
    var vInst = Validation.class.getDeclaredField("instance");
    vInst.setAccessible(true);
    vInst.set(null, mValidation);

    mFormat = mock(Format.class);
    var fInst = Format.class.getDeclaredField("instance");
    fInst.setAccessible(true);
    fInst.set(null, mFormat);

    mJson = mock(ObjectMapper.class);
    doReturn(mJson).when(mFormat).getJson();
  }

  @AfterEach
  void tearDown() throws Exception {
    var vInst = Validation.class.getDeclaredField("instance");
    vInst.setAccessible(true);
    vInst.set(null, null);

    var fInst = Format.class.getDeclaredField("instance");
    fInst.setAccessible(true);
    fInst.set(null, null);
  }

  protected String randomString() {
    return UUID.randomUUID().toString();
  }

  protected long randomTimestamp() {
    return Math.abs(random.nextInt());
  }

  protected OffsetDateTime randomDate() {
    return stampToDate(randomTimestamp());
  }

  protected OffsetDateTime stampToDate(final long stamp) {
    return OffsetDateTime.ofInstant(
      Instant.ofEpochSecond(stamp),
      ZoneId.systemDefault());
  }
}
