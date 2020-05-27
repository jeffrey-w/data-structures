package main;

import java.util.Comparator;

/**
 * The {@code Sortable} interface specifies those {@code Collection}s whose structures admit ordering of their
 * elements.
 *
 * @param <E> the type of element being sorted
 * @author Jeff Wilgus
 */
public interface Sortable<E> {

    /**
     * Sorts the elements in this {@code Collection} on the order induced by the specified {@code Comparator}, or, if
     * that is {@code null}, their natural ordering.
     *
     * @param comp the specified {@code Comparator}
     * @throws ClassCastException if the elements in this {@code Collection} are not mutually comparable
     */
    void sort(final Comparator<E> comp);

}
