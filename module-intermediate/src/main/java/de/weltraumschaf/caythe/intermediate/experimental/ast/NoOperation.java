package de.weltraumschaf.caythe.intermediate.experimental.ast;

import de.weltraumschaf.caythe.intermediate.experimental.AstVisitor;

public  final class NoOperation implements AstNode {
    public static final NoOperation INSTANCE = new NoOperation();

    private NoOperation() {
        super();
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
