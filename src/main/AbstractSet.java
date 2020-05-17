package main;

import static util.Common.hash;

public abstract class AbstractSet<E> extends AbstractCollection<E> implements Set<E> {

	Map<E, Void> map;

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public boolean contains(final E element) {
		return map.contains(element);
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public void add(final E element) {
		map.put(element, null);
	}

	@Override
	public void remove(final E element) {
		map.remove(element);
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
		return size() == set.size();
	}

	@Override
	public int hashCode() {
		int result = 0;
		for(E element : this) {
			result += hash(element);
		}
		return result;
	}

	private static final long serialVersionUID = -3360434002937380937L;

}
