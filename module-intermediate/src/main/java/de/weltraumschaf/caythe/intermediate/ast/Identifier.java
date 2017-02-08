package de.weltraumschaf.caythe.intermediate.ast;

import de.weltraumschaf.caythe.intermediate.AstVisitor;
import de.weltraumschaf.caythe.intermediate.Position;

import java.util.Objects;

public final class Identifier extends BaseNode {
    private final String name;

    public Identifier(final String name, final Position sourcePosition) {
        super(sourcePosition);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public <R> R accept(final AstVisitor<? extends R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Identifier)) {
            return false;
        }

        final Identifier that = (Identifier) o;
        return Objects.equals(name, that.name)
            && Objects.equals(sourcePosition(), that.sourcePosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, sourcePosition());
    }

    @Override
    public String toString() {
        return "Identifier{" +
            "name='" + name + '\'' +
            ", sourcePosition=" + sourcePosition() +
            '}';
    }

}
