package de.weltraumschaf.caythe.intermediate.ast;

import de.weltraumschaf.caythe.intermediate.AstVisitor;
import de.weltraumschaf.caythe.intermediate.Position;

import java.util.Objects;

public final class Subscript extends BaseNode {
    private final AstNode identifier;
    private final AstNode index;

    public Subscript(final AstNode identifier, final AstNode index, final Position sourcePosition) {
        super(sourcePosition);
        this.identifier = identifier;
        this.index = index;
    }

    public AstNode getIdentifier() {
        return identifier;
    }

    public AstNode getIndex() {
        return index;
    }

    @Override
    public <R> R accept(final AstVisitor<? extends R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Subscript)) {
            return false;
        }

        final Subscript that = (Subscript) o;
        return Objects.equals(identifier, that.identifier)
            && Objects.equals(index, that.index)
            && Objects.equals(sourcePosition(), that.sourcePosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, index, sourcePosition());
    }

    @Override
    public String toString() {
        return "Subscript{" +
            "identifier=" + identifier +
            ", index=" + index +
            ", sourcePosition=" + sourcePosition() +
            '}';
    }

    @Override
    public String serialize() {
        return serialize("[]", serialize(identifier, index));
    }
}
