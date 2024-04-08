package SearchEngineApplication;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchManager {

    // Logger for logging messages
    private static final Logger LOG = LoggerFactory.getLogger(SearchManager.class);
    
    // Method to perform search
    public List<SearchResult> search(SearchInfo searchInfo) throws SearchManagerException {
        try {
            // Initialize arrays and lists
            FileSearcher[] searchers = new FileSearcher[searchInfo.getDrives().size()];
            List<SearchResult> searchResults = new ArrayList<SearchResult>();
            List<FileSearcher> searchThreads = new ArrayList<>();

            // Create and start a new thread for each drive to search
            for (int i = 0; i < searchInfo.getDrives().size(); i++) {
                FileSearcher searcher = new FileSearcher(searchInfo.getDrives().get(i), searchInfo.getFileName());
                searcher.start(); // Start a new thread for each search
                searchThreads.add(searcher); // Add the thread to the list
                searchers[i] = searcher;
            }

            // Wait for all threads to finish
            for (FileSearcher thread : searchThreads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Collect search results from each thread
            for(int i = 0; i < searchers.length; i++) {
                searchResults.add(searchers[i].getSearchResult());
            }

            return searchResults;

        } catch (Exception e) {
            throw new SearchManagerException("An error occurred during the execution of the SearchManager class.");
        }
    }
}
