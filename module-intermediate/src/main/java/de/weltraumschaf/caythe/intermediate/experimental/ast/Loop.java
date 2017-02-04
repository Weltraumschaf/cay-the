package de.weltraumschaf.caythe.intermediate.experimental.ast;

import de.weltraumschaf.caythe.intermediate.experimental.Visitor;

import java.util.Collection;
import java.util.Objects;

public final class Loop implements AstNode {
    private final Collection<AstNode> children;

    public Loop(final Collection<AstNode> children) {
        super();
        this.children = children;
    }

    @Override
    public void accept(Visitor visitor) {

    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Loop)) {
            return false;
        }

        final Loop loop = (Loop) o;
        return Objects.equals(children, loop.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(children);
    }

    @Override
    public String toString() {
        return "Loop{" +
            "children=" + children +
            '}';
    }
}
