package de.weltraumschaf.caythe.intermediate.experimental.ast;

import de.weltraumschaf.caythe.intermediate.experimental.AstVisitor;
import de.weltraumschaf.caythe.intermediate.experimental.Position;

import java.util.Collection;
import java.util.Objects;

public final class Loop extends BaseNode {
    // First we implement an endless loop.
    private final AstNode condition = BooleanLiteral.TRUE;
    private final Collection<AstNode> statements;

    public Loop(final Collection<AstNode> statements, final Position sourcePosition) {
        super(sourcePosition);
        this.statements = statements;
    }

    public Collection<AstNode> statements() {
        return statements;
    }

    public AstNode condition() {
        return condition;
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
