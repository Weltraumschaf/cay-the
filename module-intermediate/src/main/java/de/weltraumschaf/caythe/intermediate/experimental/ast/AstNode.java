package de.weltraumschaf.caythe.intermediate.experimental.ast;

import de.weltraumschaf.caythe.intermediate.experimental.AstVisitor;
import de.weltraumschaf.caythe.intermediate.experimental.Position;

public interface AstNode {
    default Position sourcePosition() { return Position.UNKNOWN; }
    default <R> R accept(final AstVisitor<? extends R> visitor) { return null; }
}
