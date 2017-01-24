package de.weltraumschaf.caythe.frontend.experimental.types;

import java.util.Objects;

public final class IdentifierType implements ObjectType {
    private final String name;

    public IdentifierType(final String name) {
        super();
        this.name = name;
    }

    @Override
    public Type type() {
        return Type.IDENTIFIER;
    }

    @Override
    public String inspect() {
        return name;
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof IdentifierType)) {
            return false;
        }

        final IdentifierType that = (IdentifierType) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "IdentifierType{" +
            "name='" + name + '\'' +
            '}';
    }
}
