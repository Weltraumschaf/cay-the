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

package de.weltraumschaf.caythe.ast;

/**
 * Visibility of types (compilation unit) and methods.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public enum Visibility {
    /**
     * Not visible outside the compilation unit.
     */
    PRIVATE,
    /**
     * Only visible to compilation units in the same package (directory) or sub packages.
     */
    PACKAGE,
    /**
     * Visible to all others.
     */
    PUBLIC;
}
