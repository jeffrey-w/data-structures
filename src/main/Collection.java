package main;

/**
 * The {@code Collection} interface specified operations on an aggregation of like elements.
 *
 * @param <E> the type of element in this {@code Collection}
 * @author Jeff Wilgus
 */
public interface Collection<E> extends Iterable<E> {

    /**
     * Removes all elements from this {@code Collection}.
     */
    void clear();

    /**
     * Determines whether or not the specified {@code element} is in this {@code Collection}.
     *
     * @param element the specified element
     * @return {@code true} if the specified {@code element} is in this {@code Collection}
     */
    boolean contains(final E element);

    /**
     * Provides the number of the elements in this {@code Collection}.
     *
     * @return the size of this {@code Collection}
     */
    int size();

    /**
     * Determines whether or not there are any elements in this {@code Collection}.
     *
     * @return {@code true} if this {@code Collection} has no elements in it.
     */
    boolean isEmpty();

}
