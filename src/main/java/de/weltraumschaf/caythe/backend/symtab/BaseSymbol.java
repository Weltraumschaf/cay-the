package de.weltraumschaf.caythe.backend.symtab;

import de.weltraumschaf.commons.validate.Validate;

/**
 * Common functionality for all symbols.
 *
 * @since 1.0.0
 */
abstract class BaseSymbol implements Symbol {

    /**
     * All symbols at least have a name.
     */
    private final String name;
    /**
     * Type of the symbol.
     */
    private final Type type;
    /**
     * All symbols know what scope contains them.
     */
    private Scope scope;

    /**
     * Dedicated constructor.
     *
     * @param name must not be {@code null} or empty
     */
    public BaseSymbol(final String name) {
        this(name, null);
    }

    public BaseSymbol(final String name, final Type type) {
        super();
        this.name = Validate.notEmpty(name, "name");
        this.type = type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Scope getScope() {
        return scope;
    }

    void setScope(final Scope scope) {
        this.scope = scope;
    }

    @Override
    public String toString() {
        if (type != null) {
            return '<' + getName() + ":" + type + '>';
        }

        return getName();
    }
}
