package de.weltraumschaf.caythe.intermediate.experimental.ast;

import de.weltraumschaf.caythe.intermediate.experimental.AstVisitor;

import java.util.Objects;

public final class IntegerLiteral implements AstNode {
    private final long value;

    public IntegerLiteral(final long value) {
        super();
        this.value = value;
    }

    @Override
    public <R> R accept(final AstVisitor<? extends R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof IntegerLiteral)) {
            return false;
        }

        final IntegerLiteral that = (IntegerLiteral) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "IntegerLiteral{" +
            "value=" + value +
            '}';
    }

    public long getValue() {
        return value;
    }
}
