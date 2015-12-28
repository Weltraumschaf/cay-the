package de.weltraumschaf.caythe.backend.interpreter;

import de.weltraumschaf.caythe.backend.KernelApi;
import de.weltraumschaf.caythe.backend.env.Environment;
import de.weltraumschaf.caythe.backend.SyntaxError;
import de.weltraumschaf.caythe.backend.symtab.ConstantSymbol;
import de.weltraumschaf.caythe.backend.symtab.Scope;
import de.weltraumschaf.caythe.backend.symtab.Symbol;
import de.weltraumschaf.caythe.backend.symtab.SymbolTable;
import de.weltraumschaf.caythe.backend.symtab.Type;
import de.weltraumschaf.caythe.backend.symtab.Value;
import de.weltraumschaf.caythe.backend.symtab.VariableSymbol;
import de.weltraumschaf.caythe.frontend.CayTheBaseVisitor;
import de.weltraumschaf.caythe.frontend.CayTheParser;
import de.weltraumschaf.caythe.frontend.CayTheParser.*;
import de.weltraumschaf.caythe.log.Logger;
import de.weltraumschaf.caythe.log.Logging;
import de.weltraumschaf.commons.validate.Validate;
import java.util.ArrayList;
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
public final class Interpreter extends CayTheBaseVisitor<Value> {

    private final SymbolTable table = SymbolTable.newTable();
    private final KernelApi kernel;
    private Deque<Scope> scopeStack = new LinkedList<>();
    private final Logger logging;

    public Interpreter(final Environment env) {
        this(env, Logging.newBuffered());
    }

    public Interpreter(final Environment env, final Logger logging) {
        super();
        this.kernel = new KernelApi(env);
        this.logging = Validate.notNull(logging, "logging");
        this.scopeStack.push(table.getGlobals());
    }

    @Override
    protected Value defaultResult() {
        return Value.NIL;
    }

    @Override
    public Value visitCompilationUnit(final CompilationUnitContext ctx) {
        log("Visit compilation unit: '%s'", ctx.getText());
        final ParseTree compilationUnit = ctx.getChild(0);

        if (null == compilationUnit) {
            return defaultResult();
        }

        return visit(compilationUnit);
    }

    @Override
    public Value visitStatement(final StatementContext ctx) {
        log("Visit statement: '%s'", ctx.getText());
        final ParseTree statement = ctx.getChild(0);

        if (null == statement) {
            return defaultResult();
        }

        return visit(statement);
    }

    @Override
    public Value visitBlock(final BlockContext ctx) {
        scopeStack.push(Scope.newLocal(scopeStack.peek()));
        final Value value = super.visit(ctx.blockStatements);
        scopeStack.pop();
        return value;
    }

    @Override
    public Value visitConstantDeclaration(final ConstantDeclarationContext ctx) {
        log("Visit constant decl: '%s'", ctx.getText());
        final String identifier = ctx.id.getText();

        if (scopeStack.peek().isDefined(identifier)) {
            throw error(ctx.id, "Constant '%s' already declared", identifier);
        }

        final String typeIdentifier = ctx.type.getText();

        if (!scopeStack.peek().isDefined(typeIdentifier)) {
            throw error(ctx.type, "Unknown type '%s'", typeIdentifier);
        }

        final Symbol type = scopeStack.peek().resolve(typeIdentifier);
        final Value value = visit(ctx.value);

        if (!value.isType((Type) type)) {
            throw error(ctx.value.start, "Can't assign %s to constant with type %s!", value, type);
        }

        final ConstantSymbol symbol = new ConstantSymbol(identifier, value.getType());
        log("Declare constant: %s = %s", symbol, value);
        scopeStack.peek().define(symbol);
        scopeStack.peek().store(symbol, value);
        return value;
    }

    @Override
    public Value visitVariableDeclaration(final VariableDeclarationContext ctx) {
        log("Visit var decl: '%s'", ctx.getText());
        final String identifier = ctx.id.getText();

        if (scopeStack.peek().isDefined(identifier)) {
            throw error(ctx.id, "Variable '%s' already declared", identifier);
        }

        final String typeIdentifier = ctx.type.getText();

        if (!scopeStack.peek().isDefined(typeIdentifier)) {
            throw error(ctx.type, "Unknown type '%s'", typeIdentifier);
        }

        final Symbol type = scopeStack.peek().resolve(typeIdentifier);
        final Value value;

        if (null == ctx.value) {
            value = Value.NIL.asType((Type) type);
        } else {
            value = visit(ctx.value);

            if (!value.isType((Type) type)) {
                throw error(ctx.value.start, "Can't assign %s to variable with type %s!", value, type);
            }
        }

        final VariableSymbol symbol = new VariableSymbol(identifier, value.getType());
        log("Declare variable: %s = %s", symbol, value);
        scopeStack.peek().define(symbol);
        scopeStack.peek().store(symbol, value);
        return value;
    }

    @Override
    public Value visitAssignment(final AssignmentContext ctx) {
        final String identifier = ctx.id.getText();

        if (!scopeStack.peek().isDefined(identifier)) {
            throw error(ctx.id, "Unknown variable '%s'", ctx.id.getText());
        }

        final Symbol symbol = scopeStack.peek().resolve(identifier);
        final Value value = visit(ctx.value);

        try {
            log("Assign variable: %s = %s", symbol, value);
            symbol.getScope().store(symbol, value);
        } catch (final IllegalStateException ex) {
            throw error(ctx.id, "Can't write constant '%s'", identifier);
        }

        return value;
    }

    @Override
    public Value visitFunctionCall(final FunctionCallContext ctx) {
        final String functionName = ctx.id.getText();
        final List<Value> functionArgs = new ArrayList<>();

        for (final OrExpressionContext args : ctx.args) {
            functionArgs.add(visit(args));
        }

        log("Calling function %s(%s)", functionName, functionArgs);

        if (functionArgs.size() == 1) {
            final Value value = functionArgs.get(0);

            if (null != functionName) {
                switch (functionName) {
                    case "print":
                        kernel.print(value.asString());
                        break;
                    case "println":
                        kernel.println(value.asString());
                        break;
                    case "error":
                        kernel.error(value.asString());
                        break;
                    case "errorln":
                        kernel.errorln(value.asString());
                        break;
                }
            }
        }

        return defaultResult();
    }

    @Override
    public Value visitIfBranch(final IfBranchContext ctx) {
        if (visit(ctx.ifCondition).asBool() && notNull(ctx.ifBlock)) {
            visit(ctx.ifBlock);
        } else if (notNull(ctx.elseIfCondition) && visit(ctx.elseIfCondition).asBool() && notNull(ctx.elseIfBlock)) {
            visit(ctx.elseIfBlock);
        } else if (notNull(ctx.elseBlock)) {
            visit(ctx.elseBlock);
        }

        return defaultResult();
    }

    @Override
    public Value visitWhileLoop(final WhileLoopContext ctx) {
        Value condition = visit(ctx.condition);

        while (condition.asBool()) {
            visit(ctx.block());
            condition = visit(ctx.condition);
        }

        return defaultResult();
    }

    @Override
    public Value visitOrExpression(final OrExpressionContext ctx) {
        final Value left = visit(ctx.left);

        if (null == ctx.right) {
            return left;
        }

        final Value right = visit(ctx.right);
        log("Boolean: %s || %s", left, right);
        return new BoolOperations().or(left, right);
    }

    @Override
    public Value visitAndExpression(final AndExpressionContext ctx) {
        final Value left = visit(ctx.left);

        if (null == ctx.right) {
            return left;
        }

        final Value right = visit(ctx.right);
        log("Boolean: %s && %s", left, right);
        return new BoolOperations().and(left, right);
    }

    @Override
    public Value visitEqualExpression(final EqualExpressionContext ctx) {
        final Value left = visit(ctx.left);

        if (null == ctx.right) {
            return left;
        }

        final Value right = visit(ctx.right);
        final Comparator compare = new Comparator();

        switch (ctx.operator.getType()) {
            case CayTheParser.EQUAL:
                log("Compare: %s == %s", left, right);
                return compare.equal(left, right);
            case CayTheParser.NOT_EQUAL:
                log("Compare: %s != %s", left, right);
                return compare.notEqual(left, right);
            default:
                throw error(ctx.operator, "Unsupported operator '%s'", ctx.operator.getText());
        }
    }

    @Override
    public Value visitRelationExpression(final RelationExpressionContext ctx) {
        final Value left = visit(ctx.left);

        if (null == ctx.right) {
            return left;
        }

        final Value right = visit(ctx.right);
        final Comparator compare = new Comparator();

        switch (ctx.operator.getType()) {
            case CayTheParser.GREATER_THAN:
                log("Compare: %s > %s", left, right);
                return compare.greaterThan(left, right);
            case CayTheParser.LESS_THAN:
                log("Compare: %s < %s", left, right);
                return compare.lessThan(left, right);
            case CayTheParser.GREATER_EQUAL:
                log("Compare: %s >= %s", left, right);
                return compare.greaterEqual(left, right);
            case CayTheParser.LESS_EQUAL:
                log("Compare: %s <= %s", left, right);
                return compare.lessEqual(left, right);
            default:
                throw error(ctx.operator, "Unsupported operator '%s'", ctx.operator.getText());
        }
    }

    @Override
    public Value visitSimpleExpression(final SimpleExpressionContext ctx) {
        final Value left = visit(ctx.left);

        if (null == ctx.right) {
            return left;
        }

        final Value right = visit(ctx.right);
        final MathOperations math = new MathOperations();

        switch (ctx.operator.getType()) {
            case CayTheParser.ADD:
                log("Math: %s + %s", left, right);
                return math.add(left, right);
            case CayTheParser.SUB:
                log("Math: %s - %s", left, right);
                return math.sub(left, right);
            default:
                throw error(ctx.operator, "Unsupported operator '%s'", ctx.operator.getText());
        }
    }

    @Override
    public Value visitTerm(final TermContext ctx) {
        final Value left = visit(ctx.left);

        if (null == ctx.right) {
            return left;
        }

        final Value right = visit(ctx.right);
        final MathOperations math = new MathOperations();

        switch (ctx.operator.getType()) {
            case CayTheParser.MUL:
                log("Math: %s * %s", left, right);
                return math.mul(left, right);
            case CayTheParser.DIV:
                log("Math: %s / %s", left, right);
                return math.div(left, right);
            case CayTheParser.MOD:
                log("Math: %s % %s", left, right);
                return math.mod(left, right);
            default:
                throw error(ctx.operator, "Unsupported operator '%s'", ctx.operator.getText());
        }
    }

    @Override
    public Value visitFactor(final FactorContext ctx) {
        final Value base = visit(ctx.base);

        if (null == ctx.exponent) {
            return base;
        }

        return new MathOperations().pow(base, visit(ctx.exponent));
    }

    @Override
    public Value visitNegation(final NegationContext ctx) {
        final Value atom = visit(ctx.atom());
        log("Boolean: ! %s", atom);
        return new BoolOperations().not(atom);
    }

    @Override
    public Value visitVariableOrConstantDereference(final VariableOrConstantDereferenceContext ctx) {
        final String identifier = ctx.id.getText();

        if (!scopeStack.peek().isDefined(identifier)) {
            throw error(ctx.id, "Access of undeclared variable/constant '%s'", ctx.id.getText());
        }

        return scopeStack.peek().resolve(identifier).load();
    }

    @Override
    public Value visitConstant(final ConstantContext ctx) {
        final String literal = ctx.value.getText();
        log("Visit literal: '%s'", literal);

        if (null != ctx.BOOL_VALUE()) {
            log("Recognized bool value.");
            return Value.newBool(Boolean.parseBoolean(literal));
        } else if (null != ctx.FLOAT_VALUE()) {
            log("Recognized float value.");
            return Value.newFloat(Float.parseFloat(literal));
        } else if (null != ctx.INTEGER_VALUE()) {
            log("Recognized integer value.");
            return Value.newInt(Integer.parseInt(literal));
        } else if (null != ctx.STRING_VALUE()) {
            log("Recognized string value.");
            return Value.newString(literal.substring(1, literal.length() - 1));
        } else {
            throw error(ctx.value, "Unrecognized literal '%s'", literal);
        }
    }

    private void log(final String msg, final Object... args) {
        logging.log(String.format("[%d] %s", scopeStack.size(), msg), args);
    }

    private boolean notNull(final Object input) {
        return null != input;
    }

    private SyntaxError error(final Token token, final String msg, final Object... args) {
        return new SyntaxError(String.format(msg, args), token);
    }
}
