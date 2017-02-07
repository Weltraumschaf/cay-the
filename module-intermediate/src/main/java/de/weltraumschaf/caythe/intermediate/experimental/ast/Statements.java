package de.weltraumschaf.caythe.intermediate.experimental.ast;

import de.weltraumschaf.caythe.intermediate.experimental.AstVisitor;
import de.weltraumschaf.caythe.intermediate.experimental.Position;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public final class Statements extends BaseNode {
    public static final Statements EMPTY = new Statements(Collections.emptyList(), Position.UNKNOWN);

    private final Collection<AstNode> statements;

    public Statements(final Collection<AstNode> children, final Position sourcePosition) {
        super(sourcePosition);
        this.statements = children;
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
        if (!(o instanceof Statements)) {
            return false;
        }

        final Statements statements = (Statements) o;
        return Objects.equals(this.statements, statements.statements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statements);
    }

    @Override
    public String toString() {
        return "Statements{" +
            "statements=" + statements +
            '}';
    }
}