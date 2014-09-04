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

package de.weltraumschaf.caythe.listener;

import de.weltraumschaf.caythe.ast.Property;
import de.weltraumschaf.caythe.ast.Visibility;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final  class TokenConverter {

    private TokenConverter() {
        super();
    }

    static Visibility convertVisibility(final String token) {
        switch (token) {
            case "public":
                return Visibility.PUBLIC;
            case "package":
                return Visibility.PACKAGE;
            default:
                return Visibility.PRIVATE;
        }
    }

    static Property.Config convertPropertyConfig(final String token) {
        switch (token) {
            case "write":
                return Property.Config.WRITE;
            case "readwrite":
                return Property.Config.READWRITE;
            default:
            case "read":
                return Property.Config.READ;
        }
    }
}
