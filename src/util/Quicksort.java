package util;

import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A randomized implementation of {@code Quicksort}. This algorithm sorts a given array on the order induced by a
 * supplied {@code Comparator} or, if that is {@code null}, the natural ordering of the elements in the array.
 *
 * @param <E> the type of element being sorted.
 * @author Jeff Wilgus
 */
public final class Quicksort<E> {

	private static final ThreadLocalRandom RAND = ThreadLocalRandom.current();

	private final Comparator<E> comp;

	/**
	 * Constructs a new QuickSort object that compares the elements of a supplied array on the order induced by the
	 * specified {@code Comparator}, or , if that is {@code null}, their natural ordering.
	 */
	public Quicksort(final Comparator<E> comp) {
		if (comp == null) {
			this.comp = new DefaultComparator<>();
		} else {
			this.comp = comp;
		}
	}

	/**
	 * Sorts the specified {@code array} on the order induced by the {@code Comparator} supplied at construction,
	 * or, if that was {@code null}, the natural ordering of the elements in the specified {@code array}.
	 *
	 * @param array the specified array
	 * @throws ClassCastException if the specified {@code array} contains elements that are not mutually comparable by
	 * the supplied {@code Comparator}
	 * @throws NullPointerException if the specified {@code array} is {@code null}
	 */
	public void sort(E[] array) {
		sort(array, 0, array.length - 1);
	}

	private void sort(E[] array, int from, int to) {
		if (from < to) {
			int p = partition(array, from, to);
			sort(array, from, p - 1);
			sort(array, p + 1, to);
		}
	}

	private int partition(E[] array, int from, int to) {
		int index = randomIndex(from, to);
		int mid = from;
		swap(array, index, to);
		for (int i = from; i < to; i++) {
			if (lessThan(array[i], array[to])) {
				swap(array, mid++, i);
			}
		}
		swap(array, mid, to);
		return mid;
	}

	private int randomIndex(int origin, int bound) {
		return RAND.nextInt(origin, bound + 1);
	}

	private void swap(E[] arr, int a, int b) {
		E temp = arr[a];
		arr[a] = arr[b];
		arr[b] = temp;
	}

	private boolean lessThan(E a, E b) {
		return comp.compare(a, b) < 0;
	}

}