package de.weltraumschaf.caythe.backend;

import static de.weltraumschaf.caythe.backend.experimental.EvaluationError.newError;
import static de.weltraumschaf.caythe.backend.experimental.EvaluationError.newUnsupportedOperatorError;

import java.util.*;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import de.weltraumschaf.caythe.backend.experimental.BuiltInFunction;
import de.weltraumschaf.caythe.backend.experimental.Debugger;
import de.weltraumschaf.caythe.backend.experimental.Environment;
import de.weltraumschaf.caythe.backend.experimental.operations.Operations;
import de.weltraumschaf.caythe.backend.experimental.types.*;
import de.weltraumschaf.caythe.frontend.CayTheSourceBaseVisitor;
import de.weltraumschaf.caythe.frontend.CayTheSourceParser;

/**
 * Default implementation which converts the parsed tree from a source file into intermediate model.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class TreeWalkingInterpreter extends CayTheSourceBaseVisitor<ObjectType> {

    private final Debugger debugger = new Debugger();
    private final Operations ops = new Operations();
    private final Stack<Environment> currentScope = new Stack<>();

    public TreeWalkingInterpreter() {
        super();
        reset();
    }

    public void reset() {
        currentScope.clear();
        currentScope.push(new Environment());
        registerBuiltIns();
    }

    private void registerBuiltIns() {
        for (final BuiltInFunction fn : BuiltInFunction.values()) {
            currentScope.peek().setConst(fn.identifier(), fn.object());
        }
    }

    public void debug(boolean debug) {
        if (debug) {
            debugger.on();
        } else {
            debugger.off();
        }
    }

    public Environment environment() {
        return currentScope.get(0);
    }

    @Override
    protected ObjectType defaultResult() {
        return NullType.NULL;
    }

    @Override
    public ObjectType visitUnit(CayTheSourceParser.UnitContext ctx) {
        debugger.debug("Visit unit: %s", ctx.getText());
        ObjectType result = defaultResult();

        for (CayTheSourceParser.StatementContext statement : ctx.statement()) {
            result = visit(statement);
        }

        debugger.returnValue(result);
        return result;
    }

    @Override
    public ObjectType visitStatement(CayTheSourceParser.StatementContext ctx) {
        String text = ctx.getText().trim();
        ObjectType result = defaultResult();

        if (text.isEmpty()) {
            debugger.debug("Visit empty statement: ignoring");
            debugger.returnValue(result);
            return result;
        }

        debugger.debug("Visit statement: %s", text);

        for (final ParseTree child : ctx.children) {
            result = visit(child);
        }

        debugger.returnValue(result);
        return result;
    }

    @Override
    public ObjectType visitLetStatement(CayTheSourceParser.LetStatementContext ctx) {
        debugger.debug("Visit let statement: %s", ctx.getText().trim());
        final String identifier;
        final ObjectType value;

        if (null == ctx.assignStatement()) {
            // This is let w/o assignment: let a;
            identifier = ctx.IDENTIFIER().getText();
            value = defaultResult();
        } else {
            final CayTheSourceParser.AssignExpressionContext assignment = ctx.assignStatement().assignExpression();
            // This is let w/ assignment: let a = 1 + 2;
            identifier = assignment.IDENTIFIER().getText();

            if (currentScope.peek().has(identifier)) {
                throw newError(assignment.IDENTIFIER().getSymbol(), "Variable with identifier '%s' already defined!", identifier);
            }

            value = visit(assignment.expression());
        }

        debugger.debug("Set variable: %s = %s", identifier, value);
        currentScope.peek().setVar(identifier, value);
        debugger.returnValue(value);
        return value;
    }

    @Override
    public ObjectType visitConstStatement(CayTheSourceParser.ConstStatementContext ctx) {
        debugger.debug("Visit const statement: %s", ctx.getText().trim());
        final CayTheSourceParser.AssignExpressionContext assignment = ctx.assignStatement().assignExpression();
        final String identifier = assignment.identifier.getText();

        if (currentScope.peek().has(identifier)) {
            throw newError(assignment.identifier, "Can not redeclare constant with identifier '%s'!", identifier);
        }

        final ObjectType value = visit(assignment.expression());
        currentScope.peek().setConst(identifier, value);
        debugger.returnValue(value);
        return value;
    }

    @Override
    public ObjectType visitAssignStatement(CayTheSourceParser.AssignStatementContext ctx) {
        debugger.debug("Visit assign statement: %s", ctx.getText().trim());
        final CayTheSourceParser.AssignExpressionContext assignment = ctx.assignExpression();
        final String identifier = assignment.identifier.getText();
        final ObjectType value;

        if (currentScope.peek().has(identifier)) {
            value  = visit(assignment.value);
            currentScope.peek().setVar(identifier, value);
        } else {
            throw newError(assignment.identifier, "Undeclared variable '%s'!", identifier);
        }

        debugger.returnValue(value);
        return value;
    }

    @Override
    public ObjectType visitReturnStatement(CayTheSourceParser.ReturnStatementContext ctx) {
        debugger.debug("Visit return statement: %s", ctx.getText().trim());
        final ObjectType result = visit(ctx.value);
        debugger.returnValue(result);
        return result;
    }

    @Override
    public ObjectType visitBreakStatement(CayTheSourceParser.BreakStatementContext ctx) {
        debugger.debug("Visit break statement: %s", ctx.getText().trim());
        debugger.returnValue(BreakType.INSTANCE);
        return BreakType.INSTANCE;
    }

    @Override
    public ObjectType visitContinueStatement(CayTheSourceParser.ContinueStatementContext ctx) {
        debugger.debug("Visit continue statement: %s", ctx.getText().trim());
        debugger.returnValue(ContinueType.INSTANCE);
        return ContinueType.INSTANCE;
    }

    @Override
    public ObjectType visitExpressionStatement(CayTheSourceParser.ExpressionStatementContext ctx) {
        debugger.debug("Visit expression statement: %s", ctx.getText().trim());
        final ObjectType result = visit(ctx.expression());
        debugger.returnValue(result);
        return result;
    }

    @Override
    public ObjectType visitIfExpression(CayTheSourceParser.IfExpressionContext ctx) {
        debugger.debug("Visit if expression: %s", ctx.getText().trim());
        final ObjectType result;

        if (visit(ctx.condition).castToBoolean().value()) {
            debugger.debug("Condition of if expression evaluates to true.");
            result = visit(ctx.consequence);
        } else {
            debugger.debug("Condition of if expression evaluates to false.");

            if (ctx.alternative == null) {
                result = defaultResult();
            } else {
                debugger.debug("Evaluate consequence.");
                result = visit(ctx.alternative);
            }
        }

        debugger.returnValue(result);
        return result;
    }

    @Override
    public ObjectType visitEndlessLoopExpression(CayTheSourceParser.EndlessLoopExpressionContext ctx) {
        debugger.debug("Visit endless loop expression: %s", ctx.getText().trim());
        ObjectType result = defaultResult();

        for (final CayTheSourceParser.StatementContext statement : ctx.statement()) {
            if (statement.NL() != null) { // When this is not null then it is an empty line.
                debugger.debug("Ignore empty line");
                continue;
            }

            final ObjectType subResult = visit(statement);

            if (ContinueType.INSTANCE.equals(subResult)) {
                debugger.debug("Continue loop.");
                continue;
            }

            if (BreakType.INSTANCE.equals(subResult)) {
                debugger.debug("Break loop.");
                break;
            }

            result = subResult;
        }

        debugger.returnValue(result);
        return result;
    }

    @Override
    public ObjectType visitCallExpression(CayTheSourceParser.CallExpressionContext ctx) {
        debugger.debug("Visit call expression: %s", ctx.getText().trim());
        final String identifier = ctx.identifier.getText();
        ObjectType result = defaultResult();

        if (currentScope.peek().has(identifier)) {
            final ObjectType assignedValue = currentScope.peek().get(identifier);

            if (assignedValue.isOf(Type.FUNCTION)) {
                final FunctionType function = (FunctionType) assignedValue;
                debugger.debug("Extend function scope for: %s", identifier);
                final List<String> parameterIdentifiers = function.getParameterIdentifiers();
                final List<CayTheSourceParser.ExpressionContext> argumentExpressions
                    = ctx.arguments == null ?
                    Collections.emptyList() :
                    ctx.arguments.expression();

                if (parameterIdentifiers.size() != argumentExpressions.size()) {
                    throw newError(
                        ctx.identifier,
                        "Argument count mismatch for function '%s'! Expected number of arguments is %d given number is %d.",
                        identifier, parameterIdentifiers.size(), argumentExpressions.size());
                }

                final Environment functionScope = function.getClosureScope().createChild();
                final int argumentsCount = parameterIdentifiers.size();

                for (int i = 0; i < argumentsCount; ++i) {
                    final String name = parameterIdentifiers.get(i);
                    final ObjectType value = visit(argumentExpressions.get(i));
                    debugger.debug("Extend function scope with argument: %s = %s", name, value);
                    // XXX Function arguments should be const?
                    functionScope.setVar(name, value);
                }

                currentScope.push(functionScope);

                debugger.debug("Evaluate body of function: %s", identifier);

                for (CayTheSourceParser.StatementContext statement : function.getBody()) {
                    if (statement.NL() != null) { // When this is not null then it is an empty line.
                        debugger.debug("Ignore empty line");
                        continue;
                    }

                    result = visit(statement);
                }

                currentScope.pop();
            } else if (assignedValue.isOf(Type.BUILTIN)) {
                final List<ObjectType> arguments = new ArrayList<>();
                for (CayTheSourceParser.ExpressionContext expression : ctx.arguments.expression()) {
                    arguments.add(visit(expression));
                }
                final BuiltinType function = (BuiltinType) assignedValue;
                debugger.debug("Call built in function: %s(%s)", identifier, arguments);
                result = function.apply(arguments);
            } else {
                throw newError(ctx.identifier, "Assigned value '%s' is not a function!", identifier);
            }
        }else {
            throw newError(ctx.identifier, "Undefined function '%s'!", identifier);
        }

        debugger.returnValue(result);
        return result;
    }

    @Override
    public ObjectType visitSubscriptExpression(CayTheSourceParser.SubscriptExpressionContext ctx) {
        debugger.debug("Visit subscript expr: %s", ctx.getText().trim());
        final ObjectType value = visit(ctx.identifier);
        final ObjectType index = visit(ctx.index);
        final ObjectType result;

        if (value.isOf(Type.ARRAY)) {
            if (index.isOf(Type.INTEGER)) {
                final ArrayType array = (ArrayType) value;

                if (array.has(index.castToInteger())) {
                    result = array.get(index.castToInteger());
                } else {
                    throw newError(ctx.identifier.start, "Array with identifier '%s' does not have index %d", ctx.identifier.getText(), index.castToInteger().value());
                }
            } else {
                throw newError(ctx.identifier.stop, "Index must be of type integer, given was: %s!", index);
            }
        } else if (value.isOf(Type.HASH)) {
            final HashType hash = (HashType) value;

            if (hash.has(index)) {
                result = hash.get(index);
            } else {
                throw newError(ctx.identifier.start, "Hash with identifier '%s' does not have key '%s'", ctx.identifier.getText(), index);
            }
        } else {
            throw newError(ctx.identifier.getStop(), "Assigned value for identifier '%s' does not allow subscript access!", ctx.identifier.getText());
        }

        debugger.returnValue(result);
        return result;
    }

    @Override
    public ObjectType visitEqualOperation(CayTheSourceParser.EqualOperationContext ctx) {
        debugger.debug("Visit equal operation: %s", ctx.getText().trim());
        final ObjectType left = visit(ctx.firstOperand);
        final String operator = ctx.operator.getText();
        final ObjectType right = visit(ctx.secondOperand);
        debugger.debug("Comparing: %s %s %s", left, operator, right);
        final ObjectType result;

        switch (operator) {
            case "==":
                result = ops.rel().equal(left, right);
                debugger.returnValue(result);
                return result;
            case "!=":
                result = ops.rel().notEqual(left, right);
                return result;
            default:
                throw newUnsupportedOperatorError(ctx.operator);
        }
    }

    @Override
    public ObjectType visitRelationOperation(CayTheSourceParser.RelationOperationContext ctx) {
        debugger.debug("Visit relation operation: %s", ctx.getText().trim());
        final ObjectType left = visit(ctx.firstOperand);
        final ObjectType right = visit(ctx.secondOperand);
        final ObjectType result;

        switch (ctx.operator.getText()) {
            case "<":
                result = ops.rel().lessThan(left, right);
                break;
            case "<=":
                result = ops.rel().lessThanOrEqual(left, right);
                break;
            case ">":
                result = ops.rel().greaterThan(left, right);
                break;
            case ">=":
                result = ops.rel().greaterThanOrEqual(left, right);
                break;
            default:
                throw newUnsupportedOperatorError(ctx.operator);
        }

        debugger.returnValue(result);
        return result;
    }

    @Override
    public ObjectType visitPowerOperation(CayTheSourceParser.PowerOperationContext ctx) {
        debugger.debug("Visit power operation: %s", ctx.getText().trim());
        final ObjectType left = visit(ctx.firstOperand);
        final ObjectType right = visit(ctx.secondOperand);
        final ObjectType result = ops.math().power(left, right);
        debugger.returnValue(result);
        return result;
    }

    @Override
    public ObjectType visitAdditiveOperation(CayTheSourceParser.AdditiveOperationContext ctx) {
        debugger.debug("Visit additive operation: %s", ctx.getText().trim());
        final ObjectType left = visit(ctx.firstOperand);
        final ObjectType right = visit(ctx.secondOperand);
        final ObjectType result;

        switch (ctx.operator.getText()) {
            case "+":
                result = ops.math().add(left, right);
                break;
            case "-":
                result = ops.math().subtract(left, right);
                break;
            default:
                throw newUnsupportedOperatorError(ctx.operator);
        }

        debugger.returnValue(result);
        return result;
    }

    @Override
    public ObjectType visitMultiplicativeOperation(CayTheSourceParser.MultiplicativeOperationContext ctx) {
        debugger.debug("Visit multiplicative operation: %s", ctx.getText().trim());
        final ObjectType left = visit(ctx.firstOperand);
        final ObjectType right = visit(ctx.secondOperand);
        final ObjectType result;

        switch (ctx.operator.getText()) {
            case "*":
                result = ops.math().multiply(left, right);
                break;
            case "/":
                result = ops.math().divide(left, right);
                break;
            case "%":
                result = ops.math().modulo(left, right);
                break;
            default:
                throw newUnsupportedOperatorError(ctx.operator);
        }

        debugger.returnValue(result);
        return result;
    }

    @Override
    public ObjectType visitLogicalOrOperation(CayTheSourceParser.LogicalOrOperationContext ctx) {
        debugger.debug("Visit logical or operation: %s", ctx.getText().trim());
        final ObjectType left = visit(ctx.firstOperand);
        final ObjectType right = visit(ctx.secondOperand);
        final BooleanType result = ops.logic().or(left, right);
        debugger.returnValue(result);
        return result;
    }

    @Override
    public ObjectType visitLogicalAndOperation(CayTheSourceParser.LogicalAndOperationContext ctx) {
        debugger.debug("Visit logical and operation: %s", ctx.getText().trim());
        final ObjectType left = visit(ctx.firstOperand);
        final ObjectType right = visit(ctx.secondOperand);
        final BooleanType result = ops.logic().and(left, right);
        debugger.returnValue(result);
        return result;
    }


    @Override
    public ObjectType visitNegationOperation(CayTheSourceParser.NegationOperationContext ctx) {
        debugger.debug("Visit logical negation operation: %s", ctx.getText().trim());
        final ObjectType operand = visit(ctx.operand);
        final ObjectType result;

        if ("!".equals(ctx.operator.getText())) {
            result = ops.logic().not(operand);
        } else if ("-".equals(ctx.operator.getText())) {
            result = ops.math().negate(operand);
        } else {
            throw newUnsupportedOperatorError(ctx.operator);
        }

        debugger.returnValue(result);
        return result;
    }

    @Override
    public ObjectType visitNullLiteral(CayTheSourceParser.NullLiteralContext ctx) {
        debugger.debug("Visit null literal: %s", ctx.getText().trim());
        final ObjectType value = defaultResult();
        debugger.returnValue(value);
        return value;
    }

    @Override
    public ObjectType visitBooleanLiteral(CayTheSourceParser.BooleanLiteralContext ctx) {
        debugger.debug("Visit boolean literal: %s", ctx.getText().trim());
        final BooleanType value = BooleanType.valueOf(ctx.BOOLEAN().getText());
        debugger.returnValue(value);
        return value;
    }

    @Override
    public ObjectType visitFloatLiteral(CayTheSourceParser.FloatLiteralContext ctx) {
        debugger.debug("Visit float literal: %s", ctx.getText().trim());
        final FloatType value = FloatType.valueOf(ctx.FLOAT().getText());
        debugger.returnValue(value);
        return value;
    }

    @Override
    public ObjectType visitIntegerLiteral(CayTheSourceParser.IntegerLiteralContext ctx) {
        debugger.debug("Visit integer literal: %s", ctx.getText().trim());
        final IntegerType value = IntegerType.valueOf(ctx.INTEGER().getText());
        debugger.returnValue(value);
        return value;
    }

    @Override
    public ObjectType visitStringLiteral(CayTheSourceParser.StringLiteralContext ctx) {
        debugger.debug("Visit string literal: %s", ctx.getText().trim());
        final String text = ctx.STRING().getText();
        final StringType value = new StringType(text.substring(1, text.length() - 1));
        debugger.returnValue(value);
        return value;
    }

    @Override
    public ObjectType visitIdentifierLiteral(CayTheSourceParser.IdentifierLiteralContext ctx) {
        debugger.debug("Visit identifier literal: %s", ctx.getText().trim());
        final String identifier = ctx.IDENTIFIER().getText();
        final ObjectType value;

        if (currentScope.peek().has(identifier)) {
            value = currentScope.peek().get(identifier);
        } else {
            throw newError(ctx.IDENTIFIER().getSymbol(), "There is no variable '%s' declared!", identifier);
        }

        debugger.returnValue(value);
        return value;
    }

    @Override
    public ObjectType visitFunctionLiteral(CayTheSourceParser.FunctionLiteralContext ctx) {
        debugger.debug("Visit function literal: %s", ctx.getText().trim());
        final List<String> parameterIdentifiers = new ArrayList<>();

        if (ctx.arguments != null) {
            for (final TerminalNode identifier : ctx.arguments.IDENTIFIER()) {
                parameterIdentifiers.add(identifier.getText());
            }
        }

        final FunctionType value = new FunctionType(currentScope.peek(), parameterIdentifiers, ctx.statement());
        debugger.returnValue(value);
        return value;
    }

    @Override
    public ObjectType visitArrayLiteral(CayTheSourceParser.ArrayLiteralContext ctx) {
        debugger.debug("Visit array literal: %s", ctx.getText().trim());
        final List<ObjectType> values = new ArrayList<>();

        for (final CayTheSourceParser.ExpressionContext expression : ctx.values.expression()) {
            values.add(visit(expression));
        }

        final ArrayType result = new ArrayType(values);
        debugger.returnValue(result);
        return result;
    }

    @Override
    public ObjectType visitHashLiteral(CayTheSourceParser.HashLiteralContext ctx) {
        debugger.debug("Visit hash literal: %s", ctx.getText().trim());
        final Map<ObjectType, ObjectType> values = new HashMap<>();

        if (ctx.values != null) {
            for (final CayTheSourceParser.HashPairContext pair : ctx.values.hashPair()) {
                final ObjectType key = visit(pair.key);

                if (values.containsKey(key)) {
                    throw newError(pair.key.getStart(), "Duplicate key given '%s'!", key.inspect());
                }

                if (pair.value == null) {
                    throw newError(pair.key.getStart(), "Missing value for key '%s'!", key.inspect());
                }

                values.put(key, visit(pair.value));
            }
        }

        final HashType result = new HashType(values);
        debugger.returnValue(result);
        return result;
    }

}
