package de.weltraumschaf.caythe.intermediate.experimental.ast;

import de.weltraumschaf.caythe.intermediate.experimental.AstVisitor;

public interface AstNode {
    default <R> R accept(final AstVisitor<? extends R> visitor) { return null; }
}
