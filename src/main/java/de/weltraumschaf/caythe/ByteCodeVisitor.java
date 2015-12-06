package de.weltraumschaf.caythe;

import de.weltraumschaf.caythe.CayTheParser.*;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 */
final class ByteCodeVisitor extends CayTheBaseVisitor<Program> {

    private final SymbolTable table = new SymbolTable();
    private final Program program = new Program();

    @Override
    protected Program defaultResult() {
        return program;
    }

    @Override
    public Program visitCompilationUnit(final CompilationUnitContext ctx) {
        final ParseTree compilationUnit = ctx.getChild(0);

        if (null == compilationUnit) {
            return defaultResult();
        }

        return visit(compilationUnit);
    }

    @Override
    public Program visitStatement(final StatementContext ctx) {
        return visit(ctx.getChild(0));
    }

    @Override
    public Program visitAssignment(final AssignmentContext ctx) {
        final String identifier = ctx.id.getText();
        visit(ctx.value);
        return program;
    }


}
