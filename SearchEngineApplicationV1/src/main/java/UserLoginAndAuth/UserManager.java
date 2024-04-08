package UserLoginAndAuth;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserManager {

	private Scanner sc = new Scanner(System.in);
	private NotificationManager notificationManager;
	private Map<String, User> users;
	private User user;


	private static String token;

	public User getUser() {
		return user;
	}
	
	public UserManager() {

		users = new HashMap<>();
		notificationManager = new NotificationManager();
		createDefaultUser();
	}

	private void createDefaultUser() {

		// Create default user with specific permissions
		User defaultUser = new User("admin", "admin");
		defaultUser.setPermissions(Arrays.asList("view_drives"));
		users.put("default", defaultUser);
	}

	public void create() {
		
		System.out.println("Enter Username:");
		String username = sc.nextLine();
		System.out.println("Enter Password:");
		String password = sc.nextLine();
		createUser(username, password);
	}

	// Methods to create user
	public void createUser(String username, String password) {

		if (!users.containsKey(username)) {
			user = new User(username, password);

			user.setUsername(username);
			user.setPassword(password);

			// Add default permissions for new user
			user.setPermissions(Arrays.asList("view_drives", "view_search_history", "delete_search_history"));
			users.put(username, user);
			System.out.println("User created successfully.");
		}else {
			System.out.println("Username: "+username+" is already been taken!");
		}
	}

	// Method to edit user
	public void editUser(String userName, String password) {

//		User newUser = new User(userName, password);
//		if (!users.containsKey(userName)) {
//
//			newUser.setUsername(userName);
//			newUser.setPassword(password);
//
//			// Add default permissions for new user
//			newUser.setPermissions(Arrays.asList("view_drives", "view_search_history", "delete_search_history"));
//			users.put(userName, newUser);
//			System.out.println("User Edited successfully.");
//
//		} else if (userName.contains(newUser.getUsername())) {
//			System.out.println("Username already exists. Please choose another one.");
//		}
		
		System.out.println("Enter a new Username:");
		String newUsername = sc.nextLine();
		System.out.println("Enter a new Password:");
		String newPassword = sc.nextLine();
		
		user.setUsername(newUsername);
		user.setPassword(newPassword);
		
		System.out.println("Username and password has been changed!");
	}

	public User authenticateUser(String username, String password) {

		if (users.containsKey(username)) {
			User user = users.get(username);
			if (user.getPassword().equals(password)) {
				return user;
			}
		}
		return null;
	}

	public boolean hasPermission(User user, String permission) {

		return user.getPermissions().contains(permission);
	}

	public boolean userLogin(String username, String password) throws AddressException {
		
		boolean isAuthenticated = false;
		
		if(users.containsKey(username)) {
			if(users.get(username).getPassword().equals(password)) {
				isAuthenticated = true;
				setToken(JwtUtil.generateToken(username));
				if (getToken() != null) {
					// Create a session for the authenticated user
					try {
						SessionManager.createSession(token, user);
						System.out.println("User session created: " + token);
					}catch(Exception e) {
						e.printStackTrace();
					}	
				}
				
				notificationManager.setRecipientForEvent("login", "dummy123@email.com");
				notificationManager.sendNotifications("login");
				
				System.out.println("User Logged-in Successfully!");
			}else {
				System.out.println("Wrong Password! Try again.");
			}
		}else {
			System.out.println("No user found with username: "+username );
		}
		return isAuthenticated;
	}
	
	public boolean logout() {
		
		boolean isLoggedOut = false;
		try {
			SessionManager.invalidateSession(token);
			isLoggedOut = true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return isLoggedOut;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		UserManager.token = token;
	}
}
