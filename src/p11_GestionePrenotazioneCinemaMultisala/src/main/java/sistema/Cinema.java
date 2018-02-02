package sistema;

import java.util.*;

/**
 * Cinema.java
 * <p>
 * A representation of a cinema. A cinema is characterized by the following attributes: name, address,
 * a manager and a list of rooms. It also keeps track of the id of the next room and of the id
 * of the next show schedule.
 * <br>
 * A cinema is uniquely identified by its name and address.
 *
 * @author Sara Martino
 */
public class Cinema {
	
	private Integer id;
	private Integer nextSalaId;
	private Integer nextEventoId;
	private String nome;
	private String indirizzo;
	private HashMap<Integer, Sala> listaSale;
	
	/**
     * Constructs an object of type Cinema using the input parameters.
     * 
     * @param  nome					The name of the Cinema
     * @param  indirizzo			The address
     */
	public Cinema(String nome, String indirizzo) {
		this.id = -1;
		nextSalaId = 0;
		nextEventoId = 0;
		this.nome = nome;
		this.indirizzo = indirizzo;
		this.listaSale = new HashMap<Integer, Sala>();
	}
	
	/**
     * Returns the id of the cinema.
     * 
     * @return The id of the cinema
     */
	public int getId() {
		return id;
	}
	
	/**
     * Sets the id of the cinema.
     * 
     * @param id The id of the cinema
     */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Returns the name of the cinema.
	 * 
	 * @return the name of the Cinema
	 */
	public String getNome() {
		return nome;
	}
	
	/**
	 * Returns the address of the cinema.
	 * 
	 * @return the address of the Cinema
	 */
	public String getIndirizzo() {
		return indirizzo;
	}
	
	/**
	 * Adds a room to the rooms list.
	 * <p>
	 * Returns -1 if there is already a room with the same name, the id of the new Sala otherwise
	 * 
	 * @param nomeSala				The name of the new room
	 * @param numeroFile			The number of seats row
	 * @param numeroPostiFila		The number of seats per row
	 * @param tempoAttrezzaggio		The setup time of the room
	 * @return the id of the new Sala if the new room is added, -1 otherwise
	 */
	public int addSala(String nomeSala, int numeroFile, int numeroPostiFila, int tempoAttrezzaggio) {
		Iterator<Sala> allRooms = listaSale.values().iterator();
		while (allRooms.hasNext()) {
			if (allRooms.next().getNome().equals(nomeSala)) {
				return -1;
			}
		}
		
		Sala sala = new Sala(nomeSala, numeroFile, numeroPostiFila, tempoAttrezzaggio, this);
		sala.setId(nextSalaId);
		listaSale.put(sala.getId(), sala);
		return nextSalaId++;
	}
	
	/**
	 * Returns true if the cinema has a room with this id.
	 * <p>
	 * Returns false if the id is not a valid id.
	 * 
	 * @param roomId The id of the room
	 * @return true if the cinema has a room with this id, false otherwise
	 */
	public boolean hasSala(int roomId) {
		if (!listaSale.containsKey(roomId)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Removes a room from the rooms list.
	 * <p>
	 * Returns false if there is no room with the specified id.
	 * 
	 * @param id the id of the room to remove
	 * @return true if the room is removed, false otherwise
	 */
	public boolean removeSala(int id) {
		if (!listaSale.containsKey(id)) {
			return false;
		} else {
			listaSale.remove(id);
			return true;
		}
	}
	
	/**
	 * Prints the info of each room in the rooms list.
	 * <p>
	 * Returns false if the list of rooms is empty.
	 * 
	 * @return true if there is at least one room in the list, false otherwise
	 */
	public boolean printAllRooms() {
		Iterator<Sala> rooms = listaSale.values().iterator();
		
		if (!rooms.hasNext()) {
			return false;
		}
		
		System.out.println("Sale registrate (" + nome + "):");
		while (rooms.hasNext()) {
			System.out.println("----------------------------------------------------------");
			rooms.next().print();
		}
		System.out.println();
		return true;
	}
	
	/**
	 * Prints all the shows scheduled in the room specified by the id passed as input.
	 * <p>
	 * Returns false if the id is not a valid id or if there are no shows scheduled.
	 * @return true if the operation is successfully completed, false otherwise
	 */
	public boolean printAllShows(int salaId) {
		Iterator<Sala> rooms = listaSale.values().iterator();
		Sala room;
		while (rooms.hasNext()) {
			room = rooms.next();
			if (salaId == room.getId())
				return room.printAllShows();
		}
		return false;
	}
	
	/**
	 * Verify the availability of the rooms of this cinema for the entire schedule of the show passed
	 * as input (depending on numberOfDays that represents the number of days you want to keep the
	 * show in schedule).
	 * <p>
	 * Returns an empty list if no room is available.
	 * 
     * @param s The show for which you want to check the availability of the rooms
     * @param numberOfDays The number of days you want to keep the show in schedule
     * @return The list of the available rooms if present, an empty list otherwise.
	 */
	public ArrayList<Sala> verifyRoomsAvailability(Spettacolo s, int numberOfDays) {
		ArrayList<Sala> availableRooms = new ArrayList<Sala>();
		Iterator<Sala> rooms = listaSale.values().iterator();
		while(rooms.hasNext()) {
			Sala sala = rooms.next();
			if (sala.verifyAvailability(s, numberOfDays)) {
				availableRooms.add(sala);
			}
		}
		return availableRooms;
	}
	
	/**
	 * Adds a a show schedule (defined by the show and the number of days you want to keep it in schedule)
	 * to the map programmazione of the sala (defined by its id) of the cinema.
	 * <p>
	 * Returns false if the id of the sala does not exist, the number of days is zero or
	 * if the room is not available.
	 * 
	 * @param s The show to add
     * @param numberOfDays The number of days you want to keep the show in schedule
     * @param idSala The id of the sala in which you want to add the shows
     * @return true if the operation is succesfully completed, false otherwise
	 */
	public boolean addShows(Spettacolo s, int numberOfDays, int idSala) {
		if (!listaSale.containsKey(idSala) || numberOfDays == 0) {
			return false;
		}
		
		Sala sala = listaSale.get(idSala);
		s.setIdEvento(nextEventoId);
		s.setSala(sala);
		if (sala.addShow(s, numberOfDays)) {
			nextEventoId++;
			return true;
		} else {
			return false;
		}
		
	}
		
	/** 
	 * Indicates whether some other object is "equal to" this one.
	 * <p>
	 * Returns false if the other object is null or if it is not an instance of the class Cinema.
	 * A cinema is equal to another cinema whether they have the same name and address ignoring case.
	 * 
	 * @param obj the reference object with which to compare
     * @return true if this object is the same as the obj argument; false otherwise
	 */
	@Override
	public boolean equals(Object obj){
	    if (obj == null) return false;
	    if (obj == this) return true;
	    if (!(obj instanceof Cinema))return false;
	    Cinema o = (Cinema)obj;
	    
	    if (this.nome.equalsIgnoreCase(o.nome) && this.indirizzo.equalsIgnoreCase(o.indirizzo)) return true;
	    else return false;
	}
}
