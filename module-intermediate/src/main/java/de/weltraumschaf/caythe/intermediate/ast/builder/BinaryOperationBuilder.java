package de.weltraumschaf.caythe.intermediate.ast.builder;

import de.weltraumschaf.caythe.intermediate.Position;
import de.weltraumschaf.caythe.intermediate.ast.AstNode;
import de.weltraumschaf.caythe.intermediate.ast.BinaryOperation;
import de.weltraumschaf.commons.validate.Validate;

public final class BinaryOperationBuilder {

    private final StatementBuilder parent;
    
    BinaryOperationBuilder(final StatementBuilder parent) {
        super();
        this.parent = Validate.notNull(parent, "parent");
    }

    private Position createPosition(final int line, final int column) {
        return new Position(parent.getFile(), line, column);
    }

    public BinaryOperationBuilder assign(final AstNode leftOperand, final AstNode rightOperand, final int line, final int column) {
        parent.addStatement(BinaryOperationFactory.assign(leftOperand, rightOperand, createPosition(line, column)));
        return this;
    }

    public BinaryOperationBuilder addition(final AstNode leftOperand, final AstNode rightOperand, final int line, final int column) {
        parent.addStatement(BinaryOperationFactory.addition(leftOperand, rightOperand, createPosition(line, column)));
        return this;
    }

    public BinaryOperationBuilder subtraction(final AstNode leftOperand, final AstNode rightOperand, final int line, final int column) {
        parent.addStatement(BinaryOperationFactory.subtraction(leftOperand, rightOperand, createPosition(line, column)));
        return this;
    }

    public BinaryOperationBuilder multiplication(final AstNode leftOperand, final AstNode rightOperand, final int line, final int column) {
        parent.addStatement(BinaryOperationFactory.multiplication(leftOperand, rightOperand, createPosition(line, column)));
        return this;
    }

    public BinaryOperationBuilder division(final AstNode leftOperand, final AstNode rightOperand, final int line, final int column) {
        parent.addStatement(BinaryOperationFactory.division(leftOperand, rightOperand, createPosition(line, column)));
        return this;
    }

    public BinaryOperationBuilder modulo(final AstNode leftOperand, final AstNode rightOperand, final int line, final int column) {
        parent.addStatement(BinaryOperationFactory.modulo(leftOperand, rightOperand, createPosition(line, column)));
        return this;
    }

    public BinaryOperationBuilder power(final AstNode leftOperand, final AstNode rightOperand, final int line, final int column) {
        parent.addStatement(BinaryOperationFactory.power(leftOperand, rightOperand, createPosition(line, column)));
        return this;
    }

    public BinaryOperationBuilder and(final AstNode leftOperand, final AstNode rightOperand, final int line, final int column) {
        parent.addStatement(BinaryOperationFactory.and(leftOperand, rightOperand, createPosition(line, column)));
        return this;
    }

    public BinaryOperationBuilder or(final AstNode leftOperand, final AstNode rightOperand, final int line, final int column) {
        parent.addStatement(BinaryOperationFactory.or(leftOperand, rightOperand, createPosition(line, column)));
        return this;
    }

    public BinaryOperationBuilder lessThan(final AstNode leftOperand, final AstNode rightOperand, final int line, final int column) {
        parent.addStatement(BinaryOperationFactory.lessThan(leftOperand, rightOperand, createPosition(line, column)));
        return this;
    }

    public BinaryOperationBuilder lessThanEqual(final AstNode leftOperand, final AstNode rightOperand, final int line, final int column) {
        parent.addStatement(BinaryOperationFactory.lessThanEqual(leftOperand, rightOperand, createPosition(line, column)));
        return this;
    }

    public BinaryOperationBuilder greaterThan(final AstNode leftOperand, final AstNode rightOperand, final int line, final int column) {
        parent.addStatement(BinaryOperationFactory.greaterThan(leftOperand, rightOperand, createPosition(line, column)));
        return this;
    }

    public BinaryOperationBuilder greaterThanEqual(final AstNode leftOperand, final AstNode rightOperand, final int line, final int column) {
        parent.addStatement(BinaryOperationFactory.greaterThanEqual(leftOperand, rightOperand, createPosition(line, column)));
        return this;
    }

    public BinaryOperationBuilder equal(final AstNode leftOperand, final AstNode rightOperand, final int line, final int column) {
        parent.addStatement(BinaryOperationFactory.equal(leftOperand, rightOperand, createPosition(line, column)));
        return this;
    }

    public BinaryOperationBuilder notEqual(final AstNode leftOperand, final AstNode rightOperand, final int line, final int column) {
        parent.addStatement(BinaryOperationFactory.notEqual(leftOperand, rightOperand, createPosition(line, column)));
        return this;
    }

    public StatementBuilder end() {
        return parent;
    }
}

