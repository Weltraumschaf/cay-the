package de.weltraumschaf.caythe.intermediate.ast;

import de.weltraumschaf.caythe.intermediate.Position;

abstract class BaseNode implements AstNode {
    private final Position sourcePosition;

    BaseNode(final Position sourcePosition) {
        super();
        this.sourcePosition = sourcePosition;
    }

    @Override
    public final Position sourcePosition() {
        return sourcePosition;
    }
}
