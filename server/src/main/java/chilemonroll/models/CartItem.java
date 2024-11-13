package chilemonroll.models;

public class CartItem {
  private int cart_items_id;
  private int cart_id;
  private int product_id;
  private int quantity;
  private String created_at;
  private String updated_at;
  private Product product;

  // Constructor para crear un item de carrito
  public CartItem(int cart_id, int product_id, int quantity) {
    this.cart_id = cart_id;
    this.product_id = product_id;
    this.quantity = quantity;
  }

  // Constructor para obtener un item de carrito
  public CartItem(int cart_items_id, int cart_id, int product_id, int quantity) {
    this.cart_items_id = cart_items_id;
    this.cart_id = cart_id;
    this.product_id = product_id;
    this.quantity = quantity;
  }

  // Getters y setters
  public int getCart_items_id() {
    return cart_items_id;
  }

  public int getCart_id() {
    return cart_id;
  }

  public int getProduct_id() {
    return product_id;
  }

  public int getQuantity() {
    return quantity;
  }

  public String getCreated_at() {
    return created_at;
  }

  public String getUpdated_at() {
    return updated_at;
  }

  public Product getProduct() {
    return product;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public void setProduct(Product product) {
    this.product = product;
  }
}
