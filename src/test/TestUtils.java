package test;

import util.DefaultComparator;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;

final class TestUtils {

    /**
     * The number of elements to insert into a container.
     */
    static final int SIZE = 0x10000;

    /**
     * The path to a directory for temporary test files.
     */
    static final String PATH = "./output/";

    /**
     * A common {@code TestObject} to store in containers.
     */
    static final TestObject VALUE = TestObject.random();

    /**
     * A store for inter-test objects.
     */
    static Object PREV;

    /**
     * Attempts to open (or create) a temporary directory for test output.
     *
     * @throws IOException if the temporary directory could not be opened (or created)
     */
    static void openTestDir(String path) throws IOException {
        File dir = new File(path);
        if (!dir.exists() && !dir.mkdir()) {
            throw new IOException();
        }
    }

    /**
     * Determines whether or not the specified {@code array} is sorted on the order induced by the specified {@code
     * Comparator} or, if that is {@code null}, the natural ordering of its elements.
     *
     * @param array the specified array
     * @param comp the specified {@code Comparator}
     * @param <E> the type of element in the specified {@code array}
     * @return {@code true} if the specified {@code array} is sorted
     * @throws ClassCastException if the elements in the specified {@code array} are not mutually comparable
     */
    static <E> boolean isSorted(E[] array, Comparator<E> comp) {
        E last = null;
        if(comp == null) {
            comp = new DefaultComparator<>();
        }
        for (E element : array) {
            if(last != null && comp.compare(element, last) < 0) {
                return false;
            }
            last = element;
        }
        return true;
    }

    /**
     * Determines whether or not the specified {@code Iterable} is sorted on the order induced by the specified {@code
     * Comparator} or, if that is {@code null}, the natural ordering of its elements.
     *
     * @param iterable the specified {@code Iterable}
     * @param comp the specified {@code Comparator}
     * @param <E> the type of element in the specified {@code Iterable}
     * @return {@code true} if the specified {@code Iterable} is sorted
     * @throws ClassCastException if the elements in the specified {@code Iterable} are not mutually comparable
     */
    static <E> boolean isSorted(Iterable<E> iterable, Comparator<E> comp) {
        E last = null;
        if(comp == null) {
            comp = new DefaultComparator<>();
        }
        for (E element : iterable) {
            if(last != null && comp.compare(element, last) < 0) {
                return false;
            }
            last = element;
        }
        return true;
    }

}
