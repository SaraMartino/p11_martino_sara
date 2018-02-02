package sistema;

/**
 * Applicazione.java
 * <p>
 * A representation of the application.
 *
 * @author Sara Martino
 */
public abstract class Applicazione {
	
	/**
	 * Logs in the user corresponding to the specified username.
	 * <p>
	 * Returns false if username or password are null, if there is no user
	 * with this username, if the password is wrong or if the user is already
	 * logged.
	 * 
	 * @return true if there is the login is successful, false otherwise
	 */
	public abstract boolean login(String username, String password);
	
	/**
	 * Logs out the user logged to the application.
	 * <p>
	 * Returns false if no user is logged to the application
	 * 
	 * @return true if there is the logout is successful, false otherwise
	 */
	public abstract boolean logout();
}
