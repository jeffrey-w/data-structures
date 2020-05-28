package main;

import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import static util.Common.DEFAULT_CAPACITY;
import static util.Common.MAX_CAPACITY;

/**
 * The {@code ArrayList} class is an array-based implementation of the {@code List} interface. {@code Position}-based
 * methods perform in linear time, while index-based methods run in constant time. {@code ArrayList}s may contain {@code
 * null} elements.
 *
 * @param <E> the type of element in this {@code ArrayList}
 * @author Jeff Wilgus
 */
public class ArrayList<E> extends AbstractList<E> {

    private final static class Bucket<E> extends AbstractPosition<E> {

        Bucket(E element, final ArrayList<E> owner) {
            super(element, owner);
        }
    }

    private transient Object[] elements;

    /**
     * Constructs a new {@code ArrayList} object.
     */
    public ArrayList() {
        init();
    }

    @Override
    protected void init() {
        size = 0;
        state = null;
        elements = new Object[DEFAULT_CAPACITY];
    }

    @Override
    public Position<E> add(final int index, final E element) {
        return insert(index, element);
    }

    @Override
    public Position<E> addFirst(final E element) {
        return insert(0, element);
    }

    @Override
    public Position<E> addLast(final E element) {
        return insert(size, element);
    }

    @Override
    public Position<E> addBefore(final Position<E> position, final E element) {
        return insert(toIndex(position), element);
    }

    @Override
    public Position<E> addAfter(final Position<E> position, final E element) {
        return insert(toIndex(position) + 1, element);
    }

    private Position<E> insert(int index, E element) {
        validateIndex(index, true);
        ensureCapacity();
        Bucket<E> bucket = new Bucket<>(element, this);
        if (size - index > 0) {
            System.arraycopy(elements, index, elements, index + 1, size - index);
        }
        elements[index] = bucket;
        size++;
        state = null;
        return bucket;
    }

    void ensureCapacity() {
        if (elements.length == MAX_CAPACITY) {
            throw new OutOfMemoryError();
        }
        if (size == elements.length) {
            elements = Arrays.copyOf(elements, elements.length << 1);
        }
    }

    @Override
    public E remove(final int index) {
        return delete(index);
    }

    @Override
    public E removeFirst() {
        return delete(0);
    }

    @Override
    public E removeLast() {
        return delete(size - 1);
    }

    @Override
    public E removePrevious(final Position<E> position) {
        return delete(toIndex(position) - 1);
    }

    @Override
    public E removeNext(final Position<E> position) {
        return delete(toIndex(position) + 1);
    }

    private E delete(int index) {
        Bucket<E> bucket = bucketAt(index);
        if (size - index > 1) {
            System.arraycopy(elements, index + 1, elements, index, size - 1 - index);
        }
        bucket.owner = null;
        trimToSize();
        state = null;
        return bucket.getElement();
    }

    private void trimToSize() {
        if (--size < elements.length >> 1) {
            elements = Arrays.copyOf(elements, size);
        }
    }

    @Override
    public E get(final int index) {
        return getAt(index);
    }

    @Override
    public E getFirst() {
        return getAt(0);
    }

    @Override
    public E getLast() {
        return getAt(size - 1);
    }

    @Override
    public E getPrevious(final Position<E> position) {
        return getAt(toIndex(position) - 1);
    }

    @Override
    public E getNext(final Position<E> position) {
        return getAt(toIndex(position) + 1);
    }

    private E getAt(int index) {
        return bucketAt(index).getElement();
    }

    @Override
    public E set(final int index, final E element) {
        state = null;
        return bucketAt(index).setElement(element);
    }

    @Override
    public Position<E> positionOf(final E element) {
        return bucketAt(indexOf(element));
    }

    private int toIndex(Position<E> position) {
        validatePosition(position);
        for (int i = 0; i < size; i++) {
            if (elements[i] == position) {
                return i;
            }
        }
        throw new IllegalArgumentException();
    }

    @SuppressWarnings("unchecked")
    private Bucket<E> bucketAt(int index) {
        return (Bucket<E>)elements[validateIndex(index, false)];
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(final int index) {
        return new ListIter(validateIndex(index, false));
    }

    @Override
    public ListIterator<E> listIterator(final Position<E> position) {
        return new ListIter(toIndex(position));
    }

    @Override
    public Iterator<E> iterator() {
        return new Iter();
    }

    private final class ListIter implements ListIterator<E> {

        int next;
        boolean added;
        boolean removable;

        ListIter(int next) {
            this.next = next;
        }

        @Override
        public boolean hasNext() {
            return next < size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            added = false;
            removable = true;
            return getAt(next++);
        }

        @Override
        public boolean hasPrevious() {
            return next > 0;
        }

        @Override
        public E previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            added = false;
            removable = true;
            return getAt(--next);
        }

        @Override
        public int nextIndex() {
            return next;
        }

        @Override
        public int previousIndex() {
            return next - 1;
        }

        @Override
        public void remove() {
            if (!removable || added) {
                throw new IllegalStateException();
            }
            delete(next - 1);
        }

        @Override
        public void set(final E e) {
            if (!removable || added) {
                throw new IllegalStateException();
            }
            ArrayList.this.set(next - 1, e);
        }

        @Override
        public void add(final E e) {
            next++;
            added = true;
            ArrayList.this.add(next - 1, e);
        }
    }

    private final class Iter implements Iterator<E> {

        int current = 0;
        boolean removable = false;

        @Override
        public boolean hasNext() {
            return current < size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            removable = true;
            return getAt(current++);
        }

        @Override
        public void remove() {
            if (!removable) {
                throw new IllegalStateException();
            }
            removable = false;
            delete(current - 1);
        }
    }

    private static final long serialVersionUID = 3193156208696397457L;

}
