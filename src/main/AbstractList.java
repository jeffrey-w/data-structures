package main;

import util.DefaultComparator;
import util.Quicksort;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

import static util.Common.areEqual;
import static util.Common.hash;

/**
 * The {@code AbstractList} is the base class from which all {@code List} implementations shall be derived.
 *
 * @param <E> the type of element in this {@code AbstractList}
 * @author Jeff Wilgus
 */
public abstract class AbstractList<E> extends AbstractCollection<E> implements List<E> {

	Comparator<E> comp;

	@Override
	public boolean contains(final E element) {
		if (isSorted()) {
			return binaryContains(element);
		}
		for (E e : this) {
			if (areEqual(element, e)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int indexOf(final E element) {
		if (isSorted()) {
			return binarySearch(element);
		}
		int index = 0;
		for (E e : this) {
			if (areEqual(element, e)) {
				return index;
			}
			index++;
		}
		throw new NoSuchElementException();
	}

	private boolean isSorted() {
		return comp != null;
	}

	private int binarySearch(final E element) {
		int mid, from = 0, to = size - 1;
		E[] elements = toArray(); // TODO this makes this function O(n)
		while (from <= to) {
			mid = (from + to) >> 1;
			if (areEqual(element, elements[mid])) {
				return mid;
			}
			if (comp.compare(element, elements[mid]) < 0) {
				to = mid - 1;
			} else {
				from = mid + 1;
			}
		}
		throw new NoSuchElementException();
	}

	private boolean binaryContains(final E element) {
		try {
			binarySearch(element);
		} catch (NoSuchElementException e) {
			return false;
		}
		return true;
	}

	@Override
	public void sort(final Comparator<E> comp) {
		E[] elements = toArray();
		(new Quicksort<>(comp)).sort(elements);
		clear();
		for (E element : elements) {
			addLast(element);
		}
		setComp(comp);
	}

	private void setComp(final Comparator<E> comp) {
		this.comp = comp == null ? new DefaultComparator<>() : comp;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof AbstractList)) {
			return false;
		}
		AbstractList<?> list = (AbstractList<?>) obj;
		Iterator<E> i = iterator();
		Iterator<?> j = list.iterator();
		while (i.hasNext() && j.hasNext()) {
			if (!areEqual(i.next(), j.next())) {
				return false;
			}
		}
		return size == list.size;
	}

	@Override
	public int hashCode() {
		int prime = 31, result = 1;
		for (E element : this) {
			result = prime * result + hash(element);
		}
		return result;
	}

	int validateIndex(final int index, final boolean isAddition) {
		if(!isAddition && isEmpty()) {
			throw new IllegalStateException();
		}
		int bound = isAddition ? size + 1 : size;
		return Objects.checkIndex(index, bound);
	}

	private static final long serialVersionUID = -5752600475035029478L;

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();
		s.writeInt(size);
		s.writeObject(comp);
		for (E element : this) {
			s.writeObject(element);
		}
	}

	@SuppressWarnings("unchecked")
	private void readObject(ObjectInputStream s) throws ClassNotFoundException, IOException {
		int size;
		s.defaultReadObject();
		size = s.readInt();
		init();
		comp = (Comparator<E>) s.readObject();
		for (int i = 0; i < size; i++) {
			E element = (E) s.readObject();
			addLast(element);
		}
	}

}
