package main;

/**
 * The {@code AbstractPosition} class is the base class from which all {@code Collection} nodes shall be derived.
 *
 * @param <E> the type of element carried by this {@code AbstractPosition}
 * @author Jeff Wilgus
 */
abstract class AbstractPosition<E> extends AbstractOwnable<Collection<E>> implements Position<E> {

	private E element;

	/**
	 * Constructs a new {@code AbstractPosition} that carries the specified {@code element} and belongs to the
	 * specified {@code Collection}.
	 *
	 * @param element the specified {@code element}
	 * @param owner the {@code Collection} that owns this {@code AbstractPosition}
	 * @throws NullPointerException if the specified {@code Collection} is {@code null}
	 */
	AbstractPosition(final E element, final Collection<E> owner) {
		super(owner);
		setElement(element);
	}

	@Override
	public E getElement() {
		return element;
	}

	/**
	 * Sets the {@code element} carried by this {@code AbstractPosition} to the one specified.
	 *
	 * @param element the specified element
	 * @return the element previously carried by this {@code AbstractPosition}
	 */
	E setElement(final E element) {
		E result = this.element;
		this.element = element;
		return result;
	}
}
