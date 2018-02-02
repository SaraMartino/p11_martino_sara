package sistema;

import java.util.*;

/**
 * Sala.java
 * <p>
 * A representation of a cinema room. It is characterized by the following attributes: id, name,
 * number of rows, number of seats per row, setup time (in minutes), a reference to the cinema, scheduling.
 * <p>
 * The attribute programmazione (scheduling) is used to keep trace of the show scheduling. The keys of this
 * map are string representing the year, month and day of the shows (yyyy/mm/dd);
 * the values are sorted lists that contain all the shows for the specified year,
 * month and day.
 * <p>
 * The class also provide a print method.
 *
 * @author Sara Martino
 */
public class Sala {
	private Integer id;
	private String nome;
	private int numeroFile;
	private int numeroPostiFila;
	private int tempoAttrezzaggio;
	private Cinema cinema;
	private HashMap<String, LinkedList<Spettacolo>> programmazione;
	
	/**
     * Constructs an object of type Sala using the input parameters.
     * <p>
     * The id is set to -1. It is necessary to set it using the setId(int id) function after the creation.
     * 
     * @param  nome					The name of the Sala
     * @param  numeroFile			Number of rows
     * @param  numeroPostiFila		Number of seats per row
     * @param  tempoAttrezzaggio	Setup time (in minutes)
     * @param  cinema				The cinema to which it belongs
     */
	public Sala(String nome, int numeroFile, int numeroPostiFila, int tempoAttrezzaggio, Cinema cinema) {
		this.id = -1;
		this.nome = nome;
		this.numeroFile = numeroFile;
		this.numeroPostiFila = numeroPostiFila;
		this.tempoAttrezzaggio = tempoAttrezzaggio;
		this.cinema = cinema;
		this.programmazione = new HashMap<String, LinkedList<Spettacolo>>();
	}
	
	/**
     * Returns the id of the Sala.
     * 
     * @return The id of the Sala
     */
	public int getId() {
		return id;
	}
	
	/**
     * Sets the id of the Sala.
     * 
     * @param id The id of the Sala
     */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
     * Returns the setup time (in minutes) of the Sala.
     * 
     * @return The setup time of the Sala
     */
	public int getTempoAttrezzaggio() {
		return tempoAttrezzaggio;
	}
	
	/**
     * Returns the name of the Sala.
     * 
     * @return The name of the Sala
     */
	public String getNome() {
		return nome;
	}
	
	/**
     * Returns the capacity of the Sala.
     * 
     * @return The capacity of the Sala
     */
	public int getCapacity() {
		return (numeroFile * numeroPostiFila);
	}
	
	/**
     * Returns the id of the cinema.
     * 
     * @return The id of the cinema
     */
	public int getCinemaId() {
		return cinema.getId();
	}
	
	/**
	 * Verify the room availability for the show passed as input.
	 * <p>
	 * Returns false if the room is not available (if the addition of the show passed as input would
	 * cause a scheduling overlapping).
	 * 
     * @param  s The show for which you want to check the availability of the rooms
     * @return true if the room is available for the date and duration of the show passed as input,
     * false otherwise
	 */
	private boolean verifyAvailabilitySingleShow(Spettacolo s) {
		if (!programmazione.containsKey(s.getKey())) {
			return true;
		} else {
			String key = s.getKey();
			LinkedList<Spettacolo> shows = programmazione.get(key);
			int index = Collections.binarySearch(shows, s);
			// There is already a show at the same hour and minutes
			if (index >= 0) {
				return false;
			} else {
				/*
				 * Collections.binarySearch(..) returns (-(insertion point) - 1) if the element is not in the
				 * list. The insertion point is defined as the point at which the key would be inserted into
				 * the list: the index of the first element greater than the key, or list.size() if all
				 * elements in the list are less than the specified key.
				 */
				index = (index + 1) * (-1);
			}
			
			Spettacolo previous = shows.get(index - 1);			
			Calendar previousEnd = Calendar.getInstance();
			previousEnd.setTimeInMillis(previous.getDataInizio().getTimeInMillis());
			previousEnd.add(Calendar.MINUTE, previous.getDurata());
			// The previous show is the last of the day
			if (index == shows.size()) {
				if (s.getDataInizio().before(previousEnd)) {
					return false;
				} else {
					return true;
				}
			} else {
				// There are a previous and a next shows
				Calendar currentEnd = Calendar.getInstance();
				currentEnd.setTimeInMillis(s.getDataInizio().getTimeInMillis());
				currentEnd.add(Calendar.MINUTE, s.getDurata() + this.tempoAttrezzaggio);
				Spettacolo next = shows.get(index);
				if (s.getDataInizio().before(previousEnd) || currentEnd.after(next.getDataInizio())) {
					return false;
				} else {
					return true;
				}
			}
		}
	}
	
	/**
	 * Verify the room availability for the entire schedule of the show passed as input (depending on
	 * numberOfDays that represents the number of days you want to keep the show in schedule).
	 * <p>
	 * Returns false if the room is not available (if the addition of the show passed as input would
	 * cause a scheduling overlapping).
	 * 
     * @param s The show for which you want to check the availability of the rooms
     * @param numberOfDays The number of days you want to keep the show in schedule
     * @return true if the room is available for the date and duration of the show passed as input,
     * false otherwise
	 */
	public boolean verifyAvailability(Spettacolo s, int numberOfDays) {
		boolean available = true;
		for (int i = 0; i < numberOfDays; i++) {
			Spettacolo show = s.getNextSpettacolo(i);
			if (!verifyAvailabilitySingleShow(show)) {
				available = false;
				break;
			}
		}
		if (available) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Adds a show schedule (defined by the show and the number of days you want to keep it in schedule)
	 * to the corresponding list of shows of the map programmazione, keeping the list sorted by key.
	 * <p>
	 * Returns false if the room is not available (if the addition of the show passed as input would
	 * cause a scheduling overlapping) or if the id of this sala does not correspond to the id of the
	 * sala contained in s.
	 * 
	 * @param s The show to add
     * @param numberOfDays The number of days you want to keep the show in schedule
     * @return true if the operation is successfully completed, false otherwise
	 */
	public boolean addShow(Spettacolo s, int numberOfDays) {
		if (this.id != s.getSalaId()) {
			return false;
		} else if (verifyAvailability(s, numberOfDays)) {			
			// The room is available
			for (int i = 0; i < numberOfDays; i++) {
				Spettacolo nextShow = s.getNextSpettacolo(i);
				String key = nextShow.getKey();
				if (!programmazione.containsKey(key)) {
					programmazione.put(key, new LinkedList<Spettacolo>());
					programmazione.get(key).add(nextShow);
				} else {
					programmazione.get(key).add(nextShow);
					Collections.sort(programmazione.get(key));
				}
			}
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Prints the shows scheduled in this sala.
	 * <p>
	 * Returns false if there are no shows.
	 * 
     * @return true if the operation is successfully completed, false otherwise
	 */
	public boolean printAllShows() {
		if (programmazione.isEmpty()) {
			return false;
		}
		else {
			Iterator<LinkedList<Spettacolo>> allShows = programmazione.values().iterator();
			Iterator<Spettacolo> i;
			
			System.out.println("Programmazione (" + nome + "):");
			while (allShows.hasNext()) {
				i = allShows.next().iterator();
				while (i.hasNext()) {
					System.out.println("----------------------------------------------------------");
					i.next().print();
				}
			}
			System.out.println("----------------------------------------------------------");
			return true;
		}	
	}

	/**
     * Prints base info of the Sala, i.e. id, name, capacity (number of rows, number of seats per row and 
     * total seats) and the setup time (in minutes).
     */
	public void print() {
		System.out.println("Id:                      " + this.id);
		System.out.println("Nome:                    " + this.nome);
		System.out.println("Capacità:                " + this.numeroFile + " file, "
				+ this.numeroPostiFila + " posti per fila");
		System.out.println("                         (" + this.numeroFile * this.numeroPostiFila
				+ " posti totali)");
		System.out.println("Tempo di attrezzaggio:   " + this.tempoAttrezzaggio + " minuti");
	}
}
