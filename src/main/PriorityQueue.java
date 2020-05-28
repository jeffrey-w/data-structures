package main;

import util.DefaultComparator;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Comparator;
import java.util.Objects;

import static util.Common.validateObject;

public class PriorityQueue<E> extends AbstractListAdaptor<E> implements Queue<E> {

    private Comparator<E> comp;

    public PriorityQueue() {
        this(new DefaultComparator<>());
    }

    public PriorityQueue(Comparator<E> comp) {
        data = new ArrayList<>();
        this.comp = Objects.requireNonNull(comp);
    }

    @Override
    public void enqueue(final E element) {
        data.addLast(element);
        upheap(size() - 1);
    }

    @Override
    public E dequeue() {
        E result = data.getFirst();
        swap(0, size() - 1);
        data.removeLast();
        downheap(0);
        return result;
    }

    private void upheap(int index) {
        while (index > 0) {
            int parent = parent(index);
            if (comp.compare(data.get(index), data.get((parent))) > -1) {
                break;
            }
            swap(index, parent);
            index = parent;
        }
    }

    private void downheap(int index) {
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

    private void swap(int i, int j) {
        E temp = data.get(i);
        data.set(i, data.get(j));
        data.set(j, temp);
    }

    private int parent(int index) {
        return (index - 1) >>1;
    }

    private int left(int index) {
        return index << 1 + 1;
    }

    private int right(int index) {
        return index << 1 + 2;
    }

    private boolean hasLeft(int index) {
        return left(index) < size();
    }

    private boolean hasRight(int index) {
        return right(index) < size();
    }

    @Override
    public E first() {
        return data.getFirst();
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
