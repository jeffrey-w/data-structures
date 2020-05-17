package main;

public interface OrderedSet<E> extends Set<E> {

	void removeFirst();

	void removeLast();

	E removePrevious(E element);

	E removeNext(E element);

	E getFirst();

	E getLast();

	E getPrevious(E element);

	E getNext(E element);

}
