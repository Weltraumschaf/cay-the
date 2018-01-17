package de.weltraumschaf.caythe.intermediate.model.ast;

import de.weltraumschaf.caythe.intermediate.AstVisitor;
import de.weltraumschaf.caythe.intermediate.equivalence.Notification;
import de.weltraumschaf.caythe.intermediate.Position;

import java.util.Objects;

/**
 * Represents a loop break.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public final class Break extends BaseNode  {

    public Break(final Position sourcePosition) {
        super(sourcePosition);
    }

    @Override
    public <R> R accept(final AstVisitor<? extends R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String getNodeName() {
        return "break";
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Break)) {
            return false;
        }

        final Break that = (Break) o;
        return Objects.equals(getSourcePosition(), that.getSourcePosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSourcePosition());
    }

    @Override
    public String toString() {
        return "Break{" +
            "getSourcePosition=" + getSourcePosition() +
            "}";
    }

    @Override
    public void probeEquivalence(final AstNode other, final Notification result) {
        // TODO Write tests for this method.
        probeEquivalentType(Break.class, other, result);
    }
}
