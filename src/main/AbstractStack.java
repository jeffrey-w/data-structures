package main;

/**
 * The {@code AbstractStack} class is the base class from which all {@code Stack} implementations shall be derived.
 *
 * @param <E> the type of element in this {@code AbstractStack}
 * @author Jeff Wilgus
 */
public abstract class AbstractStack<E> extends AbstractListAdaptor<E> implements Stack<E> {

	@Override
	public void push(final E element) {
		data.addFirst(element);
	}

	@Override
	public E pop() {
		return data.removeFirst();
	}

	@Override
	public E peek() {
		return data.getFirst();
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof AbstractStack)) {
			return false;
		}
		return data.equals(((AbstractStack<?>) obj).data);
	}

	private static final long serialVersionUID = 3088076122729214910L;

}
