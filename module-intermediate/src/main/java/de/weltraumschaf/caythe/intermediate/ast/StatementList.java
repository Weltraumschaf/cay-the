package de.weltraumschaf.caythe.intermediate.ast;

import de.weltraumschaf.caythe.intermediate.AstVisitor;
import de.weltraumschaf.caythe.intermediate.Position;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

/**
 *
 */
public final class StatementList extends BaseNode {
    private final Collection<AstNode> children;

    public StatementList(final Collection<AstNode> children, final Position sourcePosition) {
        super(sourcePosition);
        this.children = children;
    }

    public Collection<AstNode> getChildren() {
        return Collections.unmodifiableCollection(children);
    }

    @Override
    public <R> R accept(final AstVisitor<? extends R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String serialize() {
        return serialize("statement-list", serialize(children));
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof StatementList)) {
            return false;
        }

        final StatementList that = (StatementList) o;
        return Objects.equals(children, that.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(children);
    }

    @Override
    public String toString() {
        return "StatementList{" +
            "children=" + children +
            '}';
    }
}
