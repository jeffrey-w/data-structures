package main;

import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * The {@code List} interface specified operations on a linear sequence of elements.
 *
 * @param <E> the type of element in this {@code List}
 * @author Jeff Wilgus
 */
public interface List<E> extends Collection<E>, Sortable<E> {


    /**
     * Adds the specified {@code element} to this {@code list} at the specified {@code index}. The specified {@code
     * element} is inserted before any elements at or after the specified {@code index}.
     *
     * @param index the specified index
     * @param element the specified element
     * @return the {@code Position} the specified {@code element} was inserted in
     * @throws IndexOutOfBoundsException if the specified {@code index} is negative or greater than the size of this
     * {@code List}
     */
    Position<E> add(final int index, final E element);

    /**
     * Adds the specified {@code element} to the beginning of this {@code List}.
     *
     * @param element the specified element
     * @return the {@code Position} the the specified {@code element} was inserted in
     */
    Position<E> addFirst(final E element);

    /**
     * Adds the specified {@code element} to the end of this {@code List}.
     *
     * @param element the specified element
     * @return the {@code Position} the specified {@code element} was inserted in
     */
    Position<E> addLast(final E element);

    /**
     * Adds the specified {@code element} to this {@code List} at the {@code Position} before that specified.
     *
     * @param position the specified {@code Position}
     * @param element the specified element
     * @return the {@code Position} the specified {@code element} was inserted in
     * @throws IllegalArgumentException if the specified {@code Position} does not belong to this {@code List}
     * @throws NullPointerException if the specified {@code Position} is {@code null}
     */
    Position<E> addBefore(final Position<E> position, final E element);

    /**
     * Adds the specified {@code element} to this {@code List} at the {@code Position} after that specified.
     *
     * @param position the specified {@code Position}
     * @param element the specified element
     * @return the {@code Position} the specified {@code element} was inserted in
     * @throws IllegalArgumentException if the specified {@code Position} does not belong to this {@code List}
     * @throws NullPointerException if the specified {@code Position} is {@code null}
     */
    Position<E> addAfter(final Position<E> position, final E element);

    /**
     * Removes the element at the specified {@code index} of this {@code List}.
     *
     * @param index the specified index
     * @return the removed element
     * @throws IllegalStateException if this {@code List} is empty
     * @throws IndexOutOfBoundsException if the specified {@code index} is negative or greater than or equal to the
     * size of this {@code List}
     */
    E remove(final int index);

    /**
     * Removes the first element in this {@code List}.
     *
     * @return the removed element
     * @throws IllegalStateException if this {@code List} is empty
     */
    E removeFirst();

    /**
     * Removes the last element in this {@code List}.
     *
     * @return the removed element
     * @throws IllegalStateException if this {@code List} is empty
     */
    E removeLast();

    /**
     * Removes the element in this {@code List} at the {@code Position} before that specified.
     *
     * @param position the specified {@code Position}
     * @return the removed element
     * @throws IllegalArgumentException if the specified {@code Position} does not belong to this {@code List}
     * @throws IllegalStateException if this {@code List} is empty
     * @throws NullPointerException if the specified {@code Position} is {@code null}
     */
    E removePrevious(final Position<E> position);

    /**
     * Removes the element in this {@code List} at the {@code Position} after that specified.
     *
     * @param position the specified {@code Position}
     * @return the removed element
     * @throws IllegalArgumentException if the specified {@code Position} does not belong to this {@code List}
     * @throws IllegalStateException if this {@code List} is empty
     * @throws NullPointerException if the specified {@code Position} is {@code null}
     */
    E removeNext(final Position<E> position);

    /**
     * Retrieves the element at the specified {@code index} of this {@code List}.
     *
     * @param index the specified index
     * @return the element at the specified {@code index}
     * @throws IllegalStateException if this {@code List} is empty
     * @throws IndexOutOfBoundsException if the specified {@code index} is negative or greater than or equal to the
     * size of this {@code List}
     */
    E get(final int index);

    /**
     * Retrieves the first element in this {@code List}.
     *
     * @return the first element in this {@code List}
     * @throws IllegalStateException if this {@code List} is empty
     */
    E getFirst();

    /**
     * Retrieves the last element in this {@code List}.
     *
     * @return the last element in this {@code List}
     * @throws IllegalStateException if this {@code List} is empty
     */
    E getLast();

    /**
     * Retrieves the element in this {@code List} at the {@code Position} before that specified.
     *
     * @param position the specified {@code Position}
     * @return the element at the {@code Position} before that specified
     * @throws IllegalArgumentException if the specified {@code Position} does not belong to this {@code List}
     * @throws IllegalStateException if this {@code List} is empty
     * @throws NullPointerException if the specified {@code Position} is {@code null}
     */
    E getPrevious(final Position<E> position);

    /**
     * Retrieves the element in this {@code List} at the {@code Position} after that specified.
     *
     * @param position the specified {@code Position}
     * @return the element at the {@code Position} after that specified
     * @throws IllegalArgumentException if the specified {@code Position} does not belong to this {@code List}
     * @throws IllegalStateException if this {@code List} is empty
     * @throws NullPointerException if the specified {@code Position} is {@code null}
     */
    E getNext(final Position<E> position);

    /**
     * Replaces the element at the specified {@code index} of this {@code List} with the specified {@code element}.
     *
     * @param index the specified index
     * @param element the specified element
     * @return the replaced element
     * @throws IllegalStateException if this {@code List} is empty
     * @throws IndexOutOfBoundsException if the specified {@code index} is negative or greater than or equal to the
     * size of this {@code List}
     */
    E set(final int index, final E element);

    /**
     * Provides the index of the specified {@code element} in this {@code List}. If this {@code List} is known to be
     * sorted, this operation will perform in logarithmic time, otherwise it will complete in linear time.
     *
     * @param element the specified element
     * @return the index of the specified {@code element}
     * @throws IllegalStateException if this {@code List} is empty
     * @throws NoSuchElementException if the specified {@code element} is not in this {@code List}
     */
    int indexOf(final E element);

    /**
     * Provides the last index at which the specified {@code element} is found in this {@code List}.
     *
     * @param element the specified element
     * @return the last index of the specified {@code element}
     * @throws IllegalStateException if this {@code List} is empty
     * @throws NoSuchElementException if the specified {@code element} is not in this {@code List}
     */
    int lastIndexOf(final E element);

    /**
     * Provides the {@code Position} of the specified {@code element} in this {@code List}.
     *
     * @param element the specified element
     * @return the {@code Position} of the specified {@code element}
     * @throws NoSuchElementException if the specified {@code element} is not in this {@code List}
     */
    Position<E> positionOf(final E element);

    /**
     * Provides a {@code ListIterator} over the elements in this {@code List}.
     *
     * @return A {@code ListIterator} over the elements in this {@code List}
     */
    ListIterator<E> listIterator();

    /**
     * Provides a {@code ListIterator} over the elements in this {@code List} beginning at the specified {@code index}.
     *
     * @param index the specified index
     * @return a {@code ListIterator} over the elements in this {@code List} beginning at the specified {@code index}
     * @throws IndexOutOfBoundsException if the specified {@code index} is negative or greater than the size of this
     * {@code List}
     */
    ListIterator<E> listIterator(final int index);

    /**
     * Provides a {@code ListIterator} over the elements in this {@code List} beginning at the specified {@code
     * Position}.
     *
     * @param position the specified {@code Position}
     * @return a {@code ListIterator} over the elements in this {@code List} beginning at the specified {@code
     * Position}
     * @throws IllegalArgumentException if the specified {@code Position} does not belong to this {@code List}
     * @throws IllegalStateException if this {@code List} is empty
     * @throws NullPointerException if the specified {@code Position} is {@code null}
     */
    ListIterator<E> listIterator(final Position<E> position);

}
