package chilemonroll.models;

public class Product {
    private int product_id;
    private String name;
    private double price;
    private String description;
    private int stock;
    private String img_src;

    public Product(int product_id, String name, double price, String description, int stock, String img_src) {
        this.product_id = product_id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
        this.img_src = img_src;
    }

    public Product(int product_id, String name, double price, String description, String img_src) {
        this.product_id = product_id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.img_src = img_src;
    }

    // Getters y setters
    public int getId() {
        return product_id;
    }

    public void setId(int id) {
        this.product_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImgSrc() {
        return img_src;
    }

    public void setImgSrc(String img_src) {
        this.img_src = img_src;
    }
}
