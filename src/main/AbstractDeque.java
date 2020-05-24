package main;

/**
 * The {@code AbstractDeque} class is the base class from which all {@code Deque} implementations shall be derived.
 *
 * @param <E> the type of element in this {@code AbstractDeque}
 * @author Jeff Wilgus
 */
public abstract class AbstractDeque<E> extends AbstractListAdaptor<E> implements Deque<E> {

	@Override
	public void addFirst(final E element) {
		data.addFirst(element);
	}

	@Override
	public void addLast(final E element) {
		data.addLast(element);
	}

	@Override
	public E removeFirst() {
		return data.removeFirst();
	}

	@Override
	public E removeLast() {
		return data.removeLast();
	}

	@Override
	public E getFirst() {
		return data.getFirst();
	}

	@Override
	public E getLast() {
		return data.getLast();
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof AbstractDeque)) {
			return false;
		}
		return data.equals(((AbstractDeque<?>) obj).data);
	}

	private static final long serialVersionUID = -2291296024653521480L;

}
