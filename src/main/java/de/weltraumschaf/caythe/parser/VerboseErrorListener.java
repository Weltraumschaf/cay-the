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

import java.io.PrintStream;
import java.util.Collections;
import java.util.List;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class VerboseErrorListener extends BaseErrorListener {

    private final PrintStream error;

    public VerboseErrorListener(final PrintStream error) {
        super();
        this.error = error;
    }

    @Override
    public void syntaxError(final Recognizer<?, ?> recognizer, final Object offendingSymbol, final int line, final int charPositionInLine, final String msg, final RecognitionException e) {
        final List<String> stack = ((Parser)recognizer).getRuleInvocationStack();
        Collections.reverse(stack);
        error.println(String.format("Rule stack: %s", stack));
        error.println(String.format("Line %d:%d at %s:%s", line, charPositionInLine, offendingSymbol, msg));
    }

}
