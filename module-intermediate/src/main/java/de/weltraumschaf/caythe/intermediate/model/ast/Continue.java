package de.weltraumschaf.caythe.intermediate.model.ast;

import de.weltraumschaf.caythe.intermediate.equivalence.Notification;
import de.weltraumschaf.caythe.intermediate.model.Position;

import java.util.Objects;

/**
 * Represents a loop continue.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public final class Continue extends BaseNode  {

    public Continue(final Position sourcePosition) {
        super(sourcePosition);
    }

    @Override
    public <R> R accept(final AstVisitor<? extends R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String getNodeName() {
        return "continue";
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Continue)) {
            return false;
        }

        final Continue that = (Continue) o;
        return Objects.equals(getSourcePosition(), that.getSourcePosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSourcePosition());
    }

    @Override
    public String toString() {
        return "Continue{" +
            "getSourcePosition=" + getSourcePosition() +
            "}";
    }

    @Override
    public void probeEquivalence(final AstNode other, final Notification result) {
        // TODO Write tests for this method.
        probeEquivalentType(Continue.class, other, result);
    }
}
