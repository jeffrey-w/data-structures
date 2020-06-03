package util;

import main.PriorityQueue;

import java.util.Comparator;

/**
 * An implementation of {@code Heapsort} based on a min {@code PriorityQueue}. This algorithm sorts a given array on
 * the order induced by a supplied {@code Comparator} or, if that is {@code null}, the natural ordering of the
 * elements in the array.
 *
 * @param <E> the type of element beign sorted
 * @author Jeff Wilgus
 */
public class Heapsort<E> extends AbstractSort<E> {

    @Override
    public void sort(E[] elements, final Comparator<E> comp) {
        setElements(elements);
        setComp(comp);
        PriorityQueue<E> heap = new PriorityQueue<>(this.comp);
        for (E element : elements) {
            heap.enqueue(element);
        }
        for (int i = 0; i < elements.length; i++) {
            elements[i] = heap.dequeue();
        }
    }

}
