package main;

import java.util.Iterator;

import static util.Common.hash;

/**
 * The {@code AbstractSet} class is the base class from which all {@code Set} implementations shall be derived.
 *
 * @param <E> the type of element in this {@code AbstractSet}
 * @author Jeff Wilgus
 */
public abstract class AbstractSet<E> extends AbstractCollection<E> implements Set<E> {

	Map<E, Void> map;

	@Override
	public void clear() {
		init();
	}

	@Override
	void init() {
		((AbstractMap<E, Void>) map).init();
	}

	@Override
	public boolean contains(final E element) {
		return map.contains(element);
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public void add(final E element) {
		map.put(element, null);
		size = map.size();
	}

	@Override
	public void remove(final E element) {
		map.remove(element);
		size = map.size();
	}

	@Override
	public Iterator<E> iterator() {
		return map.keySet().iterator();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(final Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof AbstractSet)) {
			return false;
		}
		AbstractSet<?> set = (AbstractSet<?>) obj;
		for (Object o : set) {
			try {
				if (!contains((E) o)) {
					return false;
				}
			} catch (ClassCastException e) {
				return false;
			}
		}
		return size == set.size;
	}

	@Override
	public int hashCode() {
		int result = 0;
		for (E element : this) {
			result += hash(element);
		}
		return result;
	}

	private static final long serialVersionUID = -3360434002937380937L;

	// TODO read/write object

}
