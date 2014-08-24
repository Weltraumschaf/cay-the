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
import de.weltraumschaf.caythe.parser.TestBaseVisitor;
import java.nio.file.Path;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class ExperimentalVisitor extends TestBaseVisitor<CompilationUnit> {

    private final CompilationUnit parsedUnit;

    public ExperimentalVisitor(final Path file) {
        super();
        parsedUnit = new CompilationUnit(file);
    }

    @Override
    protected CompilationUnit defaultResult() {
        return parsedUnit;
    }

}
