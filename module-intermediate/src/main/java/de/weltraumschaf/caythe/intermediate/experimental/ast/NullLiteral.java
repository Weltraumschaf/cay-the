package de.weltraumschaf.caythe.intermediate.experimental.ast;

import de.weltraumschaf.caythe.intermediate.experimental.Visitor;

public class NullLiteral implements AstNode {
    public static final NullLiteral NULL = new NullLiteral();

    private NullLiteral() {
        super();
    }

    @Override
    public void accept(Visitor visitor) {

    }

    @Override
    public String toString() {
        return "NullLiteral{}";
    }
}
