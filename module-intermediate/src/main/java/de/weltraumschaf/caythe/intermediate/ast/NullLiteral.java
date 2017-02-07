package de.weltraumschaf.caythe.intermediate.ast;

import de.weltraumschaf.caythe.intermediate.AstVisitor;
import de.weltraumschaf.caythe.intermediate.Position;

public class NullLiteral extends BaseNode {
    public static final NullLiteral NULL = new NullLiteral();

    private NullLiteral() {
        super(Position.UNKNOWN);
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
