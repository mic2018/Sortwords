import logic.WordProcessor;
import utilities.FileUtil;
import utilities.LogUtil;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;


public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        String outputFileTxt = "";
        LogUtil.setupLogger(LOGGER, "application.log");

        if (args.length != 1) {
            System.out.println("No path parameter was given");
            return;
        }

        String inputFilePath = args[0];
        String outputFilePath = "F2.txt";
        try {
            FileUtil fileUtil = new FileUtil(inputFilePath);
            String rawFileContent = fileUtil.getFileContent(inputFilePath);

            // Get the sorting Method
            String sortingMethod;
            do {
                System.out.println("Enter you options:");
                System.out.println("Accending: sort -a");
                System.out.println("Decending: sort -d");
                Scanner scanner = new Scanner(System.in);
                sortingMethod = scanner.nextLine().trim();
            } while (!sortingMethod.equalsIgnoreCase("sort -a") && !sortingMethod.equals("sort -d"));

            WordProcessor.SortOrder order = null;
            if (sortingMethod.equals("sort -a"))
                order = WordProcessor.SortOrder.ASCENDING;
            else
                order = WordProcessor.SortOrder.DESCENDING;

            // Get list of words
            ArrayList<String> words = WordProcessor.getWordList(rawFileContent);
            if (words.isEmpty()) {
                System.err.println("Empty word list, Check File");
                return;
            }
            // Add to output the sorted by Lexicographically
            outputFileTxt += WordProcessor.uniqueSortedByLexicographically(words, order);
            // Add to output the sorted by Frequency
            outputFileTxt += WordProcessor.wordFrequencyText(words);
            // Write to F2 file
            fileUtil.setFileContent(outputFileTxt, outputFilePath);
        }catch (Exception e){
            for (var handler : LOGGER.getHandlers()) {
                handler.flush();
                handler.close();
            }
        }
    }
}