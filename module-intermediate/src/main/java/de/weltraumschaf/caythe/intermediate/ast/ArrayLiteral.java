package de.weltraumschaf.caythe.intermediate.ast;

import de.weltraumschaf.caythe.intermediate.AstVisitor;
import de.weltraumschaf.caythe.intermediate.Position;
import de.weltraumschaf.commons.validate.Validate;

import java.util.List;
import java.util.Objects;

/**
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public final class ArrayLiteral extends BaseNode  {
    private final List<AstNode> values;

    public ArrayLiteral(final List<AstNode> values, final Position sourcePosition) {
        super(sourcePosition);
        this.values = Validate.notNull(values, "values");
    }

    public List<AstNode> getValues() {
        return values;
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
            && Objects.equals(sourcePosition(), that.sourcePosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(values, sourcePosition());
    }

    @Override
    public String toString() {
        return "ArrayLiteral{" +
            "values=" + values +
            "sourcePosition=" + sourcePosition() +
            '}';
    }

}
