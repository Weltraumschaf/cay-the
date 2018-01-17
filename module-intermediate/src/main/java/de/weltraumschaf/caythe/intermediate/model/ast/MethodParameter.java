package de.weltraumschaf.caythe.intermediate.model.ast;

import de.weltraumschaf.caythe.intermediate.equivalence.Notification;
import de.weltraumschaf.caythe.intermediate.model.Position;
import de.weltraumschaf.commons.validate.Validate;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

/**
 * Represents a method parameter.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
@ToString(callSuper = true)
public final class MethodParameter extends BaseNode {
    @Getter
    private final Identifier name;
    @Getter
    private final AstNode value;

    public MethodParameter(final Identifier name, final AstNode value, final Position sourcePosition) {
        super(sourcePosition);
        this.name = Validate.notNull(name, "name");
        this.value = Validate.notNull(value, "value");
    }

    @Override
    public String serialize() {
        return serialize(serialize(name) + " " + serialize(value));
    }

    @Override
    public String getNodeName() {
        return "param";
    }

    @Override
    public <R> R accept(final AstVisitor<? extends R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof MethodParameter)) {
            return false;
        }

        final MethodParameter that = (MethodParameter) o;
        return Objects.equals(name, that.name)
            && Objects.equals(value, that.value)
            && Objects.equals(getSourcePosition(), that.getSourcePosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value, getSourcePosition());
    }

    @Override
    public void probeEquivalence(final AstNode other, final Notification result) {
        throw new UnsupportedOperationException("Not implemented yet!");
    }
}
