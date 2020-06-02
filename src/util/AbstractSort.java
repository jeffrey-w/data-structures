package util;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;

import static util.Common.areEqual;

public abstract class AbstractSort<E> {

    E[] elements;
    Comparator<E> comp;

    public abstract void sort(E[] elements, final Comparator<E> comp);

    public int binarySearch(E element) {
        if(!isSorted()) {
            throw new IllegalStateException();
        }
        int from = 0, to = elements.length - 1, mid;
        while (from <= to) {
            mid = from + ((to - from) >>> 1);
            if(areEqual(element, elements[mid])) {
                return mid;
            }
            if(comp.compare(element, elements[mid]) < 0) {
                to = mid - 1;
            } else {
                from = mid + 1;
            }
        }
        throw new NoSuchElementException();
    }

    public boolean isSorted() {
        return elements != null && comp != null;
    }

    public void clear() {
        elements = null;
        comp = null;
    }

    void setElements(E[] elements) {
        this.elements = Objects.requireNonNull(elements);
    }

    void setComp(Comparator<E> comp) {
        this.comp = Objects.requireNonNullElseGet(comp, DefaultComparator::new);
    }

}
