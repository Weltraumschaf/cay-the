package de.weltraumschaf.caythe.intermediate.ast;

import de.weltraumschaf.caythe.intermediate.AstVisitor;
import de.weltraumschaf.caythe.intermediate.Position;

import java.util.Objects;

public  final class NoOperation extends BaseNode  {

    public NoOperation() {
        super(Position.UNKNOWN);
    }

    @Override
    public <R> R accept(final AstVisitor<? extends R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof NoOperation)) {
            return false;
        }

        final NoOperation that = (NoOperation) o;
        return Objects.equals(sourcePosition(), that.sourcePosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourcePosition());
    }

    @Override
    public String toString() {
        return "NoOperation{" +
            "sourcePosition=" + sourcePosition() +
            "}";
    }

    public static boolean isNoop(final AstNode node) {
        return node instanceof NoOperation;
    }

}
