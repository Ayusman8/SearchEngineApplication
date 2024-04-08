package SearchEngineApplication;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchModule {
	
    private static final Logger LOG = LoggerFactory.getLogger(SearchModule.class);
	        
	public void getAllSearchHistory() {

		String regex = "(\\[[\\d\\-\\s\\:]+\\].*?$)";
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE); 
		Matcher matcher = pattern.matcher(getAllHistoryData());
		while(matcher.find()) {
			System.out.println(matcher.group(1));
		}
		
	}

	private String getAllHistoryData() {
		
		String filePath = "O:\\LOGS\\Search_LOG.log";
        String logData = "";

        // StringBuilder to store the content of the file
        StringBuilder content = new StringBuilder();
		
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Read each line and append it to the content StringBuilder
            while ((logData = reader.readLine()) != null) {
                content.append(logData).append("\n"); // Append newline character if needed
            }
            logData = content.toString();
        } catch (IOException e) {
        	e.printStackTrace();
        }
		return logData;
	}
	
	public void getAllSearchHistoryBetweenDates(String fromDate, String toDate) {
		
		String regex = "(\\["+fromDate+".*?"+toDate+".*?$)";
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE); 
		Matcher matcher = pattern.matcher(getAllHistoryData());
		
		if(matcher.find()) {
			String historyBetweenDates = matcher.group(1);
			
			regex = "(\\[[\\d\\-\\s\\:]+\\].*?$)";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(historyBetweenDates);
			
			while(m.find()) {
				System.out.println(matcher.group(1));
			}
		}
	}
	
	public void storeSearchData(String drives, String fileName) {
		
		try (FileWriter writer = new FileWriter("O:\\LOGS\\Search_LOG.log", true)) {
            writer.write("[" + currentDateAndTime() + "] - Drive: "+drives.toUpperCase().replace(",", ", ")+" - File Name: "+fileName+"\n");
		} catch (IOException e) {
        	e.printStackTrace();
        }
	}
    
    private String currentDateAndTime() {
    	
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String currentDateAndTime = currentTime.format(formatter);
        return currentDateAndTime;
    }
}
