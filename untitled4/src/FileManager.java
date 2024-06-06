import java.io.*;
import java.util.ArrayList;

public class FileManager {
    public static void saveUsers(ArrayList<User> users, String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(users);
        }
    }

    public static ArrayList<User> loadUsers(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (ArrayList<User>) ois.readObject();
        }
    }

    public static void saveProducts(ArrayList<Product> products, String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(products);
        }
    }

    public static ArrayList<Product> loadProducts(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (ArrayList<Product>) ois.readObject();
        }
    }
}
