package de.weltraumschaf.caythe.intermediate.ast;

import de.weltraumschaf.caythe.intermediate.AstVisitor;
import de.weltraumschaf.caythe.intermediate.Position;

import java.util.Objects;

public final class NullLiteral extends BaseNode {
    public static final NullLiteral NULL = new NullLiteral();

    private NullLiteral() {
        super(Position.UNKNOWN);
    }

    @Override
    public <R> R accept(final AstVisitor<? extends R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof NullLiteral)) {
            return false;
        }

        final NullLiteral that = (NullLiteral) o;
        return Objects.equals(sourcePosition(), that.sourcePosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourcePosition());
    }

    @Override
    public String toString() {
        return "NullLiteral{" +
            "sourcePosition=" + sourcePosition() +
            "}";
    }
}
