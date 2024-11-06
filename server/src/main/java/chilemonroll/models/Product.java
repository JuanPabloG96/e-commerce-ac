package chilemonroll.models;

public class Product {
    private int id;
    private String name;
    private double price;
    private String description;
    private int stock;
    private String img_src;

    // Constructor actualizado
    public Product(int id, String name, double price, String description, int stock, String img_src) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
        this.img_src = img_src;
    }

    // Getters y setters para todos los campos
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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



