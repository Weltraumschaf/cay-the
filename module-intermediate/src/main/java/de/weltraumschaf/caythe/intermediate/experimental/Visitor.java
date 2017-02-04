package de.weltraumschaf.caythe.intermediate.experimental;

import de.weltraumschaf.caythe.intermediate.experimental.ast.*;

public interface Visitor<R> {
    default R visit(final ArrayLiteral node) {
        return null;
    }

    default R visit(final BinaryOperation node) {
        return null;
    }

    default R visit(final BooleanLiteral node) {
        return null;
    }

    default R visit(final Break node) {
        return null;
    }

    default R visit(final Const node) {
        return null;
    }

    default R visit(final Continue node) {
        return null;
    }

    default R visit(final FloatLiteral node) {
        return null;
    }

    default R visit(final FunctionCall node) {
        return null;
    }

    default R visit(final FunctionLiteral node) {
        return null;
    }

    default R visit(final HashLiteral node) {
        return null;
    }

    default R visit(final Identifier node) {
        return null;
    }

    default R visit(final IfExpression node) {
        return null;
    }

    default R visit(final IntegerLiteral node) {
        return null;
    }

    default R visit(final Let node) {
        return null;
    }

    default R visit(final Loop node) {
        return null;
    }

    default R visit(final NoOperation node) {
        return null;
    }

    default R visit(final NullLiteral node) {
        return null;
    }

    default R visit(final Return node) {
        return null;
    }

    default R visit(final Statement node) {
        return null;
    }

    default R visit(final StringLiteral node) {
        return null;
    }

    default R visit(final Subscript node) {
        return null;
    }

    default R visit(final UnaryOperation node) {
        return null;
    }

    default R visit(final Unit node) {
        return null;
    }

}
