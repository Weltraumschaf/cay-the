package de.weltraumschaf.caythe.intermediate.experimental.ast;

import de.weltraumschaf.caythe.intermediate.experimental.AstVisitor;

import java.util.List;
import java.util.Objects;

public final class FunctionCall implements AstNode {
    private final Identifier identifier;
    private final List<AstNode> arguments;

    public FunctionCall(final Identifier identifier, final List<AstNode> arguments) {
        super();
        this.identifier = identifier;
        this.arguments = arguments;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public List<AstNode> getArguments() {
        return arguments;
    }

    @Override
    public <R> R accept(final AstVisitor<? extends R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof FunctionCall)) {
            return false;
        }

        final FunctionCall that = (FunctionCall) o;
        return Objects.equals(identifier, that.identifier) &&
            Objects.equals(arguments, that.arguments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, arguments);
    }

    @Override
    public String toString() {
        return "FunctionCall{" +
            "identifier=" + identifier +
            ", arguments=" + arguments +
            '}';
    }
}
