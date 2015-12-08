package de.weltraumschaf.caythe.backend;

import de.weltraumschaf.caythe.backend.Pool.Type;
import de.weltraumschaf.caythe.backend.Pool.Value;
import de.weltraumschaf.caythe.backend.SymbolTable.Entry;
import de.weltraumschaf.caythe.frontend.CayTheBaseVisitor;
import de.weltraumschaf.caythe.frontend.CayTheParser;
import de.weltraumschaf.caythe.frontend.CayTheParser.AssignmentContext;
import de.weltraumschaf.caythe.frontend.CayTheParser.CompilationUnitContext;
import de.weltraumschaf.caythe.frontend.CayTheParser.ExpressionContext;
import de.weltraumschaf.caythe.frontend.CayTheParser.LiteralContext;
import de.weltraumschaf.caythe.frontend.CayTheParser.PrintStatementContext;
import de.weltraumschaf.caythe.frontend.CayTheParser.StatementContext;
import de.weltraumschaf.commons.validate.Validate;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 */
public final class Interpreter extends CayTheBaseVisitor<Void> {

    private final StringBuilder log = new StringBuilder();
    private final Pool constants = new Pool();
    private final Pool variables = new Pool();
    private final SymbolTable table = new SymbolTable();
    private final Environment env;
    private Value lastValue = Value.NIL;

    public Interpreter(final Environment env) {
        super();
        this.env = Validate.notNull(env, "env");
    }

    private void log(final String msg, final Object... args) {
        log.append(String.format(msg, args)).append('\n');
    }

    @Override
    public Void visitCompilationUnit(final CompilationUnitContext ctx) {
        log("Visit compilation unit: '%s'", ctx.getText());
        final ParseTree compilationUnit = ctx.getChild(0);

        if (null == compilationUnit) {
            return defaultResult();
        }

        visit(compilationUnit);
        return defaultResult();
    }

    @Override
    public Void visitStatement(final StatementContext ctx) {
        log("Visit statement: '%s'", ctx.getText());
        final ParseTree statement = ctx.getChild(0);

        if (null == statement) {
            return defaultResult();
        }

        visit(statement);
        return defaultResult();
    }

    @Override
    public Void visitAssignment(final AssignmentContext ctx) {
        log("Visit assignment: '%s'", ctx.getText());
        final String identifier = ctx.id.getText();

        final Entry symbol;

        if (table.enered(identifier)) {
            symbol = table.lookup(identifier);
        } else {
            symbol = table.enter(identifier);
        }

        visit(ctx.value);
        variables.set(symbol.getId(), lastValue);
        return defaultResult();
    }

    @Override
    public Void visitExpression(final ExpressionContext ctx) {
        visit(ctx.left);
        final Value left = lastValue;
        visit(ctx.right);
        final Value right = lastValue;
        final Comparator compare = new Comparator();

        switch (ctx.operator.getType()) {
            case CayTheParser.EQUAL:
                lastValue = compare.equal(left, right);
                break;
            case CayTheParser.NOT_EQUAL:
                lastValue = compare.notEqual(left, right);
                break;
            case CayTheParser.GREATER_THAN:
                lastValue = compare.greaterThan(left, right);
                break;
            case CayTheParser.LESS_THAN:
                lastValue = compare.lessThan(left, right);
                break;
            case CayTheParser.GREATER_EQUAL:
                lastValue = compare.greaterEqual(left, right);
                break;
            case CayTheParser.LESS_EQUAL:
                lastValue = compare.lessEqual(left, right);
                break;
            default:
                throw new UnsupportedOperationException(
                    String.format("Unsupported operator '%s'!", ctx.operator.getType()));
        }

        return defaultResult();
    }

    @Override
    public Void visitLiteral(final LiteralContext ctx) {
        log("Visit literal: '%s'", ctx.getText());
        final String literal = ctx.value.getText();

        if (null != ctx.BOOL_VALUE()) {
            log("Recognized bool value.");
            lastValue = Value.newBool(Boolean.parseBoolean(literal));
        } else if (null != ctx.FLOAT_VALUE()) {
            log("Recognized float value.");
            lastValue = Value.newFloat(Float.parseFloat(literal));
        } else if (null != ctx.INTEGER_VALUE()) {
            log("Recognized integer value.");
            lastValue = Value.newInt(Integer.parseInt(literal));
        } else if (null != ctx.STRING_VALUE()) {
            log("Recognized string value.");
            lastValue = Value.newString(literal.substring(1, literal.length() - 1));
        } else {
            throw new IllegalStateException(String.format("Unrecognized literal '%s'!", literal));
        }

        return defaultResult();
    }

    @Override
    public Void visitPrintStatement(final PrintStatementContext ctx) {
        visit(ctx.value);
        env.stdOut(lastValue.asString());
        return defaultResult();
    }

}
