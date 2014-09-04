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

import de.weltraumschaf.commons.guava.Lists;
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
public class CollectingErrorListener extends BaseErrorListener {

    private final List<Error> errors = Lists.newArrayList();

    @Override
    public void syntaxError(final Recognizer<?, ?> recognizer, final Object offendingSymbol, final int line, final int charPositionInLine, final String msg, final RecognitionException e) {
        final List<String> stack = ((Parser)recognizer).getRuleInvocationStack();
        Collections.reverse(stack);
        errors.add(new Error(
            String.format("Rule stack: %s", stack),
            String.format("Syntax error: %s (at line %d:%d).",  msg, line, charPositionInLine)
        ));
    }

    public void clear() {
        errors.clear();
    }

    public boolean hasErrors(){
        return !errors.isEmpty();
    }

    @Override
    public String toString() {
        final StringBuilder buffer = new StringBuilder();

        for (final Error error : errors) {
            buffer.append(error.toString()).append(String.format("%n"));
        }

        return buffer.toString();
    }

    public static final class Error {
        private final String ruleStack;
        private final String line;

        public Error(final String ruleStack, final String line) {
            this.ruleStack = ruleStack;
            this.line = line;
        }

        @Override
        public String toString() {
            return String.format("%s (%s)", line, ruleStack);
        }

    }
}
