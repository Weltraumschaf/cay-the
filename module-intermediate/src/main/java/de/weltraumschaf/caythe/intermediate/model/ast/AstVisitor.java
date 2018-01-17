package de.weltraumschaf.caythe.intermediate.model.ast;


/**
 * Interface for a visitor to traverse AST nodes.
 *
 * @param <R> return type of the visitor methods
 */
public interface AstVisitor<R> {
    /**
     * Creates and returns a default value used as return value for all default visior methods.
     *
     * @return never {@code null}
     */
    R defaultReturn();

    /**
     * Visit {@link ArrayLiteral} node.
     *
     * @param node must not be {@code null}
     * @return never {@code null}
     */
    default R visit(final ArrayLiteral node) {
        return defaultReturn();
    }

    /**
     * Visit {@link BinaryOperation} node.
     *
     * @param node must not be {@code null}
     * @return never {@code null}
     */
    default R visit(final BinaryOperation node) {
        return defaultReturn();
    }

    /**
     * Visit {@link BooleanLiteral} node.
     *
     * @param node must not be {@code null}
     * @return never {@code null}
     */
    default R visit(final BooleanLiteral node) {
        return defaultReturn();
    }

    /**
     * Visit {@link Break} node.
     *
     * @param node must not be {@code null}
     * @return never {@code null}
     */
    default R visit(final Break node) {
        return defaultReturn();
    }

    /**
     * Visit {@link Const} node.
     *
     * @param node must not be {@code null}
     * @return never {@code null}
     */
    default R visit(final Const node) {
        return defaultReturn();
    }

    /**
     * Visit {@link Continue} node.
     *
     * @param node must not be {@code null}
     * @return never {@code null}
     */
    default R visit(final Continue node) {
        return defaultReturn();
    }

    /**
     * Visit {@link RealLiteral} node.
     *
     * @param node must not be {@code null}
     * @return never {@code null}
     */
    default R visit(final RealLiteral node) {
        return defaultReturn();
    }

    /**
     * Visit {@link MethodCall} node.
     *
     * @param node must not be {@code null}
     * @return never {@code null}
     */
    default R visit(final MethodCall node) {
        return defaultReturn();
    }

    /**
     * Visit {@link MethodDeclaration} node.
     *
     * @param node must not be {@code null}
     * @return never {@code null}
     */
    default R visit(final MethodDeclaration node) {
        return defaultReturn();
    }

    /**
     * Visit {@link HashLiteral} node.
     *
     * @param node must not be {@code null}
     * @return never {@code null}
     */
    default R visit(final HashLiteral node) {
        return defaultReturn();
    }

    /**
     * Visit {@link Identifier} node.
     *
     * @param node must not be {@code null}
     * @return never {@code null}
     */
    default R visit(final Identifier node) {
        return defaultReturn();
    }

    /**
     * Visit {@link IfExpression} node.
     *
     * @param node must not be {@code null}
     * @return never {@code null}
     */
    default R visit(final IfExpression node) {
        return defaultReturn();
    }

    /**
     * Visit {@link IntegerLiteral} node.
     *
     * @param node must not be {@code null}
     * @return never {@code null}
     */
    default R visit(final IntegerLiteral node) {
        return defaultReturn();
    }

    /**
     * Visit {@link Let} node.
     *
     * @param node must not be {@code null}
     * @return never {@code null}
     */
    default R visit(final Let node) {
        return defaultReturn();
    }

    /**
     * Visit {@link Loop} node.
     *
     * @param node must not be {@code null}
     * @return never {@code null}
     */
    default R visit(final Loop node) {
        return defaultReturn();
    }

    /**
     * Visit {@link NoOperation} node.
     *
     * @param node must not be {@code null}
     * @return never {@code null}
     */
    default R visit(final NoOperation node) {
        return defaultReturn();
    }

    /**
     * Visit {@link NilLiteral} node.
     *
     * @param node must not be {@code null}
     * @return never {@code null}
     */
    default R visit(final NilLiteral node) {
        return defaultReturn();
    }

    /**
     * Visit {@link Return} node.
     *
     * @param node must not be {@code null}
     * @return never {@code null}
     */
    default R visit(final Return node) {
        return defaultReturn();
    }

    /**
     * Visit {@link Block} node.
     *
     * @param node must not be {@code null}
     * @return never {@code null}
     */
    default R visit(final Block node) {
        return defaultReturn();
    }

    /**
     * Visit {@link StringLiteral} node.
     *
     * @param node must not be {@code null}
     * @return never {@code null}
     */
    default R visit(final StringLiteral node) {
        return defaultReturn();
    }

    /**
     * Visit {@link Subscript} node.
     *
     * @param node must not be {@code null}
     * @return never {@code null}
     */
    default R visit(final Subscript node) {
        return defaultReturn();
    }

    /**
     * Visit {@link UnaryOperation} node.
     *
     * @param node must not be {@code null}
     * @return never {@code null}
     */
    default R visit(final UnaryOperation node) {
        return defaultReturn();
    }

    /**
     * Visit {@link MethodParameter} node.
     *
     * @param node must not be {@code null}
     * @return never {@code null}
     */
    default R visit(final MethodParameter node) {
        return defaultReturn();
    }

}
