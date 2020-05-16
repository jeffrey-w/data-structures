package main;

/**
 * The {@code LinkedDeque} class is an {@code LinkedList}-backed implementation of the {@code Deque} interface.
 *
 * @param <E> they type of element in this {@code LinkedDeque}
 * @author Jeff Wilgus
 */
public class LinkedDeque<E> extends AbstractDeque<E> {

	/**
	 * Constructs a new {@code LinkedDeque} object.
	 */
	public LinkedDeque() {
		data = new LinkedList<>();
	}

	private static final long serialVersionUID = -8959455333267528865L;

}
