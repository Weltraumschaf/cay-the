package de.weltraumschaf.caythe.intermediate.experimental.ast;

import de.weltraumschaf.caythe.intermediate.experimental.Visitor;

public interface AstNode {
    default void accept(final Visitor visitor) {}
}
