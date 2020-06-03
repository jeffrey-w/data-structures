package util;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;

import static util.Common.areEqual;

public abstract class AbstractSort<E> {

    E[] elements;
    Comparator<E> comp;

    /**
     * Sorts the specified {@code elements} on the order induced by the specified {@code Comparator}, or, if that is
     * {@code null}, the natural ordering of the specified {@code elements}.
     *
     * @param elements the specified elements
     * @param comp the specified {@code Comparator}
     * @throws ClassCastException if the specified {@code elements} contains elements that are not mutually
     * comparable by the specified {@code Comparator}
     * @throws NullPointerException if the specified {@code elements} are {@code null}
     */
    public abstract void sort(E[] elements, final Comparator<E> comp);

    /**
     * Provides the index of the specified {@code element} in the array of elements last sorted by this {@code
     * AbstractSort}.
     *
     * @param element the specified element.
     * @return the index of the specified {@code element}
     * @throws IllegalStateException if this {@code AbstractSort} has not previously sorted an array of elements
     * @throws NoSuchElementException if the array of elements last sorted by this {@code AbstractSort} does not
     * contain the specified {@code element}
     */
    public int binarySearch(E element) {
        if (!isSorted()) {
            throw new IllegalStateException();
        }
        int from = 0, to = elements.length - 1, mid;
        while (from <= to) {
            mid = from + ((to - from) >>> 1);
            if (areEqual(element, elements[mid])) {
                return mid;
            }
            if (comp.compare(element, elements[mid]) < 0) {
                to = mid - 1;
            } else {
                from = mid + 1;
            }
        }
        throw new NoSuchElementException();
    }

    /**
     * Determines whether or not this {@code AbstractSort} has previously sorted an array of elements.
     *
     * @return {@code true} if this {@code AbstractSort} has previously sorted an array of elements.
     */
    public boolean isSorted() {
        return elements != null && comp != null;
    }

    /**
     * Puts this {@code AbstractSort} into its native state, removing all previous sorting information.
     */
    public void clear() {
        elements = null;
        comp = null;
    }

    /**
     * Sets the specified {@code elements} as the array last sorted by this {@code AbstractSort}.
     *
     * @param elements the specified {@code elements}
     * @throws NullPointerException if the specified {@code elements} are {@code null}
     */
    void setElements(E[] elements) {
        this.elements = Objects.requireNonNull(elements);
    }

    /**
     * Sets the specified {@code Comparator} for this {@code AbstractSort}.
     *
     * @param comp the specified {@code Comparator}
     */
    void setComp(Comparator<E> comp) {
        this.comp = Objects.requireNonNullElseGet(comp, DefaultComparator::new);
    }

}
