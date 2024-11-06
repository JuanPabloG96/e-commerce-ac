package chilemonroll.services;

import chilemonroll.models.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import chilemonroll.models.DatabaseConnection;

public class ProductService {

    // Método para obtener la conexión a la base de datos
    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    // Método para obtener todos los productos
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT products_id, name, price, description, stock, img_src FROM products";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("products_id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String description = rs.getString("description");
                int stock = rs.getInt("stock");
                String imgSrc = rs.getString("img_src");

                // Creamos el objeto Product con la nueva información
                Product product = new Product(id, name, price, description, stock, imgSrc);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
}


