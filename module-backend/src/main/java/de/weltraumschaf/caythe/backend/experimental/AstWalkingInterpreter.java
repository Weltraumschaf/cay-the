package de.weltraumschaf.caythe.backend.experimental;

import de.weltraumschaf.caythe.intermediate.experimental.Visitor;
import de.weltraumschaf.caythe.intermediate.experimental.ast.*;

final class AstWalkingInterpreter implements Visitor {
    @Override
    public void visit(final ArrayLiteral node) {
        
    }

    @Override
    public void visit(final BinaryOperation node) {
        
    }

    @Override
    public void visit(final BooleanLiteral node) {
        
    }

    @Override
    public void visit(final Break node) {
        
    }

    @Override
    public void visit(final Const node) {
        
    }

    @Override
    public void visit(final Continue node) {
        
    }

    @Override
    public void visit(final FloatLiteral node) {
        
    }

    @Override
    public void visit(final FunctionCall node) {
        
    }

    @Override
    public void visit(final FunctionLiteral node) {
        
    }

    @Override
    public void visit(final HashLiteral node) {
        
    }

    @Override
    public void visit(final Identifier node) {
        
    }

    @Override
    public void visit(final IfExpression node) {
        
    }

    @Override
    public void visit(final IntegerLiteral node) {
        
    }

    @Override
    public void visit(final Let node) {
        
    }

    @Override
    public void visit(final Loop node) {
        
    }

    @Override
    public void visit(final NoOperation node) {
        
    }

    @Override
    public void visit(final NullLiteral node) {
        
    }

    @Override
    public void visit(final Return node) {
        
    }

    @Override
    public void visit(final Statement node) {
        
    }

    @Override
    public void visit(final StringLiteral node) {
        
    }

    @Override
    public void visit(final Subscript node) {
        
    }

    @Override
    public void visit(final UnaryOperation node) {
        
    }

    @Override
    public void visit(final Unit node) {
        for (final AstNode statement : node.getStatements()) {
            statement.accept(this);
        }
    }
}
