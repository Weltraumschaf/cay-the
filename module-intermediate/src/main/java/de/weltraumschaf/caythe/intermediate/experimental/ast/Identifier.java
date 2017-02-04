package de.weltraumschaf.caythe.intermediate.experimental.ast;

import de.weltraumschaf.caythe.intermediate.experimental.Visitor;

import java.util.Objects;

public final class Identifier implements AstNode {
    private final String name;

    public Identifier(final String name) {
        super();
        this.name = name;
    }

    @Override
    public void accept(Visitor visitor) {

    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Identifier)) {
            return false;
        }

        final Identifier that = (Identifier) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Identifier{" +
            "name='" + name + '\'' +
            '}';
    }
}
