package de.weltraumschaf.caythe.intermediate.model.ast;

import de.weltraumschaf.caythe.intermediate.equivalence.Notification;
import de.weltraumschaf.caythe.intermediate.model.Position;
import de.weltraumschaf.commons.validate.Validate;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

/**
 * Represents a return statement.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
@ToString(callSuper = true)
public final class Return extends BaseNode {
    @Getter
    private final AstNode value;

    public Return(final AstNode result, final Position sourcePosition) {
        super(sourcePosition);
        this.value = Validate.notNull(result, "value");
    }

    @Override
    public <R> R accept(final AstVisitor<? extends R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String serialize() {
        return serialize(serialize(value));
    }

    @Override
    public String getNodeName() {
        return "return";
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Return)) {
            return false;
        }

        final Return that = (Return) o;
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
        probeEquivalenceFor(Return.class, other, result, otherReturn -> {
            if (isNotEqual(result, otherReturn.value)) {
                result.error(
                    difference(
                        "Value",
                        "This has value %s but other has value %s"),
                    value, otherReturn.value
                );
            }
        });
    }
}
