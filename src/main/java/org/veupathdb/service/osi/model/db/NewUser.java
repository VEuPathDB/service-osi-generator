package org.veupathdb.service.osi.model.db;

import org.veupathdb.service.osi.util.Validation;

public class NewUser
{
  private final String userName;
  private final String apiKey;

  public NewUser(String userName, String apiKey) {
    this.userName = Validation.nonEmpty(userName);
    this.apiKey   = Validation.nonEmpty(apiKey);
  }

  public String getUserName() {
    return userName;
  }

  public String getApiKey() {
    return apiKey;
  }

  @Override
  public String toString() {
    return "NewUser{userName='" + userName + "', apiKey='********'}";
  }
}
