package de.weltraumschaf.caythe.intermediate.ast;

import de.weltraumschaf.caythe.intermediate.AstVisitor;
import de.weltraumschaf.caythe.intermediate.Position;
import de.weltraumschaf.caythe.intermediate.Serializable;

public interface AstNode extends Serializable {
    default Position sourcePosition() { return Position.UNKNOWN; }
    default <R> R accept(final AstVisitor<? extends R> visitor) { return null; }
}
