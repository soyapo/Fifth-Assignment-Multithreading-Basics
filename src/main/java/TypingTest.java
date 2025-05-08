import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import java.io.IOException;
import java.util.random.*;

public class TypingTest {

    private static String lastInput = "";
    private static Scanner cin = new Scanner(System.in);
    private static Boolean InputRecieved = false;

    private static int CorrectWords = 0;
    private static int IncorrectWords = 0;

    public static class InputRunnable implements Runnable {
        @Override
        public void run() {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                lastInput = reader.readLine();
                InputRecieved = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<String> loadWordsFromResource() throws IOException {
        InputStream inputStream = TypingTest.class.getResourceAsStream("/Words.txt");
        if (inputStream == null) {
            throw new IOException("Words.txt not found in resources.");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines().collect(Collectors.toList());
        }
    }

    public static void testWord(String wordToTest) {
        lastInput = "";
        InputRecieved = false;

        System.out.println("\nType the word: " + wordToTest);

        int timeoutMillis = Math.max(2000, wordToTest.length() * 500);  // Minimum 2s, 500ms per character

        Thread inputThread = new Thread(new InputRunnable());
        inputThread.start();

        long startTime = System.currentTimeMillis();
        while (!InputRecieved && (System.currentTimeMillis() - startTime < timeoutMillis)) {
            // wait
        }
        long duration = System.currentTimeMillis() - startTime;

        //if (!InputRecieved) {
        //    System.out.println("⏰ Time's up! No input received.");
        //} else {
            System.out.println("You typed: " + lastInput);
            System.out.println((lastInput.equals(wordToTest) ? "✅ Correct" : "❌ Incorrect"));
            if(lastInput.equals(wordToTest))
                CorrectWords++;
            else
                IncorrectWords++;
        //}
    }

    public static void typingTest(List<String> inputList) throws InterruptedException {
        Random R = new Random();
        for (int i = 0; i < 10; i++) {
            Thread.sleep(2000); // Pause briefly before showing the next word
            String wordToTest = inputList.get(R.nextInt(inputList.size()));
            testWord(wordToTest);
        }

        // TODO: Display a summary of test results
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        List<String> words = loadWordsFromResource();
        typingTest(words);
        System.out.println("Correct words: " + CorrectWords);
        System.out.println("Incorrect words: " + IncorrectWords);
        System.out.println("Press enter to exit.");
    }
}