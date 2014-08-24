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

package de.weltraumschaf.caythe.parser;

import java.io.IOException;
import java.nio.file.Path;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;

/**
 * Factory to create various parsers.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class Parsers {

    /**
     * Hidden for pure static class.
     */
    private Parsers() {
        super();
    }

    public static CaytheParser caythe(final Path source, final String encoding) throws IOException {
        final CaytheLexer lexer = new CaytheLexer(input(source, encoding));

        return new CaytheParser(new CommonTokenStream(lexer));
    }

    public static JavaParser java(final Path source, final String encoding) throws IOException {
        final JavaLexer lexer = new JavaLexer(input(source, encoding));

        return new JavaParser(new CommonTokenStream(lexer));
    }

    private static CharStream input(final Path source, final String encoding) throws IOException {
        return new ANTLRFileStream(source.toString(), encoding);
    }
}
