package org.veupathdb.service.osi.model.db;

import org.veupathdb.service.osi.util.Validation;

public class NewUser
{
  private final String userEmail;
  private final String apiKey;

  public NewUser(String userEmail, String apiKey) {
    this.userEmail = Validation.nonEmpty(userEmail);
    this.apiKey    = Validation.nonEmpty(apiKey);
  }

  public String getUserEmail() {
    return userEmail;
  }

  public String getApiKey() {
    return apiKey;
  }
}
