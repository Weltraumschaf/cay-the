package de.weltraumschaf.caythe.backend.symtab;

import de.weltraumschaf.commons.validate.Validate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Common implementation for all scopes.
 *
 * @since 1.0.0
 */
abstract class BaseScope implements Scope {

    /**
     * Name of the scope.
     */
    private final String scopeName;
    /**
     * {@code null} if global (outermost) scope.
     */
    private final Scope enclosingScope;
    /**
     * Symbols in this scope.
     */
    private final Map<String, Symbol> symbols = new LinkedHashMap<>();

    /**
     * Dedicated constructor.
     *
     * @param scopeName must not be {@code null} or empty
     * @param enclosingScope must nit be {@code null}
     */
    public BaseScope(final String scopeName, final Scope enclosingScope) {
        super();
        this.scopeName = Validate.notEmpty(scopeName, "scopeName");
        this.enclosingScope = Validate.notNull(enclosingScope, "enclosingScope");
    }

    @Override
    public final Symbol resolve(final String name) {
        Validate.notEmpty(name, "name");

        if (symbols.containsKey(name)) {
            return symbols.get(name);
        }

        return getEnclosing().resolve(name);
    }

    @Override
    public final void define(final Symbol sym) {
        Validate.notNull(sym, "sym");
        symbols.put(sym.getName(), sym);

        if (sym instanceof BaseSymbol) {
            ((BaseSymbol) sym).setScope(this); // Track the scope in each symbol.
        }
    }

    @Override
    public String getScopeName() {
        return scopeName;
    }

    @Override
    public final Scope getEnclosing() {
        return enclosingScope;
    }

    @Override
    public final boolean hasEnclosing() {
        return Scope.NULL != enclosingScope;
    }

    @Override
    public final String toString() {
        return symbols.keySet().toString();
    }

    @Override
    public final int hashCode() {
        return Objects.hash(scopeName, enclosingScope, symbols);
    }

    @Override
    public final boolean equals(final Object obj) {
        if (!(obj instanceof BaseScope)) {
            return false;
        }

        final BaseScope other = (BaseScope) obj;
        return Objects.equals(scopeName, other.scopeName)
            && Objects.equals(enclosingScope, other.enclosingScope)
            && Objects.equals(symbols, other.symbols);
    }

}
