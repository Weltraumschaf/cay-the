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
    private Scope scope = Scope.NULL;

    /**
     * Dedicated constructor.
     *
     * @param name must not be {@code null} or empty
     * @param type must not be {@code null}
     */
    public BaseSymbol(final String name, final Type type) {
        super();
        this.name = Validate.notEmpty(name, "name");
        this.type = Validate.notNull(type, "type");
    }

    @Override
    public final String getName() {
        return name;
    }

    @Override
    public final Scope getScope() {
        return scope;
    }

    /**
     * Set the scope of the symbol
     *
     * @param scope must not be {@code null}
     */
    final void setScope(final Scope scope) {
        this.scope = Validate.notNull(scope, "scope");
    }

    @Override
    public String toString() {
        if (type != null) {
            return '<' + getName() + ":" + type + '>';
        }

        return getName();
    }
}
