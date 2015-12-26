package de.weltraumschaf.caythe.backend.symtab;

/**
 * Describes declared variables.
 *
 * @since 1.0.0
 */
public final class VariableSymbol extends BaseSymbol {

    /**
     * Dedicated constructor.
     *
     * @param name must not be {@code null} or empty
     * @param type must not be {@code null}
     */
    public VariableSymbol(final String name, final Type type) {
        super(name, type);
    }

}
