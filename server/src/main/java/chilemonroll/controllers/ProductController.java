package chilemonroll.controllers;

import chilemonroll.models.Product;
import chilemonroll.services.ProductService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class ProductController implements HttpHandler {
    private final ProductService productService;
    private final Gson gson;

    public ProductController(ProductService productService) {
        this.productService = productService;
        this.gson = new Gson();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Agregar encabezados CORS para todas las solicitudes
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

        // Manejar solicitudes OPTIONS para la verificación de CORS
        if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(204, -1); // Responder sin cuerpo
            return;
        }

        // Manejar solicitud GET para obtener productos
        if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            List<Product> products = productService.getAllProducts();
            String jsonResponse = gson.toJson(products);

            // Configurar la respuesta como JSON
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, jsonResponse.getBytes().length);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(jsonResponse.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
                exchange.sendResponseHeaders(500, -1);
            }
        } else {
            // Responder con 405 si el método no es GET ni OPTIONS
            exchange.sendResponseHeaders(405, -1);
        }
    }
}
