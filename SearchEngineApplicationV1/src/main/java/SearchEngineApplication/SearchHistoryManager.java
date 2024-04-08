package SearchEngineApplication;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchHistoryManager {

    // Logger for logging messages
    private static final Logger LOG = LoggerFactory.getLogger(SearchHistoryManager.class);
    
    public List<String> getDataFromHistory(String fileName, List<String> driveToBeSearchedIn, String src) throws SearchHistoryManagerException {

    	ISearchHistoryManagerImpl searchHistoryManagerImpl = SearchHistoryManagerFactoryImpl.create(src);
    	
    	return searchHistoryManagerImpl.getOldSearchData(fileName, driveToBeSearchedIn);
    	
	}
}
