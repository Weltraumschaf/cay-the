package de.weltraumschaf.caythe.intermediate.ast;

import de.weltraumschaf.caythe.intermediate.AstVisitor;
import de.weltraumschaf.caythe.intermediate.Position;
import de.weltraumschaf.commons.validate.Validate;

import java.util.Objects;

/**
 *
 */
public final class MethodParameter extends BaseNode {
    private final Identifier name;
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
            && Objects.equals(sourcePosition(), that.sourcePosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value, sourcePosition());
    }

    @Override
    public String toString() {
        return "MethodParameter{" +
            "name=" + name +
            ", value=" + value +
            ", sourcePosition=" + sourcePosition() +
            '}';
    }
}
