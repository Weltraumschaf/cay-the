package de.weltraumschaf.caythe.intermediate.model.ast;

import de.weltraumschaf.caythe.intermediate.model.ast.*;

/**
 * Interface for a visitor to traverse AST nodes.
 *
 * @param <R> return type of the visitor
 */
public interface AstVisitor<R> {
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

    default R visit(final RealLiteral node) {
        return null;
    }

    default R visit(final MethodCall node) {
        return null;
    }

    default R visit(final MethodDeclaration node) {
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

    default R visit(final NilLiteral node) {
        return null;
    }

    default R visit(final Return node) {
        return null;
    }

    default R visit(final Block node) {
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

    default R visit(final MethodParameter node) {
        return null;
    }

}