package chilemonroll;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class App {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8085), 0);

        server.createContext("/mensaje", new MessageHandler());
        System.out.println("Context '/mensaje' registered.");
        // Definir rutas al servidor

        // Iniciar el servidor
        server.start();
        System.out.println("Server started at http://localhost:8085");
    }

    static class MessageHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // El mensaje que vamos a enviar como respuesta
            String response = "¡Hola, este es un mensaje desde tu servidor Java!";

            // Configurar las cabeceras de la respuesta HTTP
            exchange.getResponseHeaders().add("Content-Type", "text/plain");

            // Enviar el código de estado HTTP (200 OK)
            exchange.sendResponseHeaders(200, response.getBytes().length);

            // Enviar el cuerpo de la respuesta
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
