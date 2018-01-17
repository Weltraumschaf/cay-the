package de.weltraumschaf.caythe.intermediate.model;

import de.weltraumschaf.commons.validate.Validate;
import lombok.Getter;

import java.util.Objects;

/**
 * Delegate of a {@link Type}.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public final class Delegate implements IntermediateModel {

    @Getter
    private final String name; // TODO should be a {@link TypeName}

    /**
     * Dedicated constructor.
     *
     * @param name must not be {@code null} or empty
     */
    public Delegate(final String name) {
        super();
        this.name = Validate.notNull(name, "name");
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Delegate)) {
            return false;
        }

        final Delegate delegate = (Delegate) o;
        return Objects.equals(name, delegate.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Delegate{" +
            "name='" + name + '\'' +
            '}';
    }
}
