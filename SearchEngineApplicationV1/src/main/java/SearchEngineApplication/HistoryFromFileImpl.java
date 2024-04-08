package SearchEngineApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HistoryFromFileImpl implements ISearchHistoryManagerImpl{

    // Method to retrieve old search data based on file name and drives to be searched
	@Override
    public List<String> getOldSearchData(String fileName, List<String> driveToBeSearchedIn) throws SearchHistoryManagerException {

        List<String> result = new ArrayList<String>();

        // Retrieve log data
        String logData = getLogData();

        // If log data exists, proceed with search
        if (logData != null) {
        	String regex = ".*?-\\s+[\\w\\:\\\\]+\\s+-\\s+(.*?$)";
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
            Matcher matcher = pattern.matcher(logData);

            // Iterate through matches and add to result list if directory is active
            while (matcher.find()) {
                if(containsIgnoreCase(matcher.group(1), fileName)) {
                	if (isDirectoryActive(matcher.group(1))) {
                        result.add(matcher.group(1).trim());
                    }
                }
            }
        }
        return result;
    }
	
    // Method to retrieve log data from file
    private String getLogData() throws SearchHistoryManagerException {
        String filePath = "O:\\LOGS\\OldSearchLogs.log";
        String logData = "";

        // StringBuilder to store the content of the file
        StringBuilder content = new StringBuilder();

        // Using try-with-resources to automatically close resources
        if (isDirectoryActive(filePath)) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                // Read each line and append it to the content StringBuilder
                while ((logData = reader.readLine()) != null) {
                    content.append(logData).append("\n"); // Append newline character if needed
                }
            } catch (IOException e) {
                throw new SearchHistoryManagerException("An error occurred during the execution of the getLogData method.");
            }
        } else {
            throw new SearchHistoryManagerException("Error while fetching logs from history.");
        }

        return content.toString();
    }

    // Method to check if a directory is active
    private boolean isDirectoryActive(String dir) {

        File directory = new File(dir.trim());

        // Check if directory exists and is readable
        if (directory.exists() && directory.canRead()) {
            return true;
        } else {
            return false;
        }
    }
    
	// Method to check if a string contains another string ignoring case
	public boolean containsIgnoreCase(String content, String subContent) {
		return content.toLowerCase().contains(subContent.toLowerCase());
	}
}
