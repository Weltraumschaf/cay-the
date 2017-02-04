package de.weltraumschaf.caythe.intermediate.experimental.ast;

import de.weltraumschaf.caythe.intermediate.experimental.Visitor;

import java.util.List;
import java.util.Objects;

public final class FunctionLiteral implements AstNode {
    private final List<AstNode> arguments;
    private final List<AstNode> body;

    public FunctionLiteral(final List<AstNode> arguments, final List<AstNode> body) {
        super();
        this.arguments = arguments;
        this.body = body;
    }

    @Override
    public void accept(Visitor visitor) {

    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof FunctionLiteral)) {
            return false;
        }

        final FunctionLiteral that = (FunctionLiteral) o;
        return Objects.equals(arguments, that.arguments) &&
            Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(arguments, body);
    }

    @Override
    public String toString() {
        return "FunctionLiteral{" +
            "arguments=" + arguments +
            ", body=" + body +
            '}';
    }
}
