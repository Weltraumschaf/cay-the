package de.weltraumschaf.caythe.backend.symtab;

/**
 * @since 1.0.0
 */
public interface Scope {

    /**
     * Name of scope.
     *
     * @return never {@code null} or empty
     */
    String getScopeName();

    /**
     * Where to look next for symbols.
     *
     * @return may be {@code null}
     */
    Scope getEnclosingScope();

    /**
     * Define a symbol in the current scope.
     *
     * @param sym must not be {@code null}
     */
    void define(Symbol sym);

    /**
     * Look up name in this scope or in enclosing scope if not here.
     *
     * @param name must not be {@code null} or empty
     * @return
     */
    Symbol resolve(String name);
}
