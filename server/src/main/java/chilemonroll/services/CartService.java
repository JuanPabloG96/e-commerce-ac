package chilemonroll.services;

import chilemonroll.models.Cart;
import chilemonroll.models.CartItem;
import chilemonroll.models.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartService {
  private Connection getConnection() throws SQLException {
    return DatabaseConnection.getConnection();
  }

  public Cart createCart(int user_id) {
    String sql = "INSERT INTO cart (user_id) VALUES (?)";
    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

      stmt.setInt(1, user_id);
      stmt.executeUpdate();

      ResultSet rs = stmt.getGeneratedKeys();
      if (rs.next()) {
        return new Cart(rs.getInt(1), user_id, "active", null, null);
      }
    } catch (SQLException e) {
      System.out.println("Database error: " + e.getMessage());
    }
    return null;
  }

  public boolean addItemToCart(int cart_id, int product_id, int quantity) {
    String sql = "INSERT INTO cart_items (cart_id, product_id, quantity) VALUES (?, ?, ?)";
    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setInt(1, cart_id);
      stmt.setInt(2, product_id);
      stmt.setInt(3, quantity);

      return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("Database error: " + e.getMessage());
      return false;
    }
  }

  public boolean removeItemFromCart(int cart_items_id) {
    String sql = "DELETE FROM cart_items WHERE cart_items_id = ?";
    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setInt(1, cart_items_id);
      return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("Database error: " + e.getMessage());
      return false;
    }
  }

  public Cart getActiveCart(int user_id) {
    String sql = "SELECT * FROM cart WHERE user_id = ? AND status = 'active'";
    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setInt(1, user_id);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        Cart cart = new Cart(
            rs.getInt("cart_id"),
            rs.getInt("user_id"),
            rs.getString("status"),
            rs.getString("created_at"),
            rs.getString("updated_at"));
        cart.setItems(getCartItems(cart.getCart_id()));
        return cart;
      }
    } catch (SQLException e) {
      System.out.println("Database error: " + e.getMessage());
    }
    return null;
  }

  private List<CartItem> getCartItems(int cart_id) {
    List<CartItem> items = new ArrayList<>();
    String sql = "SELECT * FROM cart_items WHERE cart_id = ?";
    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setInt(1, cart_id);
      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        CartItem item = new CartItem(
            rs.getInt("cart_id"),
            rs.getInt("product_id"),
            rs.getInt("quantity"));
        items.add(item);
      }
    } catch (SQLException e) {
      System.out.println("Database error: " + e.getMessage());
    }
    return items;
  }
}
