package de.weltraumschaf.caythe.backend.symtab;

/**
 * Describes a found symbol.
 *
 * @since 1.0.0
 */
public interface Symbol {

    /**
     * Get the name of the symbol.
     *
     * @return never {@code null} or empty
     */
    String getName();

    /**
     * Get the scope of the symbol.
     *
     * @return never {@code null}
     */
    Scope getScope();
}
