package main;

/**
 * The {@code Queue} interface specifies operations on a {@code Collection} of elements for which a prescribed order
 * (typically FIFO) is imposed.
 *
 * @param <E> the type of element in this {@code Queue}
 * @author Jeff Wilgus
 */
public interface Queue<E> extends Collection<E> {

	/**
	 * Adds the specified {@code element} to the end of this {@code Queue}.
	 *
	 * @param element the specified element
	 */
	void enqueue(E element);

	/**
	 * Removes the element at the front of this {@code Queue}.
	 *
	 * @return the element at the front of this {@code Queue}
	 * @throws IllegalStateException if this {@code Queue} is empty
	 */
	E dequeue();

	/**
	 * Provides the element at the front of this {@code Queue}.
	 *
	 * @return the element at the front of this {@code Queue}
	 * @throws IllegalStateException if this {@code Queue} is empty
	 */
	E first();

}
