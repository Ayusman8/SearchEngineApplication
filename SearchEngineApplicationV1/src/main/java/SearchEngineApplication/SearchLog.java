package SearchEngineApplication;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchLog {

    // Logger for logging messages
    private static final Logger LOG = LoggerFactory.getLogger(SearchLog.class);
    
    // Map to store search logs
    private Map<String, SearchResult> searchLog = new HashMap<>();

    // Method to add search results to the search log
    public void add(SearchResult result, List<String> pathsFromHistory) throws SearchLogException, SearchResultException {

        try {
            // Add the result to the search log
            searchLog.put(result.getDrive(), result);
            // Dump the log to file
            dumpLogToFile(searchLog, pathsFromHistory, "O:\\LOGS\\OldSearchLogs.log");
        } catch (SearchLogException e) {
            throw new SearchLogException("An error occurred during the execution of the SearchLog class.");
        }
    }

    // Method to dump the search log to a file
    public void dumpLogToFile(Map<String, SearchResult> searchLog, List<String> pathsFromHistory, String filePath) throws SearchLogException, SearchResultException {
        
    	try (FileWriter writer = new FileWriter(filePath, true)) {
            // Iterate over the entries of the HashMap
            Set<String> keySet = searchLog.keySet();
            for (String key : keySet) {
                if(!searchLog.get(key).getSearchedPaths().isEmpty()) {
                	for (String paths : searchLog.get(key).getSearchedPaths()) {
                        if(!pathsFromHistory.contains(paths)) {
                        	writer.write("[" + currentDateAndTime() + "] - " + key + " - " + paths + '\n');
                        }
                    }
                }else {
                	continue;
                }
            }
        } catch (IOException e) {
            throw new SearchLogException("An error occurred during the execution of the dumpLogToFile method.");
        } finally {
            // Clear the search log and result list
            this.searchLog.clear();
        }
    }

    // Method to get the current date and time
    private String currentDateAndTime() {
    	
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String currentDateAndTime = currentTime.format(formatter);
        return currentDateAndTime;
    }
}
