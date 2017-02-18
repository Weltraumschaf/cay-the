package de.weltraumschaf.caythe.intermediate.ast;

import de.weltraumschaf.caythe.intermediate.AstVisitor;
import de.weltraumschaf.caythe.intermediate.Position;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
        return Objects.equals(values, that.values)
            && Objects.equals(sourcePosition(), that.sourcePosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(values, sourcePosition());
    }

    @Override
    public String toString() {
        return "HashLiteral{" +
            "values=" + values +
            ", sourcePosition=" + sourcePosition() +
            '}';
    }

    @Override
    public String serialize() {
        final String serializedValues = values.entrySet()
            .stream()
            .map(e -> String.format("(%s)", serialize(e.getKey(), e.getValue())))
            .collect(Collectors.joining(" "));
        return serialize("hash", serializedValues);
    }
}
