package de.weltraumschaf.caythe.intermediate.model.ast;

import de.weltraumschaf.caythe.intermediate.equivalence.Notification;
import de.weltraumschaf.caythe.intermediate.equivalence.ResultDescriber;
import de.weltraumschaf.caythe.intermediate.model.Position;
import de.weltraumschaf.commons.validate.Validate;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

/**
 * Represents a string literal.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
@ToString
public final class StringLiteral extends BaseNode {
    @Getter
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
            && Objects.equals(getSourcePosition(), that.getSourcePosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, getSourcePosition());
    }

    @Override
    public void probeEquivalence(final AstNode other, final Notification result) {
        // TODO Write tests for this method.
        probeEquivalenceFor(StringLiteral.class, other, result, otherStringLiteral -> {
            final ResultDescriber describer = new ResultDescriber();

            if (isNotEqual(value, otherStringLiteral.value)) {
                result.error(
                    difference(
                        "Value",
                        describer.difference(value, otherStringLiteral.value)));
            }
        });
    }
}
