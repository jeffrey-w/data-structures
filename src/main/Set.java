package main;

import java.util.NoSuchElementException;

/**
 * The {@code Set} interface specifies operations on a collection of unique elements.
 *
 * @param <E> the type of element in this {@code Set}
 * @author Jeff Wilgus
 */
public interface Set<E> extends Collection<E> {

	/**
	 * Adds the specified {@code} element to this {@code Set} if it is not already a member.
	 *
	 * @param element the specified element
	 */
	void add(E element);

	/**
	 * Removes the specified {@code element} from this {@code Set}.
	 *
	 * @param element the specifies element
	 * @throws IllegalStateException if this {@code Set} is empty
	 * @throws NoSuchElementException if the specified {@code element} does not belong to this {@code Set}
	 */
	void remove(E element);

}
