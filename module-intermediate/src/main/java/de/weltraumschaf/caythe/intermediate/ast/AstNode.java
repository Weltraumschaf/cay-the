package de.weltraumschaf.caythe.intermediate.ast;

import de.weltraumschaf.caythe.intermediate.AstVisitor;
import de.weltraumschaf.caythe.intermediate.Position;

public interface AstNode {
    default Position sourcePosition() { return Position.UNKNOWN; }
    default <R> R accept(final AstVisitor<? extends R> visitor) { return null; }
}
