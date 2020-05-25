package test;

import java.io.File;
import java.io.IOException;

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

}
