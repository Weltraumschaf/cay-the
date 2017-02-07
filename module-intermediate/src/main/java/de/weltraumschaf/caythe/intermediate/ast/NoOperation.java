package de.weltraumschaf.caythe.intermediate.ast;

import de.weltraumschaf.caythe.intermediate.AstVisitor;
import de.weltraumschaf.caythe.intermediate.Position;

public  final class NoOperation extends BaseNode  {
    public static final NoOperation NOOP = new NoOperation();

    private NoOperation() {
        super(Position.UNKNOWN);
    }

    @Override
    public <R> R accept(final AstVisitor<? extends R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "NoOperation{}";
    }

}
