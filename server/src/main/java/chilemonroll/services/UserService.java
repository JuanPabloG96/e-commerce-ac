package chilemonroll.services;

import chilemonroll.models.User;
import chilemonroll.models.DatabaseConnection;
import java.sql.*;

public class UserService {
    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    public User createUser(String username, String email, String password, String profileImg) {
        String sql = "INSERT INTO users (username, email, password, profile_img) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setString(4, profileImg);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int userId = rs.getInt(1);
                    return new User(userId, username, email, password, profileImg);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            if (password != null)
                stmt.setString(parameterIndex++, password);
            if (profileImg != null)
                stmt.setString(parameterIndex++, profileImg);
            stmt.setInt(parameterIndex, userId);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                return getUserInfo(userId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUserInfo(int userId) {
        String sql = "SELECT user_id, username, email, password, profile_img FROM users WHERE user_id = ?";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("profile_img"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User loginUser(String email, String password) {
        String query = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("profile_img"));

                if (user.checkPassword(password)) {
                    // Don't send password back to client
                    user.setPassword(null);
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
