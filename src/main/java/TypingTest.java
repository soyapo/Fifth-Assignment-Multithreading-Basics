import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TypingTest {

    private static String lastInput = "";
    private static Scanner scanner = new Scanner(System.in);
    public static class InputRunnable implements Runnable {

        //TODO: Implement a thread to get user input without blocking the main thread
        @Override
        public void run() {

        }
    }


    public static void testWord(String wordToTest) {
        try {
            System.out.println(wordToTest);
            lastInput = "";

            // TODO

            System.out.println();
            System.out.println("You typed: " + lastInput);
            if (lastInput.equals(wordToTest)) {
                System.out.println("Correct");
            } else {
                System.out.println("Incorrect");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void typingTest(List<String> inputList) throws InterruptedException {

        for (int i = 0; i < inputList.size(); i++) {
            String wordToTest = inputList.get(i);
            testWord(wordToTest);
            Thread.sleep(2000); // Pause briefly before showing the next word
        }

        // TODO: Display a summary of test results
    }

    public static void main(String[] args) throws InterruptedException {
        List<String> words = new ArrayList<>();
        words.add("remember");
        words.add("my friend");
        words.add("boredom");
        words.add("is a");
        words.add("crime");

        // TODO: Replace the hardcoded word list with words read from the given file in the resources folder (Words.txt)
        typingTest(words);

        System.out.println("Press enter to exit.");
    }
}