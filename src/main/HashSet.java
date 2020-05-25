package main;

/**
 * The {@code HashSet} class is a hash table implementation of the {@code Set} interface. This class offers constant
 * average time performance for {@code add}, {@code get}, and {@code remove} operations. Performance may be tuned by
 * supplying a load factor that determines how elements are dispersed (a lower load factor results in a more sparsely
 * populated {@code Set}). Elements may be {@code null}.
 *
 * @param <E> the type of element in this {@code HashSet}
 * @author Jeff Wilgus
 */
public class HashSet<E> extends AbstractSet<E> {

	/**
	 * Constructs a new {@code HashSet} object.
	 */
	public HashSet() {
		map = new HashMap<>();
	}

	/**
	 * Constructs a new {@code HashSet} object with the specified {@code loadFactor}.
	 *
	 * @param loadFactor the specified load factor
	 * @throws IllegalArgumentException if the specified {@code loadFactor} is not on the open interval bounded by zero
	 * and one
	 */
	public HashSet(double loadFactor) {
		map = new HashMap<>(loadFactor);
	}

	private static final long serialVersionUID = 323140859808317776L;

}
