package de.weltraumschaf.caythe.intermediate.ast;

import de.weltraumschaf.caythe.intermediate.AstVisitor;
import de.weltraumschaf.caythe.intermediate.Position;

import java.util.Objects;

public final class Const extends BaseNode {
    private final BinaryOperation assignment;

    public Const(final BinaryOperation assignment, final Position sourcePosition) {
        super(sourcePosition);
        this.assignment = assignment;
    }

    public BinaryOperation getAssignment() {
        return assignment;
    }

    @Override
    public <R> R accept(final AstVisitor<? extends R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Const)) {
            return false;
        }

        final Const that = (Const) o;
        return Objects.equals(assignment, that.assignment)
            && Objects.equals(sourcePosition(), that.sourcePosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(assignment, sourcePosition());
    }

    @Override
    public String toString() {
        return "Const{" +
            "assignment=" + assignment +
            ", sourcePosition=" + sourcePosition() +
            '}';
    }

    @Override
    public String serialize() {
        return serialize("const", serialize(assignment));
    }
}
