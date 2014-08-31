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

import de.weltraumschaf.caythe.SourceFile;
import java.io.IOException;
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

    /**
     * Parser for the Caythe language.
     *
     * @param source must not be {@code null}
     * @return never {@code nul}
     * @throws IOException if source can't be opened
     */
    public static CaytheParser caythe(final SourceFile source) throws IOException {
        return new CaytheParser(new CommonTokenStream(new CaytheLexer(source.newStream())));
    }

    /**
     * Parser for the Java language (example from ANTLR).
     *
     * @param source must not be {@code null}
     * @return never {@code nul}
     * @throws IOException if source can't be opened
     */
    public static JavaParser java(final SourceFile source) throws IOException {
        return new JavaParser(new CommonTokenStream(new JavaLexer(source.newStream())));
    }

}
