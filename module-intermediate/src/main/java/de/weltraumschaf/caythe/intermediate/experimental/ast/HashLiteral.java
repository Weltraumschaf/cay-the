package de.weltraumschaf.caythe.intermediate.experimental.ast;

import de.weltraumschaf.caythe.intermediate.experimental.Visitor;

import java.util.Map;
import java.util.Objects;

public final class HashLiteral implements AstNode {
    private final Map<AstNode, AstNode> values;

    public HashLiteral(final Map<AstNode, AstNode> values) {
        super();
        this.values = values;
    }

    @Override
    public void accept(Visitor visitor) {

    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof HashLiteral)) {
            return false;
        }

        final HashLiteral that = (HashLiteral) o;
        return Objects.equals(values, that.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(values);
    }

    @Override
    public String toString() {
        return "HashLiteral{" +
            "values=" + values +
            '}';
    }
}
