package de.weltraumschaf.caythe.intermediate.experimental.ast;

import de.weltraumschaf.caythe.intermediate.experimental.Visitor;

public final class Break implements AstNode {
    public static final Break BREAK = new Break();

    private Break() {
        super();
    }

    @Override
    public void accept(Visitor visitor) {

    }

    @Override
    public String toString() {
        return "Break{}";
    }
}
