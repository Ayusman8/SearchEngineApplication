package SearchEngineApplication;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchController {

    // Logger for logging messages
    private static final Logger LOG = LoggerFactory.getLogger(SearchController.class);
    
    // Instances of SearchManager and SystemDriveFinder
    protected SearchManager manager;
    protected SystemDriveFinder finder;

    // Constructor to initialize SearchManager and SystemDriveFinder
    public SearchController() {
        manager = new SearchManager();
        finder = new SystemDriveFinder();
    }

    // Method to display available drives
    protected void displayDriveList(List<String> driveList) {
        System.out.println("Available Drives are: ");
        for (String drive : driveList) {
            System.out.println(drive);
        }
    }

    // Method to get a list of available drives
    public List<String> getDriveList() throws DriveNotFoundException, ActiveDriveNotFoundException {
        List<String> driveList = finder.getDrives();
        // If no drives found, throw DriveNotFoundException
        if (driveList.isEmpty()) {
            throw new DriveNotFoundException("No drives found.");
        }
        return driveList;
    }
}
