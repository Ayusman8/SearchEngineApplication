package SearchEngineApplication;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchResult {

    // Logger for logging messages
    private static final Logger LOG = LoggerFactory.getLogger(SearchResult.class);
    
    // List to store found file paths
    private List<String> filePathsFound = new ArrayList<>();

    // Drive where the search was conducted
    private String drive;

    // Number of folders scanned during the search
    private int noOfFoldersScanned = 0;

    // Method to get searched paths
    public List<String> getSearchedPaths() throws SearchResultException {
        // Throw exception if no paths found
        return filePathsFound;
    }
    
    // Method to add a file path to the list of found paths
    public void addFilePathsFound(String path) {
        filePathsFound.add(path);
    }

    // Method to clear the list of found paths
    public void clearResultList() {
        filePathsFound.clear();
    }
    
    // Method to increment the number of folders scanned
    public void incrementNoOfFoldersScanned() {
        noOfFoldersScanned++;
    }
    
    // Method to get the number of folders scanned
    public int getNoOfFoldersScanned() {
        return noOfFoldersScanned;
    }

    // Method to set the drive where the search was conducted
    public void setDrive(String drive) {
        this.drive = drive;
    }
    
    // Method to get the drive where the search was conducted
    public String getDrive() {
        return this.drive;
    }
}
