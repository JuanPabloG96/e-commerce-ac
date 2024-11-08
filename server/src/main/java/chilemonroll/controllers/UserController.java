package chilemonroll.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import chilemonroll.dto.ErrorResponse;
import chilemonroll.dto.LoginRequest;
import chilemonroll.dto.UserRequest;
import chilemonroll.models.User;
import chilemonroll.services.UserService;
import chilemonroll.utils.SessionManager;
import chilemonroll.dto.LoginResponse;

public class UserController implements HttpHandler {
    private final UserService userService;
    private final Gson gson;

    public UserController(UserService userService) {
        this.userService = userService;
        this.gson = new Gson();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Add CORS headers
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

        // Handle CORS preflight
        if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        String path = exchange.getRequestURI().getPath();
        String[] pathParts = path.split("/");

        try {
            switch (exchange.getRequestMethod()) {
                case "GET":
                    if (pathParts.length == 4) {
                        handleGetUser(exchange, Integer.parseInt(pathParts[3]));
                    } else {
                        sendJsonResponse(exchange, 400, gson.toJson(new ErrorResponse("User ID is required")));
                    }
                    break;
                case "POST":
                    if (pathParts[2].equals("login")) {
                        handleLogin(exchange);
                    } else {
                        handleCreateUser(exchange);
                    }
                    break;

                case "PUT":
                    if (pathParts.length == 4) {
                        handleUpdateUser(exchange, Integer.parseInt(pathParts[3]));
                    } else {
                        sendJsonResponse(exchange, 400, gson.toJson(new ErrorResponse("User ID is required")));
                    }
                    break;
                default:
                    exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            }
        } catch (NumberFormatException e) {
            sendJsonResponse(exchange, 400, gson.toJson(new ErrorResponse("Invalid User ID format")));
        } catch (Exception e) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, "Internal server error", e);
            String errorResponse = gson.toJson(new ErrorResponse("Internal server error"));
            sendJsonResponse(exchange, 500, errorResponse);
        }
    }

    private void handleGetUser(HttpExchange exchange, int userId) throws IOException {
        User user = userService.getUserInfo(userId);
        if (user != null) {
            sendJsonResponse(exchange, 200, gson.toJson(user));
        } else {
            sendJsonResponse(exchange, 404, gson.toJson(new ErrorResponse("User not found")));
        }
    }

    private void handleCreateUser(HttpExchange exchange) throws IOException {
        try {
            // Read the request body as a string first
            String requestBody = new String(exchange.getRequestBody().readAllBytes());

            // Check if the body is not empty
            if (requestBody == null || requestBody.isEmpty()) {
                sendJsonResponse(exchange, 400, gson.toJson(new ErrorResponse("Request body is empty")));
                return;
            }

            // Parse the JSON
            UserRequest request = gson.fromJson(requestBody, UserRequest.class);

            // Validate the request object
            if (request == null || request.getUsername() == null || request.getEmail() == null
                    || request.getPassword() == null) {
                sendJsonResponse(exchange, 400, gson.toJson(new ErrorResponse("Invalid request data")));
                return;
            }

            User newUser = userService.createUser(
                    request.getUsername(),
                    request.getEmail(),
                    request.getPassword(),
                    request.getProfileImg());

            if (newUser != null) {
                sendJsonResponse(exchange, 201, gson.toJson(newUser));
            } else {
                sendJsonResponse(exchange, 400, gson.toJson(new ErrorResponse("Could not create user")));
            }
        } catch (Exception e) {
            sendJsonResponse(exchange, 400, gson.toJson(new ErrorResponse("Invalid JSON format")));
        }
    }

    private void handleUpdateUser(HttpExchange exchange, int userId) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()))) {
            UserRequest request = gson.fromJson(reader, UserRequest.class);

            if (request.getUsername() == null || request.getEmail() == null || request.getPassword() == null) {
                sendJsonResponse(exchange, 400, gson.toJson(new ErrorResponse("Missing required fields")));
                return;
            }

            User updatedUser = userService.updateUser(
                    userId,
                    request.getUsername(),
                    request.getEmail(),
                    request.getPassword(),
                    request.getProfileImg());

            if (updatedUser != null) {
                sendJsonResponse(exchange, 200, gson.toJson(updatedUser));
            } else {
                sendJsonResponse(exchange, 404, gson.toJson(new ErrorResponse("User not found")));
            }
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

    private void handleLogin(HttpExchange exchange) throws IOException {
        try {
            String requestBody = new String(exchange.getRequestBody().readAllBytes());

            if (requestBody.isEmpty()) {
                sendJsonResponse(exchange, 400, gson.toJson(new ErrorResponse("Request body is empty")));
                return;
            }

            LoginRequest request = gson.fromJson(requestBody, LoginRequest.class);

            if (request == null || request.getEmail() == null || request.getPassword() == null) {
                sendJsonResponse(exchange, 400, gson.toJson(new ErrorResponse("Invalid login data")));
                return;
            }

            User user = userService.loginUser(request.getEmail(), request.getPassword());

            if (user != null) {
                String sessionToken = SessionManager.createSession(user.getUser_id());
                LoginResponse response = new LoginResponse(user, sessionToken);
                sendJsonResponse(exchange, 200, gson.toJson(response));
            } else {
                sendJsonResponse(exchange, 401, gson.toJson(new ErrorResponse("Invalid credentials")));
            }
        } catch (Exception e) {
            sendJsonResponse(exchange, 400, gson.toJson(new ErrorResponse("Invalid request format")));
        }
    }

}
