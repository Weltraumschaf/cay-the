package de.weltraumschaf.caythe.backend.symtab;

import de.weltraumschaf.commons.validate.Validate;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This describes methods and functions.
 * <p>
 * Methods are symbols with an own local scope. They also always have a {@link #getEnclosingScope() parent scope}.
 * </p>
 *
 * @since 1.0.0
 */
public final class MethodSymbol extends BaseSymbol implements Scope {

    /**
     * Arguments of the method.
     */
    private final Map<String, BaseSymbol> orderedArgs = new LinkedHashMap<>();
    /**
     * The enclosing scope is either a class or the global scope if it is a function.
     */
    private final Scope enclosingScope;

    /**
     * Dedicated constructor.
     *
     * @param name must not be {@code null} or empty
     * @param returnType must not be {@code null}
     * @param enclosingScope must not be {@code null}
     */
    public MethodSymbol(final String name, final Type returnType, final Scope enclosingScope) {
        super(name, returnType);
        this.enclosingScope = Validate.notNull(enclosingScope, "enclosingScope");
    }

    @Override
    public BaseSymbol resolve(final String name) {
        Validate.notEmpty(name, "name");
        final BaseSymbol s = orderedArgs.get(name);

        if (s != null) {
            return s;
        }

        // If not here, check any enclosing scope.
        if (getEnclosingScope() != null) {
            return getEnclosingScope().resolve(name);
        }

        return null; // Not found.
    }

    @Override
    public void define(final BaseSymbol sym) {
        Validate.notNull(sym, "sym");
        orderedArgs.put(sym.getName(), sym);
        sym.setScope(this); // Track the scope in each symbol.
    }

    @Override
    public Scope getEnclosingScope() {
        return enclosingScope;
    }

    @Override
    public String getScopeName() {
        return getName();
    }

    @Override
    public String toString() {
        return "method" + super.toString() + ":" + orderedArgs.values();
    }

}
