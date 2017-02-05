package de.weltraumschaf.caythe.intermediate.experimental.ast;

import de.weltraumschaf.caythe.intermediate.experimental.AstVisitor;

public final class Break implements AstNode {
    public static final Break BREAK = new Break();

    private Break() {
        super();
    }

    @Override
    public <R> R accept(final AstVisitor<? extends R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "Break{}";
    }
}
