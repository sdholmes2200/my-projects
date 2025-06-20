import java.util.*;
import java.util.logging.*;

class Product {
    String productId;
    String name;
    int stock;
    int threshold;
    String restockDate;
    String category;

    public Product(String productId, String name, int stock, int threshold, String restockDate, String category) {
        this.productId = productId;
        this.name = name;
        this.stock = stock;
        this.threshold = threshold;
        this.restockDate = restockDate;
        this.category = category;
    }
}

class Inventory {
    private static final Logger logger = Logger.getLogger("InventoryLogger");
    private Map<String, Product> products = new HashMap<>();

    public Inventory() {
        try {
            Handler fileHandler = new FileHandler("inventory.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addProduct(Product product) {
        products.put(product.productId, product);
        logger.info("Added product: " + product.productId + " - " + product.name);
    }

    public void purchaseProduct(String productId, int quantity) {
        if (!products.containsKey(productId)) {
            logger.severe("Product ID " + productId + " not found.");
            System.out.println("Product not found.");
            return;
        }

        Product product = products.get(productId);

        if (product.stock == 0) {
            logger.warning("Out of stock: " + product.name);
            System.out.println("Sorry, '" + product.name + "' is currently out of stock.");
            recommendSimilar(product.category, product.productId);
            System.out.println("Estimated restock date: " + product.restockDate);
            return;
        }

        if (quantity > product.stock) {
            logger.warning("Not enough stock for " + product.name + ". Requested: " + quantity + ", Available: " + product.stock);
            System.out.println("Only " + product.stock + " units available.");
            return;
        }

        product.stock -= quantity;
        logger.info("Purchased " + quantity + " of " + product.name + ". Remaining stock: " + product.stock);
        System.out.println("Purchase successful: " + quantity + " of " + product.name + ". Remaining stock: " + product.stock);

        if (product.stock <= product.threshold) {
            sendStaffAlert(product);
        }
    }

    private void sendStaffAlert(Product product) {
        String alertMessage = "ALERT: Stock for '" + product.name + "' (ID: " + product.productId + ") has fallen below threshold. Current stock: " + product.stock;
        logger.warning(alertMessage);
        System.out.println(alertMessage);
    }

    private void recommendSimilar(String category, String excludeId) {
        System.out.println("You may also like:");
        int count = 0;
        for (Product p : products.values()) {
            if (p.category.equals(category) && !p.productId.equals(excludeId) && p.stock > 0) {
                System.out.println(" - " + p.name + " (In stock: " + p.stock + ")");
                if (++count == 3) break;
            }
        }
    }
}

public class InventorySystem {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();

        inventory.addProduct(new Product("P001", "Widget", 5, 2, "2025-07-01", "Tools"));
        inventory.addProduct(new Product("P002", "Gadget", 0, 3, "2025-07-10", "Tools"));
        inventory.addProduct(new Product("P003", "Thingamajig", 3, 1, "2025-07-05", "Tools"));
        inventory.addProduct(new Product("P004", "Toolbox", 10, 5, "2025-07-15", "Tools"));

        inventory.purchaseProduct("P002", 1); // Out of stock
        inventory.purchaseProduct("P001", 4); // Low stock alert
    }
}
