package de.weltraumschaf.caythe.intermediate.model.ast;

import de.weltraumschaf.caythe.intermediate.AstVisitor;
import de.weltraumschaf.caythe.intermediate.equivalence.Notification;
import de.weltraumschaf.caythe.intermediate.Position;
import lombok.Getter;

import java.util.Objects;

/**
 * Represents a real literal.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public final class RealLiteral extends BaseNode {
    @Getter
    private final double value;

    public RealLiteral(final double value, final Position sourcePosition) {
        super(sourcePosition);
        this.value = value;
    }

    @Override
    public <R> R accept(final AstVisitor<? extends R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String serialize() {
        return serialize(String.valueOf(value));
    }

    @Override
    public String getNodeName() {
        return "real";
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof RealLiteral)) {
            return false;
        }

        final RealLiteral that = (RealLiteral) o;
        return Double.compare(that.value, value) == 0
            && Objects.equals(getSourcePosition(), that.getSourcePosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, getSourcePosition());
    }

    @Override
    public String toString() {
        return "RealLiteral{" +
            "value=" + value +
            ", getSourcePosition=" + getSourcePosition() +
            '}';
    }

    @Override
    public void probeEquivalence(final AstNode other, final Notification result) {
        throw new UnsupportedOperationException("Not implemented yet!");
    }
}
