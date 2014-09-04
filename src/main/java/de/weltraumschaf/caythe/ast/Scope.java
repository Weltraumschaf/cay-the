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

import com.beust.jcommander.internal.Maps;
import de.weltraumschaf.commons.validate.Validate;
import java.util.Map;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class Scope {

    private final Scope enclosingScope;
    private final String name;
    private final Map<String, Symbol> symbols = Maps.newLinkedHashMap();

    public Scope(final String name) {
        this(name, new VoidScope());
    }

    public Scope(final String name, final Scope enclosingScope) {
        super();
        this.name = Validate.notEmpty(name, "name");
        this.enclosingScope = Validate.notNull(enclosingScope, "enclosingScope");
    }

    public Symbol resolve(final String name) {
        final Symbol symbol = symbols.get(name);

        if (symbol != null) {
            return symbol;
        }

        // if not here, check any enclosing scope
        if (hasEnclosingScope()) {
            return getEnclosingScope().resolve(name);
        }

        return Symbol.UNDEFINED;
    }

    public void define(final Symbol sym) {
        // TODO Throw error on redefinition.
        symbols.put(sym.getName(), sym);
        sym.setScope(this); // track the scope in each symbol
    }

    public boolean hasEnclosingScope() {
        return true;
    }

    public Scope getEnclosingScope() {
        return enclosingScope;
    }

    public String getScopeName() {
        return name;
    }

    @Override
    public String toString() {
        return getScopeName() + ":" + symbols.keySet().toString();
    }

    private static final class VoidScope extends Scope {

        public VoidScope() {
            this("void");
        }

        public VoidScope(String name) {
            super(name);
        }

        public VoidScope(String name, Scope enclosingScope) {
            super(name, enclosingScope);
        }

        @Override
        public Scope getEnclosingScope() {
            return this;
        }

        @Override
        public boolean hasEnclosingScope() {
            return false;
        }

        @Override
        public void define(final Symbol sym) {
            throw new UnsupportedOperationException("In void you can't define symbols!");
        }

        @Override
        public Symbol resolve(final String name) {
            throw new UnsupportedOperationException("In void you can't resolve symbols!");
        }
    }
}
