import java.io.IOException;

public class ReportGenerator {
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

        }

        public void makeReport() {

        }
    }

    private static final String[] ORDER_FILES = {
            //TODO: Add paths to order files
    };

    static Product[] productCatalog = new Product[10];
    static TaskRunnable[] tasks = new TaskRunnable[4];

    public static void loadProducts() throws IOException {
        //TODO: Read products from Products.txt with a single thread and store them in the productCatalog array
    }

    public static void main(String[] args) throws InterruptedException {
        //TODO: Use four threads
    }
}