package util;

import main.PriorityQueue;

import java.util.Comparator;

public class Heapsort<E> extends AbstractSort<E> {

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
