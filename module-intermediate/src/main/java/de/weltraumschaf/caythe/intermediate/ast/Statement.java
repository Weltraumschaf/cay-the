package de.weltraumschaf.caythe.intermediate.ast;

import de.weltraumschaf.caythe.intermediate.AstVisitor;
import de.weltraumschaf.caythe.intermediate.Position;

import java.util.Collection;
import java.util.Objects;

public final class Statement extends BaseNode {

    private final Collection<AstNode> statements;

    public Statement(final Collection<AstNode> children, final Position sourcePosition) {
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
        if (!(o instanceof Statement)) {
            return false;
        }

        final Statement statement = (Statement) o;
        return Objects.equals(this.statements, statement.statements)
            && Objects.equals(sourcePosition(), statement.sourcePosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(statements, sourcePosition());
    }

    @Override
    public String toString() {
        return "Statement{" +
            "statements=" + statements +
            ", sourcePosition=" + sourcePosition() +
            '}';
    }

    public static boolean isEmpty(final AstNode node) {
        return node instanceof Statement && ((Statement) node).statements.isEmpty();
    }

    @Override
    public String serialize() {
        return serialize("statement", serialize(statements));
    }
}
