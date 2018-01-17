package de.weltraumschaf.caythe.intermediate.model.ast;

import de.weltraumschaf.caythe.intermediate.AstVisitor;
import de.weltraumschaf.caythe.intermediate.equivalence.Notification;
import de.weltraumschaf.caythe.intermediate.Position;
import de.weltraumschaf.commons.validate.Validate;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

/**
 * Represents an array literal.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public final class ArrayLiteral extends BaseNode  {
    @Getter
    private final List<AstNode> values;

    public ArrayLiteral(final List<AstNode> values, final Position sourcePosition) {
        super(sourcePosition);
        this.values = Validate.notNull(values, "values");
    }

    @Override
    public <R> R accept(final AstVisitor<? extends R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String serialize() {
        return serialize(serialize(values));
    }

    @Override
    public String getNodeName() {
        return "array";
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof ArrayLiteral)) {
            return false;
        }

        final ArrayLiteral that = (ArrayLiteral) o;
        return Objects.equals(values, that.values)
            && Objects.equals(getSourcePosition(), that.getSourcePosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(values, getSourcePosition());
    }

    @Override
    public String toString() {
        return "ArrayLiteral{" +
            "values=" + values +
            "getSourcePosition=" + getSourcePosition() +
            '}';
    }

    @Override
    public void probeEquivalence(final AstNode other, final Notification result) {
        // TODO Write tests for this method.
        probeEquivalenceFor(ArrayLiteral.class, other, result, otherArray -> {
            probeEquivalences(AstNode.class, values, otherArray.values, result);
        });
    }
}
