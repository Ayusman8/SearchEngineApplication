package SearchEngineApplication;

import java.util.List;

public interface ISearchHistoryManagerImpl {
	
    public List<String> getOldSearchData(String fileName, List<String> driveToBeSearchedIn) throws SearchHistoryManagerException;

}
