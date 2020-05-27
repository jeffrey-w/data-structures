package main;

import java.util.Objects;

/**
 * The {@code AbstractOwnable} class is the base class from which all nodes shall be derived.
 *
 * @param <E> the type of element carried by this {@code AbstractOwnable}
 */
public abstract class AbstractOwnable<E> {

    E owner;

    /**
     * Constructs a new AbstractOwnable that belongs to the specified {@code owner}.
     *
     * @param owner the specified owner
     * @throws NullPointerException if the specified {@code owner} is {@code} null.
     */
    public AbstractOwnable(E owner) {
        this.owner = Objects.requireNonNull(owner);
    }

    /**
     * Induces this {@code AbstractOwnable}'s owner to disown it.
     */
    public void invalidate() {
        owner = null;
    }

    /**
     * Determines whether or not the specified {@code claimant} owns this {@code AbstractOwnable}.
     *
     * @param claimant the specified claimant
     * @return {@code true} if the specified {@code} claimant owns this {@code AbstractOwnable}
     * @throws NullPointerException if the specified {@code claimant} is {@code null}
     */
    public boolean isOwnedBy(E claimant) {
        return Objects.requireNonNull(claimant) == owner;
    }

}
