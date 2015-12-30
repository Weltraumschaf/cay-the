package de.weltraumschaf.caythe.backend.interpreter;

import de.weltraumschaf.caythe.backend.env.Environment;
import de.weltraumschaf.caythe.backend.SyntaxError;
import de.weltraumschaf.caythe.backend.symtab.*;
import de.weltraumschaf.caythe.frontend.CayTheBaseVisitor;
import de.weltraumschaf.caythe.frontend.CayTheParser;
import de.weltraumschaf.caythe.frontend.CayTheParser.*;
import de.weltraumschaf.caythe.log.Logger;
import de.weltraumschaf.caythe.log.Logging;
import de.weltraumschaf.commons.validate.Validate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * Implementation which interprets the parsed tree.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class Interpreter extends CayTheBaseVisitor<ReturnValues> {

    private final Deque<List<ConstantSymbol>> functionArgumentStack = new LinkedList<>();
    private final BuildInApiDispatcher nativeApi;
    private final SymbolTable table;
    private final Logger logging;

    public Interpreter(final Environment env) {
        this(env, SymbolTable.newTable());
    }

    public Interpreter(final Environment env, final SymbolTable table) {
        this(env, table, Logging.newBuffered());
    }

    public Interpreter(final Environment env, final SymbolTable table, final Logger logging) {
        super();
        this.table = Validate.notNull(table, "table");
        this.logging = Validate.notNull(logging, "logging");
        this.nativeApi = new BuildInApiDispatcher(env);
    }

    @Override
    protected ReturnValues defaultResult() {
        return ReturnValues.NOTHING;
    }

    private ReturnValues newResult(final Collection<Value>  values) {
        return new ReturnValues(values);
    }

    private ReturnValues newResult(final Value ... values) {
        return new ReturnValues(values);
    }

    @Override
    public ReturnValues visitCompilationUnit(final CompilationUnitContext ctx) {
        log("Visit compilation unit: '%s'", ctx.getText());
        final ParseTree compilationUnit = ctx.getChild(0);

        if (null == compilationUnit) {
            return defaultResult();
        }

        return visit(compilationUnit);
    }

    @Override
    public ReturnValues visitStatement(final StatementContext ctx) {
        log("Visit statement: '%s'", ctx.getText());
        final ParseTree statement = ctx.getChild(0);

        if (null == statement) {
            return defaultResult();
        }

        return visit(statement);
    }

    @Override
    public ReturnValues visitReturnStatement(final ReturnStatementContext ctx) {
        final List<OrExpressionContext> ret = ctx.ret;

        if (ret.isEmpty()) {
            return defaultResult();
        }

        final Collection<Value> accumulator = new ArrayList<>();

        ret.stream().forEach((expression) -> {
            accumulator.addAll(visit(expression).get());
        });

        return newResult(accumulator);
    }

    @Override
    public ReturnValues visitBlock(final BlockContext ctx) {
        table.pushNewScope();
        final ReturnValues value = super.visit(ctx.blockStatements);
        table.popScope();
        return value;
    }

    @Override
    public ReturnValues visitConstantDeclaration(final ConstantDeclarationContext ctx) {
        log("Visit constant decl: '%s'", ctx.getText());
        final String identifier = ctx.id.getText();

        if (table.currentScope().isValueDefined(identifier)) {
            throw error(ctx.id, "Constant '%s' already declared", identifier);
        }

        final String typeIdentifier = ctx.type.getText();

        if (!table.currentScope().isValueDefined(typeIdentifier)) {
            throw error(ctx.type, "Unknown type '%s'", typeIdentifier);
        }

        final Symbol type = table.currentScope().resolveValue(typeIdentifier);
        final ReturnValues value = visit(ctx.value);

        if (!value.getFirst().isOfType((Type) type)) {
            throw error(ctx.value.start, "Can't assign %s to constant with type %s!", value, type);
        }

        final ConstantSymbol symbol = new ConstantSymbol(identifier, value.getFirst().getType());
        log("Declare constant: %s = %s", symbol, value);
        table.currentScope().defineValue(symbol);
        table.currentScope().store(symbol, value.getFirst());
        return value;
    }

    @Override
    public ReturnValues visitVariableDeclaration(final VariableDeclarationContext ctx) {
        log("Visit var decl: '%s'", ctx.getText());
        final String identifier = ctx.id.getText();

        if (table.currentScope().isValueDefined(identifier)) {
            throw error(ctx.id, "Variable '%s' already declared", identifier);
        }

        final String typeIdentifier = ctx.type.getText();

        if (!table.currentScope().isValueDefined(typeIdentifier)) {
            throw error(ctx.type, "Unknown type '%s'", typeIdentifier);
        }

        final Symbol type = table.currentScope().resolveValue(typeIdentifier);
        final ReturnValues value;

        if (null == ctx.value) {
            value = defaultResult();
        } else {
            value = visit(ctx.value);

            if (!value.getFirst().isOfType((Type) type)) {
                throw error(ctx.value.start, "Can't assign %s to variable with type %s!", value, type);
            }
        }

        final VariableSymbol symbol = new VariableSymbol(identifier, value.getFirst().getType());
        log("Declare variable: %s = %s", symbol, value);
        table.currentScope().defineValue(symbol);
        table.currentScope().store(symbol, value.getFirst());
        return value;
    }

    @Override
    public ReturnValues visitAssignment(final AssignmentContext ctx) {
        final String identifier = ctx.id.getText();

        if (!table.currentScope().isValueDefined(identifier)) {
            throw error(ctx.id, "Unknown variable '%s'", ctx.id.getText());
        }

        final Symbol symbol = table.currentScope().resolveValue(identifier);
        final ReturnValues value = visit(ctx.value);

        try {
            log("Assign variable: %s = %s", symbol, value);
            symbol.getScope().store(symbol, value.getFirst());
        } catch (final IllegalStateException ex) {
            throw error(ctx.id, "Can't write constant '%s'", identifier);
        }

        return value;
    }

    @Override
    public ReturnValues visitFunctionDeclaration(final FunctionDeclarationContext ctx) {
        final String functionName = ctx.id.getText();

        if (table.currentScope().isFunctionDefined(functionName)) {
            throw error(ctx.id, "Function '%s' is already declared!", functionName);
        }

        final List<Type> returnTypes = new ArrayList<>();

        for (final Token t : ctx.returnTypes) {
            final Symbol type = table.currentScope().resolveValue(t.getText());

            if (type instanceof Type) {
                returnTypes.add((Type) type);
            } else {
                throw error(t, "Not a type '%s'! Return type must be a type.", type.getName());
            }
        }

        functionArgumentStack.push(new ArrayList<>());

        ctx.args.stream().forEach((argument) -> {
            visit(argument);
        });

        final FunctionSymbol function = new FunctionSymbol(
            functionName,
            returnTypes,
            functionArgumentStack.pop(),
            table.currentScope());
        function.body(ctx.body);
        table.currentScope().defineFunction(function);
        return defaultResult();
    }

    @Override
    public ReturnValues visitFormalArgument(final FormalArgumentContext ctx) {
        final String typeName = ctx.type.getText();

        if (!table.currentScope().isValueDefined(typeName)) {
            throw error(ctx.type, "Undefined type '%s'!", typeName);
        }

        final Symbol type = table.currentScope().resolveValue(typeName);

        if (!(type instanceof Type)) {
            throw error(ctx.type, "Expectibg  type but '%s' is not a type!", typeName);
        }

        functionArgumentStack.peek().add(
            new ConstantSymbol(ctx.id.getText(), (Type) type)
        );

        return defaultResult();
    }

    @Override
    public ReturnValues visitFunctionCall(final FunctionCallContext ctx) {
        final String functionName = ctx.id.getText();
        final List<Value> functionArgs = new ArrayList<>();
        ctx.args.stream().forEach((args) -> {
            functionArgs.addAll(visit(args).get());
        });

        log("Calling function %s(%s)", functionName, functionArgs);
        final ReturnValues result;

        if (table.isBuildInFunction(functionName)) {
            log("Native function call.");
            final FunctionSymbol function = table.globalScope().resolveFunction(functionName);
            result = nativeApi.invoke(function, functionArgs);
        } else {
            final FunctionSymbol function = table.currentScope().resolveFunction(functionName);
            table.pushScope(function);
            result = function.evaluate(this, functionArgs);
            table.popScope();
        }

//        return result; // TODO implement return.
        return defaultResult();
    }

    @Override
    public ReturnValues visitIfBranch(final IfBranchContext ctx) {
        if (visit(ctx.ifCondition).getFirst().asBool() && notNull(ctx.ifBlock)) {
            visit(ctx.ifBlock);
        } else if (notNull(ctx.elseIfCondition) && visit(ctx.elseIfCondition).getFirst().asBool() && notNull(ctx.elseIfBlock)) {
            visit(ctx.elseIfBlock);
        } else if (notNull(ctx.elseBlock)) {
            visit(ctx.elseBlock);
        }

        return defaultResult();
    }

    @Override
    public ReturnValues visitWhileLoop(final WhileLoopContext ctx) {
        Value condition = visit(ctx.condition).getFirst();

        while (condition.asBool()) {
            visit(ctx.block());
            condition = visit(ctx.condition).getFirst();
        }

        return defaultResult();
    }

    @Override
    public ReturnValues visitOrExpression(final OrExpressionContext ctx) {
        final Value left = visit(ctx.left).getFirst();

        if (null == ctx.right) {
            return new ReturnValues(left);
        }

        final Value right = visit(ctx.right).getFirst();
        log("Boolean: %s || %s", left, right);
        return new ReturnValues(new BoolOperations().or(left, right));
    }

    @Override
    public ReturnValues visitAndExpression(final AndExpressionContext ctx) {
        final Value left = visit(ctx.left).getFirst();

        if (null == ctx.right) {
            return new ReturnValues(left);
        }

        final Value right = visit(ctx.right).getFirst();
        log("Boolean: %s && %s", left, right);
        return new ReturnValues(new BoolOperations().and(left, right));
    }

    @Override
    public ReturnValues visitEqualExpression(final EqualExpressionContext ctx) {
        final Value left = visit(ctx.left).getFirst();

        if (null == ctx.right) {
            return newResult(left);
        }

        final Value right = visit(ctx.right).getFirst();
        final Comparator compare = new Comparator();

        switch (ctx.operator.getType()) {
            case CayTheParser.EQUAL:
                log("Compare: %s == %s", left, right);
                return newResult(compare.equal(left, right));
            case CayTheParser.NOT_EQUAL:
                log("Compare: %s != %s", left, right);
                return newResult(compare.notEqual(left, right));
            default:
                throw error(ctx.operator, "Unsupported operator '%s'", ctx.operator.getText());
        }
    }

    @Override
    public ReturnValues visitRelationExpression(final RelationExpressionContext ctx) {
        final Value left = visit(ctx.left).getFirst();

        if (null == ctx.right) {
            return newResult(left);
        }

        final Value right = visit(ctx.right).getFirst();
        final Comparator compare = new Comparator();

        switch (ctx.operator.getType()) {
            case CayTheParser.GREATER_THAN:
                log("Compare: %s > %s", left, right);
                return newResult(compare.greaterThan(left, right));
            case CayTheParser.LESS_THAN:
                log("Compare: %s < %s", left, right);
                return newResult(compare.lessThan(left, right));
            case CayTheParser.GREATER_EQUAL:
                log("Compare: %s >= %s", left, right);
                return newResult(compare.greaterEqual(left, right));
            case CayTheParser.LESS_EQUAL:
                log("Compare: %s <= %s", left, right);
                return newResult(compare.lessEqual(left, right));
            default:
                throw error(ctx.operator, "Unsupported operator '%s'", ctx.operator.getText());
        }
    }

    @Override
    public ReturnValues visitSimpleExpression(final SimpleExpressionContext ctx) {
        final Value left = visit(ctx.left).getFirst();

        if (null == ctx.right) {
            return newResult(left);
        }

        final Value right = visit(ctx.right).getFirst();
        final MathOperations math = new MathOperations();

        switch (ctx.operator.getType()) {
            case CayTheParser.ADD:
                log("Math: %s + %s", left, right);
                return newResult(math.add(left, right));
            case CayTheParser.SUB:
                log("Math: %s - %s", left, right);
                return newResult(math.sub(left, right));
            default:
                throw error(ctx.operator, "Unsupported operator '%s'", ctx.operator.getText());
        }
    }

    @Override
    public ReturnValues visitTerm(final TermContext ctx) {
        final Value left = visit(ctx.left).getFirst();

        if (null == ctx.right) {
            return newResult(left);
        }

        final Value right = visit(ctx.right).getFirst();
        final MathOperations math = new MathOperations();

        switch (ctx.operator.getType()) {
            case CayTheParser.MUL:
                log("Math: %s * %s", left, right);
                return newResult(math.mul(left, right));
            case CayTheParser.DIV:
                log("Math: %s / %s", left, right);
                return newResult(math.div(left, right));
            case CayTheParser.MOD:
                log("Math: %s % %s", left, right);
                return newResult(math.mod(left, right));
            default:
                throw error(ctx.operator, "Unsupported operator '%s'", ctx.operator.getText());
        }
    }

    @Override
    public ReturnValues visitFactor(final FactorContext ctx) {
        final Value base = visit(ctx.base).getFirst();

        if (null == ctx.exponent) {
            return newResult(base);
        }

        return newResult(new MathOperations().pow(base, visit(ctx.exponent).getFirst()));
    }

    @Override
    public ReturnValues visitNegation(final NegationContext ctx) {
        final Value atom = visit(ctx.atom()).getFirst();
        log("Boolean: ! %s", atom);
        return newResult(new BoolOperations().not(atom));
    }

    @Override
    public ReturnValues visitVariableOrConstantDereference(final VariableOrConstantDereferenceContext ctx) {
        final String identifier = ctx.id.getText();

        if (!table.currentScope().isValueDefined(identifier)) {
            throw error(ctx.id, "Access of undeclared variable/constant '%s'", ctx.id.getText());
        }

        return newResult(table.currentScope().resolveValue(identifier).load());
    }

    @Override
    public ReturnValues visitConstant(final ConstantContext ctx) {
        final String literal = ctx.value.getText();
        log("Visit literal: '%s'", literal);

        if (null != ctx.BOOL_VALUE()) {
            log("Recognized bool value.");
            return newResult(Value.newBool(Boolean.parseBoolean(literal)));
        } else if (null != ctx.FLOAT_VALUE()) {
            log("Recognized float value.");
            return newResult(Value.newFloat(Float.parseFloat(literal)));
        } else if (null != ctx.INTEGER_VALUE()) {
            log("Recognized integer value.");
            return newResult(Value.newInt(Integer.parseInt(literal)));
        } else if (null != ctx.STRING_VALUE()) {
            log("Recognized string value.");
            return newResult(Value.newString(literal.substring(1, literal.length() - 1)));
        } else {
            throw error(ctx.value, "Unrecognized literal '%s'", literal);
        }
    }

    private void log(final String msg, final Object... args) {
        logging.log(String.format("[%d] %s", table.scopeDepth(), msg), args);
    }

    private boolean notNull(final Object input) {
        return null != input;
    }

    private SyntaxError error(final Token token, final String msg, final Object... args) {
        return new SyntaxError(String.format(msg, args), token);
    }
}
