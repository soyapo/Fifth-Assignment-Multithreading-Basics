import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TypingTest {

    private static volatile String lastInput = "";
    private static Scanner scanner = new Scanner(System.in);
    public static Thread timeoutThread;
    public static Thread inputThread = new Thread(() -> {
        // TODO: Write a thread here to get input without stopping other thread functionalities
    });

    public static void testWord(String wordToTest) {
        try {
            System.out.println(wordToTest);
            lastInput = "";

            // TODO: Use threads here to get input and set a timer concurrently

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
        // Hint: Start the input thread here
        for (int i = 0; i < inputList.size(); i++) {
            String wordToTest = inputList.get(i);
            testWord(wordToTest);
            Thread.sleep(2000); // Some time before proceeding to the next word
        }

        // TODO: Show test results here
    }

    public static void main(String[] args) throws InterruptedException {
        List<String> words = new ArrayList<>();
        words.add("remember");
        words.add("my friend");
        words.add("boredom");
        words.add("is a");
        words.add("crime");

        // TODO: Replace hard-coded words with words read from a given file
        typingTest(words);

        System.out.println("Press Enter to exit.");
    }