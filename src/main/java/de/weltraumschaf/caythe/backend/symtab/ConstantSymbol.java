package de.weltraumschaf.caythe.backend.symtab;

/**
 * Describes declared constants.
 *
 * @since 1.0.0
 */
public final class ConstantSymbol extends BaseSymbol {

    /**
     * Dedicated constructor.
     *
     * @param name must not be {@code null} or empty
     * @param type must not be {@code null}
     */
    public ConstantSymbol(final String name, final Type type) {
        super(name, type);
    }

}
