package UserLoginAndAuth;

import java.util.*;

public class NotificationManager {

    private final Map<String, String> eventRecipients;

    // Constructor to initialize SMTP server settings and event recipients
    public NotificationManager() {
    	
        eventRecipients = new HashMap<>();
    }

    // Method to add or update recipient for an event
    public void setRecipientForEvent(String event, String recipientEmail) {
        eventRecipients.put(event, recipientEmail);
    }


    // Method to send notifications for various events
    public void sendNotifications(String event) throws AddressException {
        String recipientEmail = eventRecipients.getOrDefault(event, "");
        if (!recipientEmail.isEmpty()) {
            String subject = "";
            String message = "";

            // Define subject and message based on the event
            switch (event) {
                case "login":
                    subject = "User Login Notification";
                    message = "A user has logged in.";
                    break;
                case "delete":
                    subject = "Delete Feature Notification";
                    message = "Delete feature has been used.";
                    break;
                case "update_permission":
                    subject = "User Permission/Configuration Update Notification";
                    message = "There is an update in user permission/configuration.";
                    break;
                case "error":
                    subject = "Error Notification";
                    message = "An error has occurred.";
                    break;
                default:
                    // Handle invalid event type
                    System.out.println("Invalid event type.");
                    return;
            }

            // Send email notification
            sendEmailNotification(subject, message, recipientEmail);
        }
    }

	private void sendEmailNotification(String subject, String message, String recipientEmail) {

		System.out.println("\n\nEmail sent to: "+recipientEmail+", Subject: "+subject+", Mail-body: "+message+"\n\n");
	}
}
