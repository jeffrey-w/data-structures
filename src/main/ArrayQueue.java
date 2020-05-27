package main;

/**
 * The {@code ArrayQueue} class is an {@code ArrayList}-backed implementation of the {@code Queue} interface.
 *
 * @param <E> they type of element in this {@code ArrayQueue}
 * @author Jeff Wilgus
 */
public class ArrayQueue<E> extends AbstractQueue<E> {

    /**
     * Constructs a new {@code ArrayQueue} object.
     */
    public ArrayQueue() {
        data = new ArrayList<>();
    }

    private static final long serialVersionUID = -6973538396761781940L;

}
