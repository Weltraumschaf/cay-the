package de.weltraumschaf.caythe.backend;

import de.weltraumschaf.caythe.backend.experimental.BuiltInFunction;
import de.weltraumschaf.caythe.backend.experimental.Debugger;
import de.weltraumschaf.caythe.backend.experimental.Environment;
import de.weltraumschaf.caythe.backend.experimental.operations.Operations;
import de.weltraumschaf.caythe.backend.experimental.types.*;
import de.weltraumschaf.caythe.intermediate.experimental.AstVisitor;
import de.weltraumschaf.caythe.intermediate.experimental.ast.*;

import java.util.*;

import static de.weltraumschaf.caythe.backend.EvaluationError.newError;

public final class AstWalkingInterpreter implements AstVisitor<ObjectType> {
    private final Debugger debugger = new Debugger();
    private final Operations ops = new Operations();
    private final Stack<Environment> scopes = new Stack<>();

    public AstWalkingInterpreter() {
        super();
        reset();
    }

    public void reset() {
        scopes.clear();
        scopes.push(new Environment());
        registerBuiltIns();
    }

    public void debug(boolean debug) {
        if (debug) {
            debugger.on();
        } else {
            debugger.off();
        }
    }

    public Environment scope() {
        return scopes.get(0);
    }

    private void registerBuiltIns() {
        for (final BuiltInFunction fn : BuiltInFunction.values()) {
            currentScope().setConst(fn.identifier(), fn.object());
        }
    }

    private void pushScope(final Environment scope) {
        scopes.push(scope);
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
                throw newError("Unsupported operator '%s'!", node.getOperator());
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
        final StringType identifier = node.getAssignment().getLeftOperand().accept(this).castToString();

        if (currentScope().has(identifier.value())) {
            throw newError("Can not redeclare constant with identifier '%s'!", identifier.value());
        }

        final ObjectType value = node.getAssignment().getRightOperand().accept(this);
        currentScope().setConst(identifier.value(), value);
        return value;
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
        final String identifier = node.getIdentifier().getName();

        if (currentScope().has(identifier)) {
            final ObjectType assignedValue = currentScope().get(identifier);

            if (assignedValue.isOf(Type.FUNCTION)) {
                final FunctionType function = (FunctionType) assignedValue;

                if (node.getArguments().size() != function.getParameterIdentifiers().size()) {
                    throw newError(
                        "Argument count mismatch for function '%s'! Expected number of arguments is %d given number is %d.",
                        identifier, function.getParameterIdentifiers().size(), node.getArguments().size());
                }

                final Environment functionScope = function.getClosureScope().createChild();

                for (int i = 0; i < node.getArguments().size(); ++i) {
                    // Function argument values are const (aka. final).
                    final String name = function.getParameterIdentifiers().get(i).getName();
                    final ObjectType value = node.getArguments().get(i).accept(this);
                    functionScope.setConst(name, value);
                }

                pushScope(functionScope);
                ObjectType result = defaultResult();

                for (final AstNode statement : function.getBody()) {
                    result = statement.accept(this);
                }

                popScope();
                return result;
            } else if (assignedValue.isOf(Type.BUILTIN)) {
                final List<ObjectType> arguments = new ArrayList<>();

                for (final AstNode argument : node.getArguments()) {
                    arguments.add(argument.accept(this));
                }

                final BuiltinType function = (BuiltinType) assignedValue;
                return function.apply(arguments);
            } else {
                throw newError("Assigned value for '%s' is not a function!", identifier);
            }
        } else {
            throw newError("Undefined function '%s'!", identifier);
        }
    }

    @Override
    public ObjectType visit(final FunctionLiteral node) {
        return new FunctionType(currentScope(), node.getArguments(), node.getBody());
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
        if (currentScope().has(node.getName())) {
            return currentScope().get(node.getName());
        }

        throw newError("There is no const/var '%s' declared!", node.getName());
    }

    @Override
    public ObjectType visit(final IfExpression node) {
        final ObjectType condition = node.getCondition().accept(this);

        if (condition.castToBoolean().value()) {
            return node.getConsequence().accept(this);
        }

        return node.getAlternative().accept(this);
    }

    @Override
    public ObjectType visit(final IntegerLiteral node) {
        return new IntegerType(node.getValue());
    }

    @Override
    public ObjectType visit(final Let node) {
        final StringType identifier = node.getAssignment().getLeftOperand().accept(this).castToString();
        final ObjectType value = node.getAssignment().getRightOperand().accept(this);
        currentScope().setVar(identifier.value(), value);
        return value;
    }

    @Override
    public ObjectType visit(final Loop node) {
        boolean isConditionTrue = node.condition().accept(this).castToBoolean().value();
        ObjectType result = defaultResult();

        while (isConditionTrue) {
            // This inner loop executes the loop body.
            for (final AstNode statement : node.statements()) {
                final ObjectType statementResult = statement.accept(this);

                if (ContinueType.CONTINUE.equals(statementResult)) {
                    // Continue with the next while loop iteration.
                    break;
                }

                if (BreakType.BREAK.equals(statementResult)) {
                    // Prevent further looping regardless to what the condition will evaluate.
                    isConditionTrue = false;
                }

                result = statementResult;
            }

            if (isConditionTrue) {
                // See what the condition is, unless we break the loop.
                isConditionTrue = node.condition().accept(this).castToBoolean().value();
            }
        }

        return result;
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
        final ObjectType value = node.getIdentifier().accept(this);
        final ObjectType index = node.getIndex().accept(this);

        if (value.isOf(Type.ARRAY)) {
            return arraySubscript(node, (ArrayType) value, index);
        } else if (value.isOf(Type.HASH)) {
            return hashSubscript(node, (HashType) value, index);
        } else {
            throw newError("Assigned value for identifier '%s' does not allow subscript access!", node.getIdentifier());
        }
    }

    private ObjectType arraySubscript(final Subscript node, final ArrayType array, final ObjectType index) {
        if (index.isOf(Type.INTEGER)) {
            if (array.has(index.castToInteger())) {
                return array.get(index.castToInteger());
            } else {
                throw newError("Array with identifier '%s' does not have index %d!",
                        node.getIdentifier(), index.castToInteger().value());
            }
        } else {
            throw newError("Index must be of type integer, given was: %s!", index.type());
        }
    }

    private ObjectType hashSubscript(final Subscript node, final HashType hash, final ObjectType index) {
        if (hash.has(index)) {
            return hash.get(index);
        } else {
            throw newError("Hash with identifier '%s' does not have key '%s'", node.getIdentifier(), index);
        }
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
                throw newError("Unsupported operator '%s'!", node.getOperator());
        }
    }

    @Override
    public ObjectType visit(final Unit node) {
        ObjectType result = defaultResult();

        for (final AstNode statement : node.getStatements()) {
            result = statement.accept(this);
        }

        return result;
    }

}
