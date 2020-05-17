package main;

import java.io.Serializable;
import java.util.Iterator;

/**
 * The {@code AbstractMap} class is the base class from which all {@code Map} implementations shall be derived.
 *
 * @param <K> the type of key that maps to values
 * @param <V> the type of mapped values
 * @author Jeff Wilgus
 */
public abstract class AbstractMap<K, V> implements Map<K, V>, Serializable {

	int size;

	@Override
	public void clear() {
		init();
	}

	/**
	 * Puts this {@code AbstractMap} into its native state.
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
	public V replace(final K key, final V value) {
		if (contains(key)) {
			return put(key, value);
		}
		return null;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof AbstractMap)) {
			return false;
		}
		return entrySet().equals(((AbstractMap<?, ?>) obj).entrySet());
	}

	@Override
	public int hashCode() {
		int result = 0;
		for (Entry<K, V> entry : entrySet()) {
			result += entry.hashCode();
		}
		return result;
	}

	@Override
	public String toString() {
		int index = 0;
		StringBuilder builder = new StringBuilder("[");
		for (Entry<K, V> entry : entrySet()) {
			builder.append(entry);
			if (++index < size) {
				builder.append(", ");
			}
		}
		return builder.append("]").toString();
	}

	transient Set<K> keys;
	transient Collection<V> values;

	@Override
	public Set<K> keySet() {
		Set<K> keys = this.keys;
		if(keys == null) {
			keys = new AbstractSet<>() {
				@Override
				void init() {
					AbstractMap.this.init();
				}

				@Override
				public Iterator<K> iterator() {
					return new Iterator<>() {
						private final Iterator<Entry<K, V>> i = entrySet().iterator();

						@Override
						public boolean hasNext() {
							return i.hasNext();
						}

						@Override
						public K next() {
							return i.next().getKey();
						}

						@Override
						public void remove() {
							i.remove();
						}
					};
				}

				private static final long serialVersionUID = 6034320417510056542L;
			};
			this.keys = keys;
		}
		return keys;
	}

	private static final long serialVersionUID = -1517329747185431297L;

}
