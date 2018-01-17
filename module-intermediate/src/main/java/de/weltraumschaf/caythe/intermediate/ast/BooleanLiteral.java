package de.weltraumschaf.caythe.intermediate.ast;

import de.weltraumschaf.caythe.intermediate.AstVisitor;
import de.weltraumschaf.caythe.intermediate.Notification;
import de.weltraumschaf.caythe.intermediate.Position;
import lombok.Getter;

import java.util.Objects;

/**
 * Represents a boolean literal.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public final class BooleanLiteral extends BaseNode {
    public static final BooleanLiteral TRUE = new BooleanLiteral(true, Position.UNKNOWN);
    public static final BooleanLiteral FALSE = new BooleanLiteral(false, Position.UNKNOWN);
    @Getter
    private final Boolean value;

    public BooleanLiteral(final boolean value, final Position sourcePosition) {
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
        return "boolean";
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof BooleanLiteral)) {
            return false;
        }

        final BooleanLiteral that = (BooleanLiteral) o;
        return Objects.equals(value, that.value)
            && Objects.equals(getSourcePosition(), that.getSourcePosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, getSourcePosition());
    }

    @Override
    public String toString() {
        return "BooleanLiteral{" +
            "value=" + value +
            ", getSourcePosition=" + getSourcePosition() +
            '}';
    }

    @Override
    public void probeEquivalence(final AstNode other, final Notification result) {
        // TODO Write tests for this method.
        probeEquivalenceFor(BooleanLiteral.class, other, result, otherBooleanLiteral -> {
            if (isNotEqual(value, otherBooleanLiteral.value)) {
                result.error(
                    difference(
                        "Value",
                        "This has value %s but other has value %s"),
                    value, otherBooleanLiteral.value
                );
            }
        });
    }

}
