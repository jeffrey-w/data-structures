package util;

import java.io.InvalidObjectException;
import java.util.Objects;
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
    public static final int MAX_CAPACITY = 0x4000_0000;

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

    /**
     * Ensures that the specified {@code size} is non-negative.
     *
     * @param size the specified size
     * @return the specified {@code size} only if it is non-negative
     * @throws InvalidObjectException if the specified {@code size} is negative
     */
    public static int validateSize(int size) throws InvalidObjectException {
        if (size < 0) {
            throw new InvalidObjectException("Size less than zero.");
        }
        return size;
    }

    /**
     * Ensures that the specified {@code Object} is not {@code null}.
     *
     * @param object the specified {@code Object}
     * @return the specified {@code Object} only if it is not {@code null}
     * @throws InvalidObjectException if the specified {@code Object} is {@code null}
     */
    public static Object validateObject(Object object) throws InvalidObjectException {
        try {
            return Objects.requireNonNull(object);
        } catch (NullPointerException e) {
            throw new InvalidObjectException("Null data.");
        }
    }

    private Common() {
        throw new AssertionError();
    }

}
