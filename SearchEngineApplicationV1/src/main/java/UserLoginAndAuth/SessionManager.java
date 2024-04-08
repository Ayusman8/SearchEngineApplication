package UserLoginAndAuth;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    
    // Map to store active sessions (token as key, user as value)
    private static final Map<String, User> sessions = new HashMap<>();

    // Method to create a new session for the user
    public static void createSession(String token, User user) {
        sessions.put(token, user);
    }

    // Method to retrieve the user associated with a session token
    public static User getUser(String token) {
        return sessions.get(token);
    }

    // Method to invalidate a session (logout)
    public static void invalidateSession(String token) {
        sessions.remove(token);
    }
}
