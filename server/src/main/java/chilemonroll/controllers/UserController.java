package chilemonroll.controllers;

import java.io.IOException;
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
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

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
                    if (pathParts[3].equals("login")) {
                        handleLogin(exchange);
                    } else if (pathParts[3].equals("register")) {
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
                    exchange.sendResponseHeaders(405, -1);
            }
        } catch (Exception e) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, "Error processing request", e);
            sendJsonResponse(exchange, 500, gson.toJson(new ErrorResponse("Internal server error")));
        }
    }

    private void handleCreateUser(HttpExchange exchange) throws IOException {
        String requestBody = new String(exchange.getRequestBody().readAllBytes());

        if (requestBody.isEmpty()) {
            sendJsonResponse(exchange, 400, gson.toJson(new ErrorResponse("Request body is empty")));
            return;
        }

        UserRequest request = gson.fromJson(requestBody, UserRequest.class);

        if (request == null || request.getUsername() == null ||
                request.getEmail() == null || request.getPassword() == null) {
            sendJsonResponse(exchange, 400, gson.toJson(new ErrorResponse("Missing required fields")));
            return;
        }

        User newUser = userService.createUser(
                request.getUsername(),
                request.getEmail(),
                request.getPassword());

        if (newUser != null) {
            String sessionToken = SessionManager.createSession(newUser.getUser_id());
            LoginResponse response = new LoginResponse(newUser, sessionToken);
            sendJsonResponse(exchange, 201, gson.toJson(response));
        } else {
            sendJsonResponse(exchange, 400, gson.toJson(new ErrorResponse("Email already exists or invalid data")));
        }
    }

    private void handleLogin(HttpExchange exchange) throws IOException {
        String requestBody = new String(exchange.getRequestBody().readAllBytes());
        LoginRequest request = gson.fromJson(requestBody, LoginRequest.class);

        User storedUser = userService.getUserByEmail(request.getEmail());

        if (storedUser == null) {
            sendJsonResponse(exchange, 401, gson.toJson(new ErrorResponse("User not found")));
            return;
        }

        if (!storedUser.checkPassword(request.getPassword())) {
            sendJsonResponse(exchange, 401, gson.toJson(new ErrorResponse("Password verification failed")));
            return;
        }

        String sessionToken = SessionManager.createSession(storedUser.getUser_id());
        LoginResponse response = new LoginResponse(storedUser, sessionToken);
        sendJsonResponse(exchange, 200, gson.toJson(response));
    }

    private void handleGetUser(HttpExchange exchange, int userId) throws IOException {
        User user = userService.getUserInfo(userId);
        if (user != null) {
            sendJsonResponse(exchange, 200, gson.toJson(user));
        } else {
            sendJsonResponse(exchange, 404, gson.toJson(new ErrorResponse("User not found")));
        }
    }

    private void handleUpdateUser(HttpExchange exchange, int userId) throws IOException {
        String requestBody = new String(exchange.getRequestBody().readAllBytes());
        UserRequest request = gson.fromJson(requestBody, UserRequest.class);

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

    private void sendJsonResponse(HttpExchange exchange, int statusCode, String jsonResponse) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        byte[] responseBytes = jsonResponse.getBytes();
        exchange.sendResponseHeaders(statusCode, responseBytes.length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
            os.flush();
        }
    }
}