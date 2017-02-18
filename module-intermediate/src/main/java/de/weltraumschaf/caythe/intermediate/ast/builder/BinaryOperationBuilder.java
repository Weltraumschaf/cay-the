package de.weltraumschaf.caythe.intermediate.ast.builder;

import de.weltraumschaf.caythe.intermediate.Position;
import de.weltraumschaf.caythe.intermediate.ast.AstNode;
import de.weltraumschaf.caythe.intermediate.ast.BinaryOperation;

public final class BinaryOperationBuilder {

    private BinaryOperationBuilder() {
        super();
        throw new UnsupportedOperationException("Do not call by reflection!");
    }

    public static BinaryOperation assign(final AstNode leftOperand, final AstNode rightOperand, final int line, final int column) {
        return new BinaryOperation(BinaryOperation.Operator.ASSIGN, leftOperand, rightOperand, new Position(line, column));
    }

    public static BinaryOperation addition(final AstNode leftOperand, final AstNode rightOperand, final int line, final int column) {
        return new BinaryOperation(BinaryOperation.Operator.ADDITION, leftOperand, rightOperand, new Position(line, column));
    }

    public static BinaryOperation subtraction(final AstNode leftOperand, final AstNode rightOperand, final int line, final int column) {
        return new BinaryOperation(BinaryOperation.Operator.SUBTRACTION, leftOperand, rightOperand, new Position(line, column));
    }

    public static BinaryOperation multiplication(final AstNode leftOperand, final AstNode rightOperand, final int line, final int column) {
        return new BinaryOperation(BinaryOperation.Operator.MULTIPLICATION, leftOperand, rightOperand, new Position(line, column));
    }

    public static BinaryOperation division(final AstNode leftOperand, final AstNode rightOperand, final int line, final int column) {
        return new BinaryOperation(BinaryOperation.Operator.DIVISION, leftOperand, rightOperand, new Position(line, column));
    }

    public static BinaryOperation modulo(final AstNode leftOperand, final AstNode rightOperand, final int line, final int column) {
        return new BinaryOperation(BinaryOperation.Operator.MODULO, leftOperand, rightOperand, new Position(line, column));
    }

    public static BinaryOperation power(final AstNode leftOperand, final AstNode rightOperand, final int line, final int column) {
        return new BinaryOperation(BinaryOperation.Operator.POWER, leftOperand, rightOperand, new Position(line, column));
    }

    public static BinaryOperation and(final AstNode leftOperand, final AstNode rightOperand, final int line, final int column) {
        return new BinaryOperation(BinaryOperation.Operator.AND, leftOperand, rightOperand, new Position(line, column));
    }

    public static BinaryOperation or(final AstNode leftOperand, final AstNode rightOperand, final int line, final int column) {
        return new BinaryOperation(BinaryOperation.Operator.OR, leftOperand, rightOperand, new Position(line, column));
    }

    public static BinaryOperation lessThan(final AstNode leftOperand, final AstNode rightOperand, final int line, final int column) {
        return new BinaryOperation(BinaryOperation.Operator.LESS_THAN, leftOperand, rightOperand, new Position(line, column));
    }

    public static BinaryOperation lessThanEqual(final AstNode leftOperand, final AstNode rightOperand, final int line, final int column) {
        return new BinaryOperation(BinaryOperation.Operator.LESS_THAN_EQUAL, leftOperand, rightOperand, new Position(line, column));
    }

    public static BinaryOperation greaterThan(final AstNode leftOperand, final AstNode rightOperand, final int line, final int column) {
        return new BinaryOperation(BinaryOperation.Operator.GREATER_THAN, leftOperand, rightOperand, new Position(line, column));
    }

    public static BinaryOperation greaterThanEqual(final AstNode leftOperand, final AstNode rightOperand, final int line, final int column) {
        return new BinaryOperation(BinaryOperation.Operator.GREATER_THAN_EQUAL, leftOperand, rightOperand, new Position(line, column));
    }

    public static BinaryOperation equal(final AstNode leftOperand, final AstNode rightOperand, final int line, final int column) {
        return new BinaryOperation(BinaryOperation.Operator.EQUAL, leftOperand, rightOperand, new Position(line, column));
    }

    public static BinaryOperation notEqual(final AstNode leftOperand, final AstNode rightOperand, final int line, final int column) {
        return new BinaryOperation(BinaryOperation.Operator.NOT_EQUAL, leftOperand, rightOperand, new Position(line, column));
    }
}
