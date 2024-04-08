package SearchEngineApplication;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemDriveFinder {
    
    // Logger for logging messages
    private static final Logger LOG = LoggerFactory.getLogger(SystemDriveFinder.class);
    
    // Instance of ActiveDriveFinder
    ActiveDriveFinder activeFinder = new ActiveDriveFinder();   
    
    // Method to get drives
    public List<String> getDrives() throws DriveNotFoundException, ActiveDriveNotFoundException {

        // List to store drive names
        List<String> driveList = new ArrayList<>();

        // Get the roots (drives) from the file system
        File[] roots = File.listRoots();
        
        // Throw exception if no drives found
        if (roots == null || roots.length == 0) {
            throw new DriveNotFoundException("No drives found.");
        }

        // Add each root to the drive list
        for (File root : roots) {
            driveList.add(root.getAbsolutePath());
        }

        // Get active drives from ActiveDriveFinder
        return activeFinder.getActiveDrives(driveList);
    }
}
