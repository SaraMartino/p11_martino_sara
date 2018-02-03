package sistema;

import java.util.*;

/**
 * ApplicazioneAmministratoreSistema.java
 * <p>
 * This class represents the Applicazione Amministratore Sistema,
 * extends Applicazione.java.
 * It is characterized by the following attributes:
 * admin (the administrator of the system), listaGestori (HashMap
 * containing all the GestoreCinema registered in the system),
 * mapCinema (HashMap containing a list of cinema for each GestoreCinema
 * registered in the system), listaFilm (HashMap containing all the film
 * of the Circuito Cinema).
 *
 * @author Sara Martino
 */
public class ApplicazioneAmministratoreSistema extends Applicazione {
	private static AmministratoreSistema admin;
	private static HashMap <String, GestoreCinema> listaGestori;
	private static HashMap <String, ListaCinema> mapCinema;
	private static HashMap <String, Film> listaFilm;
	
	static {
		listaGestori = new HashMap<String, GestoreCinema>();
		mapCinema = new HashMap<String, ListaCinema>();
		listaFilm = new HashMap<String, Film>();
	}
	
	/**
	 * Constructs an object of type ApplicazioneAmministratoreSistema using the input parameters.
	 * <p>
	 * Throws an exception if username or password are null.
	 * 
	 * @param nome				The name of the admin
	 * @param cognome			The surname of the admin
	 * @param codiceFiscale		The fiscal code of the admin
	 * @param dataNascita		The date of birth of the admin
	 * @param username			The username of the admin
	 * @param password			The password of the admin
	 * @param email				The email of the admin
	 * @throws	IllegalArgumentException
	 */
	public ApplicazioneAmministratoreSistema(String nome, String cognome, String codiceFiscale, Calendar dataNascita,
			String username, String password, String email) {
		admin = new AmministratoreSistema(nome, cognome, codiceFiscale, dataNascita,
			username, password, email);
	}
	
	/**
	 * Returns true if the Amministratore Sistema is logged, false otherwise.
	 * 
	 * @return true if the user is logged, false otherwise
	 */
	public boolean isAdminLogged() {
		return admin.isLogged();
	}
	
	/**
	 * Clears all the lists/maps of this app. It is useful to reset the system
	 * <p>
	 * Returns false if the administrator is not logged.
	 * 
	 * @return true if the reset is successful, false otherwise
	 */
	public boolean resetApplication() {
		if (!admin.isLogged()) {
			return false;
		} else {
			listaGestori.clear();
			mapCinema.clear();;
			listaFilm.clear();;
			return true;
		}	
	}
	
	@Override
	public boolean login(String username, String password) {
		if (username == null || password == null || !admin.getUsername().equals(username) ||
				!admin.getPassword().equals(password) || admin.isLogged()) {
			return false;
		} else {
			admin.setAutenticato(true);
			return true;
		}
	}

	@Override
	public boolean logout() {
		if (!admin.isLogged()) {
			return false;
		} else {
			admin.setAutenticato(false);
			return true;
		}
	}
	
	/**
	 * Returns the GestoreCinema specified by the username passed as input.
	 * <p>
	 * Returns null if there are no registered managers with the specified username.
	 * @param usernameGestoreCinema		The username of the GestoreCinema
	 * @return The GestoreCinema if it is present, null otherwise
	 */
	public static GestoreCinema getRegisteredGestoreCinema(String usernameGestoreCinema) {
		if (listaGestori.containsKey(usernameGestoreCinema))
			return listaGestori.get(usernameGestoreCinema);
		else
			return null;
	}
	
	/**
	 * Adds a new GestoreCinema to the list of managers of the Circuito Cinema.
	 * <p>
	 * Returns false if the AmministratoreSistema is not logged or if there is
	 * already a GestoreCinema with the same fiscal code.
	 * 
     * @param  nome							The name of the new manager
     * @param  cognome						The surname of the new manager
     * @param  codiceFiscale				The fiscal code of the new manager
     * @param  dataNascita					The date of birth of the new manager
     * @param  email						The email of the new manager
	 * @return true if the new manager is added, false otherwise
	 */
	public boolean registraNuovoGestoreCinema(String nome, String cognome, String codiceFiscale,
			Calendar dataNascita, String email) {
		if (!admin.isLogged()) {
			return false;
		} else {
			Iterator<GestoreCinema> gestori = listaGestori.values().iterator();
			while (gestori.hasNext()) {
				// Simplification: a manager is uniquely identified by his fiscal code
				if (gestori.next().getCodiceFiscale().equals(codiceFiscale)) {
					return false;
				}
			}
			
			// Simplification of the generation of the GestoreCinema credentials by the system
			String username = codiceFiscale;
			String password = "0000";
			GestoreCinema newGestore = new GestoreCinema(nome, cognome, codiceFiscale, dataNascita,
					username, password, email);
			listaGestori.put(newGestore.getUsername(), newGestore);
			mapCinema.put(newGestore.getUsername(), new ListaCinema());
			// Send an email with the credentials to the GestoreCinema
			sendEmail(email, username, password);
			return true;
		}
	}
	
	/**
	 * Removes the GestoreCinema specified by the username passed as input from the list of
	 * managers of the Circuito Cinema.
	 * <p>
	 * Returns false if the AmministratoreSistema is not logged or if the username is not
	 * valid.
	 * 
     * @param  usernameGestoreCinema				The username of the manager
	 * @return true if the manager is removed, false otherwise
	 */
	public boolean removeGestoreCinema(String usernameGestoreCinema) {
		if (!admin.isLogged()) {
			return false;
		} else {
			if (!listaGestori.containsKey(usernameGestoreCinema)) {
				return false;
			}
			listaGestori.get(usernameGestoreCinema).setAutenticato(false);
			listaGestori.remove(usernameGestoreCinema);
			mapCinema.remove(usernameGestoreCinema);			
			return true;
		}
	}
	
	/**
	 * Prints the info of each registered Gestore Cinema.
	 * <p>
	 * Returns false if the Amministratore Sistema is not registered and logged
	 * or if the list of room is empty.
	 * 
	 * @return true if the operation has been successful, false otherwise
	 */
	public boolean printAllManagers() {
		if (!admin.isLogged()) {
			return false;
		} else {
			if (listaGestori.isEmpty()) {
				return false;
			}
			else {
				Iterator<GestoreCinema> i = listaGestori.values().iterator();
				while (i.hasNext()) {
					i.next().printProfilo();
				}
				return true;
			}
		}
	}
	
	/**
	 * Modifies a GestoreCinema of the list of managers of the Circuito Cinema.
	 * <p>
	 * Returns false if the AmministratoreSistema is not logged or if the username is not
	 * valid.
	 * 
     * @param  usernameGestoreCinema		The username of the manager
     * @param  email						The new email of the manager
	 * @return true if the email of manager is modified, false otherwise
	 */
	public boolean modifyEmailGestoreCinema(String usernameGestoreCinema, String email) {
		if (!admin.isLogged()) {
			return false;
		} else {
			if (!listaGestori.containsKey(usernameGestoreCinema)) {
				return false;
			}
			
			listaGestori.get(usernameGestoreCinema).setEmail(email);			
			return true;
		}
	}
	
	/**
	 * Modifies a GestoreCinema of the list of managers of the Circuito Cinema.
	 * <p>
	 * Returns false if the AmministratoreSistema is not logged, if the username is not
	 * valid or if the password is not correctly formatted (minimum 4 characters, no blank space).
	 * 
     * @param  usernameGestoreCinema		The username of the manager
     * @param  password						The new password of the manager
	 * @return true if the password of the manager is modified, false otherwise
	 */
	public boolean modifyPasswordGestoreCinema(String usernameGestoreCinema, String password) {
		if (!admin.isLogged()) {
			return false;
		}
		else {
			if (!listaGestori.containsKey(usernameGestoreCinema)) {
				return false;
			}
			else if (password == null || password.length() < 4 || password.contains(" ")) {
				return false;
			}
			else {
				listaGestori.get(usernameGestoreCinema).setPassword(password);			
				return true;
			}
		}
	}
	
	/**
	 * Adds a new Cinema to the list of Cinema of the specified GestoreCinema.
	 * <p>
	 * Returns false if the AmministratoreSistema is not logged, if the
	 * GestoreCinema is not registered in the system, or if there is
	 * already a Cinema with the same name and address.
	 * 
     * @param  usernameGestoreCinema		The username of the GestoreCinema
     * @param  cinema						The new cinema
	 * @return true if the new cinema is added, false otherwise
	 */
	public boolean addNewCinema(String usernameGestoreCinema, Cinema cinema) {
		if (!admin.isLogged() || !mapCinema.containsKey(usernameGestoreCinema)) {
			return false;
		}
		else {
			return mapCinema.get(usernameGestoreCinema).addNewCinema(cinema);
		}
	}
	
	/**
	 * Removes the cinema (specified by the id passed as input) from the list
	 * of the GestoreCinema (specified by the username passed as input).
	 * <p>
	 * Returns false if this cinema does not exist in the list.
	 * 
	 * @param usernameGestoreCinema		The GestoreCinema
	 * @param cinemaId					The id of the cinema
	 * @return true if the cinema is removed, false otherwise
	 */
	public boolean removeCinema(String usernameGestoreCinema, int cinemaId) {
		if (!admin.isLogged() || !mapCinema.containsKey(usernameGestoreCinema)) {
			return false;
		}
		else {
			return mapCinema.get(usernameGestoreCinema).removeCinema(cinemaId);
		}
	}
	
	/**
	 * Adds a new film to the films list of the Circuito Cinema.
	 * <p>
	 * Returns false if the AmministratoreSistema is not logged or if there is
	 * already a film with the same id.
	 * 
     * @param  id							An unique id identifying the film
     * 										For example EIDR Entertainment Identifier Register
     * @param  titolo						The title of the film
     * @param  regista						The director of the film
     * @param  attori						A list containing the main actors of the film
     * @param  durata						The duration of the film
     * @param  anno							The year of the film
     * @param  genere						A list of the genres of the film
     * @param  casaDiProduzione				The production company
     * @param  trama						The plot of the film
     * @param  tag							A list of tag that are pertinent to the film
	 * @return true if the new film is added, false otherwise
	 */
	public boolean inserisciNuovoFilm(String id, String titolo, String regista,
			ArrayList<String> attori, int durata, int anno, ArrayList<String> genere, String casaDiProduzione,
    		String trama, ArrayList<String> tag) {
		if (!admin.isLogged()) {
			return false;
		} else {
			Iterator<Film> allFilm = listaFilm.values().iterator();
			while (allFilm.hasNext()) {
				if (allFilm.next().getId().equals(id)) {
					return false;
				}
			}
			
			Film film = new Film(id, titolo, regista, attori, durata, anno, genere,
					casaDiProduzione, trama, tag);
			listaFilm.put(film.getId(), film);
			return true;
		}
	}
	
	/**
	 * Removes a film from the films list of the cinema of the Circuito Cinema.
	 * <p>
	 * Returns false if the AmministratoreSistema is not logged or if there is
	 * no film with the specified id.
	 * 
	 * @param idFilm The id of the film
	 * @return true if the film is removed, false otherwise
	 */
	public boolean eliminaFilm(String idFilm) {
		if (!admin.isLogged()) {
			return false;
		} else if (!listaFilm.containsKey(idFilm)) {
			return false;
		} else {
			listaFilm.remove(idFilm);
			return true;
		}
	}
	
	/**
	 * Prints the info of the AmministratoreSistema.
	 * <p>
	 * Returns false if the AmministratoreSistema is not logged.
	 * 
	 * @return true if the operation has been successful, false otherwise
	 */
	public boolean printProfilo() {
		if (!admin.isLogged()) {
			return false;
		} else {
			admin.printProfilo();
			return true;
		}
	}
	
	/**
	 * Changes the email of the AmministratoreSistema.
	 * <p>
	 * Returns false if the AmministratoreSistema is not logged or if the
	 * email is not correctly formatted
	 * 
	 * @param email								The new email
	 * @return true if the operation has been successful, false otherwise
	 */
	public boolean changeEmail(String email) {
		if (!admin.isLogged()) {
			return false;
		} else {
			// TODO validate email
			admin.setEmail(email);
			return true;
		}
	}
	
	/**
	 * Changes the username of the AmministratoreSistema.
	 * <p>
	 * Returns false if the AmministratoreSistema is not logged or if the
	 * username is not correctly formatted (not null, minimum 4 characters, no blank space)
	 *
	 * @param username							The new username
	 * @return true if the operation has been successful, false otherwise
	 */
	public boolean changeUsername(String username) {
		if (!admin.isLogged()) {
			return false;
		} else if (username == null || username.length() < 4 || username.contains(" ")) {
			return false;
		} else {
			admin.setUsername(username);
			return true;
		}
	}
	
	/**
	 * Changes the password of the AmministratoreSistema.
	 * <p>
	 * Returns false if the AmministratoreSistema is not logged or if the
	 * password is not correctly formatted (not null, minimum 4 characters, no blank space)
	 * 
	 * @param password							The new password
	 * @return true if the operation has been successful, false otherwise
	 */
	public boolean changePassword(String password) {
		if (!admin.isLogged()) {
			return false;
		} else if (password == null || password.length() < 4 || password.contains(" ")) {
			return false;
		} else {
			admin.setPassword(password);
			return true;
		}
	}
	
	/**
	 * Returns a ListaCinemaIterator over the list of Cinema of the GestoreCinema
	 * specified by the username passed as input.
	 * <p>
	 * Returns null if the specified GestoreCinema is not registered in the
	 * system.
	 * 
	 * @param usernameGestoreCinema	The username of the manager of the cinemas
	 * @return			The iterator over the list of cinemas
	 */
	public static ListaCinemaIterator getListaCinemaIterator(String usernameGestoreCinema) {
		if (!mapCinema.containsKey(usernameGestoreCinema)) {
			return null;
		}
		else {
			return mapCinema.get(usernameGestoreCinema).getIterator();
		}
	}
	
	/**
	 * Prints the base info of each film in the films list.
	 * <p>
	 * Returns false if the list of films is empty.
	 * 
	 * @return true if there is at least one film in the list, false otherwise
	 */
	public static boolean printAllFilm() {
		Iterator<Film> film = listaFilm.values().iterator();
		
		if (!film.hasNext()) {
			return false;
		}
		
		System.out.println("Film del Circuito Cinema:");
		while(film.hasNext()) {
			System.out.println("----------------------------------------------------------");
			film.next().printBaseInfo();
		}
		System.out.println();
		return true;
	}
	
	/**
	 * Prints the base info of each film in the list passed as input.
	 * 
	 * @param film The list of film to print
	 */
	public static void printFilmFromList(ArrayList<Film> film) {		
		for (int i = 0; i < film.size(); i++) {
			System.out.println("----------------------------------------------------------");
			film.get(i).printBaseInfo();
		}	
	}
	
	/**
	 * Prints all info (Scheda Film) of the film corresponding to the id passed as input.
	 * <p>
	 * Returns false if there is no film corresponding to this id.
	 * 
	 * @param filmId The id of the desired film
	 * @return true if there is the film that corresponds to this id, false otherwise.
	 */
	public static boolean printSchedaFilm(String filmId) {
		if (!listaFilm.containsKey(filmId)) {
			return false;
		}
		
		listaFilm.get(filmId).printAllInfo();
		return true;
	}
	
	/**
	 * Searches a films by title (or part of it) in the list of films.
	 * <p>
	 * If no film matches the search criteria, it returns an empty list; the return may not be null.
	 * 
	 * @param  param Title (or part of it) to search for
	 * @return	The list of films resulting from the search if present, an empty list otherwise
	 */
	public static ArrayList<Film> cercaFilmPerTitolo(String param) {
		ArrayList<Film> list = new ArrayList<Film>();
		Iterator<Film> allFilm = listaFilm.values().iterator();
		Film film = null;
		while (allFilm.hasNext()) {
			film = allFilm.next();
			boolean flag = film.compareTitle(param.trim());
			if (flag) {
				list.add(film);
			}
		}
		return list;
	}
	
	/**
	 * Searches a films by year in the list of films.
	 * <p>
	 * If no film matches the search criteria, it returns an empty list; the return may not be null.
	 * 
	 * @param  param Year to search for
	 * @return	The list of films resulting from the search if present, an empty list otherwise
	 */
	public static ArrayList<Film> cercaFilmPerAnno(int param) {
		ArrayList<Film> list = new ArrayList<Film>();
		Iterator<Film> allFilm = listaFilm.values().iterator();
		Film film = null;
		while (allFilm.hasNext()) {
			film = allFilm.next();
			if (film.compareYear(param)) {
				list.add(film);
			}
		}
		return list;
	}
	
	/**
	 * Searches a films by genre (only one) in the list of films.
	 * <p>
	 * If no film matches the search criteria, it returns an empty list; the return may not be null.
	 * 
	 * @param  param Genre to search for
	 * @return	The list of films resulting from the search if present, an empty list otherwise
	 */
	public static ArrayList<Film> cercaFilmPerGenere(String param) {
		ArrayList<Film> list = new ArrayList<Film>();
		Iterator<Film> allFilm = listaFilm.values().iterator();
		Film film = null;
		while (allFilm.hasNext()) {
			film = allFilm.next();
			if (film.compareGenre(param.trim().replaceAll("\\s+", " "))) {
				list.add(film);
			}
		}
		return list;
	}
	
	/**
	 * Searches a films by director (name and/or surname of a director) in the list of films.
	 * <p>
	 * If no film matches the search criteria, it returns an empty list; the return may not be null.
	 * 
	 * @param  param Director to search for
	 * @return	The list of films resulting from the search if present, an empty list otherwise
	 */
	public static ArrayList<Film> cercaFilmPerRegista(String param) {
		ArrayList<Film> list = new ArrayList<Film>();
		Iterator<Film> allFilm = listaFilm.values().iterator();
		Film film = null;
		while (allFilm.hasNext()) {
			film = allFilm.next();
			if (film.compareDirector(param.trim())) {
				list.add(film);
			}
		}
		return list;
	}
	
	/**
	 * Searches a films by actor (name and/or surname of an actor) in the list of films.
	 * <p>
	 * If no film matches the search criteria, it returns an empty list; the return may not be null.
	 * 
	 * @param  param Actor to search for
	 * @return	The list of films resulting from the search if present, an empty list otherwise
	 */
	public static ArrayList<Film> cercaFilmPerAttore(String param) {
		ArrayList<Film> list = new ArrayList<Film>();
		Iterator<Film> allFilm = listaFilm.values().iterator();
		Film film = null;
		while (allFilm.hasNext()) {
			film = allFilm.next();
			if (film.compareActor(param.trim())) {
				list.add(film);
			}
		}
		return list;
	}
	
	/**
	 * Searches a films by production company (only one) in the list of films.
	 * <p>
	 * If no film matches the search criteria, it returns an empty list; the return may not be null.
	 * 
	 * @param  param Production company to search for
	 * @return	The list of films resulting from the search if present, an empty list otherwise
	 */
	public static ArrayList<Film> cercaFilmPerCasaDiProduzione(String param) {
		ArrayList<Film> list = new ArrayList<Film>();
		Iterator<Film> allFilm = listaFilm.values().iterator();
		Film film = null;
		while (allFilm.hasNext()) {
			film = allFilm.next();
			if (film.compareProductionCompany(param.trim().replaceAll("\\s+", " "))) {
				list.add(film);
			}
		}
		return list;
	}

	/**
	 * Searches a films by tags (also more than one) in the list of films.
	 * <p>
	 * If no film matches the search criteria, it returns an empty list; the return may not be null.
	 * 
	 * @param  param Tags to search for
	 * @return	The list of films resulting from the search if present, an empty list otherwise
	 */
	public static ArrayList<Film> cercaFilmPerTag(String param) {
		ArrayList<Film> list = new ArrayList<Film>();
		Iterator<Film> allFilm = listaFilm.values().iterator();
		Film film = null;
		while (allFilm.hasNext()) {
			film = allFilm.next();
			if (film.compareTag(param.trim())) {
				list.add(film);
			}
		}
		return list;
	}
	
	/**
	 * Searches a films by id in the list of films.
	 * <p>
	 * If no film matches the search criteria, it returns null.
	 * 
	 * @param  param Id to search for
	 * @return	The film resulting from the search if present, null otherwise
	 */
	public static Film cercaFilmPerId(String param) {
		Iterator<Film> allFilm = listaFilm.values().iterator();
		Film film = null;
		while (allFilm.hasNext()) {
			film = allFilm.next();
			if (film.getId().equals(param)) {
				return film;
			}
		}
		return null;
	}
	
	/**
	 * Not yet implemented.
	 * <p>
	 * Send a mail to the specified email address passed an input.
	 * 
	 * @param email			The email address of the recipient
	 * @param arguments		An array of objects to be used to create the mail
	 */
	public void sendEmail(String email, Object... arguments) {
		// In this first implementation this function is not implemented
	}

}
