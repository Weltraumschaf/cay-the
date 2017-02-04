package de.weltraumschaf.caythe.intermediate.experimental.ast;

import de.weltraumschaf.caythe.intermediate.experimental.Visitor;

import java.util.Objects;

public final class Subscript implements AstNode {
    private final AstNode identifier;
    private final AstNode index;

    public Subscript(final AstNode identifier, final AstNode index) {
        super();
        this.identifier = identifier;
        this.index = index;
    }

    @Override
    public void accept(Visitor visitor) {

    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Subscript)) {
            return false;
        }

        final Subscript subscript = (Subscript) o;
        return Objects.equals(identifier, subscript.identifier) &&
            Objects.equals(index, subscript.index);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, index);
    }

    @Override
    public String toString() {
        return "Subscript{" +
            "identifier=" + identifier +
            ", index=" + index +
            '}';
    }
}
