package de.weltraumschaf.caythe;

import de.weltraumschaf.caythe.CayTheParser.*;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 */
final class ByteCodeVisitor extends CayTheBaseVisitor<Programm> {

    private final SymbolTable table = new SymbolTable();

    @Override
    protected Programm defaultResult() {
        return Programm.EMPTY;
    }

    @Override
    public Programm visitCompilationUnit(final CompilationUnitContext ctx) {
        final ParseTree compilationUnit = ctx.getChild(0);

        if (null == compilationUnit) {
            return defaultResult();
        }

        return visit(compilationUnit);
    }

    @Override
    public Programm visitStatement(final StatementContext ctx) {
        return visit(ctx.getChild(0));
    }


}
