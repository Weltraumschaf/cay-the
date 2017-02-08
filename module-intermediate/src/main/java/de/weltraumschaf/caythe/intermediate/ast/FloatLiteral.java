package de.weltraumschaf.caythe.intermediate.ast;

import de.weltraumschaf.caythe.intermediate.AstVisitor;
import de.weltraumschaf.caythe.intermediate.Position;

import java.util.Objects;

public final class FloatLiteral extends BaseNode {
    private final double value;

    public FloatLiteral(final double value, final Position sourcePosition) {
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
    public boolean equals(final Object o) {
        if (!(o instanceof FloatLiteral)) {
            return false;
        }

        final FloatLiteral that = (FloatLiteral) o;
        return Double.compare(that.value, value) == 0
            && Objects.equals(sourcePosition(), that.sourcePosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, sourcePosition());
    }

    @Override
    public String toString() {
        return "FloatLiteral{" +
            "value=" + value +
            ", sourcePosition=" + sourcePosition() +
            '}';
    }

}
