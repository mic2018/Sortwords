package utilities;

import java.io.*;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileUtil {
    private static final Logger LOGGER = Logger.getLogger(FileUtil.class.getName());

    // Base directory path for file operations
    private String DIRECTORY_PATH = "";

    /**
     * Constructor for FileUtil.
     * Initializes the base directory path.
     *
     * @param path The full path to the input file (F1.txt).
     *             The base directory is extracted by removing the file name.
     * @throws InvalidPathException If the provided path is invalid.
     */
    public FileUtil(String path) {
        try {
            // Remove "F1.txt" from the path to get the base directory
            this.DIRECTORY_PATH = getNormalizedPath(path.replace("F1.txt", "")).toString();
        } catch (InvalidPathException e) {
            LOGGER.log(Level.SEVERE, "Path is invalid: {0}", new Object[]{path, e.getMessage()});
            throw new IllegalArgumentException("Invalid path: " + path, e);
        }
    }

    /**
     * Reads the content of a file and returns it as a string.
     *
     * @param filePath Path to the file to be read.
     * @return Content of the file as a string.
     * @throws RuntimeException If there is an issue reading the file.
     */
    public String getFileContent(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(getNormalizedPath(filePath).toString()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n"); // Append each line with a newline
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading file", e.getMessage());
            throw new RuntimeException("Failed to read file: " + filePath, e);
        }
        return content.toString(); // Return the full file content as a string
    }

    /**
     * Writes the provided string to a file named "F2.txt" in the base directory.
     *
     * @param content The content to be written to the file.
     * @throws RuntimeException If there is an issue writing to the file.
     */
    public void setFileContent(String content, String outputFileName) {
        String fullPath = Paths.get(DIRECTORY_PATH).resolve(outputFileName).toString();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fullPath))) {
            writer.write(content);
            System.out.println("File written successfully to " + fullPath);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error writing to file", e.getMessage());
            throw new RuntimeException("Failed to write to file: F2.txt", e);
        }
    }

    /**
     * Normalizes the given file path.
     * Removes any redundant "." and ".." elements.
     *
     * @param path The file path to normalize.
     * @return The normalized Path object.
     * @throws InvalidPathException If the path format is invalid.
     */
    protected Path getNormalizedPath(String path) throws InvalidPathException {
        return Paths.get(path).normalize();
    }
}
