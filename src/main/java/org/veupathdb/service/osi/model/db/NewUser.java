package org.veupathdb.service.osi.model.db;

import org.veupathdb.service.osi.util.Field;
import org.veupathdb.service.osi.util.Format;
import org.veupathdb.service.osi.util.Validation;

public class NewUser
{
  private final String userName;

  private final String apiKey;

  public NewUser(final String userName, final String apiKey) {
    this.userName = Validation.nonEmpty(userName);
    this.apiKey   = Validation.nonEmpty(apiKey);
  }

  /**
   * Internal constructor avoiding duplicate validation.
   */
  protected NewUser(final NewUser user) {
    this.userName = user.getUserName();
    this.apiKey   = user.getApiKey();
  }

  public String getUserName() {
    return userName;
  }

  public String getApiKey() {
    return apiKey;
  }

  @Override
  public String toString() {
    return Format.Json()
      .createObjectNode()
      .put(Field.User.USERNAME, getUserName())
      .put(Field.User.API_KEY, "********")
      .toString();
  }
}
