import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ReportGenerator {

    private static final String[] ORDER_FILES = {
            //TODO: Add paths to order files
    };

    static Product[] productCatalog = new Product[10];
    static RunnableClass[] tasks = new RunnableClass[4];

    public static void loadProducts() throws IOException {
        //TODO: Read products from Products.txt with a single thread and store them in the productCatalog array
    }

    public static void main(String[] args) throws InterruptedException {
        //TODO: Use four threads
    }
}