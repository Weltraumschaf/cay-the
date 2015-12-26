package de.weltraumschaf.caythe.symtab;

/**
 */
public interface Scope {

    public String getScopeName();

    /**
     * Where to look next for symbols
     *
     * @return
     */
    public Scope getEnclosingScope();

    /**
     * Define a symbol in the current scope
     *
     * @param sym
     */
    public void define(Symbol sym);

    /**
     * Look up name in this scope or in enclosing scope if not here
     *
     * @param name
     * @return
     */
    public Symbol resolve(String name);
}
