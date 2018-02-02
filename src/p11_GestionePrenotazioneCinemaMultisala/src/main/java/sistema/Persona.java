package sistema;

import java.util.*;

/**
* Persona.java
* <p>
* A representation of a person, characterized by name, surname, fiscal code and date of birth. 
*
* @author Sara Martino
*/
public class Persona {
	private String nome;
	private String cognome;
	private String codiceFiscale;
	private Calendar dataNascita;
	
	/**
	 * 
	 * Constructs an object of type Persona using the input parameters.
	 * 
	 * @param  nome					The name of this person
	 * @param  cognome				The surname of this person
	 * @param  codiceFiscale		The fiscal code of this person
	 * @param  dataNascita			The date of birth of this person
    */
	public Persona(String nome, String cognome, String codiceFiscale, Calendar dataNascita) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.codiceFiscale = codiceFiscale;
		this.dataNascita = dataNascita;
	}
	
	/**
	 * Returns the name.
	 * 
	 * @return the name
	 */
	public String getNome() {
		return nome;
	}
	
	/**
	 * Returns the surname.
	 * 
	 * @return the surname
	 */
	public String getCognome() {
		return cognome;
	}
	
	/**
	 * Returns the date of birth.
	 * 
	 * @return the date of birth
	 */
	public Calendar getDataNascita() {
		return dataNascita;
	}
	
	/**
	 * Returns the fiscal code.
	 * 
	 * @return the fiscal code
	 */
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	
	/**
     * Prints the attributes of the class Persona.
     */
    public void print() {
    	System.out.println("Nome:                    " + this.nome);
		System.out.println("Cognome:                 " + this.cognome);
		System.out.println("Data di nascita:         " + this.dataNascita.get(Calendar.DAY_OF_MONTH)
				+ "/" + this.dataNascita.get(Calendar.MONTH) + "/" + this.dataNascita.get(Calendar.YEAR));		
		System.out.println("Codice fiscale:          " + this.codiceFiscale);
    }
}
