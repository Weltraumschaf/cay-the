package de.weltraumschaf.caythe.backend;

import de.weltraumschaf.caythe.backend.SymbolTable.Entry;
import de.weltraumschaf.caythe.frontend.CayTheBaseVisitor;
import de.weltraumschaf.caythe.frontend.CayTheParser.*;
import de.weltraumschaf.commons.validate.Validate;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 */
public final class Interpreter extends CayTheBaseVisitor<Void> {

    private final StringBuilder log = new StringBuilder();
    private final Pool constants = new Pool();
    private final Pool variables = new Pool();
    private final SymbolTable table = new SymbolTable();
    private final Program program = new Program();
    private final Environment env;

    public Interpreter(final Environment env) {
        super();
        this.env = Validate.notNull(env, "env");
    }

    private void log(final String msg, final Object... args) {
        log.append(String.format(msg, args)).append('\n');
    }

    @Override
    protected Void defaultResult() {
        return null;
    }

    @Override
    public Void visitCompilationUnit(final CompilationUnitContext ctx) {
        log("Visit compilation unit: '%s'", ctx.getText());
        final ParseTree compilationUnit = ctx.getChild(0);

        if (null == compilationUnit) {
            return defaultResult();
        }

        return visit(compilationUnit);
    }

    @Override
    public Void visitStatement(final StatementContext ctx) {
        log("Visit statement: '%s'", ctx.getText());
        final ParseTree child = ctx.getChild(0);

        return visit(child);
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
        return defaultResult();
    }

    @Override
    public Void visitLiteral(final LiteralContext ctx) {
        log("Visit literal: '%s'", ctx.getText());
        final String literal = ctx.getChild(0).getText();

        if (null != ctx.BOOL_VALUE()) {
            log("Recognized bool value.");
            Boolean.parseBoolean(literal);
        } else if (null != ctx.FLOAT_VALUE()) {
            log("Recognized float value.");
            Float.parseFloat(literal);
        } else if (null != ctx.INTEGER_VALUE()) {
            log("Recognized integer value.");
            Integer.parseInt(literal);
        } else if (null != ctx.STRING_VALUE()) {
            log("Recognized string value.");
        } else {
            throw new IllegalStateException(String.format("Unrecognized literal '%s'!", literal));
        }

        return defaultResult();
    }

}
