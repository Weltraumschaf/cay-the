package de.weltraumschaf.caythe.intermediate.model.ast;

import de.weltraumschaf.caythe.intermediate.equivalence.Notification;
import de.weltraumschaf.caythe.intermediate.model.Position;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Represents a hash literal.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
@ToString(callSuper = true)
public final class HashLiteral extends BaseNode {
    @Getter
    private final Map<AstNode, AstNode> values;

    public HashLiteral(final Map<AstNode, AstNode> values, final Position sourcePosition) {
        super(sourcePosition);
        this.values = values;
    }

    @Override
    public <R> R accept(final AstVisitor<? extends R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String serialize() {
        final String serializedValues = values.entrySet()
            .stream()
            .map(e -> "("+ serialize(e.getKey(), e.getValue()) + ")")
            .collect(Collectors.joining(" "));
        return serialize(serializedValues);
    }

    @Override
    public String getNodeName() {
        return "hash";
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof HashLiteral)) {
            return false;
        }

        final HashLiteral that = (HashLiteral) o;
        return Objects.equals(values, that.values)
            && Objects.equals(getSourcePosition(), that.getSourcePosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(values, getSourcePosition());
    }

    @Override
    public void probeEquivalence(final AstNode other, final Notification result) {
        throw new UnsupportedOperationException("Not implemented yet!");
    }
}
