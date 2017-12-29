package de.weltraumschaf.caythe.intermediate.ast;

import de.weltraumschaf.caythe.intermediate.AstVisitor;
import de.weltraumschaf.caythe.intermediate.Notification;
import de.weltraumschaf.caythe.intermediate.Position;

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
        return Objects.equals(sourcePosition(), that.sourcePosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourcePosition());
    }

    @Override
    public String toString() {
        return "Continue{" +
            "sourcePosition=" + sourcePosition() +
            "}";
    }

    @Override
    public void probeEquivalence(final AstNode other, final Notification result) {
        // TODO Write tests for this method.
        probeEquivalentType(Continue.class, other, result);
    }
}
