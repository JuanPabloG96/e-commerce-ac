package chilemonroll.controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.google.gson.Gson;
import chilemonroll.models.Cart;
import chilemonroll.services.CartService;
import chilemonroll.dto.ErrorResponse;
import chilemonroll.utils.SessionManager;
import java.io.IOException;
import java.io.OutputStream;

public class CartController implements HttpHandler {
  private final CartService cartService;
  private final Gson gson;

  public CartController(CartService cartService) {
    this.cartService = cartService;
    this.gson = new Gson();
  }

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
    exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, DELETE, OPTIONS");
    exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");

    if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
      exchange.sendResponseHeaders(204, -1);
      return;
    }

    String path = exchange.getRequestURI().getPath();
    String[] pathParts = path.split("/");

    try {
      String token = exchange.getRequestHeaders().getFirst("Authorization");
      Integer userId = token != null ? SessionManager.getUserId(token) : null;

      if (userId == null) {
        sendJsonResponse(exchange, 401, gson.toJson(new ErrorResponse("Unauthorized")));
        return;
      }

      switch (exchange.getRequestMethod()) {
        case "GET":
          handleGetCart(exchange, userId);
          break;
        case "POST":
          if (pathParts.length > 3 && pathParts[3].equals("items")) {
            handleAddItem(exchange, userId);
          }
          break;
        case "DELETE":
          if (pathParts.length > 4 && pathParts[3].equals("items")) {
            handleRemoveItem(exchange, Integer.parseInt(pathParts[4]));
          }
          break;
        default:
          exchange.sendResponseHeaders(405, -1);
      }
    } catch (Exception e) {
      sendJsonResponse(exchange, 500, gson.toJson(new ErrorResponse("Internal server error")));
    }
  }

  private void handleGetCart(HttpExchange exchange, int userId) throws IOException {
    Cart cart = cartService.getActiveCart(userId);
    if (cart != null) {
      sendJsonResponse(exchange, 200, gson.toJson(cart));
    } else {
      cart = cartService.createCart(userId);
      sendJsonResponse(exchange, 201, gson.toJson(cart));
    }
  }

  private void handleAddItem(HttpExchange exchange, int userId) throws IOException {
    String requestBody = new String(exchange.getRequestBody().readAllBytes());
    CartItemRequest request = gson.fromJson(requestBody, CartItemRequest.class);

    Cart cart = cartService.getActiveCart(userId);
    if (cart == null) {
      cart = cartService.createCart(userId);
    }

    if (cartService.addItemToCart(cart.getCart_id(), request.getProduct_id(), request.getQuantity())) {
      sendJsonResponse(exchange, 201, gson.toJson(cartService.getActiveCart(userId)));
    } else {
      sendJsonResponse(exchange, 400, gson.toJson(new ErrorResponse("Failed to add item to cart")));
    }
  }

  private void handleRemoveItem(HttpExchange exchange, int cartItemId) throws IOException {
    if (cartService.removeItemFromCart(cartItemId)) {
      exchange.sendResponseHeaders(204, -1);
    } else {
      sendJsonResponse(exchange, 404, gson.toJson(new ErrorResponse("Item not found")));
    }
  }

  private void sendJsonResponse(HttpExchange exchange, int statusCode, String jsonResponse) throws IOException {
    exchange.getResponseHeaders().set("Content-Type", "application/json");
    byte[] responseBytes = jsonResponse.getBytes();
    exchange.sendResponseHeaders(statusCode, responseBytes.length);

    try (OutputStream os = exchange.getResponseBody()) {
      os.write(responseBytes);
    }
  }
}

class CartItemRequest {
  private int product_id;
  private int quantity;

  public int getProduct_id() {
    return product_id;
  }

  public int getQuantity() {
    return quantity;
  }
}
