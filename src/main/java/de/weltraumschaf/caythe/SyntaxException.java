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

package de.weltraumschaf.caythe;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class SyntaxException extends Exception {

    private final int line;
    private final int column;

    public SyntaxException(final String message) {
        this(message, 0, 0);
    }

    public SyntaxException(final String message, final int line, final int column) {
        this(message, line, column, null);
    }

    public SyntaxException(final String message, final Throwable cause) {
        this(message, 0, 0, cause);
    }

    public SyntaxException(final String message, final int line, final int column, final Throwable cause) {
        super(message, cause);
        this.line = line;
        this.column = column;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

}
