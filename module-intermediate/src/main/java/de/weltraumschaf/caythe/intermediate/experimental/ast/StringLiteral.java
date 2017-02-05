package de.weltraumschaf.caythe.intermediate.experimental.ast;

import de.weltraumschaf.caythe.intermediate.experimental.AstVisitor;

import java.util.Objects;

public final class StringLiteral implements AstNode {
    private final String value;

    public StringLiteral(final String value) {
        super();
        this.value = value;
    }

    @Override
    public <R> R accept(final AstVisitor<? extends R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof StringLiteral)) {
            return false;
        }

        final StringLiteral that = (StringLiteral) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "StringLiteral{" +
            "value='" + value + '\'' +
            '}';
    }

    public String getValue() {
        return value;
    }
}
