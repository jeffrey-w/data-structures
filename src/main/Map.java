package main;

/**
 * The {@code Map} interface specifies operations on a collection of key value pairs.
 *
 * @param <K> the type of key
 * @param <V>
 */
public interface Map<K, V> {

	void clear();

	int size();

	boolean isEmpty();

	V put(K key, V value);

	V remove(K key);

	V get(K key);

	V replace(K key, V value);

	Set<K> keySet();

	Collection<V> values();

	Set<Entry<K, V>> entrySet();

}
