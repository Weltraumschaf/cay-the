package de.weltraumschaf.caythe.intermediate.experimental.ast;

import de.weltraumschaf.caythe.intermediate.experimental.AstVisitor;

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
    public <R> R accept(final AstVisitor<? extends R> visitor) {
        return visitor.visit(this);
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
