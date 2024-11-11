package chilemonroll.models;

import java.util.ArrayList;
import java.util.List;

public class Cart {
  private int cart_id;
  private int user_id;
  private String status;
  private String created_at;
  private String updated_at;
  private List<CartItem> items;

  public Cart(int user_id) {
    this.user_id = user_id;
    this.status = "active";
    this.items = new ArrayList<>();
  }

  // Constructor for existing carts
  public Cart(int cart_id, int user_id, String status, String created_at, String updated_at) {
    this.cart_id = cart_id;
    this.user_id = user_id;
    this.status = status;
    this.created_at = created_at;
    this.updated_at = updated_at;
    this.items = new ArrayList<>();
  }

  // Getters and setters
  public int getCart_id() {
    return cart_id;
  }

  public int getUser_id() {
    return user_id;
  }

  public String getStatus() {
    return status;
  }

  public String getCreated_at() {
    return created_at;
  }

  public String getUpdated_at() {
    return updated_at;
  }

  public List<CartItem> getItems() {
    return items;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setItems(List<CartItem> items) {
    this.items = items;
  }
}
