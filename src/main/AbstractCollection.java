package main;

import java.io.Serializable;

/**
 * A base implementation of the {@code Collection} interface.
 *
 * @param <E> the type of element in this {@code AbstractCollection}
 * @author Jeff Wilgus
 */
public abstract class AbstractCollection<E> implements Collection<E>, Serializable {

	int size;

	@Override
	public void clear() {
		init();
	}

	/**
	 * Puts this {@code AbstractCollection} into its native state.
	 */
	protected abstract void init();

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public String toString() {
		int index = 0;
		StringBuilder builder = new StringBuilder("[");
		for (E element : this) {
			builder.append(element == this ? "(this collection)" : element);
			if (++index < size) {
				builder.append(", ");
			}
		}
		return builder.append("]").toString();
	}

	/**
	 * Provides an array of the elements in this {@code AbstractCollection}.
	 *
	 * @return an array containing the elements in this {@code AbstractCollection}
	 */
	E[] toArray() {
		int index = 0;
		@SuppressWarnings("unchecked")
		E[] elements = (E[]) new Object[size];
		for (E element : this) {
			elements[index++] = element;
		}
		return elements;
	}

	private static final long serialVersionUID = 1294951821974395815L;

}
