package main;

import java.io.Serializable;
import java.util.Iterator;

import static util.Common.areEqual;

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
	public boolean putIfAbsent(final K key, final V value) {
		if (!contains(key)) {
			put(key, value);
			return true;
		}
		return false;
	}

	@Override
	public boolean removeIfPresent(final K key, final V value) {
		if (contains(key) && areEqual(get(key), value)) {
			remove(key);
			return true;
		}
		return false;
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
		if (keys == null) {
			keys = new MinSet<>() {

				@Override
				public void clear() {
					AbstractMap.this.clear();
				}

				@Override
				public boolean contains(final K key) {
					return AbstractMap.this.contains(key);
				}

				@Override
				public int size() {
					return AbstractMap.this.size;
				}

				@Override
				public boolean isEmpty() {
					return AbstractMap.this.isEmpty();
				}

				@Override
				public Iterator<K> iterator() {
					return new Iterator<>() {

						final Iterator<Entry<K, V>> i = entrySet().iterator();

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

				private static final long serialVersionUID = 4131854097715185701L;

			};
			this.keys = keys;
		}
		return keys;
	}

	@Override
	public Collection<V> values() {
		Collection<V> values = this.values;
		if (values == null) {
			values = new AbstractCollection<>() {

				@Override
				protected void init() {
					AbstractMap.this.init();
				}

				@Override
				public boolean contains(final V value) {
					for (K key : keySet()) {
						if (areEqual(get(key), value)) {
							return true;
						}
					}
					return false;
				}

				@Override
				public int size() {
					return AbstractMap.this.size;
				}

				@Override
				public Iterator<V> iterator() {
					return new Iterator<>() {

						final Iterator<Entry<K, V>> i = entrySet().iterator();

						@Override
						public boolean hasNext() {
							return i.hasNext();
						}

						@Override
						public V next() {
							return i.next().getValue();
						}

						@Override
						public void remove() {
							i.remove();
						}

					};
				}

				private static final long serialVersionUID = -3052619367658786907L;

			};
			this.values = values;
		}
		return values;
	}

	static abstract class MinSet<E> extends AbstractSet<E> {

		@Override
		public void add(final E element) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void remove(final E element) {
			throw new UnsupportedOperationException();
		}

		private static final long serialVersionUID = 3620430710434884753L;

	}

	private static final long serialVersionUID = -1517329747185431297L;

}
