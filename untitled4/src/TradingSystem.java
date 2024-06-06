import java.io.IOException;
import java.util.ArrayList;

public class TradingSystem {
    private ArrayList<User> users;
    private ArrayList<Product> products;
    private String userFile = "users.txt";
    private String productFile = "products.txt";

    public TradingSystem() {
        try {
            users = FileManager.loadUsers(userFile);
        } catch (IOException | ClassNotFoundException e) {
            users = new ArrayList<>();
        }
        try {
            products = FileManager.loadProducts(productFile);
        } catch (IOException | ClassNotFoundException e) {
            products = new ArrayList<>();
        }
    }

    public boolean register(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }
        users.add(new User(username, password));
        try {
            FileManager.saveUsers(users, userFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public User login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public void addProduct(String name, String description, double price, String owner, String contact) {
        products.add(new Product(name, description, price, owner, contact));
        try {
            FileManager.saveProducts(products, productFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeProduct(String name, String owner) {
        products.removeIf(product -> product.getName().equals(name) && product.getOwner().equals(owner));
        try {
            FileManager.saveProducts(products, productFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public ArrayList<Product> searchProducts(String keyword) {
        ArrayList<Product> result = new ArrayList<>();
        for (Product product : products) {
            if (product.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                    product.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(product);
            }
        }
        return result;
    }
}
