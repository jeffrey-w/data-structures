package main;

import util.DefaultComparator;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

import static util.Common.areEqual;

/**
 * The {@code TreeMap} class is a red-black tree implementation of the {@code Map} interface. Entries are sorted by key
 * on the order induced by a supplied {@code Comparator}, or natural ordering if none is supplied. This class offers
 * logarithmic time performance for {@code put}, {@code get}, and {@code remove} operations. Both keys and values may be
 * {@code null}, however, only one key may be.
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
	 * @throws NullPointerException if the specified {@code Comparator} is {@code null}
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
			if (areEqual(key, cmp)) {
				return y.setValue(value);
			}
			if (lessThan(key, cmp)) {
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
			if (lessThan(key, cmp)) {
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
		ensureNonEmpty();
		return delete(search(root, key)).getValue();
	}

	@Override
	public Entry<K, V> removeFirst() {
		ensureNonEmpty();
		return delete(minimum(root));
	}

	@Override
	public Entry<K, V> removeLast() {
		ensureNonEmpty();
		return delete(maximum(root));
	}

	@Override
	public Entry<K, V> removePrevious(final K key) {
		ensureNonEmpty();
		return delete(predecessor(search(root, key)));
	}

	@Override
	public Entry<K, V> removeNext(final K key) {
		ensureNonEmpty();
		return delete(successor(search(root, key)));
	}

	Node<K, V> delete(Node<K, V> z) {
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
		return z;
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
		ensureNonEmpty();
		return search(root, key).getValue();
	}

	@Override
	public Entry<K, V> getFirst() {
		ensureNonEmpty();
		return minimum(root);
	}

	@Override
	public Entry<K, V> getLast() {
		ensureNonEmpty();
		return maximum(root);
	}

	@Override
	public Entry<K, V> getPrevious(final K key) {
		ensureNonEmpty();
		return predecessor(search(root, key));
	}

	@Override
	public Entry<K, V> getNext(final K key) {
		ensureNonEmpty();
		return successor(search(root, key));
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
		if (a == null) {
			return b != null;
		}
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

	private void ensureNonEmpty() {
		if (isEmpty()) {
			throw new IllegalStateException();
		}
	}

	private transient Set<Entry<K, V>> entries;

	@Override
	public Set<Entry<K, V>> entrySet() {
		Set<Entry<K, V>> entries = this.entries;
		if (entries == null) {
			entries = new MinSet<>() {

				@Override
				public void clear() {
					TreeMap.this.clear();
				}

				@Override
				public boolean contains(final Entry<K, V> entry) {
					if (TreeMap.this.contains(entry.getKey())) {
						return areEqual(get(entry.getKey()), entry.getValue());
					}
					return false;
				}

				@Override
				public int size() {
					return size;
				}

				@Override
				public boolean isEmpty() {
					return TreeMap.this.isEmpty();
				}

				@Override
				public Iterator<Entry<K, V>> iterator() {
					return new Iterator<>() {
						Node<K, V> current = minimum(root), last;
						boolean removable = false;

						@Override
						public boolean hasNext() {
							return current != nil;
						}

						@Override
						public Entry<K, V> next() {
							if (!hasNext()) {
								throw new NoSuchElementException();
							}
							removable = true;
							last = current;
							current = successor(current);
							return last;
						}

						@Override
						public void remove() {
							if (!removable) {
								throw new IllegalStateException();
							}
							removable = false;
							delete(last);
						}

					};
				}

			};
			this.entries = entries;
		}
		return entries;
	}

	private static final long serialVersionUID = -2526600353447364806L;

	private void writeObject(ObjectOutputStream stream) throws IOException {
		stream.defaultWriteObject();
		stream.writeInt(size);
		stream.writeObject(comp);
		for(Entry<K, V> entry : entrySet()) {
			stream.writeObject(entry.getKey());
			stream.writeObject(entry.getValue());
		}
	}

	@SuppressWarnings("unchecked")
	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
		int size;
		stream.defaultReadObject();
		size = stream.readInt();
		comp = (Comparator<K>) Objects.requireNonNull(stream.readObject());
		init();
		for(int i = 0; i < size; i++) {
			K key = (K) stream.readObject();
			V value = (V) stream.readObject();
			put(key, value);
		}
	}

}
