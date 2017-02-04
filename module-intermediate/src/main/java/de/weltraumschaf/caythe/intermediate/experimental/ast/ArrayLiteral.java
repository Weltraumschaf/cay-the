package de.weltraumschaf.caythe.intermediate.experimental.ast;

import de.weltraumschaf.caythe.intermediate.experimental.Visitor;

import java.util.List;
import java.util.Objects;

public final class ArrayLiteral implements AstNode {
    private final List<AstNode> values;

    public ArrayLiteral(final List<AstNode> values) {
        super();
        this.values = values;
    }

    @Override
    public void accept(Visitor visitor) {

    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof ArrayLiteral)) {
            return false;
        }

        final ArrayLiteral that = (ArrayLiteral) o;
        return Objects.equals(values, that.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(values);
    }

    @Override
    public String toString() {
        return "ArrayLiteral{" +
            "values=" + values +
            '}';
    }
}
