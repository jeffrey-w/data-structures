package util;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Instances of the {@code DefaultComparator} class compare like objects on their natural ordering.
 *
 * @param <E> the type of object being compared
 * @author Jeff Wilgus
 */
public final class DefaultComparator<E> implements Comparator<E>, Serializable {

    @SuppressWarnings("unchecked")
    @Override
    public int compare(E a, E b) {
        return ((Comparable<E>)a).compareTo(b);
    }

    private static final long serialVersionUID = 6061609908437846539L;

}
