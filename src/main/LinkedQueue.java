package main;

/**
 * The {@code LinkedQueue} class is an {@code LinkedList}-backed implementation of the {@code Queue} interface.
 *
 * @param <E> they type of element in this {@code LinkedQueue}
 * @author Jeff Wilgus
 */
public class LinkedQueue<E> extends AbstractQueue<E> {

	/**
	 * Constructs a new {@code LinkedQueue} object.
	 */
	public LinkedQueue() {
		data = new LinkedList<>();
	}

	private static final long serialVersionUID = -6137631008777715031L;

}
