package main;

import util.DefaultComparator;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Comparator;
import java.util.Objects;

import static util.Common.validateObject;

/**
 * The {@code PriorityQueue} class is an implementation of the {@code Queue} interface for which a prescribed order
 * on the elements is maintained. At all times, the element at the front of this {@code Queue} is the minimum element.
 * Insertion and removal operations perform in logarithmic time. Null elements are permitted, but must be mutually
 * comparable with the other elements in this {@code PriorityQueue}.
 *
 * @param <E> the type of element in this {@code PriorityQueue}
 * @author Jeff Wilgus
 */
public class PriorityQueue<E> extends AbstractQueue<E> implements Sortable<E> {

    private Comparator<E> comp;

    /**
     * Constructs a new {@code PriorityQueue} object.
     */
    public PriorityQueue() {
        this(new DefaultComparator<>());
    }

    /**
     * Constructs a new {@code PriorityQueue} object sorted on the order induced by the specified {@code Comparator}.
     *
     * @param comp the specified {@code Comparator}
     * @throws NullPointerException if the specified {@code Comparator} is {@code null}
     */
    public PriorityQueue(final Comparator<E> comp) {
        data = new ArrayList<>();
        this.comp = Objects.requireNonNull(comp);
    }

    /**
     * {@inheritDoc} The specified {@code element} is promoted to the appropriate position in this {@code
     * PriorityQueue} as determined by its relative order among other elements.
     */
    @Override
    public void enqueue(final E element) {
        data.addLast(element);
        upheap();
    }

    @Override
    public E dequeue() {
        E result = data.getFirst();
        swap(0, size() - 1);
        data.removeLast();
        downheap();
        return result;
    }

    private void upheap() {
        int index = size() - 1;
        while (index > 0) {
            int parent = parent(index);
            if (comp.compare(data.get(index), data.get((parent))) > -1) {
                break;
            }
            swap(index, parent);
            index = parent;
        }
    }

    private void downheap() {
        int index = 0;
        while (hasLeft(index)) {
            int left, small;
            left = small = left(index);
            if (hasRight(index)) {
                int right = right(index);
                if (comp.compare(data.get(left), data.get(right)) > 0) {
                    small = right;
                }
            }
            if (comp.compare(data.get(small), data.get(index)) > -1) {
                break;
            }
            swap(index, small);
            index = small;
        }
    }

    private int parent(int index) {
        return (index - 1) >> 1;
    }

    private int left(int index) {
        return (index << 1) + 1;
    }

    private int right(int index) {
        return (index << 1) + 2;
    }

    private boolean hasLeft(int index) {
        return left(index) < size();
    }

    private boolean hasRight(int index) {
        return right(index) < size();
    }

    private void swap(int i, int j) {
        E temp = data.get(i);
        data.set(i, data.get(j));
        data.set(j, temp);
    }

    @Override
    public E first() {
        return data.getFirst();
    }

    @Override
    public void sort(final Comparator<E> comp) {
        E[] elements = data.toArray();
        this.comp = Objects.requireNonNullElseGet(comp, DefaultComparator::new);
        data.init();
        for (E element : elements) {
            enqueue(element);
        }
    }

    private static final long serialVersionUID = -2669565621428618944L;

    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeObject(comp);
    }

    @SuppressWarnings("unchecked")
    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        comp = (Comparator<E>)validateObject(stream.readObject());
    }

}
