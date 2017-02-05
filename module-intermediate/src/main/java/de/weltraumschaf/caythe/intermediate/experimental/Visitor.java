package de.weltraumschaf.caythe.intermediate.experimental;

import de.weltraumschaf.caythe.intermediate.experimental.ast.*;

public interface Visitor {
    default void visit(final ArrayLiteral node) {
    }

    default void visit(final BinaryOperation node) {
    }

    default void visit(final BooleanLiteral node) {

    }

    default void visit(final Break node) {

    }

    default void visit(final Const node) {

    }

    default void visit(final Continue node) {

    }

    default void visit(final FloatLiteral node) {

    }

    default void visit(final FunctionCall node) {

    }

    default void visit(final FunctionLiteral node) {

    }

    default void visit(final HashLiteral node) {

    }

    default void visit(final Identifier node) {

    }

    default void visit(final IfExpression node) {

    }

    default void visit(final IntegerLiteral node) {

    }

    default void visit(final Let node) {

    }

    default void visit(final Loop node) {

    }

    default void visit(final NoOperation node) {

    }

    default void visit(final NullLiteral node) {

    }

    default void visit(final Return node) {

    }

    default void visit(final Statement node) {

    }

    default void visit(final StringLiteral node) {

    }

    default void visit(final Subscript node) {

    }

    default void visit(final UnaryOperation node) {

    }

    default void visit(final Unit node) {

    }

}
