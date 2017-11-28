package de.weltraumschaf.caythe.intermediate.ast;

import de.weltraumschaf.caythe.intermediate.AstVisitor;
import de.weltraumschaf.caythe.intermediate.Position;

import java.util.Objects;

/**
 * Represents an integer literal.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public final class IntegerLiteral extends BaseNode {
    private final long value;

    public IntegerLiteral(final long value, final Position sourcePosition) {
        super(sourcePosition);
        this.value = value;
    }

    public long getValue() {
        return value;
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
            && Objects.equals(sourcePosition(), that.sourcePosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, sourcePosition());
    }

    @Override
    public String toString() {
        return "IntegerLiteral{" +
            "value=" + value +
            ", sourcePosition=" + sourcePosition() +
            '}';
    }

}
