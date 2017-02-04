package de.weltraumschaf.caythe.intermediate.experimental.ast;

import de.weltraumschaf.caythe.intermediate.experimental.Visitor;

public final class Continue implements AstNode {
    public static final Continue CONTINUE = new Continue();

    private Continue() {
        super();
    }

    @Override
    public void accept(Visitor visitor) {

    }

    @Override
    public String toString() {
        return "Continue{}";
    }
}