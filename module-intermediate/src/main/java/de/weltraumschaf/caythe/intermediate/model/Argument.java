package de.weltraumschaf.caythe.intermediate.model;

import de.weltraumschaf.commons.validate.Validate;

import java.util.Objects;

/**
 * Represents a method argument.
 */
public final class Argument {
    private final String name;
    private final String type;

    public Argument(final String name, final String type) {
        super();
        this.name = Validate.notEmpty(name, "name");
        this.type = Validate.notEmpty(type, "type");
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Argument)) {
            return false;
        }

        final Argument argument = (Argument) o;
        return Objects.equals(name, argument.name) &&
            Objects.equals(type, argument.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }

    @Override
    public String toString() {
        return "Argument{" +
            "name='" + name + '\'' +
            ", type=" + type +
            '}';
    }
}
