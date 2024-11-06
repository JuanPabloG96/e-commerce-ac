package chilemonroll.controllers;

import chilemonroll.models.Product;
import chilemonroll.services.ProductService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import com.google.gson.Gson;

public class ProductController implements HttpHandler {
    private final ProductService productService;
    private final Gson gson;  // Instancia de Gson

    // Constructor
    public ProductController(ProductService productService) {
        this.productService = productService;
        this.gson = new Gson(); // Crear una sola instancia de Gson
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Verifica que el método HTTP sea GET
        if ("GET".equals(exchange.getRequestMethod())) {
            List<Product> products = productService.getAllProducts();
            String jsonResponse = gson.toJson(products);  // Usar la instancia de Gson

            // Configura la respuesta como JSON
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, jsonResponse.getBytes().length);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(jsonResponse.getBytes());
            } catch (IOException e) {
                e.printStackTrace();  // Para depuración
                exchange.sendResponseHeaders(500, -1); // Enviar error 500 en caso de fallo
            }
        } else {
            // Si el método no es GET, responde con un error 405 (Method Not Allowed)
            exchange.sendResponseHeaders(405, -1);
        }
    }
}
