package main;

/**
 * The {@code AbstractQueue} class is the base class from which all {@code Queue} implementations shall be derived.
 *
 * @param <E> the type of element in this {@code AbstractQueue}
 * @author Jeff Wilgus
 */
public abstract class AbstractQueue<E> extends AbstractListAdaptor<E> implements Queue<E> {

	@Override
	public void enqueue(final E element) {
		data.addFirst(element);
	}

	@Override
	public E dequeue() {
		return data.removeLast();
	}

	@Override
	public E first() {
		return data.getLast();
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof AbstractQueue)) {
			return false;
		}
		return data.equals(((AbstractQueue<?>) obj).data);
	}

	private static final long serialVersionUID = 8415345781461475769L;

}
