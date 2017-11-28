package de.weltraumschaf.caythe.intermediate.ast;

import de.weltraumschaf.caythe.intermediate.AstVisitor;
import de.weltraumschaf.caythe.intermediate.Position;

import java.util.Objects;

/**
 * Represents a nil literal.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public final class NilLiteral extends BaseNode {

    public NilLiteral(final Position sourcePosition) {
        super(sourcePosition);
    }

    @Override
    public <R> R accept(final AstVisitor<? extends R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String getNodeName() {
        return "nil";
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof NilLiteral)) {
            return false;
        }

        final NilLiteral that = (NilLiteral) o;
        return Objects.equals(sourcePosition(), that.sourcePosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourcePosition());
    }

    @Override
    public String toString() {
        return "NilLiteral{" +
            "sourcePosition=" + sourcePosition() +
            "}";
    }

}
