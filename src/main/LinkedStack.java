package main;

/**
 * The {@code LinkedStack} class is an {@code LinkedList}-backed implementation of the {@code Stack} interface.
 *
 * @param <E> they type of element in this {@code LinkedStack}
 * @author Jeff Wilgus
 */
public class LinkedStack<E> extends AbstractStack<E> {

    /**
     * Constructs a new {@code LinkedStack} object.
     */
    public LinkedStack() {
        data = new LinkedList<>();
    }

    private static final long serialVersionUID = -8808438556908560096L;

}
