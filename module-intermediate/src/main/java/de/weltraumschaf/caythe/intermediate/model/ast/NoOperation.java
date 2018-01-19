package de.weltraumschaf.caythe.intermediate.model.ast;

import de.weltraumschaf.caythe.intermediate.equivalence.Notification;
import de.weltraumschaf.caythe.intermediate.model.Position;
import lombok.ToString;

import java.util.Objects;

/**
 * Represents a no operation.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
@ToString
public  final class NoOperation extends BaseNode  {

    @Deprecated
    public NoOperation() {
        this(Position.UNKNOWN);
    }

    public NoOperation(final Position sourcePosition) {
        super(sourcePosition);
    }

    @Override
    public <R> R accept(final AstVisitor<? extends R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String getNodeName() {
        return "noop";
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof NoOperation)) {
            return false;
        }

        final NoOperation that = (NoOperation) o;
        return Objects.equals(getSourcePosition(), that.getSourcePosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSourcePosition());
    }

    public static boolean isNoop(final AstNode node) {
        return node instanceof NoOperation;
    }

    @Override
    public void probeEquivalence(final AstNode other, final Notification result) {
        // TODO Write tests for this method.
        probeEquivalentType(NoOperation.class, other, result);
    }
}
