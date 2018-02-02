package sistema;

import java.util.LinkedList;

/**
 * ListaCinemaIterator.java
 * <p>
 * An iterator over a list of Cinema. Implements MyIterator<Cinema>.
 *
 * @author Sara Martino
 */
public class ListaCinemaIterator implements MyIterator<Cinema> {

	private LinkedList<Cinema> listaCinema;
	private int currentPosition;
	
	/**
	 * Constructs an object of type ListaCinemaIterator using the input parameter.
	 * 
	 *  @param listaCinema		the list of Cinema
	 */
	public ListaCinemaIterator(LinkedList<Cinema> listaCinema) {
		this.currentPosition = -1;
		this.listaCinema = listaCinema;
	}

	@Override
	public boolean hasNext() {
		if(currentPosition + 1 < listaCinema.size()) return true;
		else return false;
	}

	@Override
	public Cinema next() {
		if (hasNext()) {
			currentPosition++;
			return listaCinema.get(currentPosition);
		}
		else return null;
	}
}
