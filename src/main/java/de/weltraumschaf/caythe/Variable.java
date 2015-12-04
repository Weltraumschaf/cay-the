package de.weltraumschaf.caythe;

import de.weltraumschaf.commons.validate.Validate;
import java.util.Objects;

/**
 */
final class Variable {

    private final String name;
    private final int value;

    public Variable(final String name) {
        this(name, 0);
    }

    public Variable(final String name, final int value) {
        super();
        this.name = Validate.notEmpty(name, "name");
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Variable)) {
            return false;
        }

        final Variable other = (Variable) obj;
        return Objects.equals(name, other.name)
            && Objects.equals(value, other.value);
    }

    @Override
    public String toString() {
        return "Variable{" + "name=" + name + ", value=" + value + '}';
    }

}
