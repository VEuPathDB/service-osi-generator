package test;

import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class TestUtil
{
  public static final ObjectMapper Json = new ObjectMapper()
    .registerModule(new JavaTimeModule());

  public static String randStr() {
    return UUID.randomUUID().toString().replaceAll("-", "");
  }
}
