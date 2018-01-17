package de.weltraumschaf.caythe.intermediate.model.ast.builder;

import de.weltraumschaf.caythe.intermediate.Position;
import de.weltraumschaf.caythe.intermediate.model.ast.AstNode;
import de.weltraumschaf.caythe.intermediate.model.ast.BinaryOperation;

/**
 * Factory to create {@link BinaryOperation binary operations}.
 */
public final class BinaryOperationFactory {

    private BinaryOperationFactory() {
        super();
    }

    public static BinaryOperation assign(final AstNode leftOperand, final AstNode rightOperand, final int line, final int column) {
        return assign(leftOperand, rightOperand, new Position(line, column));
    }

    public static BinaryOperation assign(final AstNode leftOperand, final AstNode rightOperand, final Position pos) {
        return new BinaryOperation(BinaryOperation.Operator.ASSIGN, leftOperand, rightOperand, pos);
    }

    public static BinaryOperation addition(final AstNode leftOperand, final AstNode rightOperand, final int line, final int column) {
        return addition(leftOperand, rightOperand, new Position(line, column));
    }

    public static BinaryOperation addition(final AstNode leftOperand, final AstNode rightOperand, final Position pos) {
        return new BinaryOperation(BinaryOperation.Operator.ADDITION, leftOperand, rightOperand, pos);
    }

    public static BinaryOperation subtraction(final AstNode leftOperand, final AstNode rightOperand, final int line, final int column) {
        return subtraction(leftOperand, rightOperand, new Position(line, column));
    }

    public static BinaryOperation subtraction(final AstNode leftOperand, final AstNode rightOperand, final Position pos) {
        return new BinaryOperation(BinaryOperation.Operator.SUBTRACTION, leftOperand, rightOperand, pos);
    }

    public static BinaryOperation multiplication(final AstNode leftOperand, final AstNode rightOperand, final int line, final int column) {
        return multiplication(leftOperand, rightOperand, new Position(line, column));
    }

    public static BinaryOperation multiplication(final AstNode leftOperand, final AstNode rightOperand, final Position pos) {
        return new BinaryOperation(BinaryOperation.Operator.MULTIPLICATION, leftOperand, rightOperand, pos);
    }

    public static BinaryOperation division(final AstNode leftOperand, final AstNode rightOperand, final int line, final int column) {
        return division(leftOperand, rightOperand, new Position(line, column));
    }

    public static BinaryOperation division(final AstNode leftOperand, final AstNode rightOperand, final Position pos) {
        return new BinaryOperation(BinaryOperation.Operator.DIVISION, leftOperand, rightOperand, pos);
    }

    public static BinaryOperation modulo(final AstNode leftOperand, final AstNode rightOperand, final int line, final int column) {
        return modulo(leftOperand, rightOperand, new Position(line, column));
    }

    public static BinaryOperation modulo(final AstNode leftOperand, final AstNode rightOperand, final Position pos) {
        return new BinaryOperation(BinaryOperation.Operator.MODULO, leftOperand, rightOperand, pos);
    }

    public static BinaryOperation power(final AstNode leftOperand, final AstNode rightOperand, final int line, final int column) {
        return power(leftOperand, rightOperand, new Position(line, column));
    }

    public static BinaryOperation power(final AstNode leftOperand, final AstNode rightOperand, final Position pos) {
        return new BinaryOperation(BinaryOperation.Operator.POWER, leftOperand, rightOperand, pos);
    }

    public static BinaryOperation and(final AstNode leftOperand, final AstNode rightOperand, final int line, final int column) {
        return and(leftOperand, rightOperand, new Position(line, column));
    }

    public static BinaryOperation and(final AstNode leftOperand, final AstNode rightOperand, final Position pos) {
        return new BinaryOperation(BinaryOperation.Operator.AND, leftOperand, rightOperand, pos);
    }

    public static BinaryOperation or(final AstNode leftOperand, final AstNode rightOperand, final int line, final int column) {
        return or(leftOperand, rightOperand, new Position(line, column));
    }

    public static BinaryOperation or(final AstNode leftOperand, final AstNode rightOperand, final Position pos) {
        return new BinaryOperation(BinaryOperation.Operator.OR, leftOperand, rightOperand, pos);
    }

    public static BinaryOperation lessThan(final AstNode leftOperand, final AstNode rightOperand, final int line, final int column) {
        return lessThan(leftOperand, rightOperand, new Position(line, column));
    }

    public static BinaryOperation lessThan(final AstNode leftOperand, final AstNode rightOperand, final Position pos) {
        return new BinaryOperation(BinaryOperation.Operator.LESS_THAN, leftOperand, rightOperand, pos);
    }

    public static BinaryOperation lessThanEqual(final AstNode leftOperand, final AstNode rightOperand, final int line, final int column) {
        return lessThanEqual(leftOperand, rightOperand, new Position(line, column));
    }

    public static BinaryOperation lessThanEqual(final AstNode leftOperand, final AstNode rightOperand, final Position pos) {
        return new BinaryOperation(BinaryOperation.Operator.LESS_THAN_EQUAL, leftOperand, rightOperand, pos);
    }

    public static BinaryOperation greaterThan(final AstNode leftOperand, final AstNode rightOperand, final int line, final int column) {
        return greaterThan(leftOperand, rightOperand, new Position(line, column));
    }

    public static BinaryOperation greaterThan(final AstNode leftOperand, final AstNode rightOperand, final Position pos) {
        return new BinaryOperation(BinaryOperation.Operator.GREATER_THAN, leftOperand, rightOperand, pos);
    }

    public static BinaryOperation greaterThanEqual(final AstNode leftOperand, final AstNode rightOperand, final int line, final int column) {
        return greaterThanEqual(leftOperand, rightOperand, new Position(line, column));
    }

    public static BinaryOperation greaterThanEqual(final AstNode leftOperand, final AstNode rightOperand, final Position pos) {
        return new BinaryOperation(BinaryOperation.Operator.GREATER_THAN_EQUAL, leftOperand, rightOperand, pos);
    }

    public static BinaryOperation equal(final AstNode leftOperand, final AstNode rightOperand, final int line, final int column) {
        return equal(leftOperand, rightOperand, new Position(line, column));
    }

    public static BinaryOperation equal(final AstNode leftOperand, final AstNode rightOperand, final Position pos) {
        return new BinaryOperation(BinaryOperation.Operator.EQUAL, leftOperand, rightOperand, pos);
    }

    public static BinaryOperation notEqual(final AstNode leftOperand, final AstNode rightOperand, final int line, final int column) {
        return notEqual(leftOperand, rightOperand, new Position(line, column));
    }

    public static BinaryOperation notEqual(final AstNode leftOperand, final AstNode rightOperand, final Position pos) {
        return new BinaryOperation(BinaryOperation.Operator.NOT_EQUAL, leftOperand, rightOperand, pos);
    }
}
