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

import de.weltraumschaf.commons.guava.Objects;
import de.weltraumschaf.commons.validate.Validate;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class Symbol {

    public static final Symbol UNDEFINED = new Symbol("", Type.UNDEFINED);
    /**
     * All symbols at least have a name.
     */
    private final String name;
    private final Type type;
    /**
     * All symbols know what scope contains them.
     */
    private Scope scope;

    public Symbol(final String name, final Type type) {
        super();
        this.name = Validate.notNull(name, "name");
        this.type = Validate.notNull(type, "type");
    }

    public String getName() {
        return name;
    }

    public void setScope(Scope scope) {
        this.scope = Validate.notNull(scope, "scope");
    }

    @Override
    public String toString() {
        return String.format("<%s:%s>", name, type);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, type, scope);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Symbol)) {
            return false;
        }

        final Symbol other = (Symbol) obj;
        return Objects.equal(name, other.name)
                && Objects.equal(type, other.type)
                && Objects.equal(scope, other.scope);
    }

    public static enum Type {
        UNDEFINED,
        CLASS, ANNOTATION, INTERFACE,
        CONSTANT, PROPERTY, DELEGATE, METHOD,
        VARAIBLE
    }
}
