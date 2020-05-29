package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;

import static util.Common.validateObject;

/**
 * The {@code AbstractListAdaptor} is the base class from which all {@code List} adaptors shall be derived.
 *
 * @param <E> the type of element in this {@code AbstractListAdaptor}
 * @author Jeff Wilgus
 */
public abstract class AbstractListAdaptor<E> implements Collection<E>, Serializable {

    AbstractList<E> data;

    @Override
    public void clear() {
        data.clear();
    }

    @Override
    public boolean contains(final E element) {
        return data.contains(element);
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public Iterator<E> iterator() {
        return data.iterator();
    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }

    @Override
    public String toString() {
        return data.toString();
    }

    private static final long serialVersionUID = -5616829587411714697L;

    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeObject(data);
    }

    @SuppressWarnings("unchecked")
    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        data = (AbstractList<E>)validateObject(stream.readObject());
    }

}
