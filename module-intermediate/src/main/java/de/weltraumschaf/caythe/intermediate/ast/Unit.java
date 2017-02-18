package de.weltraumschaf.caythe.intermediate.ast;

import de.weltraumschaf.caythe.intermediate.AstVisitor;
import de.weltraumschaf.caythe.intermediate.Position;

import java.util.Collection;
import java.util.Objects;

public final class Unit extends BaseNode {
    private final Collection<AstNode> statements;

    public Unit(final Collection<AstNode> statements, final Position sourcePosition) {
        super(sourcePosition);
        this.statements = statements;
    }

    public Collection<AstNode> getStatements() {
        return statements;
    }

    @Override
    public <R> R accept(final AstVisitor<? extends R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Unit)) {
            return false;
        }

        final Unit unit = (Unit) o;
        return Objects.equals(statements, unit.statements)
            && Objects.equals(sourcePosition(), unit.sourcePosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(statements, sourcePosition());
    }

    @Override
    public String toString() {
        return "Unit{" +
            "statements=" + statements +
            ", sourcePosition=" + sourcePosition() +
            '}';
    }

    @Override
    public String serialize() {
        return serialize("unit", serialize(statements));
    }
}
