package de.weltraumschaf.caythe.intermediate.experimental.ast;

import de.weltraumschaf.caythe.intermediate.experimental.Visitor;

import java.util.Objects;

public final class Const implements AstNode {
    private final AstNode assignment;

    public Const(final AstNode assignment) {
        super();
        this.assignment = assignment;
    }

    public AstNode getAssignment() {
        return assignment;
    }

    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Const)) {
            return false;
        }

        final Const aConst = (Const) o;
        return Objects.equals(assignment, aConst.assignment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assignment);
    }

    @Override
    public String toString() {
        return "Const{" +
            "assignment=" + assignment +
            '}';
    }
}
