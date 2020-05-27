package main;

/**
 * The {@code Stack} interface specifies operations on a linear sequence of elements for which a LIFO order is induced.
 *
 * @param <E> the type of element in this {@code Stack}
 * @author Jeff Wilgus
 */
public interface Stack<E> extends Collection<E> {

    /**
     * Adds the specified {@code element} to the top of this {@code Stack}.
     *
     * @param element the specified element
     */
    void push(E element);

    /**
     * Removes the element at the top of this {@code Stack}.
     *
     * @return the element at the top of this {@code Stack}
     * @throws IllegalStateException if this {@code Stack} is empty
     */
    E pop();

    /**
     * Provides the element at the top of this {@code Stack}.
     *
     * @return the element at the top of this {@code Stack}
     * @throws IllegalStateException if this {@code Stack} is empty
     */
    E peek();

}
