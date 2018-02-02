package sistema;

/**
 * MyIterator.java
 * <p>
 * An interface for an iterator.
 *
 * @version 2.0, 24 June 2017
 * @author Sara Martino
 * 
 * @param <E> The type of elements returned by this iterator
 */
public interface MyIterator<E> {
	/**
	 * Returns true if the iteration has more elements.
	 * (In other words, returns true if next() would return an element rather
	 * than throwing an exception.)
	 * 
	 *  @return true if the iteration has more elements, false otherwise
	 */
	public boolean hasNext();
	/**
	 * Returns the next element in the iteration if present.
	 * 
	 * @return the next element in the iteration, null if it is not present
	 */
	public E next();
}
