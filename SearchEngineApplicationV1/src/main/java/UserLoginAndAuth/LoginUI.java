package UserLoginAndAuth;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import SearchEngineApplication.*;

public class LoginUI {

	private static final Logger LOG = LoggerFactory.getLogger(LoginUI.class);
	private static UserManager manager = new UserManager();

	public static void main(String[] args) throws AddressException {
			
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Welcome to Search Engine Application");		
		
		while(true) {
			
			System.out.println("1. Login\n2. Create User\n3. Close the application");
			int choice = Integer.parseInt(sc.nextLine());
			
			switch (choice) {
			case 1:
				System.out.println("Enter Username:");
				String username = sc.nextLine();
				System.out.println("Enter Password:");
				String password = sc.nextLine();
				
				boolean isLoggedIn = manager.userLogin(username, password);
				
				if(isLoggedIn) {
					try {
						// Create an instance of SearchUI and start the application
						new SearchUI().load(manager);
					} catch (Exception e) {
						LOG.error(e.getMessage());
					}
				}
				
				break;
				
			case 2:
				manager.create();
				break;
				
			case 3:
				System.out.println("Closing the application...");
				sc.close();
				return;
				
			default:
				System.out.println("Not a valid option");
				break;
			}
		}
	}
}
