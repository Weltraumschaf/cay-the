package de.weltraumschaf.caythe.intermediate.experimental.ast;

import de.weltraumschaf.caythe.intermediate.experimental.AstVisitor;

public final class Continue implements AstNode {
    public static final Continue CONTINUE = new Continue();

    private Continue() {
        super();
    }

    @Override
    public <R> R accept(final AstVisitor<? extends R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "Continue{}";
    }
}
