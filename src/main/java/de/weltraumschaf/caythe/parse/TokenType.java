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

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
enum TokenType {

    NEW_LINE, END_OF_FILE,

    BOOLEAN_VALUE, INTEGER_VALUE, FLOAT_VALUE, STRING_VALUE;
}
