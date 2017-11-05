package de.weltraumschaf.caythe.intermediate.ast;

import de.weltraumschaf.caythe.intermediate.AstVisitor;
import de.weltraumschaf.caythe.intermediate.Position;

import java.util.Collection;
import java.util.Objects;

public final class Statement extends BaseNode {

    private final AstNode child;

    public Statement(final AstNode child, final Position sourcePosition) {
        super(sourcePosition);
        this.child = child;
    }

    public AstNode getChild() {
        return child;
    }

    @Override
    public <R> R accept(final AstVisitor<? extends R> visitor) {
        return visitor.visit(this);
    }

    public static boolean isEmpty(final AstNode node) {
        return node instanceof Statement && NoOperation.isNoop(((Statement) node).child);
    }

    @Override
    public String serialize() {
        return serialize("statement", serialize(child));
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Statement)) {
            return false;
        }

        final Statement statement = (Statement) o;
        return Objects.equals(this.child, statement.child)
            && Objects.equals(sourcePosition(), statement.sourcePosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(child, sourcePosition());
    }

    @Override
    public String toString() {
        return "Statement{" +
            "child=" + child +
            ", sourcePosition=" + sourcePosition() +
            '}';
    }
}
