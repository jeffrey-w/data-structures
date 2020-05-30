package test;

import main.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class TestList extends AbstractList<TestObject> {

    private static final class TestPosition extends AbstractPosition<TestObject> {

        public TestPosition(final TestObject element, final TestList owner) {
            super(element, owner);
        }

    }

    private transient ArrayList<TestPosition> list;

    TestList() {
        init();
    }

    @Override
    protected void init() {
        setSize(0);
        list = new ArrayList<>();
    }

    private void setSize(int size) {
        try {
            Field s = AbstractCollection.class.getDeclaredField("size");
            s.setAccessible(true);
            s.set(this, size);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private TestPosition validatePosition(Position<TestObject> position) {
        try {
            Method validate = AbstractList.class.getDeclaredMethod("validatePosition", Position.class);
            validate.setAccessible(true);
            validate.invoke(this, position);
            return (TestPosition)position;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new AssertionError();
        }
    }

    @Override
    public Position<TestObject> add(final int index, final TestObject element) {
        TestPosition position = new TestPosition(element, this);
        list.add(index, position);
        setSize(list.size());
        return position;
    }

    @Override
    public Position<TestObject> addFirst(final TestObject element) {
        TestPosition position = new TestPosition(element, this);
        list.add(0, position);
        setSize(list.size());
        return position;
    }

    @Override
    public Position<TestObject> addLast(final TestObject element) {
        TestPosition position = new TestPosition(element, this);
        list.add(list.size(), position);
        setSize(list.size());
        return position;
    }

    @Override
    public Position<TestObject> addBefore(final Position<TestObject> position, final TestObject element) {
        TestPosition testPosition = new TestPosition(element, this);
        list.add(list.indexOf(validatePosition(position)), testPosition);
        setSize(list.size());
        return testPosition;
    }

    @Override
    public Position<TestObject> addAfter(final Position<TestObject> position, final TestObject element) {
        TestPosition testPosition = new TestPosition(element, this);
        list.add(list.indexOf(validatePosition(position)) + 1, testPosition);
        setSize(list.size());
        return testPosition;
    }

    @Override
    public TestObject remove(final int index) {
        TestObject object = list.remove(index).getElement();
        setSize(list.size());
        return object;
    }

    @Override
    public TestObject removeFirst() {
        TestObject object = list.remove(0).getElement();
        setSize(list.size());
        return object;
    }

    @Override
    public TestObject removeLast() {
        TestObject object = list.remove(list.size() - 1).getElement();
        setSize(list.size());
        return object;
    }

    @Override
    public TestObject removePrevious(final Position<TestObject> position) {
        TestObject object = list.remove(list.indexOf(validatePosition(position)) - 1).getElement();
        setSize(list.size());
        return object;
    }

    @Override
    public TestObject removeNext(final Position<TestObject> position) {
        TestObject object = list.remove(list.indexOf(validatePosition(position)) + 1).getElement();
        setSize(list.size());
        return object;
    }

    @Override
    public TestObject get(final int index) {
        return list.get(index).getElement();
    }

    @Override
    public TestObject getFirst() {
        return list.get(0).getElement();
    }

    @Override
    public TestObject getLast() {
        return list.get(list.size() - 1).getElement();
    }

    @Override
    public TestObject getPrevious(final Position<TestObject> position) {
        return list.get(list.indexOf(validatePosition(position)) - 1).getElement();
    }

    @Override
    public TestObject getNext(final Position<TestObject> position) {
        return list.get(list.indexOf(validatePosition(position)) + 1).getElement();
    }

    @Override
    public TestObject set(final int index, final TestObject element) {
        return list.set(index, new TestPosition(element, this)).getElement();
    }

    @Override
    public Position<TestObject> positionOf(final TestObject element) {
        return list.get(indexOf(element));
    }

    @Override
    public ListIterator<TestObject> listIterator() {
        return new ListIter(0);
    }

    @Override
    public ListIterator<TestObject> listIterator(final int index) {
        return new ListIter(index);
    }

    @Override
    public ListIterator<TestObject> listIterator(final Position<TestObject> position) {
        TestPosition testPosition = validatePosition(position);
        for (int i = 0; i < size(); i++) {
            if (list.get(i) == testPosition) {
                return new ListIter(i);
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Iterator<TestObject> iterator() {
        return new Iter();
    }

    private final class ListIter implements ListIterator<TestObject> {

        final ListIterator<TestPosition> i;

        ListIter(int index) {
            i = list.listIterator(index);
        }

        @Override
        public boolean hasNext() {
            return i.hasNext();
        }

        @Override
        public TestObject next() {
            return i.next().getElement();
        }

        @Override
        public boolean hasPrevious() {
            return i.hasPrevious();
        }

        @Override
        public TestObject previous() {
            return i.previous().getElement();
        }

        @Override
        public int nextIndex() {
            return i.nextIndex();
        }

        @Override
        public int previousIndex() {
            return i.previousIndex();
        }

        @Override
        public void remove() {
            i.remove();
        }

        @Override
        public void set(final TestObject object) {
            i.set(new TestPosition(object, TestList.this));
        }

        @Override
        public void add(final TestObject object) {
            i.add(new TestPosition(object, TestList.this));
        }

    }

    private final class Iter implements Iterator<TestObject> {

        final Iterator<TestPosition> i = list.iterator();

        @Override
        public boolean hasNext() {
            return i.hasNext();
        }

        @Override
        public TestObject next() {
            return i.next().getElement();
        }

        @Override
        public void remove() {
            i.remove();
        }

    }

    private static final long serialVersionUID = 679621545260527924L;

}
