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
	abstract void init();

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
			if (++index != size) {
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

	/**
	 * Ensures that the specified {@code Position} is valid insofar as it belongs to this {@code AbstractCollection}.
	 *
	 * @param position the specified {@code Position}
	 * @param type the expected type of the specified {@code Position}
	 * @return the specified {@code Position} cast to the specified type of {@code AbstractPosition}
	 * @throws IllegalArgumentException if the specified {@code Position} is not of the specified {@code type} or it is
	 * not owned by this {@code AbstractCollection}
	 * @throws IllegalStateException if this {@code AbstractCollection} is empty
	 * @throws NullPointerException if the specified {@code Position} is {@code null}
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	AbstractPosition<E> validatePosition(Position<E> position, Class<? extends AbstractPosition> type) {
		if (isEmpty()) {
			throw new IllegalStateException();
		}
		AbstractPosition<E> abstractPosition;
		if (!(type.isInstance(position)) || !(abstractPosition = type.cast(position)).isOwnedBy(this)) {
			throw new IllegalArgumentException();
		}
		return abstractPosition;
	}

	private static final long serialVersionUID = 1294951821974395815L;

}
