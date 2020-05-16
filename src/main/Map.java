package main;

/**
 * The {@code Map} interface specifies operations on a collection of key-value pairs.
 *
 * @param <K> the type of key that maps to values
 * @param <V> the type of mapped values
 * @author Jeff Wilgus
 */
public interface Map<K, V> {

	/**
	 * Removes all entries from this {@code Map}.
	 */
	void clear();

	/**
	 * Determines whether or not there exists a mapping between a value and the specified {@code key} in this {@code
	 * Map}.
	 *
	 * @param key the specified key
	 * @return {@code true} if a mapping exists between the specified {@code key} and a value in this {@code Map}
	 */
	boolean contains(K key);

	/**
	 * Provides the number of entries in this {@code Map}.
	 *
	 * @return the size of this {@code Map}
	 */
	int size();

	/**
	 * Determines whether or not there are any entries in this {@code Map}.
	 *
	 * @return {@code true} if this {@code Map} has no entries in it
	 */
	boolean isEmpty();

	/**
	 * Adds an entry to this {@code Map} associating the specified {@code key} to the specified {@code value}.
	 *
	 * @param key the specified key
	 * @param value the specified value
	 * @return the value previously associated with the specified {@code key} or {@code null} if none existed
	 */
	V put(K key, V value);

	/**
	 * Deletes an entry from this {@code Map} associating some value with the specified {@code key} if such an entry
	 * exists.
	 *
	 * @param key the specified key
	 * @return the value associated with the specified {@code key}, or, exceptionally, {@code null} if either no such
	 * mapping exists or if that was the value associated with the specified {@code key}
	 */
	V remove(K key);

	/**
	 * Provides a value in this {@code Map} associated with the specified {@code key} if such an entry exists.
	 *
	 * @param key the specified key
	 * @return the value associated with the specified {@code key}, or, exceptionally, {@code null} if either no such
	 * mapping exists or if that was the value associated with the specified {@code key}
	 */
	V get(K key);

	/**
	 * Replaces the {@code value} associated with the specified {@code key} with that specified if such an entry
	 * exists.
	 *
	 * @param key the specified key
	 * @param value the specified value
	 * @return the value associated with the specified {@code key}, or, exceptionally, {@code null} if either no such
	 * mapping exists or if that was the value associated with the specified {@code key}
	 */
	V replace(K key, V value);

	/**
	 * Provides an {@code Iterable} collection of the keys in this {@code Map}.
	 *
	 * @return the keys in this {@code Map}
	 */
	Set<K> keySet();

	/**
	 * Provides an {@code Iterable} collection of the values in this {@code Map}.
	 *
	 * @return the values in this {@code Map}
	 */
	Collection<V> values();

	/**
	 * Provides an {@code Iterable} collection of the entries in this {@code Map}.
	 *
	 * @return the entries in this {@code Map}
	 */
	Set<Entry<K, V>> entrySet();

}
