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
import java.nio.file.Path;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * Factory to provide various visitor implementations.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class Visitors {

    /**
     * Hidden for pure static class.
     */
    private Visitors() {
        super();
    }

    public static ParseTreeVisitor<CompilationUnit> production(final Path file) {
        return new ProductionVisitor(file);
    }

    public static ParseTreeVisitor<CompilationUnit> experimental(final Path file) {
        return new ExperimentalVisitor(file);
    }

}
