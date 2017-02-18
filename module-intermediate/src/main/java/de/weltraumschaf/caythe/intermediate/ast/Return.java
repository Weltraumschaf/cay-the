package de.weltraumschaf.caythe.intermediate.ast;

import de.weltraumschaf.caythe.intermediate.AstVisitor;
import de.weltraumschaf.caythe.intermediate.Position;

import java.util.Objects;

public final class Return extends BaseNode {
    private final AstNode result;

    public Return(final AstNode result, final Position sourcePosition) {
        super(sourcePosition);
        this.result = result;
    }

    public AstNode getResult() {
        return result;
    }

    @Override
    public <R> R accept(final AstVisitor<? extends R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Return)) {
            return false;
        }

        final Return that = (Return) o;
        return Objects.equals(result, that.result)
            && Objects.equals(sourcePosition(), that.sourcePosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(result, sourcePosition());
    }

    @Override
    public String toString() {
        return "Return{" +
            "result=" + result +
            "sourcePosition=" + sourcePosition() +
            '}';
    }

    @Override
    public String serialize() {
        return serialize("return", serialize(result));
    }
}
