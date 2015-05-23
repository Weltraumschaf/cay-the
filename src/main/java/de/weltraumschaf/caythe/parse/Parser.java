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

package de.weltraumschaf.caythe.parse;

import de.weltraumschaf.caythe.parse.Scanner.Token;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class Parser {

    void parse(final String source) throws SyntaxException {
        parse(new Scanner(source));
    }

    void parse(final Scanner source) throws SyntaxException {
        while (source.hasNext()) {
            final Token current = source.getNext();
        }
    }
}
