package test;

import java.io.Serializable;
import java.util.Comparator;

import static util.Common.RAND;

final class TestObject implements Cloneable, Comparable<TestObject>, Serializable {

	static final Comparator<TestObject> DESCENDING_ORDER = (a, b) -> Integer.compare(b.state, a.state);

	static TestObject random() {
		return new TestObject(RAND.nextInt());
	}

	private final int state;

	TestObject(int state) {
		this.state = state;
	}

	@Override
	protected TestObject clone() {
		try {
			return (TestObject) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e);
		}
	}

	int getState() {
		return state;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof TestObject)) {
			return false;
		}
		TestObject testObject = (TestObject) obj;
		return state == testObject.state;
	}

	@Override
	public int compareTo(final TestObject o) {
		return Integer.compare(state, o.state);
	}

	@Override
	public int hashCode() {
		return 31 + state;
	}

	@Override
	public String toString() {
		return Integer.toString(state);
	}

	private static final long serialVersionUID = 3036974920060020389L;
	
}
