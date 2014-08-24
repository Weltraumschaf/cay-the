/*
 *  LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" <weltraumschaf@googlemail.com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */

package de.weltraumschaf.caythe.visitors;

import de.weltraumschaf.caythe.ast.CompilationUnit;
import de.weltraumschaf.caythe.parser.CaytheBaseVisitor;
import de.weltraumschaf.caythe.parser.CaytheLexer;
import de.weltraumschaf.caythe.parser.CaytheParser;
import java.nio.file.Path;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.log4j.Logger;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class ProductionVisitor extends CaytheBaseVisitor<CompilationUnit> {

    /**
     * Log facility.
     */
    private static final Logger LOG = Logger.getLogger(ProductionVisitor.class);

    private final CompilationUnit parsedUnit;

    public ProductionVisitor(final Path file) {
        super();
        parsedUnit = new CompilationUnit(file);
    }

    @Override
    protected CompilationUnit defaultResult() {
        return parsedUnit;
    }

    @Override
    public CompilationUnit visitUnit(final CaytheParser.UnitContext ctx) {
        LOG.debug(String.format("Visit unit: %s", ctx));
        final ParseTree unit = ctx.getChild(0);

        if (null == unit) {
            return defaultResult();
        }

        return visit(unit);
    }

    @Override
    public CompilationUnit visitImportStmnt(final CaytheParser.ImportStmntContext ctx) {
        LOG.debug(String.format("Visit import statememt: %s", ctx));

        return visit(ctx);
    }

    @Override
    public CompilationUnit visitInterfaceStmnt(final CaytheParser.InterfaceStmntContext ctx) {
        LOG.debug(String.format("Visit interface statememt: %s", ctx));
        final ParseTree unit = ctx.getChild(0);

        if (CaytheLexer.K_PUBLIC == ctx.modifier.getType()) {
//            parsedUnit.setPublic();
        }

        final String name = ctx.IDENTIFIER().getText();

        return visit(ctx);
    }

    @Override
    public CompilationUnit visitInterfaceBody(CaytheParser.InterfaceBodyContext ctx) {
        return super.visitInterfaceBody(ctx);
    }


    @Override
    public CompilationUnit visitClassStmnt(final CaytheParser.ClassStmntContext ctx) {
        LOG.debug(String.format("Visit class statememt: %s", ctx));

        if (CaytheLexer.K_PUBLIC == ctx.modifier.getType()) {
//            parsedUnit.setPublic();
        }

        return visit(ctx);
    }

    @Override
    public CompilationUnit visitAnnotationStmnt(final CaytheParser.AnnotationStmntContext ctx) {
        LOG.debug(String.format("Visit annotation statememt: %s", ctx));

        if (CaytheLexer.K_PUBLIC == ctx.modifier.getType()) {
//            parsedUnit.setPublic();
        }

        return visit(ctx);
    }



}
