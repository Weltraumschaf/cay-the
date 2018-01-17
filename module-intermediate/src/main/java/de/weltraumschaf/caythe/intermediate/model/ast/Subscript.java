package de.weltraumschaf.caythe.intermediate.model.ast;

import de.weltraumschaf.caythe.intermediate.AstVisitor;
import de.weltraumschaf.caythe.intermediate.equivalence.Notification;
import de.weltraumschaf.caythe.intermediate.Position;
import de.weltraumschaf.commons.validate.Validate;
import lombok.Getter;

import java.util.Objects;

/**
 * Represents a subscript operation.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public final class Subscript extends BaseNode {
    @Getter
    private final AstNode identifier;
    @Getter
    private final AstNode index;

    public Subscript(final AstNode identifier, final AstNode index, final Position sourcePosition) {
        super(sourcePosition);
        this.identifier = Validate.notNull(identifier, "identifier");
        this.index = Validate.notNull(index, "index");
    }

    @Override
    public <R> R accept(final AstVisitor<? extends R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String serialize() {
        return serialize(serialize(identifier, index));
    }

    @Override
    public String getNodeName() {
        return "[]";
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Subscript)) {
            return false;
        }

        final Subscript that = (Subscript) o;
        return Objects.equals(identifier, that.identifier)
            && Objects.equals(index, that.index)
            && Objects.equals(getSourcePosition(), that.getSourcePosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, index, getSourcePosition());
    }

    @Override
    public String toString() {
        return "Subscript{" +
            "identifier=" + identifier +
            ", index=" + index +
            ", getSourcePosition=" + getSourcePosition() +
            '}';
    }

    @Override
    public void probeEquivalence(final AstNode other, final Notification result) {
        throw new UnsupportedOperationException("Not implemented yet!");
    }
}
