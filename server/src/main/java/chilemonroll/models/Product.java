package chilemonroll.models;

public class Product {
    private int product_id;
    private String name;
    private double price;
    private String description;
    private String img_src;

    // Constructor
    public Product(int product_id, String name, double price, String description, String img_src) {
        this.product_id = product_id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.img_src = img_src;
    }

    // Getters
    public int getProductId() {
        return product_id;
    }
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getImgSrc() {
        return img_src;
    }
}


