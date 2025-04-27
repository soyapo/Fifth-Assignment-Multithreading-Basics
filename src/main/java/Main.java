public class Main {
    public static void main(String[] args) {
        try {
            ReportGenerator.loadProducts();

            ReportGenerator.readFilesWithThreads();

             ReportGenerator.readFilesSingleThreaded();

        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
