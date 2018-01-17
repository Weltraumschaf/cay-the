package de.weltraumschaf.caythe.intermediate.model.ast;

import de.weltraumschaf.caythe.intermediate.AstVisitor;
import de.weltraumschaf.caythe.intermediate.Notification;
import de.weltraumschaf.caythe.intermediate.Position;
import de.weltraumschaf.caythe.intermediate.model.TypeName;
import lombok.Getter;

import java.util.Objects;

/**
 * Represents a variable declaration.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public final class Let extends BaseNode {
    @Getter
    private final TypeName type;
    @Getter
    private final BinaryOperation assignment;

    public Let(final TypeName type, final BinaryOperation assignment, final Position sourcePosition) {
        super(sourcePosition);
        this.type = type;
        this.assignment = assignment;
    }

    @Override
    public <R> R accept(final AstVisitor<? extends R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String serialize() {
        return serialize(serialize(assignment));
    }

    @Override
    public String getNodeName() {
        return "let";
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Let)) {
            return false;
        }

        final Let let = (Let) o;
        return  Objects.equals(getSourcePosition(), let.getSourcePosition()) &&
            Objects.equals(type, let.type) &&
            Objects.equals(assignment, let.assignment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSourcePosition(), type, assignment);
    }

    @Override
    public String toString() {
        return "Let{" +
            "type=" + type +
            ", assignment=" + assignment +
            ", getSourcePosition=" + getSourcePosition() +
            '}';
    }

    @Override
    public void probeEquivalence(final AstNode other, final Notification result) {
        // TODO Write tests for this method.
        probeEquivalenceFor(Let.class, other, result, otherLet -> {
            type.probeEquivalence(otherLet.type, result);
            assignment.probeEquivalence(otherLet.assignment, result);
        });
    }
}
