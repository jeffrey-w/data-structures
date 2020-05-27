package main;

/**
 * The {@code Deque} interface specifies operations on a linear sequence of elements for which elements may be added and
 * removed from either end.
 *
 * @param <E> the type of element in this {@code Deque}
 * @author Jeff Wilgus
 */
public interface Deque<E> extends Collection<E> {

    /**
     * Adds the specified {@code element} to the front of this {@code Deque}.
     *
     * @param element the specified element
     */
    void addFirst(E element);

    /**
     * Adds the specified {@code element} to the end of this {@code Deque}.
     *
     * @param element the specified element
     */
    void addLast(E element);

    /**
     * Removes the element at the front of this {@code Deque}.
     *
     * @return the element at the front of this {@code Deque}
     * @throws IllegalStateException if this {@code Deque} is empty
     */
    E removeFirst();

    /**
     * Removes the element at the end of this {@code Deque}.
     *
     * @return the element at the end of this {@code Deque}
     * @throws IllegalStateException if this {@code Deque} is empty
     */
    E removeLast();

    /**
     * Provides the element at the front of this {@code Deque}.
     *
     * @return the element at the front of this {@code Deque}
     * @throws IllegalStateException if this {@code Deque} is empty
     */
    E getFirst();

    /**
     * Provides the element at the end of this {@code Deque}.
     *
     * @return the element at the end of this {@code Deque}
     * @throws IllegalStateException if this {@code Deque} is empty
     */
    E getLast();

}
