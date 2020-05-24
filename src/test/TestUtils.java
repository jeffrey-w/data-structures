package test;

import java.io.File;
import java.io.IOException;

final class TestUtils {

	static final int SIZE = 2 << 20;
	static final String PATH = "./output/";
	static final TestObject VALUE = TestObject.random();
	static Object PREV;

	static void openTestDir() throws IOException {
		File dir = new File(PATH);
		if (!dir.exists() && !dir.mkdir()) {
			throw new IOException();
		}
	}

}
