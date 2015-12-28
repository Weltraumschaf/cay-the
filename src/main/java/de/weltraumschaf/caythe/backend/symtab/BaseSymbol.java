package de.weltraumschaf.caythe.backend.symtab;

import de.weltraumschaf.commons.validate.Validate;
import java.util.Objects;

/**
 * Common functionality for all symbols.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
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
     * Set the scope of the symbol.
     *
     * @param scope must not be {@code null}
     */
    final void setScope(final Scope scope) {
        this.scope = Validate.notNull(scope, "scope");
    }

    @Override
    public final Value load() {
        return getScope().load(this);
    }

    @Override
    public String toString() {
        return '<' + getName() + ":" + type.getName() + '>';
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof BaseSymbol)) {
            return false;
        }

        final BaseSymbol other = (BaseSymbol) obj;
        return Objects.equals(name, other.name)
            && Objects.equals(type, other.type);
    }

}
