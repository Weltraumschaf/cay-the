package de.weltraumschaf.caythe.intermediate.experimental.ast;

import de.weltraumschaf.caythe.intermediate.experimental.Visitor;

import java.util.Objects;

public final class Let implements AstNode {
    private final AstNode assignment;

    public Let(final AstNode assignment) {
        super();
        this.assignment = assignment;
    }

    @Override
    public void accept(Visitor visitor) {

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
