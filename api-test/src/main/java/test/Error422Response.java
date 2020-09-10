package test;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class Error422Response
{
  public static final String
    KEY_STATUS  = "status",
    KEY_MESSAGE = "message",
    KEY_ERRORS  = "errors";

  private String status;

  private String message;

  private Errors errors;

  @JsonGetter(KEY_STATUS)
  public String getStatus() {
    return status;
  }

  @JsonSetter(KEY_STATUS)
  public Error422Response setStatus(String status) {
    this.status = status;
    return this;
  }

  @JsonGetter(KEY_MESSAGE)
  public String getMessage() {
    return message;
  }

  @JsonSetter(KEY_MESSAGE)
  public Error422Response setMessage(String message) {
    this.message = message;
    return this;
  }

  @JsonGetter(KEY_ERRORS)
  public Errors getErrors() {
    return errors;
  }

  @JsonSetter(KEY_ERRORS)
  public Error422Response setErrors(Errors errors) {
    this.errors = errors;
    return this;
  }

  public static class Errors
  {
    public static final String
      KEY_GENERAL = "general",
      KEY_BY_KEY  = "byKey";

    private String[] general;

    private Map < String, String[] > byKey;

    @JsonGetter(KEY_GENERAL)
    public String[] getGeneral() {
      return general;
    }

    @JsonSetter(KEY_GENERAL)
    public Errors setGeneral(String[] general) {
      this.general = general;
      return this;
    }

    @JsonGetter(KEY_BY_KEY)
    public Map < String, String[] > getByKey() {
      return byKey;
    }

    @JsonSetter(KEY_BY_KEY)
    public Errors setByKey(Map < String, String[] > byKey) {
      this.byKey = byKey;
      return this;
    }
  }
}
