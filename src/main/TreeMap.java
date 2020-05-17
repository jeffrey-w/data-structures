package main;

import util.DefaultComparator;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;

import static util.Common.areEqual;

/**
 * The {@code TreeMap} class is a red-black tree implementation of the {@code Map} interface. Entries are sorted by key
 * on the order induced by a supplied {@code Comparator}, or natural ordering if none is supplied. Both keys and values
 * may be {@code null}, however, only one key may be.
 *
 * @param <K> the type of key that maps to values
 * @param <V> the type of mapped values
 * @author Jeff Wilgus
 */
public class TreeMap<K, V> extends AbstractMap<K, V> implements OrderedMap<K, V> {

	private static final class Node<K, V> extends AbstractEntry<K, V> {

		static <K, V> Node<K, V> nil(TreeMap<K, V> owner) {
			Node<K, V> nil = new Node<>(null, null, owner);
			nil.parent = nil;
			nil.left = nil;
			nil.right = nil;
			nil.color = BLACK;
			return nil;
		}

		Node<K, V> parent;
		Node<K, V> left;
		Node<K, V> right;
		boolean color;

		Node(K key, V value, TreeMap<K, V> owner) {
			super(key, value, owner);
		}

	}

	private static final boolean RED = false, BLACK = true;

	private transient Node<K, V> nil;
	private transient Node<K, V> root;
	private Comparator<K> comp;

	/**
	 * Constructs a new {@code TreeMap} object.
	 */
	public TreeMap() {
		this(new DefaultComparator<>());
	}

	/**
	 * Constructs a new {@code TreeMap} object sorted on the order induced by the specified {@code Comparator}.
	 *
	 * @param comp the specified {@code Comparator}
	 */
	public TreeMap(Comparator<K> comp) {
		init();
		setComp(comp);
	}

	@Override
	void init() {
		size = 0;
		nil = Node.nil(this);
		root = nil;
	}

	private void setComp(Comparator<K> comp) {
		this.comp = Objects.requireNonNull(comp);
	}

	@Override
	public boolean contains(final K key) {
		try {
			search(root, key);
		} catch (NoSuchElementException e) {
			return false;
		}
		return true;
	}

	@Override
	public V put(final K key, final V value) {
		Node<K, V> z = new Node<>(key, value, this);
		Node<K, V> y = nil, x = root;
		K cmp;
		while (x != nil) {
			y = x;
			cmp = x.getKey();
			if (areEqual(cmp, key)) {
				return y.setValue(value);
			}
			if (lessThan(cmp, key)) {
				x = x.left;
			} else {
				x = x.right;
			}
		}
		z.parent = y;
		if (y == nil) {
			root = z;
		} else {
			cmp = y.getKey();
			if (lessThan(cmp, key)) {
				y.left = z;
			} else {
				y.right = z;
			}
		}
		z.left = nil;
		z.right = nil;
		z.color = RED;
		insertFixup(z);
		size++;
		return null;
	}

	private void insertFixup(Node<K, V> z) {
		while (z.parent.color == RED) {
			Node<K, V> y;
			if (z.parent == z.parent.parent.left) {
				y = z.parent.parent.right;
				if (y.color == RED) {
					z.parent.color = BLACK;
					y.color = BLACK;
					z.parent.parent.color = RED;
					z = z.parent.parent;
				} else {
					if (z == z.parent.right) {
						z = z.parent;
						rotateLeft(z);
					}
					z.parent.color = BLACK;
					z.parent.parent.color = RED;
					rotateRight(z.parent.parent);
				}
			} else {
				y = z.parent.parent.left;
				if (y.color == RED) {
					z.parent.color = BLACK;
					y.color = BLACK;
					z.parent.parent.color = RED;
					z = z.parent.parent;
				} else {
					if (z == z.parent.left) {
						z = z.parent;
						rotateRight(z);
					}
					z.parent.color = BLACK;
					z.parent.parent.color = RED;
					rotateLeft(z.parent.parent);
				}
			}
		}
		root.color = BLACK;
	}

	@Override
	public V remove(final K key) {
		return delete(search(root, key));
	}

	@Override
	public Entry<K, V> removeFirst() {
		Node<K, V> first = minimum(root);
		delete(first);
		return first;
	}

	@Override
	public Entry<K, V> removeLast() {
		Node<K, V> last = maximum(root);
		delete(last);
		return last;
	}

	@Override
	public Entry<K, V> removePrevious(final K key) {
		Node<K, V> previous = predecessor(search(root, key));
		delete(predecessor(previous));
		return previous;
	}

	@Override
	public Entry<K, V> removeNext(final K key) {
		Node<K, V> next = successor(search(root, key));
		delete(next);
		return next;
	}

	V delete(Node<K, V> z) {
		if (isEmpty()) {
			throw new IllegalStateException();
		}
		Node<K, V> y = z, x;
		boolean original = y.color;
		if (z.left == nil) {
			x = z.right;
			transplant(z, x);
		} else if (z.right == nil) {
			x = z.left;
			transplant(z, x);
		} else {
			y = minimum(z.right);
			original = y.color;
			x = y.right;
			if (y.parent == z) {
				x.parent = y;
			} else {
				transplant(y, y.right);
				y.right = z.right;
				y.right.parent = y;
			}
			transplant(z, y);
			y.left = z.left;
			y.left.parent = y;
			y.color = z.color;
		}
		if (original == BLACK) {
			deleteFixup(x);
		}
		z.invalidate();
		size--;
		return z.getValue();
	}

	private void transplant(Node<K, V> u, Node<K, V> v) {
		if (u.parent == nil) {
			root = v;
		} else if (u == u.parent.left) {
			u.parent.left = v;
		} else {
			u.parent.right = v;
		}
		v.parent = u.parent;
	}

	private void deleteFixup(Node<K, V> x) {
		while (x != root && x.color == BLACK) {
			Node<K, V> w;
			if (x == x.parent.left) {
				w = x.parent.right;
				if (w.color == RED) {
					w.color = BLACK;
					x.parent.color = RED;
					rotateLeft(x.parent);
					w = x.parent.right;
				}
				if (w.left.color == BLACK && w.right.color == BLACK) {
					w.color = RED;
					x = x.parent;
				} else {
					if (w.right.color == BLACK) {
						w.left.color = BLACK;
						w.color = RED;
						rotateRight(w);
						w = x.parent.right;
					}
					w.color = x.parent.color;
					x.parent.color = BLACK;
					w.right.color = BLACK;
					rotateLeft(x.parent);
					x = root;
				}
			} else {
				w = x.parent.left;
				if (w.color == RED) {
					w.color = BLACK;
					x.parent.color = RED;
					rotateRight(x.parent);
					w = x.parent.left;
				}
				if (w.left.color == BLACK && w.right.color == BLACK) {
					w.color = RED;
					x = x.parent;
				} else {
					if (w.left.color == BLACK) {
						w.right.color = BLACK;
						w.color = RED;
						rotateLeft(w);
						w = x.parent.left;
					}
					w.color = x.parent.color;
					x.parent.color = BLACK;
					w.left.color = BLACK;
					rotateRight(x.parent);
					x = root;
				}
			}
		}
		x.color = BLACK;
	}

	private void rotateLeft(Node<K, V> x) {
		Node<K, V> y = x.right;
		x.right = y.left;
		if (y.left != nil) {
			y.left.parent = x;
		}
		y.parent = x.parent;
		if (x.parent == nil) {
			root = y;
		} else if (x == x.parent.left) {
			x.parent.left = y;
		} else {
			x.parent.right = y;
		}
		y.left = x;
		x.parent = y;
	}

	private void rotateRight(Node<K, V> x) {
		Node<K, V> y = x.left;
		x.left = y.right;
		if (y.right != nil) {
			y.right.parent = x;
		}
		y.parent = x.parent;
		if (x.parent == nil) {
			root = y;
		} else if (x == x.parent.right) {
			x.parent.right = y;
		} else {
			x.parent.left = y;
		}
		y.right = x;
		x.parent = y;
	}

	@Override
	public V get(final K key) {
		return validateNode(search(root, key)).getValue();
	}

	@Override
	public Entry<K, V> getFirst() {
		return validateNode(minimum(root));
	}

	@Override
	public Entry<K, V> getLast() {
		return validateNode(maximum(root));
	}

	@Override
	public Entry<K, V> getPrevious(final K key) {
		return validateNode(predecessor(search(root, key)));
	}

	@Override
	public Entry<K, V> getNext(final K key) {
		return validateNode(successor(search(root, key)));
	}

	private Entry<K, V> validateNode(Node<K, V> node) {
		if (isEmpty()) {
			throw new IllegalStateException();
		}
		return node;
	}

	private Node<K, V> search(Node<K, V> root, K key) {
		while (root != nil) {
			if (areEqual(key, root.getKey())) {
				return root;
			}
			if (lessThan(key, root.getKey())) {
				root = root.left;
			} else {
				root = root.right;
			}
		}
		throw new NoSuchElementException();
	}

	private boolean lessThan(K a, K b) {
		return comp.compare(a, b) < 0;
	}

	private Node<K, V> minimum(Node<K, V> root) {
		while (root.left != nil) {
			root = root.left;
		}
		return root;
	}

	private Node<K, V> maximum(Node<K, V> root) {
		while (root.right != nil) {
			root = root.right;
		}
		return root;
	}

	private Node<K, V> predecessor(Node<K, V> x) {
		if (x.left != nil) {
			return maximum(x.left);
		}
		Node<K, V> y = x.parent;
		while (y != nil && x == y.left) {
			x = y;
			y = y.parent;
		}
		return y;
	}

	private Node<K, V> successor(Node<K, V> x) {
		if (x.right != nil) {
			return minimum(x.right);
		}
		Node<K, V> y = x.parent;
		while (y != nil && x == y.right) {
			x = y;
			y = y.parent;
		}
		return y;
	}

	@Override
	public Set<K> keySet() {
		return null;
	}

	@Override
	public Collection<V> values() {
		return null;
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return null;
	}

	private static final long serialVersionUID = -2526600353447364806L;

	// TODO read/write object

}
