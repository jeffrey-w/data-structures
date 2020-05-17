package main;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static util.Common.*;

// TODO fix JavaDoc

/**
 * The {@code HashMap} class is a hash table implementation of the {@code Map} interface. This class offers constant
 * average time performance for {@code put}, {@code get}, and {@code remove} operations. Performance may be tuned by
 * supplying a load factor that determines how keys are dispersed (a lower load factor results in a more sparsely
 * populated {@code Map}). Both keys and values may be {@code null}, however, only one key may be.
 *
 * @param <K> the type of key that maps to values
 * @param <V> the type of mapped values
 * @author Jeff Wilgus
 */
public class HashMap<K, V> extends AbstractMap<K, V> {

	private static class Bucket<K, V> extends AbstractEntry<K, V> {

		Bucket(K key, V value, HashMap<K, V> owner) {
			super(key, value, owner);
		}

	}

	private static class Chain<K, V> extends LinkedList<Bucket<K, V>> {

		Bucket<K, V> getBucket(K key) {
			for (Bucket<K, V> bucket : this) {
				if (areEqual(key, bucket.getKey())) {
					return bucket;
				}
			}
			return null;
		}

		V insert(K key, V value, HashMap<K, V> owner) {
			for (Bucket<K, V> bucket : this) {
				if (areEqual(key, bucket.getKey())) {
					return bucket.setValue(value);
				}
			}
			addLast(new Bucket<>(key, value, owner));
			return null;
		}

		V remove(K key) {
			return remove(indexOf(getBucket(key))).getValue();
		}

		private static final long serialVersionUID = -7957845362854571964L;

	}

	private static final double DEFAULT_LOAD_FACTOR = 0.75;

	private static double validateLoadFactor(double loadFactor) {
		if (lessThanOrEqual(loadFactor, 0) || lessThanOrEqual(1, loadFactor)) {
			throw new IllegalArgumentException();
		}
		return loadFactor;
	}

	private static boolean lessThanOrEqual(double a, double b) {
		return Double.compare(a, b) <= 0;
	}

	private double loadFactor;
	private transient Object[] data;

	/**
	 * Constructs a new {@code HashMap} object.
	 */
	public HashMap() {
		this(DEFAULT_LOAD_FACTOR);
	}

	/**
	 * Constructs a new {@code HashMap} object with the specified {@code loadFactor}.
	 *
	 * @param loadFactor the specified load factor
	 * @throws IllegalArgumentException if the specified {@code loadFactor} is not on the open interval bounded by zero
	 * and one
	 */
	public HashMap(double loadFactor) {
		init();
		setLoadFactor(loadFactor);
	}


	@Override
	void init() {
		data = new Object[DEFAULT_CAPACITY];
	}

	private void setLoadFactor(double loadFactor) {
		this.loadFactor = validateLoadFactor(loadFactor);
	}

	@Override
	public boolean contains(final K key) {
		return chainAt(key).getBucket(key) != null;
	}

	@Override
	public V put(final K key, final V value) {
		ensureCapacity();
		size++;
		return chainAt(key).insert(key, value, this);
	}

	private void ensureCapacity() {
		// TODO need overflow handling
		if (size >= data.length * loadFactor) {
			Object[] old = data;
			data = new Object[old.length << 1];
			for (Object o : old) {
				@SuppressWarnings("unchecked")
				Chain<K, V> chain = (Chain<K, V>) o;
				for (Bucket<K, V> bucket : chain) {
					chain.insert(bucket.getKey(), bucket.getValue(), this);
				}
			}
		}
	}

	@Override
	public V remove(final K key) {
		return chainAt(key).remove(key);
	}

	@Override
	public V get(final K key) {
		return chainAt(key).getBucket(key).getValue();
	}

	@SuppressWarnings("unchecked")
	private Chain<K, V> chainAt(K key) {
		int index = compress(hash(key));
		Object chain = data[index];
		if (chain == null) {
			chain = data[index] = new Chain<K, V>();
		}
		return (Chain<K, V>) chain;
	}

	private int compress(int hash) {
		return hash & (data.length - 1);
	}

	private transient Set<Entry<K, V>> entries;

	@Override
	public Set<Entry<K, V>> entrySet() {
		Set<Entry<K, V>> entries = this.entries;
		if (entries == null) {
			entries = new MinSet<>() {

				@Override
				public void clear() {
					HashMap.this.clear();
				}

				@Override
				public boolean contains(final Entry<K, V> entry) {
					if (HashMap.this.contains(entry.getKey())) {
						return areEqual(get(entry.getKey()), entry.getValue());
					}
					return false;
				}

				@Override
				public int size() {
					return size;
				}

				@Override
				public boolean isEmpty() {
					return HashMap.this.isEmpty();
				}

				@Override
				public Iterator<Entry<K, V>> iterator() {
					return new Iterator<>() {
						int index = 0, returned = 0;
						Bucket<K, V> last;
						Iterator<Bucket<K, V>> i;
						boolean removable = false;

						@Override
						public boolean hasNext() {
							return returned < size;
						}

						@SuppressWarnings("unchecked")
						@Override
						public Entry<K, V> next() {
							if (!hasNext()) {
								throw new NoSuchElementException();
							}
							if (i == null || !i.hasNext()) {
								while (data[index] == null) {
									index++;
								}
								i = ((Chain<K, V>) data[index]).iterator();
							}
							returned++;
							removable = true;
							return last = i.next();
						}

						@Override
						public void remove() {
							if (!removable) {
								throw new IllegalStateException();
							}
							returned--;
							removable = false;
							HashMap.this.remove(last.getKey());
						}

					};
				}

			};
			this.entries = entries;
		}
		return entries;
	}

	private static final long serialVersionUID = 2575388588879268752L;

	// TODO read/write object

}
