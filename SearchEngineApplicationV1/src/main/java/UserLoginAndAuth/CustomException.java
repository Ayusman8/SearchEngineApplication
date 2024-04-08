package UserLoginAndAuth;

final public class CustomException {

}

@SuppressWarnings("serial")
class SearchEngineException extends Exception {

	public SearchEngineException(String message) {
		
		super(message);
	}
}

@SuppressWarnings("serial")
class SearchUIException extends Exception {

	public SearchUIException(String message) {
		
		super(message);
	}
}

@SuppressWarnings("serial")
class SearchHistoryManagerException extends Exception {

	public SearchHistoryManagerException(String message) {
		
		super(message);
	}
}

@SuppressWarnings("serial")
class SearchResultException extends Exception {

	public SearchResultException(String message) {
		
		super(message);
	}
}

@SuppressWarnings("serial")
class SearchManagerException extends Exception {

	public SearchManagerException(String message) {
		
		super(message);
	}
}

@SuppressWarnings("serial")
class SearchLogException extends Exception {

	public SearchLogException(String message) {
		
		super(message);
	}
}

@SuppressWarnings("serial")
class SearchInfoException extends Exception {

	public SearchInfoException(String message) {
		
		super(message);
	}
}

@SuppressWarnings("serial")
class DriveNotFoundException extends Exception {

	public DriveNotFoundException(String message) {
		
		super(message);
	}
}

@SuppressWarnings("serial")
class FileSearchException extends Exception {

	public FileSearchException(String message) {
		
		super(message);
	}
}

@SuppressWarnings("serial")
class ActiveDriveNotFoundException extends Exception {

	public ActiveDriveNotFoundException(String message) {
		
		super(message);
	}
}

@SuppressWarnings("serial")
class AuthenticationException extends Exception {

    public AuthenticationException(String message) {
    	
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
    	
        super(message, cause);
    }
}

@SuppressWarnings("serial")
class AddressException extends Exception{
	
	public AddressException(String message) {

		super(message);
	
	}
}