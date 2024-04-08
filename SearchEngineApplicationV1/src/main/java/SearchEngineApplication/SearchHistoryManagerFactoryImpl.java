package SearchEngineApplication;

public class SearchHistoryManagerFactoryImpl {
	
	public static ISearchHistoryManagerImpl create(String src) {
		
		if(src.toLowerCase().contains("file")) {
			return new HistoryFromFileImpl();
		}else if(src.toLowerCase().contains("db")) {
			return new HistoryFromDBImpl();
		}else {
			return null;
		}
		
	}
}
