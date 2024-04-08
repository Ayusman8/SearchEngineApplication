package SearchEngineApplication;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActiveDriveFinder {

    // Logger for logging messages
    private static final Logger LOG = LoggerFactory.getLogger(ActiveDriveFinder.class);
    
    // Method to get active drives from a list of drive paths
    public List<String> getActiveDrives(List<String> driveList) throws ActiveDriveNotFoundException {

        File directory;

        // Iterate through the list of drive paths
        for (int i = 0; i < driveList.size(); i++) {
            // Get the File object for the current drive path
            directory = new File(driveList.get(i));
            // Check if the directory is readable, if not, remove it from the list
            if (!directory.canRead()) {
                driveList.remove(i);
            }
        }

        // If no active drives found, throw exception
        if (driveList.isEmpty()) {
            throw new ActiveDriveNotFoundException("No active drives found.");
        }

        // Return the list of active drives
        return driveList;
    }

}
