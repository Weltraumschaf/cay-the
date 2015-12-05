package de.weltraumschaf.caythe;

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
    public Programm visitCompilationUnit(final CayTheParser.CompilationUnitContext ctx) {
        final ParseTree compilationUnit = ctx.getChild(0);

        if (null == compilationUnit) {
            return defaultResult();
        }

        return visit(compilationUnit);
    }


}
