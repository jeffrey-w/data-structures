package main;

import util.Quicksort;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * The {@code LinkedList} class is a linked-list implementation of the {@code List} interface. {@code Position}-based
 * methods perform in constant time, while index-based operations run in linear time. {@code LinkedList}s may contain
 * {@code null} elements.
 *
 * @param <E> the type of element in this {@code LinkedList}
 * @author Jeff Wilgus
 */
public class LinkedList<E> extends AbstractList<E> {

    private static final class Node<E> extends AbstractPosition<E> {

        static <E> Node<E> emptyNode(LinkedList<E> owner) {
            return new Node<>(null, null, null, owner);
        }

        private Node<E> prev;
        private Node<E> next;

        Node(E element, Node<E> prev, Node<E> next, LinkedList<E> owner) {
            super(element, owner);
            this.prev = prev;
            this.next = next;
        }

    }

    private transient Node<E> head;
    private transient Node<E> tail;

    /**
     * Constructs a new {@code LinkedList} object.
     */
    public LinkedList() {
        init();
    }

    @Override
    protected void init() {
        size = 0;
        sort = new Quicksort<>();
        head = Node.emptyNode(this);
        tail = Node.emptyNode(this);
        head.next = tail;
        tail.prev = head;
    }

    @Override
    public Position<E> add(final int index, final E element) {
        Node<E> node = toNode(index, true);
        return link(element, node, node.next);
    }

    @Override
    public Position<E> addFirst(final E element) {
        return link(element, head, head.next);
    }

    @Override
    public Position<E> addLast(final E element) {
        return link(element, tail.prev, tail);
    }

    @Override
    public Position<E> addBefore(final Position<E> position, final E element) {
        Node<E> node = validatePosition(position);
        return link(element, node.prev, node);
    }

    @Override
    public Position<E> addAfter(final Position<E> position, final E element) {
        Node<E> node = validatePosition(position);
        return link(element, node, node.next);
    }

    private Position<E> link(E element, Node<E> prev, Node<E> next) {
        Node<E> node = new Node<>(element, prev, next, this);
        prev.next = node;
        next.prev = node;
        size++;
        sort.clear();
        return node;
    }

    @Override
    public E remove(final int index) {
        return unlink(toNode(index, false));
    }

    @Override
    public E removeFirst() {
        return unlink(validatePosition(head.next));
    }

    @Override
    public E removeLast() {
        return unlink(validatePosition(tail.prev));
    }

    @Override
    public E removePrevious(final Position<E> position) {
        return unlink(validatePosition(position).prev);
    }

    @Override
    public E removeNext(final Position<E> position) {
        return unlink(validatePosition(position).next);
    }

    private E unlink(Node<E> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
        node.owner = null;
        size--;
        return node.getElement();
    }

    @Override
    public E get(final int index) {
        return getAt(toNode(index, false));
    }

    @Override
    public E getFirst() {
        return getAt(validatePosition(head.next));
    }

    @Override
    public E getLast() {
        return getAt(validatePosition(tail.prev));
    }

    @Override
    public E getPrevious(final Position<E> position) {
        return getAt(validatePosition(position).prev);
    }

    @Override
    public E getNext(final Position<E> position) {
        return getAt(validatePosition(position).next);
    }

    private E getAt(Node<E> node) {
        return node.getElement();
    }

    @Override
    public E set(final int index, final E element) {
        return setAt(toNode(index, false), element);
    }

    private E setAt(Node<E> node, E element) {
        sort.clear();
        return node.setElement(element);
    }

    @Override
    public Position<E> positionOf(final E element) {
        return toNode(indexOf(element), false);
    }

    private Node<E> toNode(int index, boolean isAddition) {
        validateIndex(index, isAddition);
        Node<E> node;
        if (index < size >> 1) {
            node = head.next;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail.prev;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    Node<E> validatePosition(Position<E> position) {
        return (Node<E>)super.validatePosition(position);
    }

    @Override
    public ListIterator<E> listIterator() {
        return new ListIter(0, head.next);
    }

    @Override
    public ListIterator<E> listIterator(final int index) {
        return new ListIter(index, toNode(index, true).next);
    }

    @Override
    public ListIterator<E> listIterator(final Position<E> position) {
        Node<E> node = validatePosition(position), current = head.next;
        for (int i = 0; i < size; i++) {
            if (node == current) {
                return new ListIter(i, node);
            }
            current = current.next;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Iterator<E> iterator() {
        return new Iter();
    }

    private final class ListIter implements ListIterator<E> {

        int index;
        Node<E> last, current;
        boolean removable, added;

        ListIter(int index, Node<E> current) {
            this.index = index;
            this.last = current.prev;
            this.current = current;
        }

        @Override
        public boolean hasNext() {
            return current != tail;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            index++;
            removable = true;
            added = false;
            last = current;
            current = current.next;
            return last.getElement();
        }

        @Override
        public boolean hasPrevious() {
            return current.prev != head;
        }

        @Override
        public E previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            index--;
            removable = true;
            added = false;
            current = current.prev;
            last = current;
            return last.getElement();
        }

        @Override
        public int nextIndex() {
            return index;
        }

        @Override
        public int previousIndex() {
            return index - 1;
        }

        @Override
        public void remove() {
            if (!removable|| added) {
                throw new IllegalStateException();
            }
            removable = false;
            unlink(last);
            last = null;
        }

        @Override
        public void set(final E e) {
            if (!removable || added) {
                throw new IllegalStateException();
            }
            setAt(last, e);
        }

        @Override
        public void add(final E e) {
            index++;
            added = true;
            addAfter(last, e);
        }

    }

    private final class Iter implements Iterator<E> {

        Node<E> current = head;
        boolean removable = false;

        @Override
        public boolean hasNext() {
            return current.next != tail;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            current = current.next;
            removable = true;
            return current.getElement();
        }

        @Override
        public void remove() {
            if (!removable) {
                throw new IllegalStateException();
            }
            removable = false;
            current = current.prev;
            unlink(current.next);
        }

    }

    private static final long serialVersionUID = -9146421135119588071L;

}
