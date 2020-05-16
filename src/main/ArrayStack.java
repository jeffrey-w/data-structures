package main;

/**
 * The {@code ArrayStack} class is an {@code ArrayList}-backed implementation of the {@code Stack} interface.
 *
 * @param <E> they type of element in this {@code ArrayStack}
 * @author Jeff Wilgus
 */
public class ArrayStack<E> extends AbstractStack<E> {

	/**
	 * Constructs a new {@code ArrayStack} object.
	 */
	public ArrayStack() {
		data = new ArrayList<>();
	}

	private static final long serialVersionUID = 4385986773919661768L;

}
