package main;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static util.Common.*;

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
			throw new NoSuchElementException();
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
			Bucket<K, V> bucket = remove(indexOf(getBucket(key)));
			bucket.invalidate();
			return bucket.getValue();
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
	protected void init() {
		size = 0;
		data = new Object[DEFAULT_CAPACITY];
	}

	private void setLoadFactor(double loadFactor) {
		this.loadFactor = validateLoadFactor(loadFactor);
	}

	@Override
	public boolean contains(final K key) {
		try {
			chainAt(key).getBucket(key);
		} catch (NoSuchElementException e) {
			return false;
		}
		return true;
	}

	@Override
	public V put(final K key, final V value) {
		ensureCapacity();
		size++;
		return chainAt(key).insert(key, value, this);
	}

	private void ensureCapacity() {
		if (data.length == MAX_CAPACITY) {
			throw new OutOfMemoryError();
		}
		if (size >= data.length * loadFactor) {
			size = 0;
			Object[] old = data;
			data = new Object[old.length << 1];
			for (Object o : old) {
				if (o != null) {
					@SuppressWarnings("unchecked")
					Chain<K, V> chain = (Chain<K, V>) o;
					for (Bucket<K, V> bucket : chain) {
						put(bucket.getKey(), bucket.getValue());
					}
				}
			}
		}
	}

	@Override
	public V remove(final K key) {
		V value = chainAt(key).remove(key);
		size--;
		return value;
	}

	@Override
	public V get(final K key) {
		return chainAt(key).getBucket(key).getValue();
	}

	@SuppressWarnings("unchecked")
	private Chain<K, V> chainAt(K key) {
		if (isEmpty()) {
			throw new IllegalStateException();
		}
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
					return HashMap.this.size;
				}

				@Override
				public boolean isEmpty() {
					return HashMap.this.isEmpty();
				}

				@Override
				public Iterator<Entry<K, V>> iterator() {
					return new EntryIter();
				}

				private static final long serialVersionUID = -2548516156968773633L;

			};
			this.entries = entries;
		}
		return entries;
	}

	private final class EntryIter implements Iterator<Entry<K, V>> {

		int index;
		Iterator<Bucket<K, V>> current, next;
		boolean removable;

		@SuppressWarnings("unchecked")
		EntryIter() {
			index = -1;
			if (!isEmpty()) {
				advance();
				current = ((Chain<K, V>) data[index]).iterator();
				advance();
				if (index < data.length) {
					next = ((Chain<K, V>) data[index]).iterator();
				}
			}
		}

		@Override
		public boolean hasNext() {
			return next != null;
		}

		@SuppressWarnings("unchecked")
		@Override
		public Entry<K, V> next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			if (!current.hasNext()) {
				current = next;
				advance();
				if (index < data.length) {
					next = ((Chain<K, V>) data[index]).iterator();
				} else {
					next = null;
				}
			}
			removable = true;
			return current.next();
		}

		private void advance() {
			do {
				index++;
			} while (index < data.length && data[index] == null);
		}

		@Override
		public void remove() {
			if (!removable) {
				throw new IllegalStateException();
			}
			removable = false;
			HashMap.this.size--;
			current.remove();
		}

	}

	private static final long serialVersionUID = 2575388588879268752L;

	private void writeObject(ObjectOutputStream stream) throws IOException {
		stream.defaultWriteObject();
		stream.writeInt(size);
		stream.writeDouble(loadFactor);
		for (Entry<K, V> entry : entrySet()) {
			stream.writeObject(entry.getKey());
			stream.writeObject(entry.getValue());
		}
	}

	@SuppressWarnings("unchecked")
	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
		stream.defaultReadObject();
		int size = validateSize(stream.readInt());
		data = new Object[nextPowTwo(size)];
		loadFactor = validateLoadFactor(stream);
		for (int i = 0; i < size; i++) {
			K key = (K) stream.readObject();
			V value = (V) stream.readObject();
			put(key, value);
		}
	}

	private static int nextPowTwo(int size) {
		if (size < DEFAULT_CAPACITY) {
			return DEFAULT_CAPACITY;
		}
		while (Integer.bitCount(size) > 1) {
			if ((++size & 1) != 0) { // i is odd and so cannot be a power of two.
				size++;
			}
		}
		return size;
	}

	private static double validateLoadFactor(ObjectInputStream stream) throws IOException {
		try {
			return validateLoadFactor(stream.readDouble());
		} catch (IllegalArgumentException e) {
			throw new InvalidObjectException("Load factor not on (0, 1).");
		}
	}

}
