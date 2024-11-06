package chilemonroll.models;
import org.mindrot.jbcrypt.BCrypt;

public class User {
  private int user_id;
  private String name;
  private String email;
  private String password;
  private String profile_img;

  public User(int user_id, String name, String email, String password, String profile_img) {
    this.user_id = user_id;
    this.name = name;
    this.email = email;
    this.setPassword(password);
    this.profile_img = profile_img;
  }

  // Getters
  public int getUser_id() {
    return user_id;
  }
  public String getName() {
    return name;
  }
  public String getEmail() {
    return email;
  }
  public String getPassword() {
    return this.password;
  }
  public String getProfile_img() {
    return profile_img;
  }
  // Setters
  public void setUser_id(int user_id) {
    this.user_id = user_id;
  }
  public void setName(String name) {
    this.name = name;
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
    password = BCrypt.hashpw(password, BCrypt.gensalt());
    this.password = password;
}

  public void setProfile_img(String profile_img) {
    this.profile_img = profile_img;
  }

  // Check password
  public boolean checkPassword(String password) {
    return BCrypt.checkpw(password, this.password);
  }

}
