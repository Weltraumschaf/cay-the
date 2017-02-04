package de.weltraumschaf.caythe.intermediate.experimental.ast;

import de.weltraumschaf.caythe.intermediate.experimental.Visitor;

public  final class NoOperation implements AstNode {
    public static final NoOperation INSTANCE = new NoOperation();

    private NoOperation() {
        super();
    }

    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "NoOperation{}";
    }

}
