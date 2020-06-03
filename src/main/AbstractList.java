package main;

import util.AbstractSort;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

import static util.Common.*;

/**
 * The {@code AbstractList} is the base class from which all {@code List} implementations shall be derived.
 *
 * @param <E> the type of element in this {@code AbstractList}
 * @author Jeff Wilgus
 */
public abstract class AbstractList<E> extends AbstractCollection<E> implements List<E> {

    transient AbstractSort<E> sort;

    @Override
    public boolean contains(final E element) {
        if (sort.isSorted()) {
            return binaryContains(element);
        }
        for (E e : this) {
            if (areEqual(element, e)) {
                return true;
            }
        }
        return false;
    }

    private boolean binaryContains(final E element) {
        try {
            sort.binarySearch(element);
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    @Override
    public int indexOf(final E element) {
        if (isEmpty()) {
            throw new IllegalStateException();
        }
        if (sort.isSorted()) {
            return sort.binarySearch(element);
        }
        int index = 0;
        for (E e : this) {
            if (areEqual(element, e)) {
                return index;
            }
            index++;
        }
        throw new NoSuchElementException();
    }

    @Override
    public void sort(final Comparator<E> comp) {
        E[] elements = toArray();
        sort.sort(elements, comp);
        clear();
        for(E element : elements) {
            addLast(element);
        }
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AbstractList)) {
            return false;
        }
        AbstractList<?> list = (AbstractList<?>)obj;
        Iterator<E> i = iterator();
        Iterator<?> j = list.iterator();
        while (i.hasNext() && j.hasNext()) {
            if (!areEqual(i.next(), j.next())) {
                return false;
            }
        }
        return size() == list.size();
    }

    @Override
    public int hashCode() {
        int prime = 31, result = 1;
        for (E element : this) {
            result = prime * result + hash(element);
        }
        return result;
    }

    /**
     * Ensures that the specified {@code index} is valid insofar as it is on the interval between zero and the size
     * of this {@code AbstractList}, which may be either open or closed at the end depending on the purpose for which
     * the {@code index} is being validated.
     *
     * @param index the specified index
     * @param isAddition if {@code true}, then the size of this {@code AbstractList} constitutes a valid index
     * @return the specified {@code index} if it is valid
     * @throws IllegalStateException if this {@code AbstractList} is empty and the specified {@code index} is not zero
     * @throws IndexOutOfBoundsException if the specified {@code index} does not lie on the interval between zero and
     * the size of this {@code AbstractList}
     */
    int validateIndex(final int index, final boolean isAddition) {
        if (!isAddition && isEmpty()) {
            throw new IllegalStateException();
        }
        int bound = isAddition ? size + 1 : size;
        return Objects.checkIndex(index, bound);
    }

    /**
     * Ensures that the specified {@code Position} is valid insofar as it belongs to this {@code AbstractList}.
     *
     * @param position the specified {@code Position}
     * @return the specified {@code Position} if it is valid
     * @throws IllegalArgumentException if the specified {@code Position} is not owned by this {@code AbstractList}
     * @throws IllegalStateException if this {@code AbstractList} is empty
     * @throws NullPointerException if the specified {@code Position} is {@code null}
     */
    AbstractPosition<E> validatePosition(Position<E> position) {
        if (isEmpty()) {
            throw new IllegalStateException();
        }
        if(position == null) {
            throw new NullPointerException();
        }
        if (!(position instanceof AbstractPosition) || ((AbstractPosition<E>)position).owner != this) {
            throw new IllegalArgumentException();
        }
        return (AbstractPosition<E>)position;
    }

    private static final long serialVersionUID = -5752600475035029478L;

    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeInt(size);
        for (E element : this) {
            stream.writeObject(element);
        }
    }

    @SuppressWarnings("unchecked")
    private void readObject(ObjectInputStream stream) throws ClassNotFoundException, IOException {
        stream.defaultReadObject();
        int size = validateSize(stream.readInt());
        init();
        for (int i = 0; i < size; i++) {
            addLast((E)stream.readObject());
        }
    }

}
