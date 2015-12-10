package de.weltraumschaf.caythe.backend;

import de.weltraumschaf.caythe.backend.SymbolTable.Entry;
import de.weltraumschaf.caythe.frontend.CayTheBaseVisitor;
import de.weltraumschaf.caythe.frontend.CayTheParser;
import de.weltraumschaf.caythe.frontend.CayTheParser.AssignmentContext;
import de.weltraumschaf.caythe.frontend.CayTheParser.CompilationUnitContext;
import de.weltraumschaf.caythe.frontend.CayTheParser.ExpressionContext;
import de.weltraumschaf.caythe.frontend.CayTheParser.IfBranchContext;
import de.weltraumschaf.caythe.frontend.CayTheParser.LiteralContext;
import de.weltraumschaf.caythe.frontend.CayTheParser.PrintStatementContext;
import de.weltraumschaf.caythe.frontend.CayTheParser.StatementContext;
import de.weltraumschaf.caythe.frontend.CayTheParser.VariableContext;
import de.weltraumschaf.commons.validate.Validate;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 */
public final class Interpreter extends CayTheBaseVisitor<Value> {

    private final StringBuilder log = new StringBuilder();
    private final Pool constants = new Pool();
    private final Pool variables = new Pool();
    private final SymbolTable table = new SymbolTable();
    private final Environment env;

    public Interpreter(final Environment env) {
        super();
        this.env = Validate.notNull(env, "env");
    }

    @Override
    protected Value defaultResult() {
        return Value.NIL;
    }

    private void log(final String msg, final Object... args) {
        log.append(String.format(msg, args)).append('\n');
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
    public Value visitAssignment(final AssignmentContext ctx) {
        log("Visit assignment: '%s'", ctx.getText());
        final String identifier = ctx.id.getText();

        final Entry symbol;

        if (table.enered(identifier)) {
            symbol = table.lookup(identifier);
        } else {
            symbol = table.enter(identifier);
        }

        final Value value = visit(ctx.value);
        variables.set(symbol.getId(), value);
        return value;
    }

    @Override
    public Value visitExpression(final ExpressionContext ctx) {
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
                return  compare.notEqual(left, right);
            case CayTheParser.GREATER_THAN:
                return  compare.greaterThan(left, right);
            case CayTheParser.LESS_THAN:
                return  compare.lessThan(left, right);
            case CayTheParser.GREATER_EQUAL:
                return  compare.greaterEqual(left, right);
            case CayTheParser.LESS_EQUAL:
                return  compare.lessEqual(left, right);
            default:
                throw error(ctx.operator, "Unsupported operator '%s'", ctx.operator.getText());
        }
    }

    @Override
    public Value visitIfBranch(final IfBranchContext ctx) {
        if (visit(ctx.ifCondition).asBool() && notNull(ctx.ifBlock)) {
            return visit(ctx.ifBlock);
        } else if (notNull(ctx.elseIfCondition) && visit(ctx.elseIfCondition).asBool() && notNull(ctx.elseIfBlock)) {
            return visit(ctx.elseIfBlock);
        } else if (notNull(ctx.elseBlock)) {
            return visit(ctx.elseBlock);
        }

        return Value.NIL;
    }

    @Override
    public Value visitVariable(final VariableContext ctx) {
        final Entry entry = table.lookup(ctx.id.getText());

        if (null == entry) {
            throw error(ctx.id, "Access of undeclared variable '%s'", ctx.id.getText());
        }

        return variables.get(entry.getId());
    }

    @Override
    public Value visitLiteral(final LiteralContext ctx) {
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

    @Override
    public Value visitPrintStatement(final PrintStatementContext ctx) {
        final Value value = visit(ctx.value);
        env.stdOut(value.asString());
        return value;
    }

    private boolean notNull(final Object input) {
        return null != input;
    }

    private SyntaxError error(final Token token, final String msg, final Object ... args) {
        return new SyntaxError(String.format(msg, args), token);
    }
}
