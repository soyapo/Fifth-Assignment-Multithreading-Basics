import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ReportGenerator {

    private static final String PRODUCTS_FILE_PATH =
            "C:\\Users\\AmirAli\\IdeaProjects\\untitled4\\src\\Products.txt";

    private static final String[] ORDER_FILES = {
            "C:\\Users\\AmirAli\\IdeaProjects\\untitled4\\src\\2021_order_details.txt",
            "C:\\Users\\AmirAli\\IdeaProjects\\untitled4\\src\\2022_order_details.txt",
            "C:\\Users\\AmirAli\\IdeaProjects\\untitled4\\src\\2023_order_details.txt",
            "C:\\Users\\AmirAli\\IdeaProjects\\untitled4\\src\\2024_order_details.txt"
    };

    private static Product[] productCatalog = new Product[10]; // فرض کردیم حداکثر 100 محصول داریم

    public static void loadProducts() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(PRODUCTS_FILE_PATH));
        for (String line : lines) {
            if (line.trim().isEmpty()) continue;
            String[] parts = line.split(",");
            if (parts.length != 3) continue;

            int id = Integer.parseInt(parts[0].trim());
            String name = parts[1].trim();
            double price = Double.parseDouble(parts[2].trim());

            productCatalog[id] = new Product(id, name, price);
        }
    }

    public static void readFilesWithThreads() throws InterruptedException {
        Thread[] threads = new Thread[ORDER_FILES.length];

        final double[] grandTotalCost = {0};
        final int[] grandTotalAmount = {0};
        final int[] grandTotalDiscountSum = {0};
        final int[] grandTotalLines = {0};
        final Product[] grandMostExpensiveProduct = {null};
        final double[] grandHighestCost = {0};

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < ORDER_FILES.length; i++) {
            final int index = i;
            final String path = ORDER_FILES[i];

            threads[i] = new Thread(() -> {
                try {
                    double totalCost = 0;
                    int totalAmount = 0;
                    int totalDiscountSum = 0;
                    int linesCount = 0;
                    Product mostExpensiveProduct = null;
                    double highestCostAfterDiscount = 0;

                    List<String> lines = Files.readAllLines(Paths.get(path));
                    for (String line : lines) {
                        if (line.trim().isEmpty()) continue;
                        String[] parts = line.split(",");
                        if (parts.length != 3) continue;

                        int productID = Integer.parseInt(parts[0].trim());
                        int amount = Integer.parseInt(parts[1].trim());
                        int discountPercent = Integer.parseInt(parts[2].trim());

                        if (productID < 0 || productID >= productCatalog.length || productCatalog[productID] == null) {
                            System.err.println("Unknown product ID " + productID + " in file " + path);
                            continue;
                        }

                        Product product = productCatalog[productID];

                        double costBeforeDiscount = product.getPrice() * amount;
                        double discountAmount = costBeforeDiscount * discountPercent / 100.0;
                        double costAfterDiscount = costBeforeDiscount - discountAmount;

                        totalCost += costAfterDiscount;
                        totalAmount += amount;
                        totalDiscountSum += discountPercent;
                        linesCount++;

                        if (costAfterDiscount > highestCostAfterDiscount) {
                            highestCostAfterDiscount = costAfterDiscount;
                            mostExpensiveProduct = product;
                        }
                    }

                    System.out.println("File: " + path);
                    System.out.printf("  Total Cost: $%.2f%n", totalCost);
                    System.out.println("  Total Products Bought: " + totalAmount);
                    double avgDiscount = linesCount == 0 ? 0 : (double) totalDiscountSum / linesCount;
                    System.out.printf("  Average Discount: %.2f%%%n", avgDiscount);
                    if (mostExpensiveProduct != null) {
                        System.out.printf("  Most Expensive Purchase: %s (Product ID %d) - $%.2f%n",
                                mostExpensiveProduct.getProductName(),
                                mostExpensiveProduct.getProductID(),
                                highestCostAfterDiscount);
                    } else {
                        System.out.println("  Most Expensive Purchase: None");
                    }
                    System.out.println();

                    synchronized (grandTotalCost) {
                        grandTotalCost[0] += totalCost;
                        grandTotalAmount[0] += totalAmount;
                        grandTotalDiscountSum[0] += totalDiscountSum;
                        grandTotalLines[0] += linesCount;
                        if (highestCostAfterDiscount > grandHighestCost[0]) {
                            grandHighestCost[0] = highestCostAfterDiscount;
                            grandMostExpensiveProduct[0] = mostExpensiveProduct;
                        }
                    }

                } catch (IOException e) {
                    System.err.println("Error reading file " + path + ": " + e.getMessage());
                }
            });

            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Time taken with 4 threads: " + (endTime - startTime) + " ms");

        System.out.println("Overall Report (All Files - Multithreading):");
        System.out.printf("  Total Cost: $%.2f%n", grandTotalCost[0]);
        System.out.println("  Total Products Bought: " + grandTotalAmount[0]);
        double overallAvgDiscount = grandTotalLines[0] == 0 ? 0 : (double) grandTotalDiscountSum[0] / grandTotalLines[0];
        System.out.printf("  Average Discount: %.2f%%%n", overallAvgDiscount);
        if (grandMostExpensiveProduct[0] != null) {
            System.out.printf("  Most Expensive Purchase: %s (Product ID %d) - $%.2f%n",
                    grandMostExpensiveProduct[0].getProductName(),
                    grandMostExpensiveProduct[0].getProductID(),
                    grandHighestCost[0]);
        } else {
            System.out.println("  Most Expensive Purchase: None");
        }
    }

    public static void readFilesSingleThreaded() {
        double totalCost = 0;
        int totalAmount = 0;
        int totalDiscountSum = 0;
        int totalLines = 0;
        Product mostExpensiveProduct = null;
        double highestCostAfterDiscount = 0;

        long startTime = System.currentTimeMillis();

        for (String path : ORDER_FILES) {
            try {
                List<String> lines = Files.readAllLines(Paths.get(path));

                for (String line : lines) {
                    if (line.trim().isEmpty()) continue;
                    String[] parts = line.split(",");
                    if (parts.length != 3) continue;

                    int productID = Integer.parseInt(parts[0].trim());
                    int amount = Integer.parseInt(parts[1].trim());
                    int discountPercent = Integer.parseInt(parts[2].trim());

                    if (productID < 0 || productID >= productCatalog.length || productCatalog[productID] == null) {
                        System.err.println("Unknown product ID " + productID + " in file " + path);
                        continue;
                    }

                    Product product = productCatalog[productID];

                    double costBeforeDiscount = product.getPrice() * amount;
                    double discountAmount = costBeforeDiscount * discountPercent / 100.0;
                    double costAfterDiscount = costBeforeDiscount - discountAmount;

                    totalCost += costAfterDiscount;
                    totalAmount += amount;
                    totalDiscountSum += discountPercent;
                    totalLines++;

                    if (costAfterDiscount > highestCostAfterDiscount) {
                        highestCostAfterDiscount = costAfterDiscount;
                        mostExpensiveProduct = product;
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading file " + path + ": " + e.getMessage());
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("\nOverall Report (Single-threaded):");
        System.out.printf("  Total Cost: $%.2f%n", totalCost);
        System.out.println("  Total Products Bought: " + totalAmount);
        double overallAvgDiscount = totalLines == 0 ? 0 : (double) totalDiscountSum / totalLines;
        System.out.printf("  Average Discount: %.2f%%%n", overallAvgDiscount);
        if (mostExpensiveProduct != null) {
            System.out.printf("  Most Expensive Purchase: %s (Product ID %d) - $%.2f%n",
                    mostExpensiveProduct.getProductName(),
                    mostExpensiveProduct.getProductID(),
                    highestCostAfterDiscount);
        } else {
            System.out.println("  Most Expensive Purchase: None");
        }

        System.out.println("Time taken with Single-thread: " + (endTime - startTime) + " ms");
    }


}
