package de.weltraumschaf.caythe.backend.symtab;

import de.weltraumschaf.commons.validate.Validate;

/**
 * Describes a found symbol.
 *
 * @since 1.0.0
 */
public class Symbol {

    /**
     * All symbols at least have a name.
     */
    private final String name;
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
    public Symbol(final String name) {
        this(name, null);
    }

    public Symbol(final String name, final Type type) {
        super();
        this.name = Validate.notEmpty(name, "name");
        this.type = type;
    }

    public String getName() {
        return name;
    }

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
