package main;

import java.io.Serializable;
import java.util.Iterator;

/**
 * The {@code AbstractListAdaptor} is the base class from which all {@code List} adaptors shall be derived.
 *
 * @param <E> the type of element in this {@code AbstractListAdaptor}
 * @author Jeff Wilgus
 */
public abstract class AbstractListAdaptor<E> implements Collection<E>, Serializable {

	List<E> data;

	@Override
	public void clear() {
		data.clear();
	}

	@Override
	public boolean contains(final E element) {
		return data.contains(element);
	}

	@Override
	public int size() {
		return data.size();
	}

	@Override
	public boolean isEmpty() {
		return data.isEmpty();
	}

	@Override
	public Iterator<E> iterator() {
		return data.iterator();
	}

	@Override
	public int hashCode() {
		return data.hashCode();
	}

	@Override
	public String toString() {
		return data.toString();
	}

	private static final long serialVersionUID = -5616829587411714697L;

	// TODO read/write object

}
