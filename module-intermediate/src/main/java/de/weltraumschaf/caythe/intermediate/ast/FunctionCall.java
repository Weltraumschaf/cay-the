package de.weltraumschaf.caythe.intermediate.ast;

import de.weltraumschaf.caythe.intermediate.AstVisitor;
import de.weltraumschaf.caythe.intermediate.Position;

import java.util.List;
import java.util.Objects;

public final class FunctionCall extends BaseNode {
    private final Identifier identifier;
    private final List<AstNode> arguments;

    public FunctionCall(final Identifier identifier, final List<AstNode> arguments, final Position sourcePosition) {
        super(sourcePosition);
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
        return Objects.equals(identifier, that.identifier)
            && Objects.equals(arguments, that.arguments)
            && Objects.equals(sourcePosition(), that.sourcePosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, arguments, sourcePosition());
    }

    @Override
    public String toString() {
        return "FunctionCall{" +
            "identifier=" + identifier +
            ", arguments=" + arguments +
            ", sourcePosition=" + sourcePosition() +
            '}';
    }

    @Override
    public String serialize() {
        return serialize("fn-call", serialize(identifier) + " " + serialize(arguments));
    }
}
