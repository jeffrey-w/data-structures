package main;

/**
 * The {@code AbstractDeque} class is an {@code ArrayList}-backed implementation of the {@code Deque} interface.
 *
 * @param <E> they type of element in this {@code ArrayDeque}
 * @author Jeff Wilgus
 */
public class ArrayDeque<E> extends AbstractDeque<E> {

    /**
     * Constructs a new {@code ArrayDeque} object.
     */
    public ArrayDeque() {
        data = new ArrayList<>();
    }

    private static final long serialVersionUID = 1037874193195017109L;

}
