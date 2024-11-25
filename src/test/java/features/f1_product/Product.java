package features.f1_product;

public class Product {
    private String name;
    private String description;
    private String Image;
    private String price;

    public Product(String name, String description, String image, String price) {
        this.name = name;
        this.description = description;
        this.Image = image;
        this.price = price;
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getImage() {
        return Image;
    }
    public String getPrice() {
        return price;
    }

}
