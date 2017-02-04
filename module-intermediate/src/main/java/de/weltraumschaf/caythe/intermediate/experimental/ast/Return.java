package de.weltraumschaf.caythe.intermediate.experimental.ast;

import de.weltraumschaf.caythe.intermediate.experimental.Visitor;

import java.util.Objects;

public final class Return implements AstNode {
    private final AstNode result;

    public Return(final AstNode result) {
        super();
        this.result = result;
    }

    @Override
    public void accept(Visitor visitor) {

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
