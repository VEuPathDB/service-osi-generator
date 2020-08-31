package util;

import java.util.Random;

import com.fasterxml.jackson.databind.ObjectMapper;
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
}
