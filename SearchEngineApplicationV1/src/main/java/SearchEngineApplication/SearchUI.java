package SearchEngineApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import UserLoginAndAuth.UserManager;

import java.lang.ModuleLayer.Controller;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle.Control;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchUI {

	// Logger for logging messages
	private static final Logger LOG = LoggerFactory.getLogger(SearchUI.class);

	// Scanner object for user input
	private final Scanner sc = new Scanner(System.in);

	// Instances of various components
	protected SearchController controller;
	protected SearchManager manager;
	protected SearchHistoryManager historyManager;
	protected SearchLog searchLog;
	protected SearchModule searchModule;

	// Constructor to initialize components
	public SearchUI() {
		
		controller = new SearchController();
		manager = new SearchManager();
		historyManager = new SearchHistoryManager();
		searchLog = new SearchLog();
		searchModule = new SearchModule();
	}

	// Method to start the search UI
	public void load(UserManager manager) throws SearchEngineException, NullValuePassedException, DriveNotFoundException, LogoutException {
		
		System.out.println("Enter your choice: \n1. Search file \n2. Show search history \n3. Show search history between dates \n4. Edit User \n5. Logout");
		int choice = Integer.parseInt(sc.nextLine());
		switch (choice) {
		case 1:
			initiateSearch();
			break;

		case 2:
			searchModule.getAllSearchHistory();
			break;

		case 3:
			System.out.println("Please enter the dates you want to show the search history(Format: dd-MM-YYYY)");
			System.out.println("Enter from-date");
			String fromDate = sc.nextLine();
			System.out.println("Enter to-date");
			String toDate = sc.nextLine();
			searchModule.getAllSearchHistoryBetweenDates(fromDate, toDate);
			break;
			
		case 4:
			System.out.println("Enter Username:");
			String username = sc.nextLine();
			System.out.println("Enter Password:");
			String password = sc.nextLine();
			if(username==manager.getUser().getUsername() && password ==manager.getUser().getPassword()) {
				manager.editUser(username, password);
			}
			
		case 5:
			System.out.println("Logging out...");
			boolean isLoggedOut = manager.logout();
			if(isLoggedOut) {
				System.out.println("Logged out successfully!");
			}else {
				throw new LogoutException("Unexpected error occured during logout");
			}
			return;
			
		default:
			System.out.println("Not a valid option");
			break;
		}
		load(manager);
	}

	// Method to get user input for drives choice
	private List<String> getDriveChoice() throws DriveNotFoundException, ActiveDriveNotFoundException {
		
		System.out.println("Enter the drives name you want to search in (Should be comma separated)");
		String driveInput = sc.nextLine();
		
		HashSet<String> set = new HashSet<>(new ArrayList<String>(Arrays.asList(driveInput.toUpperCase().split(","))));
		List<String>driveToBeSearchedIn = new ArrayList<>(set);
		Iterator<String> iterator = driveToBeSearchedIn.iterator();
		
		for(int i=0; i<driveToBeSearchedIn.size(); i++) {
			driveToBeSearchedIn.set(i, driveToBeSearchedIn.get(i).concat(":\\"));
		}
		
		if (!driveInput.isBlank()) {
			while(iterator.hasNext()) {
				if(!controller.getDriveList().contains(iterator.next())) {
					iterator.remove();
				}
			}

		} else {
			System.out.println("You have not entered any drive");
			getDriveChoice();
		}
		return driveToBeSearchedIn;
	}

	private void initiateSearch() throws SearchEngineException, DriveNotFoundException {
		
		try {
			// Display available drives
			controller.displayDriveList(controller.getDriveList());

			// Get user input for drives and file name to search
			List<String> drivesToBeSearchedIn = getDriveChoice();
			String fileName = getFileName();

			searchModule.storeSearchData(drivesToBeSearchedIn.toString(), fileName);

			String choice = null;

			// Get old search data from history
			List<String> pathsFromHistory = historyManager.getDataFromHistory(fileName, drivesToBeSearchedIn, "file");
			List<SearchResult> paths = null;

			// Check if any old search data matches the current search criteria
			if (pathsFromHistory.size() != 0) {
				for(String historyPath : pathsFromHistory) {
					System.out.println(historyPath + " ---------------------------------------> FROM HISTORY");
				}
			}

			// Perform search for drives which don't have results in history
			if (!drivesToBeSearchedIn.isEmpty()) {
				if (!fileName.isBlank()) {
					SearchInfo info = new SearchInfo(drivesToBeSearchedIn, fileName);
					paths = manager.search(info);
				} else {
					throw new NullValuePassedException(
							"File name is blank! Please ensure to give correct information.");
				}
				displayResult(paths, pathsFromHistory);
				System.out.println("Do you want to save the results?(Y/N)");
				choice = sc.nextLine();
				if (containsIgnoreCase(choice, "Y")) {
					logResult(paths, pathsFromHistory);
				}
			}
		}catch (DriveNotFoundException ex) {
            throw new DriveNotFoundException("Drive not found");
        } catch (Exception e) {
            throw new SearchEngineException("An error occurred during the execution of the SearchUI class.");
        }
	}
	

	// Method to get user input for file name
	private String getFileName() {
		
		System.out.println("Enter the file name with extension you want to search");
		String fileName = sc.nextLine();
		if (!fileName.isBlank()) {
			if (fileName.matches("[\\w\\d[0-9].]+?\\..*?$")) {
				return fileName;
			} else {
				System.out.println("Extension is missing, Please follow the Instruction");
				getFileName();
			}
		} else {
			System.out.println("You have not entered any file name");
			getFileName();
		}
		return fileName;
	}

	// Method to log search results
	private void logResult(List<SearchResult> paths, List<String> pathsFromHistory) throws SearchUIException {
		
		try {
			for (SearchResult result : paths) {
				searchLog.add(result, pathsFromHistory);
			}
		} catch (Exception e) {
			throw new SearchUIException("An error occurred during the execution of the logResult method.");
		}
	}
	
	// Method to display search results
	private void displayResult(List<SearchResult> paths, List<String> pathFromHistory) throws SearchResultException {
		
		for (SearchResult result : paths) {
			if (!result.getSearchedPaths().isEmpty()) {
				for (String path : result.getSearchedPaths()) {
					if(!pathFromHistory.contains(path)) {
						System.out.println(path);
					}
				}
			} else
				System.out.println("No file found in Drive: " + result.getDrive());
			continue;
		}
	}

	// Method to check if a string contains another string ignoring case
	public boolean containsIgnoreCase(String content, String subContent) {
		
		return content.toLowerCase().contains(subContent.toLowerCase());
	}
}
