package test;

import java.io.File;
import java.io.IOException;

final class TestUtils {

	static final String PATH = "./output/";

	static void openTestDir() throws IOException {
		File dir = new File(PATH);
		if(!dir.exists() && !dir.mkdir()) {
			throw new IOException();
		}
	}

}
