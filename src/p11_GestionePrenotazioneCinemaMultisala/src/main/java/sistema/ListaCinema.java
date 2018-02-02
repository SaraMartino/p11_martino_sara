package sistema;

import java.util.*;

/**
 * ListaCinema.java
 * <p>
 * A representation of a list of cinema implementing Aggregate<Cinema>. A ListaCinema is
 * is characterized by a static LinkedList of Cinema.
 *
 * @author Sara Martino
 */
public class ListaCinema implements Aggregate<Cinema> {

	private LinkedList<Cinema> listaCinema;
	
	/**
	 * Constructs an object of type ListaCinema.
	 */
	public ListaCinema() {
		listaCinema = new LinkedList<Cinema>();
	}
	
	/**
	 * Adds a new cinema to the list and sets the id of this cinema. The id is the index of the
	 * cinema in the list.
	 * <p>
	 * Returns false if this cinema already exists.
	 * 
	 * @param cinema		The new cinema
	 * @return true if the new cinema is added, false otherwise
	 */
	public boolean addNewCinema(Cinema cinema) {
		if (!listaCinema.contains(cinema)) {
			int nextIndex = listaCinema.size();
			cinema.setId(nextIndex);
			return listaCinema.add(cinema);
		}
		else return false;
	}
	
	/**
	 * Removes the cinema specified by the id passed as input from the list.
	 * <p>
	 * Returns null if this cinema does not exist in the list.
	 * 
	 * @param cinemaId		The id of the cinema to remove
	 * @return true if the cinema is removed, false otherwise
	 */
	public boolean removeCinema(int cinemaId) {
		if (cinemaId < 0 || cinemaId >= listaCinema.size())
			return false;
		
		listaCinema.remove(cinemaId);
		return true;
	}

	/**
	 * Returns a ListaCinemaIterator over the list of Cinema.
	 * @return the ListaCinemaIterator.
	 */
	@Override
	public ListaCinemaIterator getIterator() {
		return new ListaCinemaIterator(listaCinema);
	}
}
