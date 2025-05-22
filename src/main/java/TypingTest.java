import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ReportGenerator {

    static class Product {
        private int productID;
        private String productName;
        private double price;

        public Product(int productID, String productName, double price) {
            this.productID = productID;
            this.productName = productName;
            this.price = price;
        }

        public int getProductID() {
            return productID;
        }

        public String getProductName() {
            return productName;
        }

        public double getPrice() {
            return price;
        }
    }

    static class TaskRunnable implements Runnable {
        private final String path;
        private double totalCost;
        private int totalAmount;
        private int totalDiscountSum;
        private int totalLines;
        private Product mostExpensiveProduct;
        private double highestCostAfterDiscount;

        public TaskRunnable(String path) {
            this.path = path;
            this.totalCost = 0;
            this.totalAmount = 0;
            this.totalDiscountSum = 0;
            this.totalLines = 0;
            this.highestCostAfterDiscount = 0;
            this.mostExpensiveProduct = null;
        }

        @Override
        public void run() {
            try {
                List<String> lines = Files.readAllLines(Paths.get(path));
                for (String line : lines) {
                    String[] parts = line.split(",");
                    int id = Integer.parseInt(parts[0]);
                    int amount = Integer.parseInt(parts[1]);
                    int discount = Integer.parseInt(parts[2]);

                    Product product = productCatalog[id];
                    if (product == null) continue;

                    double cost = amount * product.getPrice();
                    double discountAmount = cost * discount / 100.0;
                    double finalCost = cost - discountAmount;

                    totalCost += finalCost;
                    totalAmount += amount;
                    totalDiscountSum += discount;
                    totalLines++;

                    if (finalCost > highestCostAfterDiscount) {
                        highestCostAfterDiscount = finalCost;
                        mostExpensiveProduct = product;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void makeReport() {
            System.out.println("📄 Report for: " + path);
            System.out.printf("Total cost: $%.2f\n", totalCost);
            System.out.println("Total items bought: " + totalAmount);
            double avgDiscount = totalLines == 0 ? 0 : (double) totalDiscountSum / totalLines;
            System.out.printf("Average discount: %.2f%%\n", avgDiscount);
            if (mostExpensiveProduct != null) {
                System.out.printf("Most expensive purchase: %s ($%.2f after discount)\n",
                        mostExpensiveProduct.getProductName(), highestCostAfterDiscount);
            }
            System.out.println();
        }
    }

    private static final String[] ORDER_FILES = {
        "src/main/resources/Order1.txt",
        "src/main/resources/Order2.txt",
        "src/main/resources/Order3.txt",
        "src/main/resources/Order4.txt"
    };

    static Product[] productCatalog = new Product[10];

    public static void loadProducts() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("src/main/resources/Products.txt"));
        for (String line : lines) {
            String[] parts = line.split(",");
            int id = Integer.parseInt(parts[0]);
            String name = parts[1];
            double price = Double.parseDouble(parts[2]);
            productCatalog[id] = new Product(id, name, price);
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        loadProducts();

        List<TaskRunnable> tasks = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();

        for (String file : ORDER_FILES) {
            TaskRunnable task = new TaskRunnable(file);
            Thread t = new Thread(task);
            tasks.add(task);
            threads.add(t);
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }

        for (TaskRunnable task : tasks) {
            task.makeReport();
        }
    }
}
