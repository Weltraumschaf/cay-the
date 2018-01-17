package de.weltraumschaf.caythe.intermediate.model.ast;

import de.weltraumschaf.caythe.intermediate.equivalence.Notification;
import de.weltraumschaf.caythe.intermediate.model.Position;
import de.weltraumschaf.commons.validate.Validate;
import lombok.Getter;
import lombok.ToString;

import java.util.*;

/**
 * Represents a block which is a set of getStatements.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
@ToString(callSuper = true)
public final class Block extends BaseNode {
    @Getter
    private final List<AstNode> children;

    public Block(final Collection<AstNode> children, final Position sourcePosition) {
        super(sourcePosition);
        this.children = new ArrayList<>(Validate.notNull(children, "children"));
    }

    @Override
    public <R> R accept(final AstVisitor<? extends R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String serialize() {
        return serialize(serialize(children));
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
            && Objects.equals(getSourcePosition(), that.getSourcePosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(children, getSourcePosition());
    }

    @Override
    public void probeEquivalence(final AstNode other, final Notification result) {
        // TODO Write tests for this method.
        probeEquivalenceFor(Block.class, other, result, otherBlock -> {
            probeEquivalences(AstNode.class, children, otherBlock.children, result);
        });
    }
}
