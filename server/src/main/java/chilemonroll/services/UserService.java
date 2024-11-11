package chilemonroll.services;

import chilemonroll.models.User;
import chilemonroll.models.DatabaseConnection;
import java.sql.*;

public class UserService {
  private Connection getConnection() throws SQLException {
    return DatabaseConnection.getConnection();
  }

  private boolean emailExists(String email) throws SQLException {
    String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, email);
      ResultSet rs = stmt.executeQuery();
      return rs.next() && rs.getInt(1) > 0;
    }
  }

  public User createUser(String username, String email, String password) {
    try {
      if (emailExists(email)) {
        System.out.println("Email already exists: " + email);
        return null;
      }

      String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";

      try (Connection conn = getConnection();
          PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

        User newUser = new User(username, email, password);

        stmt.setString(1, newUser.getUsername());
        stmt.setString(2, newUser.getEmail());
        stmt.setString(3, newUser.getPassword());

        int affectedRows = stmt.executeUpdate();
        if (affectedRows > 0) {
          return newUser;
        }
      }
    } catch (SQLException e) {
      System.out.println("Database error: " + e.getMessage());
    }
    return null;
  }

  public User getUserInfo(int userId) {
    String sql = "SELECT * FROM users WHERE user_id = ?";

    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setInt(1, userId);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        User user = new User(
            rs.getInt("user_id"),
            rs.getString("username"),
            rs.getString("email"),
            rs.getString("password"));
        user.setProfile_img(rs.getString("profile_img"));
        user.setCreated_at(rs.getString("created_at"));
        user.setUpdated_at(rs.getString("updated_at"));
        return user;
      }
    } catch (SQLException e) {
      System.out.println("Database error: " + e.getMessage());
    }
    return null;
  }

  public User loginUser(String email, String password) {
    String sql = "SELECT * FROM users WHERE email = ?";

    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setString(1, email);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        User user = new User(
            rs.getString("username"),
            rs.getString("email"),
            rs.getString("password"));

        if (user.checkPassword(password)) {
          user.setProfile_img(rs.getString("profile_img"));
          user.setCreated_at(rs.getString("created_at"));
          user.setUpdated_at(rs.getString("updated_at"));
          return user;
        }
      }
    } catch (SQLException e) {
      System.out.println("Database error: " + e.getMessage());
    }
    return null;
  }

  public User updateUser(int userId, String username, String email, String password, String profileImg) {
    StringBuilder sql = new StringBuilder("UPDATE users SET ");
    boolean needsComma = false;

    if (username != null) {
      sql.append("username = ?");
      needsComma = true;
    }
    if (email != null) {
      if (needsComma)
        sql.append(", ");
      sql.append("email = ?");
      needsComma = true;
    }
    if (password != null) {
      if (needsComma)
        sql.append(", ");
      sql.append("password = ?");
      needsComma = true;
    }
    if (profileImg != null) {
      if (needsComma)
        sql.append(", ");
      sql.append("profile_img = ?");
    }
    sql.append(" WHERE user_id = ?");

    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

      int parameterIndex = 1;
      if (username != null)
        stmt.setString(parameterIndex++, username);
      if (email != null)
        stmt.setString(parameterIndex++, email);
      if (password != null) {
        User tempUser = new User(username, email, password);
        stmt.setString(parameterIndex++, tempUser.getPassword());
      }
      if (profileImg != null)
        stmt.setString(parameterIndex++, profileImg);
      stmt.setInt(parameterIndex, userId);

      int affectedRows = stmt.executeUpdate();
      if (affectedRows > 0) {
        return getUserInfo(userId);
      }
    } catch (SQLException e) {
      System.out.println("Database error: " + e.getMessage());
    }
    return null;
  }

  public User getUserByEmail(String email) {
    String query = "SELECT * FROM users WHERE email = ?";
    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {

      stmt.setString(1, email);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        return new User(
            rs.getInt("user_id"), // Include user_id
            rs.getString("username"),
            rs.getString("email"),
            rs.getString("password") // This is already hashed
        );
      }
    } catch (SQLException e) {
      System.out.println("Database error: " + e.getMessage());
    }
    return null;
  }

}
