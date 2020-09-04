package test;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;

import static org.junit.jupiter.api.Assertions.*;

public class TestUtil
{
  public static final ObjectMapper Json = new ObjectMapper();


  public static String randStr() {
    return UUID.randomUUID().toString();
  }


}
