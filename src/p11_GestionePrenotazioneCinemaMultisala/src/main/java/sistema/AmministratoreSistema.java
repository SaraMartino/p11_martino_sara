package sistema;

import java.util.*;

/**
 * GestoreCinema.java
 * <p>
 * A representation of a AmministratoreSistema Cinema; it extends
 * the Persona.java class.
 * An AmministratoreSistema has in addition the following attributes:
 * username, password, email, autenticato.
 *
 * @author Sara Martino
 */
public class AmministratoreSistema extends Persona {
	private String username;
	private String password;
	private String email;
	private boolean autenticato;
	
	/**
	 * Constructs an object of type AmministratoreSistema using the input
	 * parameters.
	 * <p>
	 * It calls the constructor of the base class Persona.java with the
	 * correct input parameters.
	 * 
	 * @param  nome					The name of this AmministratoreSistema
	 * @param  cognome				The surname of this AmministratoreSistema
	 * @param  codiceFiscale		The fiscal code of this
	 * 								AmministratoreSistema
	 * @param  dataNascita			The date of birth of this
	 * 								AmministratoreSistema
	 * @param  username				The username
	 * @param  password				The password
	 * @param  email				The email of this AmministratoreSistema
	 */
	public AmministratoreSistema(String nome, String cognome, String codiceFiscale,
			Calendar dataNascita, String username, String password, String email) {
		super(nome, cognome, codiceFiscale, dataNascita);
		this.username = username;
		this.password = password;
		this.email = email;
		this.autenticato = false;
	}
	
	/**
	 * Returns username.
	 * 
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Returns password.
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Returns email.
	 * 
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Returns true if the Amministratore Sistema is logged, false otherwise.
	 * 
	 * @return true if the user is logged, false otherwise
	 */
	public boolean isLogged() {
		return autenticato;
	}
	
	/**
	 * Sets autenticato.
	 * 
	 * @param autenticato The new value of autenticato
	 */
	public void setAutenticato(boolean autenticato) {
		this.autenticato = autenticato;
	}
	
	/**
	 * Sets username.
	 * 
	 * @param username The new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Sets password.
	 * 
	 * @param password The new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Sets email.
	 * 
	 * @param email The new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Prints the attributes of the class AmministratoreSistema (profilo).
	 * <p>
	 * It calls the print() method of the base class Persona.java.
	 */
	public void printProfilo() {
		super.print();
		System.out.println("Username:                " + this.username);
		System.out.println("Password:                " + this.password);
		System.out.println("Email:                   " + this.email);
	}
}
