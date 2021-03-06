package de.weltraumschaf.caythe.intermediate.model.ast;

import de.weltraumschaf.caythe.intermediate.equivalence.Notification;
import de.weltraumschaf.caythe.intermediate.equivalence.ResultDescriber;
import de.weltraumschaf.caythe.intermediate.model.Position;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

/**
 * Represents a boolean literal.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
@ToString
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
    public void probeEquivalence(final AstNode other, final Notification result) {
        // TODO Write tests for this method.
        probeEquivalenceFor(BooleanLiteral.class, other, result, otherBooleanLiteral -> {
            final ResultDescriber describer = new ResultDescriber();

            if (isNotEqual(value, otherBooleanLiteral.value)) {
                result.error(
                    difference(
                        "Value",
                        describer.difference(this, other)),
                    value, otherBooleanLiteral.value
                );
            }
        });
    }

}
