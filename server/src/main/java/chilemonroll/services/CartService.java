package chilemonroll.services;

import chilemonroll.models.Cart;
import chilemonroll.models.CartItem;
import chilemonroll.models.DatabaseConnection;
import chilemonroll.models.Product;
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
    // Primero verificamos si el item ya existe
    String checkSql = "SELECT quantity FROM cart_items WHERE cart_id = ? AND product_id = ?";
    String updateSql = "UPDATE cart_items SET quantity = ? WHERE cart_id = ? AND product_id = ?";
    String insertSql = "INSERT INTO cart_items (cart_id, product_id, quantity) VALUES (?, ?, ?)";

    try (Connection conn = getConnection()) {
      // Verificar item existente
      PreparedStatement checkStmt = conn.prepareStatement(checkSql);
      checkStmt.setInt(1, cart_id);
      checkStmt.setInt(2, product_id);
      ResultSet rs = checkStmt.executeQuery();

      if (rs.next()) {
        // Actualizar cantidad existente
        int newQuantity = rs.getInt("quantity") + quantity;
        PreparedStatement updateStmt = conn.prepareStatement(updateSql);
        updateStmt.setInt(1, newQuantity);
        updateStmt.setInt(2, cart_id);
        updateStmt.setInt(3, product_id);
        return updateStmt.executeUpdate() > 0;
      } else {
        // Insertar nuevo item
        PreparedStatement insertStmt = conn.prepareStatement(insertSql);
        insertStmt.setInt(1, cart_id);
        insertStmt.setInt(2, product_id);
        insertStmt.setInt(3, quantity);
        return insertStmt.executeUpdate() > 0;
      }
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
    String sql = "SELECT ci.*, p.* FROM cart_items ci " +
        "JOIN products p ON ci.product_id = p.products_id " +
        "WHERE ci.cart_id = ?";

    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setInt(1, cart_id);
      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        CartItem item = new CartItem(
            rs.getInt("cart_items_id"),
            rs.getInt("cart_id"),
            rs.getInt("product_id"),
            rs.getInt("quantity"));

        Product product = new Product(
            rs.getInt("products_id"),
            rs.getString("name"),
            rs.getDouble("price"),
            rs.getString("description"),
            rs.getString("img_src"));

        item.setProduct(product);
        items.add(item);
      }
    } catch (SQLException e) {
      System.out.println("Database error: " + e.getMessage());
    }
    return items;
  }

  public boolean updateItemQuantity(int cartItemId, int quantityChange) {
    String selectSql = "SELECT quantity FROM cart_items WHERE cart_items_id = ?";
    String updateSql = "UPDATE cart_items SET quantity = ? WHERE cart_items_id = ?";

    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement selectStmt = conn.prepareStatement(selectSql);
        PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {

      selectStmt.setInt(1, cartItemId);
      ResultSet rs = selectStmt.executeQuery();

      if (rs.next()) {
        int currentQuantity = rs.getInt("quantity");
        int newQuantity = currentQuantity + quantityChange;

        if (newQuantity < 1) {
          return false;
        }

        updateStmt.setInt(1, newQuantity);
        updateStmt.setInt(2, cartItemId);

        int rowsAffected = updateStmt.executeUpdate();
        return rowsAffected > 0;
      }

      return false;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

}
