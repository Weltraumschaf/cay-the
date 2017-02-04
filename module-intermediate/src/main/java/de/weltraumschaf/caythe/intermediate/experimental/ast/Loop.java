package de.weltraumschaf.caythe.intermediate.experimental.ast;

import de.weltraumschaf.caythe.intermediate.experimental.Visitor;

import java.util.Collection;
import java.util.Objects;

public final class Loop implements AstNode {
    private final Collection<AstNode> statements;

    public Loop(final Collection<AstNode> statements) {
        super();
        this.statements = statements;
    }

    public Collection<AstNode> getStatements() {
        return statements;
    }

    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Loop)) {
            return false;
        }

        final Loop loop = (Loop) o;
        return Objects.equals(statements, loop.statements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statements);
    }

    @Override
    public String toString() {
        return "Loop{" +
            "statements=" + statements +
            '}';
    }
}
