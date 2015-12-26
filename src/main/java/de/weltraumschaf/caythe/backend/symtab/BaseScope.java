package de.weltraumschaf.caythe.backend.symtab;

import de.weltraumschaf.commons.validate.Validate;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Common implementation for all scopes.
 *
 * @since 1.0.0
 */
abstract class BaseScope implements Scope {

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
     * @param enclosingScope must nit be {@code null}
     */
    public BaseScope(final Scope enclosingScope) {
        super();
        this.enclosingScope = Validate.notNull(enclosingScope, "enclosingScope");
    }

    @Override
    public final Symbol resolve(final String name) {
        Validate.notEmpty(name, "name");
        final Symbol s = symbols.get(name);

        if (s != null) {
            return s;
        }

        // If not here, check any enclosing scope.
        if (enclosingScope != null) {
            return enclosingScope.resolve(name);
        }

        return null; // Not found.
    }

    @Override
    public final void define(final Symbol sym) {
        Validate.notNull(sym, "sym");
        symbols.put(sym.getName(), sym);
        ((BaseSymbol)sym).setScope(this); // Track the scope in each symbol.
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
}
