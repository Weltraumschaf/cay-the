package de.weltraumschaf.caythe.intermediate.experimental.ast;

import de.weltraumschaf.caythe.intermediate.experimental.Visitor;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public final class Statement implements AstNode {
    public static final Statement EMPTY = new Statement(Collections.emptyList());

    private final Collection<AstNode> children;

    public Statement(final Collection<AstNode> children) {
        super();
        this.children = children;
    }

    public Collection<AstNode> getChildren() {
        return children;
    }

    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Statement)) {
            return false;
        }

        final Statement statement = (Statement) o;
        return Objects.equals(children, statement.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(children);
    }

    @Override
    public String toString() {
        return "Statement{" +
            "children=" + children +
            '}';
    }
}
