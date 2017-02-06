package de.weltraumschaf.caythe.intermediate.experimental.ast;

import de.weltraumschaf.caythe.intermediate.experimental.AstVisitor;
import de.weltraumschaf.caythe.intermediate.experimental.Position;

import java.util.Map;
import java.util.Objects;

public final class HashLiteral extends BaseNode {
    private final Map<AstNode, AstNode> values;

    public HashLiteral(final Map<AstNode, AstNode> values, final Position sourcePosition) {
        super(sourcePosition);
        this.values = values;
    }

    public Map<AstNode, AstNode> getValues() {
        return values;
    }

    @Override
    public <R> R accept(final AstVisitor<? extends R> visitor) {
        return visitor.visit(this);
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
