package sistema;

import java.util.*;

/**
 * Spettacolo.java
 * <p>
 * A representation of a scheduled cinema show.
 * It is characterized by the following attributes: idEvento, a reference to the cinema room, a reference to
 * the film played, inizio, duration (duration of the film + setup time of the sala), total seats
 * (capacity of the cinema room), basic price.
 * <p>
 * The attribute idEvento has to be the same for each show of a single schedule.
 * For example if a manager creates a scheduling for a film f that lasts 5 days,
 * there will be 5 objects of type Spettacolo with the same id, one for each days.
 * of the scheduling.
 * <p>
 * The attribute data has to store information about day, month, year, hour and minutes.
 * <p>
 * The class also provide a print method.
 *
 * @author Sara Martino
 */
public class Spettacolo implements Comparable<Spettacolo> {

	private int idEvento;
	private Sala sala;
	private Film film;
	private Calendar dataInizio;
	private int durata;
	private int postiTotali;
	private int postiRimanenti;
	private float prezzoBase;
	
	/**
     * Constructs an object of type Spettacolo using the input parameters.
     * <p>
     * The duration of the show is automatically set to the duration of the film
     * 
     * @param  film					The film played
     * @param  data					The date of the show (day, month, year, hour, minutes)
     * @param  prezzoBase			The basic price		
     */
	public Spettacolo(Film film, Calendar data, float prezzoBase) {
		this.idEvento = -1;
		this.sala = null;
		this.film = film;
		this.dataInizio = data;
		this.durata = film.getDurata();
		this.prezzoBase = prezzoBase;
	}
	
	/**
	 * Sets idEvento.
	 * 
	 * @param id The id of the scheduling of the show
	 */
	public void setIdEvento(int id) {
		this.idEvento = id;
	}
	
	/**
	 * Gets idEvento.
	 * 
	 * @return id The id of the scheduling of the show
	 */
	public int getIdEvento() {
		return this.idEvento;
	}
	
	/**
	 * Sets sala.
	 * <p>
	 * The duration of the show is increased by the setup time of the sala.
     * <p>
     * The number of seats and the remaining seats are automatically set to the capacity of the sala.
	 * 
	 * @param s The room in which the show is scheduled
	 */
	public void setSala(Sala s) {
		this.sala = s;
		this.durata += sala.getTempoAttrezzaggio();
		this.postiTotali = sala.getCapacity();
		this.postiRimanenti = postiTotali;
	}
	
	/**
	 * Returns dataInizio.
	 * 
	 * @return the date of the beginning of the show
	 */
	public Calendar getDataInizio() {
		return dataInizio;
	}
	
	/**
	 * Returns the id of the film.
	 * 
	 * @return the id of the film
	 */
	public String getFilmId() {
		return film.getId();
	}
	
	/**
	 * Returns durata.
	 * 
	 * @return the duration of the show
	 */
	public int getDurata() {
		return durata;
	}
	
	/**
	 * Returns durata.
	 * 
	 * @return the duration of the show
	 */
	public float getPrezzoBase() {
		return prezzoBase;
	}
	
	/**
	 * Returns an object of type Spettacolo with the same attributes of this Spettacolo except for
	 * dataInizio, that is modified depending on the input
	 * <p>
	 * If the amount is 0, it returns a clone of this Spettacolo
	 * 
	 * @param amount The number of days to add to dataInizio
	 * @return the Spettacolo delayed depending on the input
	 */
	public Spettacolo getNextSpettacolo(int amount) {
		Calendar next = (Calendar) this.dataInizio.clone();
		next.add(Calendar.DAY_OF_MONTH, amount);
		Spettacolo s = new Spettacolo(film, next, prezzoBase);
		s.idEvento = idEvento;
		s.durata = durata;
		s.sala = sala;
		s.postiTotali = postiTotali;
		s.postiRimanenti = postiRimanenti;
		return s;
	}
	
	/**
	 * Returns the key for storing this show.
	 * 
	 * @return the key for storing this show
	 */
	public String getKey() {
		String key = dataInizio.get(Calendar.YEAR) + "/" + dataInizio.get(Calendar.MONTH) + "/"
				+ dataInizio.get(Calendar.DAY_OF_MONTH);
		return key;
	}
	
	/**
	 * Returns the id of the sala of this show.
	 * <p>
	 * Returns -1 is there is no sala associated with this show.
	 * 
	 * @return the id of the sala of this show
	 */
	public int getSalaId() {
		if (this.sala == null) {
			return -1;
		} else {
			return sala.getId();
		}
	}
	
	/**
	 * Returns the id of the cinema of this show.
	 * <p>
	 * Returns -1 is there is no cinema associated with this show.
	 * 
	 * @return the id of the cinema of this show
	 */
	public int getCinemaId() {
		if (this.sala == null) {
			return -1;
		} else {
			return sala.getCinemaId();
		}
	}

	@Override
	public int compareTo(Spettacolo s) {
		return this.dataInizio.compareTo(s.dataInizio);
	}
	
	/** 
	 * Indicates whether some other object is "equal to" this one.
	 * <p>
	 * Returns false if the other object is null or if it is not an instance of the class Spettacolo.
	 * A show is equal to another show whether they have the same idEvento and have been
	 * scheduled in the same sala of the same cinema.
	 * 
	 * @param obj the reference object with which to compare
     * @return true if this object is the same as the obj argument; false otherwise
	 */
	@Override
	public boolean equals(Object obj){
	    if (obj == null) return false;
	    if (obj == this) return true;
	    if (!(obj instanceof Spettacolo))return false;
	    Spettacolo o = (Spettacolo)obj;
	    
	    if (this.idEvento == o.getIdEvento() && this.getSalaId() == o.getSalaId()
	    		&& this.getCinemaId() == o.getCinemaId()) return true;
	    else return false;
	}

	/**
     * Prints base info of the Spettacolo, i.e. id, name of the sala, id of the film, date, duration,
     * total seats, remaining seats, basic price.
     */
	public void print() {
		System.out.println("Id evento:               " + this.idEvento);
		System.out.println("Nome sala:               " + this.sala.getNome());
		System.out.println("Id film:                 " + this.film.getId());
		System.out.println("Data:                    " + this.dataInizio.get(Calendar.DAY_OF_MONTH) + "/"
				+ this.dataInizio.get(Calendar.MONTH) + "/" + this.dataInizio.get(Calendar.YEAR) + " "
				+ this.dataInizio.get(Calendar.HOUR_OF_DAY) + ":" + this.dataInizio.get(Calendar.MINUTE));
		System.out.println("Durata:                  " + this.durata);
		System.out.println("Posti totali:            " + this.postiTotali);
		System.out.println("Posti rimanenti:         " + this.postiRimanenti);
		System.out.println("Prezzo base biglietto:   " + this.prezzoBase);
	}

}
