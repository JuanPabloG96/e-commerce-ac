package chilemonroll;

import chilemonroll.controllers.ProductController;
import chilemonroll.services.ProductService;
import chilemonroll.controllers.UserController;
import chilemonroll.services.UserService;
import chilemonroll.controllers.CartController;
import chilemonroll.services.CartService;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class App {

    public static void main(String[] args) throws IOException {
        // Crear el servidor HTTP
        HttpServer server = HttpServer.create(new InetSocketAddress(8085), 0);

        // Crear el servicio
        ProductService productService = new ProductService();
        UserService userService = new UserService();
        CartService cartService = new CartService();

        // Registrar el controlador para la ruta
        server.createContext("/api/products", new ProductController(productService));
        server.createContext("/api/users", new UserController(userService));
        server.createContext("/api/cart", new CartController(cartService));

        // Iniciar el servidor
        server.start();
        System.out.println("Server started at http://localhost:8085");
    }
}
