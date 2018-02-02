package sistema;

/**
 * Aggregate.java
 * <p>
 * An interface for an aggregate.
 * 
 * @author Sara Martino
 *
 * @param <E> The type of elements contained in this aggregate
 */
public interface Aggregate<E> {
	/**
	 * Returns an iterator.
	 * @return the iterator.
	 */
	public MyIterator<E> getIterator();
}
