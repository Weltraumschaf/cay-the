package de.weltraumschaf.caythe.backend;

import de.weltraumschaf.caythe.backend.experimental.Environment;
import de.weltraumschaf.caythe.backend.experimental.operations.Operations;
import de.weltraumschaf.caythe.backend.experimental.types.*;
import de.weltraumschaf.caythe.intermediate.experimental.AstVisitor;
import de.weltraumschaf.caythe.intermediate.experimental.ast.*;

import java.util.*;

public final class AstWalkingInterpreter implements AstVisitor<ObjectType> {
    private final Operations ops = new Operations();
    private final Stack<Environment> scopes = new Stack<>();

    public AstWalkingInterpreter() {
        super();
        scopes.push(new Environment());
    }

    private void pushScope() {
        scopes.push(currentScope().createChild());
    }

    private void popScope() {
        scopes.pop();
    }

    private Environment currentScope() {
        return scopes.peek();
    }

    private ObjectType defaultResult() {
        return NullType.NULL;
    }

    @Override
    public ObjectType visit(final ArrayLiteral node) {
        final List<ObjectType> values = new ArrayList<>();

        for (final AstNode valueNode : node.getValues()) {
            values.add(valueNode.accept(this));
        }

        return new ArrayType(values);
    }

    @Override
    public ObjectType visit(final BinaryOperation node) {
        final ObjectType left = node.getLeftOperand().accept(this);
        final BinaryOperation.Operator operator = node.getOperator();
        final ObjectType right = node.getRightOperand().accept(this);

        switch (node.getOperator()) {
            case EQ:
                return ops.rel().equal(left, right);
            case NEQ:
                return ops.rel().notEqual(left, right);
            case LT:
                return ops.rel().lessThan(left, right);
            case LTE:
                return ops.rel().lessThanOrEqual(left, right);
            case GT:
                return ops.rel().greaterThan(left, right);
            case GTE:
                return ops.rel().greaterThanOrEqual(left, right);
            case POW:
                return ops.math().power(left, right);
            case ADD:
                return ops.math().add(left, right);
            case SUB:
                return ops.math().subtract(left, right);
            case MUL:
                return ops.math().multiply(left, right);
            case DIV:
                return ops.math().divide(left, right);
            case MOD:
                return ops.math().modulo(left, right);
            case OR:
                return ops.logic().or(left, right);
            case AND:
                return ops.logic().and(left, right);
            default:
                throw new RuntimeException(String.format("Unsupported operator '%s'!", node.getOperator()));
        }
    }

    @Override
    public ObjectType visit(final BooleanLiteral node) {
        return BooleanType.valueOf(node.getValue());
    }

    @Override
    public ObjectType visit(final Break node) {
        return BreakType.BREAK;
    }

    @Override
    public ObjectType visit(final Const node) {
        return defaultResult();
    }

    @Override
    public ObjectType visit(final Continue node) {
        return ContinueType.CONTINUE;
    }

    @Override
    public ObjectType visit(final FloatLiteral node) {
        return new FloatType(node.getValue());
    }

    @Override
    public ObjectType visit(final FunctionCall node) {
        return defaultResult();
    }

    @Override
    public ObjectType visit(final FunctionLiteral node) {
        return defaultResult();
    }

    @Override
    public ObjectType visit(final HashLiteral node) {
        final Map<ObjectType, ObjectType> values = new HashMap<>();

        for (final Map.Entry<AstNode, AstNode> pair : node.getValues().entrySet()) {
            values.put(pair.getKey().accept(this), pair.getValue().accept(this));
        }

        return new HashType(values);
    }

    @Override
    public ObjectType visit(final Identifier node) {
        return defaultResult();
    }

    @Override
    public ObjectType visit(final IfExpression node) {
        return defaultResult();
    }

    @Override
    public ObjectType visit(final IntegerLiteral node) {
        return new IntegerType(node.getValue());
    }

    @Override
    public ObjectType visit(final Let node) {
        return defaultResult();
    }

    @Override
    public ObjectType visit(final Loop node) {
        return defaultResult();
    }

    @Override
    public ObjectType visit(final NoOperation node) {
        return defaultResult();
    }

    @Override
    public ObjectType visit(final NullLiteral node) {
        return defaultResult();
    }

    @Override
    public ObjectType visit(final Return node) {
        return new ReturnValueType(node.getResult().accept(this));
    }

    @Override
    public ObjectType visit(final Statements node) {
        ObjectType result = defaultResult();

        for (final AstNode statement : node.getStatements()) {
            result = statement.accept(this);
        }

        return result;
    }

    @Override
    public ObjectType visit(final StringLiteral node) {
        return new StringType(node.getValue());
    }

    @Override
    public ObjectType visit(final Subscript node) {
        return defaultResult();
    }

    @Override
    public ObjectType visit(final UnaryOperation node) {
        final ObjectType operand = node.getOperand().accept(this);

        switch (node.getOperator()) {
            case NOT:
                return ops.logic().not(operand);
            case NEG:
                return ops.math().negate(operand);
            default:
                throw new RuntimeException(String.format("Unsupported operator '%s'!", node.getOperator()));
        }
    }

    @Override
    public ObjectType visit(final Unit node) {
        for (final AstNode statement : node.getStatements()) {
            statement.accept(this);
        }

        return defaultResult();
    }
}
