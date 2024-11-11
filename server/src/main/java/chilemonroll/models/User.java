package chilemonroll.models;

import org.mindrot.jbcrypt.BCrypt;

public class User {
  private int user_id;
  private String username;
  private String email;
  private String password;
  private String created_at;
  private String updated_at;
  private String profile_img;

  // Constructor para registro
  public User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.setPassword(password);
  }

  // Constructor para login
  public User(int userId, String username, String email, String hashedPassword) {
    this.user_id = userId;
    this.username = username;
    this.email = email;
    this.password = hashedPassword;
  }

  // Constructor para actualizar
  public User(int userId, String username, String email, String hashedPassword, String profile_img) {
    this.user_id = userId;
    this.username = username;
    this.email = email;
    this.password = hashedPassword;
    this.profile_img = profile_img;
  }

  // Getters
  public int getUser_id() {
    return user_id;
  }

  public String getUsername() {
    return username;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return this.password;
  }

  public String getCreated_at() {
    return created_at;
  }

  public String getUpdated_at() {
    return updated_at;
  }

  public String getProfileImg() {
    return profile_img;
  }

  // Setters
  public void setUsername(String username) {
    this.username = username;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    if (password == null) {
      throw new IllegalArgumentException("Password cannot be null");
    }
    if (password.length() < 4) {
      throw new IllegalArgumentException("Password must be at least 4 characters long");
    }
    this.password = BCrypt.hashpw(password, BCrypt.gensalt());
  }

  public void setCreated_at(String created_at) {
    this.created_at = created_at;
  }

  public void setUpdated_at(String updated_at) {
    this.updated_at = updated_at;
  }

  public void setProfile_img(String profile_img) {
    this.profile_img = profile_img;
  }

  public boolean checkPassword(String password) {
    return BCrypt.checkpw(password, this.password);
  }
}
