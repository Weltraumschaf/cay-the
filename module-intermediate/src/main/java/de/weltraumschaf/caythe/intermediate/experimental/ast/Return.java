package de.weltraumschaf.caythe.intermediate.experimental.ast;

import de.weltraumschaf.caythe.intermediate.experimental.AstVisitor;
import de.weltraumschaf.caythe.intermediate.experimental.Position;

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

        final Return aReturn = (Return) o;
        return Objects.equals(result, aReturn.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(result);
    }

    @Override
    public String toString() {
        return "Return{" +
            "result=" + result +
            '}';
    }
}
