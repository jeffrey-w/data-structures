package main;

import java.util.Objects;

/**
 * The {@code AbstractPosition} class is the base class from which all {@code Collection} nodes shall be derived.
 *
 * @param <E> the type of element at this {@code AbstractPosition}
 * @author Jeff Wilgus
 */
public abstract class AbstractPosition<E> implements Position<E> {

    private E element;
    Collection<E> owner;

    /**
     * Constructs a new {@code AbstractPosition} object that carries the specified {@code element} and belongs to the
     * specified {@code Collection}.
     *
     * @param element the specified {@code element}
     * @param owner the {@code Collection} that owns this {@code AbstractPosition}
     * @throws NullPointerException if the specified {@code Collection} is {@code null}
     */
    public AbstractPosition(final E element, final Collection<E> owner) {
        this.element = element;
        this.owner = Objects.requireNonNull(owner);
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
    public E setElement(final E element) {
        E result = this.element;
        this.element = element;
        return result;
    }

}
