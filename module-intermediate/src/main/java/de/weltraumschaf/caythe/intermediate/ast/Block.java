package de.weltraumschaf.caythe.intermediate.ast;

import de.weltraumschaf.caythe.intermediate.AstVisitor;
import de.weltraumschaf.caythe.intermediate.Position;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

/**
 * A block is a set of statements.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public final class Block extends BaseNode {
    private final Collection<AstNode> children;

    public Block(final Collection<AstNode> children, final Position sourcePosition) {
        super(sourcePosition);
        this.children = children;
    }

    public Collection<AstNode> getStatements() {
        return Collections.unmodifiableCollection(children);
    }

    @Override
    public <R> R accept(final AstVisitor<? extends R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String serialize() {
        return serialize(getNodeName(), serialize(children));
    }

    @Override
    public String getNodeName() {
        return "block";
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Block)) {
            return false;
        }

        final Block that = (Block) o;
        return Objects.equals(children, that.children)
            && Objects.equals(sourcePosition(), that.sourcePosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(children, sourcePosition());
    }

    @Override
    public String toString() {
        return "Block{" +
            "children=" + children +
            '}';
    }

}
