package util;

import main.PriorityQueue;

import java.util.Comparator;

public class Heapsort<E> {

    private final PriorityQueue<E> heap;

    public Heapsort(Comparator<E> comp) {
        if(comp == null) {
            heap = new PriorityQueue<>();
        } else {
            heap = new PriorityQueue<>(comp);
        }
    }

    public void sort(E[] array) {
        for (E element : array) {
            heap.enqueue(element);
        }
        for (int i = 0; i < array.length; i++) {
            array[i] = heap.dequeue();
        }
    }

}
