package de.weltraumschaf.caythe.intermediate.ast;

import de.weltraumschaf.caythe.intermediate.AstVisitor;
import de.weltraumschaf.caythe.intermediate.Position;

import java.util.Objects;

/**
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public final class RealLiteral extends BaseNode {
    private final double value;

    public RealLiteral(final double value, final Position sourcePosition) {
        super(sourcePosition);
        this.value = value;
    }

    public double getValue() {
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
        return "real";
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof RealLiteral)) {
            return false;
        }

        final RealLiteral that = (RealLiteral) o;
        return Double.compare(that.value, value) == 0
            && Objects.equals(sourcePosition(), that.sourcePosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, sourcePosition());
    }

    @Override
    public String toString() {
        return "RealLiteral{" +
            "value=" + value +
            ", sourcePosition=" + sourcePosition() +
            '}';
    }

}
