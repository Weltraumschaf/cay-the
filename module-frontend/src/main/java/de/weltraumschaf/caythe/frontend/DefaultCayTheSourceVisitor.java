package de.weltraumschaf.caythe.frontend;

import de.weltraumschaf.caythe.frontend.experimental.Debugger;
import de.weltraumschaf.caythe.frontend.experimental.Environment;
import de.weltraumschaf.caythe.frontend.experimental.operations.Operations;
import de.weltraumschaf.caythe.frontend.experimental.types.*;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Default implementation which converts the parsed tree from a source file into intermediate model.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class DefaultCayTheSourceVisitor extends CayTheSourceBaseVisitor<ObjectType> {

    private final Debugger debugger = new Debugger().on();
    private final Operations ops = new Operations();
    private final Environment rootScope = new Environment();

    @Override
    protected ObjectType defaultResult() {
        return NullType.NULL;
    }

    @Override
    public ObjectType visitLetStatement(CayTheSourceParser.LetStatementContext ctx) {
        debugger.debug("Visit let statement: %s", ctx.getText());
        final CayTheSourceParser.AssignStatementContext assignment = ctx.assignStatement();
        final String identifier;
        final ObjectType value;

        if (null == assignment) {
            // This is let w/o assignment: let a;
            identifier = ctx.IDENTIFIER().getText();
            value = NullType.NULL;
        } else {
            // This is let w/ assignment: let a = 1 + 2;
            identifier = assignment.IDENTIFIER().getText();

            if (rootScope.has(identifier)) {
                throw new RuntimeException("Variable with identifier '" + identifier + "' already defined!");
            }

            value = visit(assignment.expression());
            debugger.debug("Set variable with identifier '%s' with value: ", identifier, value);
        }

        rootScope.set(identifier, value);
        return defaultResult();
    }

    @Override
    public ObjectType visitIfExpression(CayTheSourceParser.IfExpressionContext ctx) {
        debugger.debug("Visit if expression: %s", ctx.getText());
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
    public ObjectType visitEqualOperation(CayTheSourceParser.EqualOperationContext ctx) {
        debugger.debug("Visit equal operation: %s", ctx.getText());
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
                throw new RuntimeException("Unsupported operator: " + ctx.operator.getText() + "!");
        }
    }

    @Override
    public ObjectType visitRelationOperation(CayTheSourceParser.RelationOperationContext ctx) {
        debugger.debug("Visit relation operation: %s", ctx.getText());
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
                result = ops.rel().greaterThan(left, right);;
                break;
            case ">=":
                result = ops.rel().greaterThanOrEqual(left, right);
                break;
            default:
                throw new UnsupportedOperationException("Unknown operator " + ctx.operator.getText() + "!");
        }

        debugger.returnValue(result);
        return result;
    }

    @Override
    public ObjectType visitPowerOperation(CayTheSourceParser.PowerOperationContext ctx) {
        debugger.debug("Visit power operation: %s", ctx.getText());
        final ObjectType left = visit(ctx.firstOperand);
        final ObjectType right = visit(ctx.secondOperand);
        final ObjectType result = ops.math().power(left, right);
        debugger.returnValue(result);
        return result;
    }

    @Override
    public ObjectType visitAdditiveOperation(CayTheSourceParser.AdditiveOperationContext ctx) {
        debugger.debug("Visit additive operation: %s", ctx.getText());
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
                throw new UnsupportedOperationException("Unknown operator " + ctx.operator.getText() + "!");
        }

        debugger.returnValue(result);
        return result;
    }

    @Override
    public ObjectType visitMultiplicativeOperation(CayTheSourceParser.MultiplicativeOperationContext ctx) {
        debugger.debug("Visit multiplicative operation: %s", ctx.getText());
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
                throw new UnsupportedOperationException("Unknown operator " + ctx.operator.getText() + "!");
        }

        debugger.returnValue(result);
        return result;
    }

    @Override
    public ObjectType visitLogicalOrOperation(CayTheSourceParser.LogicalOrOperationContext ctx) {
        debugger.debug("Visit logical or operation: %s", ctx.getText());
        final ObjectType left = visit(ctx.firstOperand);
        final ObjectType right = visit(ctx.secondOperand);
        final BooleanType result = ops.logic().or(left, right);
        debugger.returnValue(result);
        return result;
    }

    @Override
    public ObjectType visitLogicalAndOperation(CayTheSourceParser.LogicalAndOperationContext ctx) {
        debugger.debug("Visit logical and operation: %s", ctx.getText());
        final ObjectType left = visit(ctx.firstOperand);
        final ObjectType right = visit(ctx.secondOperand);
        final BooleanType result = ops.logic().and(left, right);
        debugger.returnValue(result);
        return result;
    }


    @Override
    public ObjectType visitNegationOperation(CayTheSourceParser.NegationOperationContext ctx) {
        debugger.debug("Visit logical negation operation: %s", ctx.getText());
        final ObjectType operand = visit(ctx.operand);
        final ObjectType result;

        if ("!".equals(ctx.operator.getText())) {
            result = ops.logic().not(operand);
        } else if ("-".equals(ctx.operator.getText())) {
            result = ops.math().negate(operand);
        } else {
            throw new UnsupportedOperationException("Unknown operator " + ctx.operator.getText() + "!");
        }

        debugger.returnValue(result);
        return result;
    }

    @Override
    public ObjectType visitNullLiteral(CayTheSourceParser.NullLiteralContext ctx) {
        debugger.debug("Visit null literal: %s", ctx.getText());
        final ObjectType value = defaultResult();
        debugger.returnValue(value);
        return value;
    }

    @Override
    public ObjectType visitBooleanLiteral(CayTheSourceParser.BooleanLiteralContext ctx) {
        debugger.debug("Visit boolean literal: %s", ctx.getText());
        final BooleanType value = BooleanType.valueOf(ctx.BOOLEAN().getText());
        debugger.returnValue(value);
        return value;
    }

    @Override
    public ObjectType visitFloatLiteral(CayTheSourceParser.FloatLiteralContext ctx) {
        debugger.debug("Visit float literal: %s", ctx.getText());
        final FloatType value = FloatType.valueOf(ctx.FLOAT().getText());
        debugger.returnValue(value);
        return value;
    }

    @Override
    public ObjectType visitIntegerLiteral(CayTheSourceParser.IntegerLiteralContext ctx) {
        debugger.debug("Visit integer literal: %s", ctx.getText());
        final IntegerType value = IntegerType.valueOf(ctx.INTEGER().getText());
        debugger.returnValue(value);
        return value;
    }

    @Override
    public ObjectType visitStringLiteral(CayTheSourceParser.StringLiteralContext ctx) {
        debugger.debug("Visit string literal: %s", ctx.getText());
        final String text = ctx.STRING().getText();
        final StringType value = new StringType(text.substring(1, text.length() - 1));
        debugger.returnValue(value);
        return value;
    }

    @Override
    public ObjectType visitIdentifierLiteral(CayTheSourceParser.IdentifierLiteralContext ctx) {
        debugger.debug("Visit identifier literal: %s", ctx.getText());
        final String identifier = ctx.IDENTIFIER().getText();
        final ObjectType value = rootScope.get(identifier);

        if (value.isOf(Type.NULL)) {
            throw new RuntimeException("There is no variable " + identifier + " declared!");
        }

        debugger.returnValue(value);
        return value;
    }

    @Override
    public ObjectType visitFunctionLiteral(CayTheSourceParser.FunctionLiteralContext ctx) {
        debugger.debug("Visit function literal: %s", ctx.getText());
        final Collection<String> parameterIdentifiers = new ArrayList<>();

        if (ctx.arguments != null) {
            for (final TerminalNode identifier : ctx.arguments.IDENTIFIER()) {
                parameterIdentifiers.add(identifier.getText());
            }
        }

        final FunctionType value = new FunctionType(rootScope.createChild(), parameterIdentifiers, ctx.body);
        debugger.returnValue(value);
        return value;
    }
}
