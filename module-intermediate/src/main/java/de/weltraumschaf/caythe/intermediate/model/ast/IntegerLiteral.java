package de.weltraumschaf.caythe.intermediate.model.ast;

import de.weltraumschaf.caythe.intermediate.AstVisitor;
import de.weltraumschaf.caythe.intermediate.Notification;
import de.weltraumschaf.caythe.intermediate.Position;
import lombok.Getter;

import java.util.Objects;

/**
 * Represents an integer literal.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public final class IntegerLiteral extends BaseNode {
    @Getter
    private final long value;

    public IntegerLiteral(final long value, final Position sourcePosition) {
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
        return "integer";
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof IntegerLiteral)) {
            return false;
        }

        final IntegerLiteral that = (IntegerLiteral) o;
        return value == that.value
            && Objects.equals(getSourcePosition(), that.getSourcePosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, getSourcePosition());
    }

    @Override
    public String toString() {
        return "IntegerLiteral{" +
            "value=" + value +
            ", getSourcePosition=" + getSourcePosition() +
            '}';
    }

    @Override
    public void probeEquivalence(final AstNode other, final Notification result) {
        // TODO Write tests for this method.
        probeEquivalenceFor(IntegerLiteral.class, other, result, otherIntegerLiteral -> {
            if (isNotEqual(value, otherIntegerLiteral.value)) {
                result.error(
                    difference(
                        "Value",
                        "This has value %d but other has value %d"),
                    value, otherIntegerLiteral.value
                );
            }
        });
    }
}
