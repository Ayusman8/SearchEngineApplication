package SearchEngineApplication;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileSearcher extends Thread {

    // Logger for logging messages
    private static final Logger LOG = LoggerFactory.getLogger(FileSearcher.class);

    // Variables to store drive, file name to search, and search result
    private String drive;
    private String fileToSearch;
    private SearchResult result;

    // Constructor to initialize variables
    public FileSearcher(String drive, String fileToSearch) {
        result = new SearchResult();
        this.drive = drive;
        this.fileToSearch = fileToSearch;

        result.setDrive(drive);
    }

    // Method to get search result
    public SearchResult getSearchResult() {
        return result;
    }

    // Method to search for a file in a given drive
    public void searchFile(String drive, String fileName) {

        // Increment the count of folders scanned
        result.incrementNoOfFoldersScanned();
        // Create a File object for the given drive path
        File directory = new File(drive.trim());

        // List files in the directory
        File[] files = directory.listFiles();

        // Iterate through the files in the directory
        if (files != null) {
            for (File file : files) {
                // If it's a directory, recursively search inside
                if (file.isDirectory()) {
                    searchFile(file.getAbsolutePath(), fileName);
                } 
                // If it's a file and matches the search criteria, add it to the search result
                else if (file.getName().equalsIgnoreCase(fileName)) {
                    result.addFilePathsFound(file.getAbsolutePath());
                }
            }
        }
    }

    // Override run method of Thread class
    @Override
    public void run() {
        try {
            // Start searching the file from the root of the drive
            searchFile(this.drive, this.fileToSearch);
        } catch (Exception e) {
            // Log any exceptions that occur during the search
            LOG.error(e.getMessage());
        }
    }
}
