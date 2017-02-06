package de.weltraumschaf.caythe.intermediate.experimental.ast;

import de.weltraumschaf.caythe.intermediate.experimental.AstVisitor;
import de.weltraumschaf.caythe.intermediate.experimental.Position;

import java.util.List;
import java.util.Objects;

public final class FunctionLiteral extends BaseNode {
    private final List<Identifier> arguments;
    private final List<AstNode> body;

    public FunctionLiteral(final List<Identifier> arguments, final List<AstNode> body, final Position sourcePosition) {
        super(sourcePosition);
        this.arguments = arguments;
        this.body = body;
    }

    public List<Identifier> getArguments() {
        return arguments;
    }

    public List<AstNode> getBody() {
        return body;
    }

    @Override
    public <R> R accept(final AstVisitor<? extends R> visitor) {
        return visitor.visit(this);
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
