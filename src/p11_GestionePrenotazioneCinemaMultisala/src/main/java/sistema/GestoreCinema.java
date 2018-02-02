package sistema;

import java.util.Calendar;

/**
 * GestoreCinema.java
 * <p>
 * A representation of a Gestore Cinema; it extends the Persona.java class. A GestoreCinema has in addition
 * the following attributes: username, password, email, cinema, autenticato.
 * 
 * @author Sara Martino
 */
public class GestoreCinema extends Persona {
	private String username;
	private String password;
	private String email;
	//private ApplicazioneGestoreCinema applicazione;
	private boolean autenticato;
	
	/**
	 * Constructs an object of type GestoreCinema using the input parameters.
	 * <p>
	 * It calls the constructor of the base class Persona.java with the correct input parameters.
	 * 
	 * @param  nome					The name of this GestoreCinema
	 * @param  cognome				The surname of this GestoreCinema
	 * @param  codiceFiscale		The fiscal code of this GestoreCinema
	 * @param  dataNascita			The date of birth of this GestoreCinema
	 * @param  username				The username
	 * @param  password				The password
	 * @param  email				The email of this GestoreCinema
	 */
	public GestoreCinema(String nome, String cognome, String codiceFiscale, Calendar dataNascita,
			String username, String password, String email) {
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
	 * Set the password.
	 * 
	 * @param password		The new password
	 */
	public void setPassword(String password) {
		this.password = password;
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
	 * Set the email.
	 * 
	 * @param email		The new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Returns true if the Gestore Cinema is logged, false otherwise.
	 * 
	 * @return true if the user is logged, false otherwise
	 */
	public boolean isLogged() {
		return autenticato;
	}
	
	/**
	 * Set autenticato.
	 * 
	 * @param autenticato The new value of autenticato
	 */
	public void setAutenticato(boolean autenticato) {
		this.autenticato = autenticato;
	}
	
	/**
	 * Prints the attributes of the class GestoreCinema (profilo).
	 * <p>
	 * It calls the print() method of the base class Persona.java.
	 */
	public void printProfilo() {
		super.print();
		System.out.println("Username:                " + this.username);
		System.out.println("Password:                " + this.password);
		System.out.println("Email:                   " + this.email);
		System.out.println();
	}
}
