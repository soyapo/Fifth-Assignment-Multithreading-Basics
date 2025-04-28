import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class RunnableClass implements Runnable {
    private final String path;
    private double totalCost;
    private int totalAmount;
    private int totalDiscountSum;
    private int totalLines;
    private Product mostExpensiveProduct;
    private double highestCostAfterDiscount;

    public RunnableClass(String path) {
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
