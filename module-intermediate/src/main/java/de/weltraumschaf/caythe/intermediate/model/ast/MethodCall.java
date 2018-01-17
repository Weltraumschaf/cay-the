package de.weltraumschaf.caythe.intermediate.model.ast;

import de.weltraumschaf.caythe.intermediate.AstVisitor;
import de.weltraumschaf.caythe.intermediate.Notification;
import de.weltraumschaf.caythe.intermediate.Position;
import de.weltraumschaf.commons.validate.Validate;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

/**
 * Represents a method call.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public final class MethodCall extends BaseNode {
    @Getter
    private final Identifier identifier;
    @Getter
    private final List<AstNode> arguments;

    public MethodCall(final Identifier identifier, final List<AstNode> arguments, final Position sourcePosition) {
        super(sourcePosition);
        this.identifier = Validate.notNull(identifier, "identifier");
        this.arguments = Validate.notNull(arguments, "arguments");
    }

    @Override
    public <R> R accept(final AstVisitor<? extends R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String serialize() {
        return serialize(serialize(identifier) + " " + serialize(arguments));
    }

    @Override
    public String getNodeName() {
        return "fn-call";
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof MethodCall)) {
            return false;
        }

        final MethodCall that = (MethodCall) o;
        return Objects.equals(identifier, that.identifier)
            && Objects.equals(arguments, that.arguments)
            && Objects.equals(getSourcePosition(), that.getSourcePosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, arguments, getSourcePosition());
    }

    @Override
    public String toString() {
        return "MethodCall{" +
            "identifier=" + identifier +
            ", arguments=" + arguments +
            ", getSourcePosition=" + getSourcePosition() +
            '}';
    }

    @Override
    public void probeEquivalence(final AstNode other, final Notification result) {
        throw new UnsupportedOperationException("Not implemented yet!");
    }
}
