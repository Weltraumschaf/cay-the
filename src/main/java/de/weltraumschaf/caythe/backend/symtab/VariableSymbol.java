package de.weltraumschaf.caythe.backend.symtab;

/**
 * Describes declared variables.
 *
 * @since 1.0.0
 */
public final class VariableSymbol extends Symbol {

    /**
     * Dedicated constructor.
     *
     * @param name must not be {@code null} or empty
     * @param type must not be {@code null}
     */
    public VariableSymbol(String name, Type type) {
        super(name, type);
    }

}
