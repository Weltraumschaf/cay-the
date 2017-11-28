package de.weltraumschaf.caythe.intermediate.ast;

import de.weltraumschaf.caythe.intermediate.AstVisitor;
import de.weltraumschaf.caythe.intermediate.Position;
import de.weltraumschaf.commons.validate.Validate;

import java.util.Objects;

/**
 * Represents a string literal.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public final class StringLiteral extends BaseNode {
    private final String value;

    public StringLiteral(final String value, final Position sourcePosition) {
        super(sourcePosition);
        this.value = Validate.notNull(value, "value");
    }

    @Override
    public <R> R accept(final AstVisitor<? extends R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String serialize() {
        return serialize(value);
    }

    @Override
    public String getNodeName() {
        return "string";
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof StringLiteral)) {
            return false;
        }

        final StringLiteral that = (StringLiteral) o;
        return Objects.equals(value, that.value)
            && Objects.equals(sourcePosition(), that.sourcePosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, sourcePosition());
    }

    @Override
    public String toString() {
        return "StringLiteral{" +
            "value='" + value + '\'' +
            ", sourcePosition=" + sourcePosition() +
            '}';
    }

    public String getValue() {
        return value;
    }

}
