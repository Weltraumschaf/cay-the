package de.weltraumschaf.caythe.intermediate.ast;

import de.weltraumschaf.caythe.intermediate.AstVisitor;
import de.weltraumschaf.caythe.intermediate.Position;

import java.util.Objects;

public final class Let extends BaseNode {
    private final BinaryOperation assignment;

    public Let(final BinaryOperation assignment, final Position sourcePosition) {
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
        if (!(o instanceof Let)) {
            return false;
        }

        final Let that = (Let) o;
        return Objects.equals(assignment, that.assignment)
            && Objects.equals(sourcePosition(), that.sourcePosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(assignment, sourcePosition());
    }

    @Override
    public String toString() {
        return "Let{" +
            "assignment=" + assignment +
            ", sourcePosition=" + sourcePosition() +
            '}';
    }

    @Override
    public String serialize() {
        return serialize("let", serialize(assignment));
    }
}
