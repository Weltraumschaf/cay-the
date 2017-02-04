package de.weltraumschaf.caythe.intermediate.experimental.ast;

import de.weltraumschaf.caythe.intermediate.experimental.Visitor;

import java.util.Objects;

public final class FloatLiteral implements AstNode {
    private final double value;

    public FloatLiteral(final double value) {
        super();
        this.value = value;
    }
    @Override
    public void accept(Visitor visitor) {

    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof FloatLiteral)) {
            return false;
        }

        final FloatLiteral that = (FloatLiteral) o;
        return Double.compare(that.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "FloatLiteral{" +
            "value=" + value +
            '}';
    }
}
