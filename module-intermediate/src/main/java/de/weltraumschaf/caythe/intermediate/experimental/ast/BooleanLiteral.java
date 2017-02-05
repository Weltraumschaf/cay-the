package de.weltraumschaf.caythe.intermediate.experimental.ast;

import de.weltraumschaf.caythe.intermediate.experimental.AstVisitor;

import java.util.Objects;

public final class BooleanLiteral implements AstNode {
    public static final BooleanLiteral TRUE = new BooleanLiteral(true);
    public static final BooleanLiteral FALSE = new BooleanLiteral(false);
    private final boolean value;

    private BooleanLiteral(final boolean value) {
        super();
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public <R> R accept(final AstVisitor<? extends R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof BooleanLiteral)) {
            return false;
        }

        final BooleanLiteral that = (BooleanLiteral) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "BooleanLiteral{" +
            "value=" + value +
            '}';
    }
}
