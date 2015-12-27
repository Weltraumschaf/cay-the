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
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * Implementation which interprets the parsed tree.
 *
 * @since 1.0.0
 */
public final class Interpreter extends CayTheBaseVisitor<Value> {

    private final StringBuilder log = new StringBuilder();
    private final SymbolTable table = new SymbolTable();
    private final Scope current = table.getGlobals();
    private final KernelApi kernel;

    public Interpreter(final Environment env) {
        super();
        kernel = new KernelApi(env);
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
    public Value visitConstantDeclaration(final ConstantDeclarationContext ctx) {
        log("Visit constant decl: '%s'", ctx.getText());
        final String identifier = ctx.id.getText();

        if (current.isDefined(identifier)) {
            throw error(ctx.id, "Constant '%s' already declared", identifier);
        }

        final Value value = visit(ctx.value);
        final ConstantSymbol symbol = new ConstantSymbol(identifier, value.getType());
        current.define(symbol);
        current.store(symbol, value);
        return value;
    }

    @Override
    public Value visitVariableDeclaration(final VariableDeclarationContext ctx) {
        log("Visit var decl: '%s'", ctx.getText());
        final String identifier = ctx.id.getText();

        if (current.isDefined(identifier)) {
            throw error(ctx.id, "Variable '%s' already declared", identifier);
        }

        final Value value;

        if (null == ctx.value) {
            value = Value.NIL;
        } else {
            value = visit(ctx.value);
        }

        final VariableSymbol symbol = new VariableSymbol(identifier, value.getType());
        current.define(symbol);
        current.store(symbol, value);
        return value;
    }

    @Override
    public Value visitAssignment(final AssignmentContext ctx) {
        final String identifier = ctx.id.getText();

        if (!current.isDefined(identifier)) {
            throw error(ctx.id, "Unknown variable '%s'", ctx.id.getText());
        }

        final Symbol symbol = current.resolve(identifier);
        final Value value = visit(ctx.value);

        try {
            current.store(symbol, value);
        } catch (final IllegalStateException ex) {
            throw error(ctx.id, "Can't write constant '%s'", identifier);
        }

        return value;
    }

    @Override
    public Value visitPrintStatement(final PrintStatementContext ctx) {
        final Value value = visit(ctx.value);
        kernel.print(value.asString());
        return value;
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
        return new BoolOperations().or(left, right);
    }

    @Override
    public Value visitAndExpression(final AndExpressionContext ctx) {
        final Value left = visit(ctx.left);

        if (null == ctx.right) {
            return left;
        }

        final Value right = visit(ctx.right);
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
                return compare.equal(left, right);
            case CayTheParser.NOT_EQUAL:
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
                return compare.greaterThan(left, right);
            case CayTheParser.LESS_THAN:
                return compare.lessThan(left, right);
            case CayTheParser.GREATER_EQUAL:
                return compare.greaterEqual(left, right);
            case CayTheParser.LESS_EQUAL:
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
                return math.add(left, right);
            case CayTheParser.SUB:
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
                return math.mul(left, right);
            case CayTheParser.DIV:
                return math.div(left, right);
            case CayTheParser.MOD:
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
        return new BoolOperations().not(visit(ctx.atom()));
    }

    @Override
    public Value visitVariableOrConstantDereference(final VariableOrConstantDereferenceContext ctx) {
        final String identifier = ctx.id.getText();

        if (!current.isDefined(identifier)) {
            throw error(ctx.id, "Access of undeclared variable/constant '%s'", ctx.id.getText());
        }

        return current.load(current.resolve(identifier));
    }

    @Override
    public Value visitConstant(final ConstantContext ctx) {
        log("Visit literal: '%s'", ctx.getText());
        final String literal = ctx.value.getText();

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
        log.append(String.format(msg, args)).append('\n');
    }

    private boolean notNull(final Object input) {
        return null != input;
    }

    private SyntaxError error(final Token token, final String msg, final Object... args) {
        return new SyntaxError(String.format(msg, args), token);
    }
}
