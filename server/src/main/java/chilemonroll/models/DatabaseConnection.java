package chilemonroll.models;  // Asegúrate de que este paquete sea correcto

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/chilemonroll_db";
    private static final String USER = "john";
    private static final String PASSWORD = "admin";

    // Método para obtener la conexión a la base de datos
    public static Connection getConnection() throws SQLException {
        try {
            // Intenta cargar el driver JDBC de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC no encontrado: " + e.getMessage());
            throw new SQLException("No se pudo cargar el driver JDBC", e);
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            throw e;  // Lanza la excepción para que el llamador maneje el error
        }
    }

    // Método para cerrar la conexión
    public static void closeConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexión cerrada correctamente.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}



