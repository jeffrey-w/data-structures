package main;

import java.util.NoSuchElementException;

/**
 * The {@code OrderedSet} interface specifies operations on a collection of unique elements that are sorted on a
 * prescribed order.
 *
 * @param <E> the type of element in this {@code OrderedSet}
 * @author Jeff Wilgus
 */
public interface OrderedSet<E> extends Set<E> {

	/**
	 * Removes the first element in this {@code OrderedSet}
	 *
	 * @return the removed element
	 * @throws IllegalStateException if this {@code OrderedSet} is empty
	 */
	E removeFirst();

	/**
	 * Removes the last element in this {@code OrderedSet}
	 *
	 * @return the removed element
	 * @throws IllegalStateException if this {@code OrderedSet} is empty
	 */
	E removeLast();

	/**
	 * Removes the {@code element} in this {@code OrderedSet} immediately preceding that specified.
	 *
	 * @param element the specified element
	 * @return the removed element
	 * @throws IllegalStateException if this {@code OrderedSet} is empty
	 * @throws NoSuchElementException if there the specified {@code element} does not belong to this {@code OrderedSet}
	 */
	E removePrevious(E element);

	/**
	 * Removes the {@code element} in this {@code OrderedSet} immediately after that specified.
	 *
	 * @param element the specified element
	 * @return the removed element
	 * @throws IllegalStateException if this {@code OrderedSet} is empty
	 * @throws NoSuchElementException if there the specified {@code element} does not belong to this {@code OrderedSet}
	 */
	E removeNext(E element);

	/**
	 * Retrieves the first element in this {@code OrderedSet}.
	 *
	 * @return the first element in this {@code OrderedSet}
	 */
	E getFirst();

	/**
	 * Retrieves the last element in this {@code OrderedSet}.
	 *
	 * @return the last element in this {@code OrderedSet}
	 */
	E getLast();

	/**
	 * Retrieves the {@code element} in this {@code OrderedSet} immediately preceding that specified.
	 *
	 * @param element the specified element
	 * @return the {@code element} before that specified one
	 * @throws IllegalStateException if this {@code OrderedSet} is empty
	 * @throws NoSuchElementException if there the specified {@code element} does not belong to this {@code OrderedSet}
	 */
	E getPrevious(E element);

	/**
	 * Retrieves the {@code element} in this {@code OrderedSet} immediately after that specified.
	 *
	 * @param element the specified element
	 * @return the {@code element} after that specified one
	 * @throws IllegalStateException if this {@code OrderedSet} is empty
	 * @throws NoSuchElementException if there the specified {@code element} does not belong to this {@code OrderedSet}
	 */
	E getNext(E element);

}
