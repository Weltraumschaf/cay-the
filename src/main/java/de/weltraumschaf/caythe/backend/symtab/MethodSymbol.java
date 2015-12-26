package de.weltraumschaf.caythe.symtab;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 */
public final class MethodSymbol extends Symbol implements Scope {

    Map<String, Symbol> orderedArgs = new LinkedHashMap<String, Symbol>();
    Scope enclosingScope;

    public MethodSymbol(String name, Type retType, Scope enclosingScope) {
        super(name, retType);
        this.enclosingScope = enclosingScope;
    }

    @Override
    public Symbol resolve(String name) {
        Symbol s = orderedArgs.get(name);
        if (s != null) {
            return s;
        }
        // if not here, check any enclosing scope
        if (getEnclosingScope() != null) {
            return getEnclosingScope().resolve(name);
        }
        return null; // not found
    }

    @Override
    public void define(Symbol sym) {
        orderedArgs.put(sym.name, sym);
        sym.scope = this; // track the scope in each symbol
    }

    @Override
    public Scope getEnclosingScope() {
        return enclosingScope;
    }

    @Override
    public String getScopeName() {
        return name;
    }

    @Override
    public String toString() {
        return "method" + super.toString() + ":" + orderedArgs.values();
    }

}
