package de.weltraumschaf.caythe.intermediate.experimental.ast;

import de.weltraumschaf.caythe.intermediate.experimental.AstVisitor;

public class NullLiteral implements AstNode {
    public static final NullLiteral NULL = new NullLiteral();

    private NullLiteral() {
        super();
    }

    @Override
    public <R> R accept(final AstVisitor<? extends R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "NullLiteral{}";
    }
}
