package logic;

import utilities.FileUtil;

import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.logging.Level;
import java.util.logging.Logger;


public class WordProcessor {
    // Logger specific to this class
    private static final Logger LOGGER = Logger.getLogger(WordProcessor.class.getName());

    // Enum to define sorting order (Ascending or Descending)
    public enum SortOrder {
        ASCENDING,
        DESCENDING
    }

    /**
     * Get the most frequent word and its frequency from the input text.
     *
     * @param strText The input text.
     * @return A string showing the most frequent word and its count.
     */
    public static String wordFrequencyText(ArrayList<String> words) {
        if (words == null || words.isEmpty()) {
            LOGGER.log(Level.SEVERE, "Invalid input, empty 'words' list");
            throw new IllegalArgumentException("Word list cannot be empty");
        }
        Map<String, Long> wordsFrequencyMap = getWordFrequencyMap(words);
        String mostFrequentWord = getMostFrequentWord(wordsFrequencyMap);
        return "\nThe most frequent word in the text is - '" + mostFrequentWord +"', count: " + wordsFrequencyMap.get(mostFrequentWord) +"\n";
    }

    /**
     * Get a list of unique words sorted lexicographically in ascending or descending order.
     *
     * @param words     List of words to be sorted.
     * @param sortOrder Sort order - ascending or descending.
     * @return A string with sorted unique words.
     */
    public static String uniqueSortedByLexicographically(ArrayList<String> words, SortOrder sortOrder) {
        if (words == null || words.isEmpty()) {
            LOGGER.log(Level.SEVERE, "Invalid input, empty 'words' list");
            throw new IllegalArgumentException("Word list cannot be empty");
        }

        Comparator<String> comparator = sortOrder.equals(SortOrder.ASCENDING) ? Comparator.naturalOrder() : Comparator.reverseOrder();
        Set<String> sortedSet = new TreeSet<>(comparator);
        sortedSet.addAll(words);
        return "\nF1 Words list ordered lexicographically by " + sortOrder.toString() + ":\n" + String.join(", \n", sortedSet);
    }

    /**
     * Extracts a list of words from the input string.
     * Converts all words to lowercase and removes punctuation.
     *
     * @param str Input string
     * @return ArrayList of words
     */
    public static ArrayList<String> getWordList(String str) {
        if (str == null || str.isEmpty()) {
            LOGGER.log(Level.SEVERE, "Invalid input, empty str from raw file");
            throw new IllegalArgumentException("str cannot be empty");
        }
        return (ArrayList<String>) Arrays.stream(str.toLowerCase().split("\\W+"))
                    .filter(word -> !word.isEmpty())
                    .collect(Collectors.toList());

    }

    /**
     * Creates a map of word frequencies from a list of words.
     *
     * @param words List of words
     * @return Map<String, Long> containing word frequencies
     */
    private static Map<String, Long> getWordFrequencyMap(ArrayList<String> words) {
        return words.stream()
                .collect(Collectors.groupingBy(word -> word, Collectors.counting()));
    }

    /**
     * Finds the most frequent word from a frequency map.
     *
     * @param frequencyMap Map containing word frequencies
     * @return The most frequent word or null if the map is empty
     */
    private static String getMostFrequentWord(Map<String, Long> frequencyMap) {
        return frequencyMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }
}
