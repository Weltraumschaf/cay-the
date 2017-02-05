package de.weltraumschaf.caythe.intermediate.experimental.ast;

import de.weltraumschaf.caythe.intermediate.experimental.AstVisitor;

import java.util.Objects;

public final class Let implements AstNode {
    private final BinaryOperation assignment;

    public Let(final BinaryOperation assignment) {
        super();
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

        final Let let = (Let) o;
        return Objects.equals(assignment, let.assignment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assignment);
    }

    @Override
    public String toString() {
        return "Let{" +
            "assignment=" + assignment +
            '}';
    }
}
