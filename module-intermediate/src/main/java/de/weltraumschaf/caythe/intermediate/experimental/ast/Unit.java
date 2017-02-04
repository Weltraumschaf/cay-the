package de.weltraumschaf.caythe.intermediate.experimental.ast;

import de.weltraumschaf.caythe.intermediate.experimental.Visitor;

import java.util.Collection;
import java.util.Objects;

public final class Unit implements AstNode {
    private final Collection<AstNode> statements;

    public Unit(final Collection<AstNode> statements) {
        super();
        this.statements = statements;
    }

    @Override
    public void accept(Visitor visitor) {

    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Unit)) {
            return false;
        }

        final Unit unit = (Unit) o;
        return Objects.equals(statements, unit.statements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statements);
    }

    @Override
    public String toString() {
        return "Unit{" +
            "statements=" + statements +
            '}';
    }
}
