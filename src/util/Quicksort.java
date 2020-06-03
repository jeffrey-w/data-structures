package util;

import java.util.Comparator;

import static util.Common.RAND;

/**
 * A randomized implementation of {@code Quicksort}. This algorithm sorts a given array on the order induced by a
 * supplied {@code Comparator} or, if that is {@code null}, the natural ordering of the elements in the array.
 *
 * @param <E> the type of element being sorted.
 * @author Jeff Wilgus
 */
public final class Quicksort<E> extends AbstractSort<E> {

    @Override
    public void sort(E[] elements, final Comparator<E> comp) {
        setElements(elements);
        setComp(comp);
        sort(0, elements.length - 1);
    }

    private void sort(int from, int to) {
        if (from < to) {
            int p = partition(from, to);
            sort(from, p - 1);
            sort(p + 1, to);
        }
    }

    private int partition(int from, int to) {
        int index = randomIndex(from, to);
        int mid = from;
        swap(index, to);
        for (int i = from; i < to; i++) {
            if (lessThan(elements[i], elements[to])) {
                swap(mid++, i);
            }
        }
        swap(mid, to);
        return mid;
    }

    private int randomIndex(int origin, int bound) {
        return RAND.nextInt(origin, bound + 1);
    }

    private void swap(int a, int b) {
        E temp = elements[a];
        elements[a] = elements[b];
        elements[b] = temp;
    }

    private boolean lessThan(E a, E b) {
        return comp.compare(a, b) < 0;
    }

}