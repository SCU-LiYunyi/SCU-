import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private String description;
    private double price;
    private String owner;
    private String contact;

    public Product(String name, String description, double price, String owner, String contact) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.owner = owner;
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getOwner() {
        return owner;
    }

    public String getContact() {
        return contact;
    }
}
