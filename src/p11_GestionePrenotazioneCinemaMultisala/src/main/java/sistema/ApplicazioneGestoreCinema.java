package sistema;

import java.util.*;

/**
 * ApplicazioneGestoreCinema.java
 * <p>
 * This class represents the Applicazione Gestore Cinema, extends
 * Applicazione.java.
 *
 * @author Sara Martino
 */
public class ApplicazioneGestoreCinema extends Applicazione {
	private GestoreCinema gestore;
	
	/**
	 * Constructs an object of type ApplicazioneGestoreCinema with a null
	 * manager.
	 */
	public ApplicazioneGestoreCinema() {
		gestore = null;
	}
	
	/**
	 * Refreshes the reference to the GestoreCinema of this app if it is
	 * present in the system.
	 * In case the manager has been unregistered, this function sets
	 * this.gestore to null.
	 * <p>
	 * Returns false if the connection has not already been established or
	 * if the manager has been unregistered from the system by the admin.
	 * 
	 */
	private void refreshGestoreCinema() {
		if (gestore == null)
			return;
		
		GestoreCinema registeredGestore = ApplicazioneAmministratoreSistema.getRegisteredGestoreCinema(gestore.getUsername());
		if (registeredGestore == null) {
			// It means that this.gestore has been unregistered from the system by the admin
			gestore = null;
			return;
		}	
		else {
			// Check that the reference is correct
			if (gestore != registeredGestore)
				this.gestore = registeredGestore;
			return;
		}
	}
	
	@Override
	public boolean login(String username, String password) {
		if (username == null || password == null)
			return false;
		
		// Gets the reference to the GestoreCinema specified by the username passed as input
		GestoreCinema registeredGestore = ApplicazioneAmministratoreSistema.getRegisteredGestoreCinema(username);
		if (registeredGestore == null) {
			// The username specified by input is not a username of a registered GestoreCinema
			// (the manager corresponding to the specified username has never been registered
			// or has been removed by the admin)
			this.gestore = null;
			return false;		
		}
		
		// Sets the reference if the manager is logging into the app for the fist time,
		// if he is not the last logged manager or if the reference is changed
		if (gestore == null || !gestore.getUsername().equals(registeredGestore.getUsername())
				|| gestore != registeredGestore)
			this.gestore = registeredGestore;
		
		// Returns false if the gestore is already logged or if the password is wrong
		if (gestore.isLogged() || !gestore.getPassword().equals(password)) {
			return false;
		} 
		else {
			gestore.setAutenticato(true);
			return true;
		}
	}
	
	@Override
	public boolean logout() {
		refreshGestoreCinema();
		if (gestore == null || !gestore.isLogged()) {
			return false;
		} else {
			gestore.setAutenticato(false);
			return true;
		}
	}
	
	/**
	 * Adds a new room to the rooms list of the cinema of the Gestore Cinema specified by the
	 * id passed as input.
	 * <p>
	 * Returns -1 if the GestoreCinema is not registered and logged, if the id passed as input is not a
	 * valid id or if there is already a room with the same name.
	 *
	 * @param cinemaId				The id of the cinema
	 * @param nomeSala				The name of the new room
	 * @param numeroFile			The number of seats row
	 * @param numeroPostiFila		The number of seats per row
	 * @param tempoAttrezzaggio		The setup time of the room
	 * @return The id of the room if the new room is added, -1 otherwise
	 */
	public int inserisciNuovaSala(int cinemaId, String nomeSala, int numeroFile, int numeroPostiFila, int tempoAttrezzaggio) {
		refreshGestoreCinema();
		if (gestore == null || !gestore.isLogged()) {
			return -1;
		}
		else {
			// ApplicazioneAmministratoreSistema.getListaCinemaIterator(gestore) returns null if
			// the specified GestoreCinema is not registered in the system, but it is impossible
			// because of the previous calling of refreshGestoreCinema().
			// (In this first implementation concurrency problems are not considered).
			ListaCinemaIterator allCinema = ApplicazioneAmministratoreSistema.getListaCinemaIterator(gestore.getUsername());
			Cinema cinema = null;
			while (allCinema.hasNext()) {
				Cinema temp = allCinema.next();
				if (temp.getId() == cinemaId) {
					cinema = temp;
					break;
				}	
			}
			if (cinema == null)
				return -1;
			
			return cinema.addSala(nomeSala, numeroFile, numeroPostiFila, tempoAttrezzaggio);
		}
	}
	
	/**
	 * Removes a room from the rooms list of the cinema of the Gestore Cinema specified by the
	 * id passed as input.
	 * <p>
	 * Returns false if the GestoreCinema is not registered and logged, if the id passed as input
	 * is not a valid id or if there is no room with the specified id.
	 * 
	 * @param cinemaId				The id of the cinema
	 * @param idSala				The id of the room
	 * @return true if the room is removed, false otherwise
	 */
	public boolean eliminaSala(int cinemaId, int idSala) {
		refreshGestoreCinema();
		if (gestore == null || !gestore.isLogged()) {
			return false;
		}
		else {
			// ApplicazioneAmministratoreSistema.getListaCinemaIterator(gestore) returns null if
			// the specified GestoreCinema is not registered in the system, but it is impossible
			// because of the previous calling of refreshGestoreCinema().
			// (In this first implementation concurrency problems are not considered).
			ListaCinemaIterator allCinema = ApplicazioneAmministratoreSistema.getListaCinemaIterator(gestore.getUsername());
			Cinema cinema = null;
			while (allCinema.hasNext()) {
				Cinema temp = allCinema.next();
				if (temp.getId() == cinemaId) {
					cinema = temp;
					break;
				}	
			}
			if (cinema == null)
				return false;
			
			return cinema.removeSala(idSala);
		}
	}
	
	/**
	 * Prints the info of each room in the cinema of the Gestore Cinema specified by the
	 * id passed as input.
	 * <p>
	 * Returns false if the GestoreCinema is not registered and logged, if the id passed as input
	 * is not a valid id or if the list of room is empty.
	 * 
	 * @param cinemaId		The id of the cinema
	 * @return true if the operation has been successful, false otherwise
	 */
	public boolean printRooms(int cinemaId) {
		refreshGestoreCinema();
		if (gestore == null || !gestore.isLogged()) {
			return false;
		} else {
			// ApplicazioneAmministratoreSistema.getListaCinemaIterator(gestore) returns null if
			// the specified GestoreCinema is not registered in the system, but it is impossible
			// because of the previous calling of refreshGestoreCinema().
			// (In this first implementation concurrency problems are not considered).
			ListaCinemaIterator allCinema = ApplicazioneAmministratoreSistema.getListaCinemaIterator(gestore.getUsername());
			Cinema cinema = null;
			while (allCinema.hasNext()) {
				Cinema temp = allCinema.next();
				if (temp.getId() == cinemaId) {
					cinema = temp;
					break;
				}	
			}
			if (cinema == null)
				return false;
			
			return cinema.printAllRooms();
		}
	}
	
	/**
	 * Prints all the shows scheduled in the room (specified by the id passed as input) of the
	 * cinema (specified by the id passed as input).
	 * <p>
	 * Returns false if the GestoreCinema is not registered and logged, if the id are not valid
	 * id or if there are no shows scheduled.
	 * @return true if the operation is successfully completed, false otherwise
	 */
	public boolean printAllShows(int cinemaId, int salaId) {
		refreshGestoreCinema();
		if (gestore == null || !gestore.isLogged()) {
			return false;
		} else {
			// ApplicazioneAmministratoreSistema.getListaCinemaIterator(gestore) returns null if
			// the specified GestoreCinema is not registered in the system, but it is impossible
			// because of the previous calling of refreshGestoreCinema().
			// (In this first implementation concurrency problems are not considered).
			ListaCinemaIterator allCinema = ApplicazioneAmministratoreSistema.getListaCinemaIterator(gestore.getUsername());
			Cinema cinema = null;
			while (allCinema.hasNext()) {
				Cinema temp = allCinema.next();
				if (temp.getId() == cinemaId) {
					cinema = temp;
					break;
				}	
			}
			if (cinema == null)
				return false;
			
			return cinema.printAllShows(salaId);
		}	
	}
	
	/**
	 * Prints the info of the GestoreCinema.
	 * <p>
	 * Returns false if the GestoreCinema is not registered and logged.
	 * 
	 * @return true if the operation has been successful, false otherwise
	 */
	public boolean printProfilo() {
		refreshGestoreCinema();
		if (gestore == null || !gestore.isLogged()) {
			return false;
		} else {
			gestore.printProfilo();
			// ApplicazioneAmministratoreSistema.getListaCinemaIterator(gestore) returns null if
			// the specified GestoreCinema is not registered in the system, but it is impossible
			// because of the previous calling of refreshGestoreCinema().
			// (In this first implementation concurrency problems are not considered).
			ListaCinemaIterator cinema = ApplicazioneAmministratoreSistema.getListaCinemaIterator(gestore.getUsername());
			if (!cinema.hasNext()) {
				System.out.println("Nessun cinema registrato");
				return true;
			}
			System.out.println("Cinema registrati:");
			while(cinema.hasNext()) {
				Cinema c = cinema.next();
				System.out.println("----------------------------------------------------------");
				System.out.println("Cinema:                  " + c.getNome());
				System.out.println("Indirizzo cinema:        " + c.getIndirizzo());
			}
			System.out.println("----------------------------------------------------------");
			System.out.println();
			return true;
		}
	}
	
	/**
	 * Verify the availability of the rooms of the cinema (specified by the id passed as input)
	 * for a show passed as input depending on the number of days you want to keep it in schedule.
	 * <p>
	 * Returns null if the id of the cinema passed as input is not a valid id or the GestoreCinema
	 * is not registered and logged; an empty list if no room is available.
	 * 
	 * @param cinemaId 		The id of the cinema
     * @param spettacolo 			The show for which you want to check the availability of the rooms
     * @param numberOfDays 	The number of days you want to keep the show in schedule
     * @return null in case of errors, the list of the available rooms if present,
     * an empty list otherwise.
	 */
	public ArrayList<Sala> verifyRoomsAvailability(int cinemaId, Spettacolo spettacolo,
			int numberOfDays) {
		refreshGestoreCinema();
		if (gestore == null || !gestore.isLogged()) {
			return null;
		}
		else {
			// ApplicazioneAmministratoreSistema.getListaCinemaIterator(gestore) returns null if
			// the specified GestoreCinema is not registered in the system, but it is impossible
			// because of the previous calling of refreshGestoreCinema().
			// (In this first implementation concurrency problems are not considered).
			ListaCinemaIterator allCinema = ApplicazioneAmministratoreSistema.getListaCinemaIterator(gestore.getUsername());
			Cinema cinema = null;
			while (allCinema.hasNext()) {
				Cinema temp = allCinema.next();
				if (temp.getId() == cinemaId) {
					cinema = temp;
					break;
				}	
			}
			if (cinema == null)
				return null;
			
			return cinema.verifyRoomsAvailability(spettacolo, numberOfDays);
		}
	}
	
	/**
	 * Adds a show to the schedule of the cinema (specified by the id passed as input)
	 * depending on the number of days you want to keep it in schedule.
	 * <p>
	 * Returns false if the id of the cinema passed as input is not a valid id, the GestoreCinema
	 * is not registered and logged, the idSala does not exist, the number of days is zero or
	 * if the sala specified by idSala is not available.
	 * 
	 * @param cinemaId The id of the cinema
     * @param spettacolo The show to add
     * @param numberOfDays The number of days you want to keep the show in schedule
     * @param idSala The id of the sala in which you want to add the shows
     * @return true if the operation is succesfully completed, false otherwise
	 */
	public boolean addShows(int cinemaId, Spettacolo spettacolo, int numberOfDays, int idSala) {
		refreshGestoreCinema();
		if (gestore == null || !gestore.isLogged()	|| numberOfDays == 0) {
			return false;
		} else {
			// ApplicazioneAmministratoreSistema.getListaCinemaIterator(gestore) returns null if
			// the specified GestoreCinema is not registered in the system, but it is impossible
			// because of the previous calling of refreshGestoreCinema().
			// (In this first implementation concurrency problems are not considered).
			ListaCinemaIterator allCinema = ApplicazioneAmministratoreSistema.getListaCinemaIterator(gestore.getUsername());
			Cinema cinema = null;
			while (allCinema.hasNext()) {
				Cinema temp = allCinema.next();
				if (temp.getId() == cinemaId) {
					cinema = temp;
					break;
				}	
			}
			if (cinema == null)
				return false;
			
			return cinema.addShows(spettacolo, numberOfDays, idSala);
		}
	}
	
	/**
	 * Returns true if the gestore logged in this app has a cinema with this id.
	 * <p>
	 * Returns false if the gestore is not registered and logged or if the id is not a valid id.
	 * @param cinemaId The id of the cinema
	 * @return true if the gestore registered and logged in this app has a cinema with this id,
	 * false otherwise
	 */
	public boolean hasCinema(int cinemaId) {
		refreshGestoreCinema();
		if (gestore == null) {
			return false;
		}
		else if(!gestore.isLogged()) 
			return false;
		else {
			// ApplicazioneAmministratoreSistema.getListaCinemaIterator(gestore) returns null if
			// the specified GestoreCinema is not registered in the system, but it is impossible
			// because of the previous calling of refreshGestoreCinema().
			// (In this first implementation concurrency problems are not considered).
			ListaCinemaIterator allCinema = ApplicazioneAmministratoreSistema.getListaCinemaIterator(gestore.getUsername());
			Cinema cinema = null;
			while (allCinema.hasNext()) {
				cinema = allCinema.next();
				if (cinema.getId() == cinemaId) {
					return true;
				}	
			}
			return false;
		}
	}
	
	/**
	 * Returns true if the cinema (specified by its id) of the gestore logged in this app has
	 * the room specified by its id passed as input.
	 * <p>
	 * Returns false if the gestore is not registered and logged or if the id are not valid id.
	 * @param cinemaId The id of the cinema
	 * @param roomId The id of the room
	 * @return true if the gestore registered and logged in this app has a cinema with cinemaId that
	 * contains a room with roomId, false otherwise
	 */
	public boolean hasRoom(int cinemaId, int roomId) {
		refreshGestoreCinema();
		if (gestore == null) {
			return false;
		}
		else if(!gestore.isLogged()) 
			return false;
		else {
			// ApplicazioneAmministratoreSistema.getListaCinemaIterator(gestore) returns null if
			// the specified GestoreCinema is not registered in the system, but it is impossible
			// because of the previous calling of refreshGestoreCinema().
			// (In this first implementation concurrency problems are not considered).
			ListaCinemaIterator allCinema = ApplicazioneAmministratoreSistema.getListaCinemaIterator(gestore.getUsername());
			Cinema cinema = null;
			while (allCinema.hasNext()) {
				Cinema temp = allCinema.next();
				if (temp.getId() == cinemaId) {
					cinema = temp;
					break;
				}	
			}
			
			if (cinema == null)
				return false;
			
			return cinema.hasSala(roomId);
		}
	}
	
	/**
	 * Prints the info of each cinema of the manager logged in this app.
	 * <p>
	 * Returns false if the user is not registered and logged or if the list of rooms is empty.
	 * 
	 * @return true if the operation i succesfully completed, false otherwise
	 */
	public boolean printAllCinema() {		
		refreshGestoreCinema();
		if (gestore == null || !gestore.isLogged()) {
			return false;
		}
		else {
			// ApplicazioneAmministratoreSistema.getListaCinemaIterator(gestore) returns null if
			// the specified GestoreCinema is not registered in the system, but it is impossible
			// because of the previous calling of refreshGestoreCinema().
			// (In this first implementation concurrency problems are not considered).
			ListaCinemaIterator allCinema = ApplicazioneAmministratoreSistema.getListaCinemaIterator(gestore.getUsername());
			if (!allCinema.hasNext()) {
				return false;
			}
			
			Cinema cinema;
			System.out.println("Cinema registrati:");
			while(allCinema.hasNext()) {
				cinema = allCinema.next();
				System.out.println("----------------------------------------------------------");
				System.out.println("Id:                      " + cinema.getId());
				System.out.println("Nome:                    " + cinema.getNome());
				System.out.println("Indirizzo:               " + cinema.getIndirizzo());
			}
			System.out.println();
			return true;
		}
	}
	
	/**
	 * Prints the info of each rooms in the list passed as input.
	 * 
	 * @param rooms The list of film to print
	 */
	public void printRoomsFromList(ArrayList<Sala> rooms) {		
		for (int i = 0; i < rooms.size(); i++) {
			System.out.println("----------------------------------------------------------");
			rooms.get(i).print();
		}	
	}
	
	/**
	 * Send a mail to the specified email address passed an input.
	 * <p>
	 * In this first implementation this function is not implemented.
	 * 
	 * @param email			The email address of the recipient
	 * @param arguments		An array of objects to be used to create the mail
	 */
	public void sendEmail(String email, Object... arguments) {
		// In this first implementation this function is not implemented
	}
}
