package chilemonroll;

import chilemonroll.controllers.ProductController;
import chilemonroll.services.ProductService;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class App {

    public static void main(String[] args) throws IOException {
        // Crear el servidor HTTP
        HttpServer server = HttpServer.create(new InetSocketAddress(8085), 0);

        // Crear el servicio de productos
        ProductService productService = new ProductService();

        // Registrar el controlador para la ruta /productos
        server.createContext("/api/products", new ProductController(productService));

        // Iniciar el servidor
        server.start();
        System.out.println("Server started at http://localhost:8085");
    }
}
