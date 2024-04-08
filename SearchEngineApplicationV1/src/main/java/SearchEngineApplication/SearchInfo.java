package SearchEngineApplication;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchInfo {

    // Logger for logging messages
    private static final Logger LOG = LoggerFactory.getLogger(SearchInfo.class);
    
    // Variables to store file name and drives
    private String fileName;
    private List<String> drives;

    // Constructor to initialize SearchInfo with drives and file name
    public SearchInfo(List<String> drive, String fileName) {
        this.drives = drive;
        this.fileName = fileName;
    }

    // Method to get the file name
    public String getFileName() {
        return fileName;
    }

    // Method to get the list of drives
    public List<String> getDrives() throws SearchInfoException {
        // Throw exception if no drives found
        if (drives == null || drives.isEmpty()) {
            throw new SearchInfoException("No drives found.");
        }
        return drives;
    }

    // Method to add a drive to the list of drives
    public void addDrive(String drive) {
        this.drives.add(drive);
    }
}
