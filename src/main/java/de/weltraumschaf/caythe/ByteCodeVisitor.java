package de.weltraumschaf.caythe;

import de.weltraumschaf.caythe.CayTheParser.*;
import de.weltraumschaf.caythe.SymbolTable.Entry;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 */
final class ByteCodeVisitor extends CayTheBaseVisitor<Program> {

    private final StringBuilder log = new StringBuilder();
    private final Pool constants = new Pool();
    private final Pool variables = new Pool();
    private final SymbolTable table = new SymbolTable();
    private final Program program = new Program();

    private void log(final String msg, final Object ... args) {
        log.append(String.format(msg, args)).append('\n');
    }

    @Override
    protected Program defaultResult() {
        return program;
    }

    @Override
    public Program visitCompilationUnit(final CompilationUnitContext ctx) {
        log("Visit compilation unit: '%s'", ctx.getText());
        final ParseTree compilationUnit = ctx.getChild(0);

        if (null == compilationUnit) {
            return defaultResult();
        }

        return visit(compilationUnit);
    }

    @Override
    public Program visitStatement(final StatementContext ctx) {
        log("Visit statement: '%s'", ctx.getText());
        final ParseTree child = ctx.getChild(0);

        return visit(child);
    }

    @Override
    public Program visitAssignment(final AssignmentContext ctx) {
        log("Visit assignment: '%s'", ctx.getText());
        final String identifier = ctx.id.getText();

        final Entry symbol;

        if (table.enered(identifier)) {
            symbol = table.lookup(identifier);
        } else {
            symbol = table.enter(identifier);
        }

        visit(ctx.value);
        return program;
    }

    @Override
    public Program visitLiteral(final LiteralContext ctx) {
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

        return program;
    }


}
