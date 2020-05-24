package util;

import java.util.concurrent.ThreadLocalRandom;

/**
 * The {@code Common} class exposes a number of constants and methods that are useful for the implementation of a
 * several data structures.
 *
 * @author Jeff Wilgus
 */
public class Common {

	/**
	 * The default size allocation for array-backed data structures.
	 */
	public static final int DEFAULT_CAPACITY = 8;

	/**
	 * The maximum size allocation for array-backed data structures.
	 */
	public static final int MAX_CAPACITY = 2 << 29;

	/**
	 * A common source of randomness.
	 */
	public static final ThreadLocalRandom RAND = ThreadLocalRandom.current();

	/**
	 * Determines whether or not the specified elements have equal values.
	 *
	 * @param a the first element
	 * @param b the other element
	 * @param <E> the type of element being compared
	 * @return {@code true} if both elements are evaluated as equal by their classes {@code equals} method
	 */
	public static <E> boolean areEqual(E a, E b) {
		if (a == null) {
			return b == null;
		}
		return a.equals(b);
	}

	/**
	 * Provides the hash code for the specified {@code element}.
	 *
	 * @param element the specified element
	 * @param <E> the type of the specified {@code element}
	 * @return the specified {@code element}'s hash code
	 */
	public static <E> int hash(E element) {
		return element == null ? 0 : element.hashCode();
	}

	private Common() {
		throw new AssertionError();
	}

}
