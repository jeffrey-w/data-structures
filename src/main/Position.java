package main;

/**
 * The {@code Position} interface abstracts indexicality among a sequence of elements.
 *
 * @param <E> the type of element at this {@code Position}
 * @author Jeff Wilgus
 */
public interface Position<E> {

    /**
     * Retrieves the element at this {@code Position}.
     *
     * @return the element at this {@code Position}
     */
    E getElement();

}
