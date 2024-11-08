package chilemonroll.dto;

import chilemonroll.models.User;

public class LoginResponse {
  private User user;
  private String sessionToken;

  public LoginResponse(User user, String sessionToken) {
    this.user = user;
    this.sessionToken = sessionToken;
  }

  // Getters
  public User getUser() {
    return user;
  }

  public String getSessionToken() {
    return sessionToken;
  }
}
