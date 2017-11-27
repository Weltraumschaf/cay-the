package de.weltraumschaf.caythe.intermediate.ast;

import de.weltraumschaf.caythe.intermediate.AstVisitor;
import de.weltraumschaf.caythe.intermediate.Position;

import java.util.Objects;

public final class BooleanLiteral extends BaseNode {
    public static final BooleanLiteral TRUE = new BooleanLiteral(true, Position.UNKNOWN);
    public static final BooleanLiteral FALSE = new BooleanLiteral(false, Position.UNKNOWN);
    private final boolean value;

    public BooleanLiteral(final boolean value, final Position sourcePosition) {
        super(sourcePosition);
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public <R> R accept(final AstVisitor<? extends R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String serialize() {
        return serialize(getNodeName(), String.valueOf(value));
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
        return value == that.value
            && Objects.equals(sourcePosition(), that.sourcePosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, sourcePosition());
    }

    @Override
    public String toString() {
        return "BooleanLiteral{" +
            "value=" + value +
            ", sourcePosition=" + sourcePosition() +
            '}';
    }

}
