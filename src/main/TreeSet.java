package main;

import java.util.Comparator;

/**
 * The {@code TreeSet} class is a red-black tree implementation of the {@code Set} interface. Elements are sorted on the
 * order induced by a supplied {@code Comparator}, or their natural ordering if none is supplied. This class offers
 * logarithmic time performance for {@code add}, {@code get}, and {@code remove} operations. Elements may be {@code
 * null}.
 *
 * @param <E> the type of element in this {@code TreeSet}
 * @author Jeff Wilgus
 */
public class TreeSet<E> extends AbstractSet<E> implements OrderedSet<E> {

    /**
     * Constructs a new {@code TreeSet} object.
     */
    public TreeSet() {
        map = new TreeMap<>();
    }

    /**
     * Constructs a new {@code TreeSet} object sorted on the order induced by the specified {@code Comparator}.
     *
     * @param comp the specified {@code Comparator}
     */
    public TreeSet(Comparator<E> comp) {
        map = new TreeMap<>(comp);
    }

    @Override
    public E removeFirst() {
        return map().removeFirst().getKey();
    }

    @Override
    public E removeLast() {
        return map().removeLast().getKey();
    }

    @Override
    public E removePrevious(final E element) {
        return map().removePrevious(element).getKey();
    }

    @Override
    public E removeNext(final E element) {
        return map().removeNext(element).getKey();
    }

    @Override
    public E getFirst() {
        return map().getFirst().getKey();
    }

    @Override
    public E getLast() {
        return map().getLast().getKey();
    }

    @Override
    public E getPrevious(final E element) {
        return map().getPrevious(element).getKey();
    }

    @Override
    public E getNext(final E element) {
        return map().getNext(element).getKey();
    }

    private TreeMap<E, Void> map() {
        return (TreeMap<E, Void>)map;
    }

    private static final long serialVersionUID = 7605404853438032617L;

}
