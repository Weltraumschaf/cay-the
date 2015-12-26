package de.weltraumschaf.caythe.backend.symtab;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This describes methods and functions.
 * <p>
 * Methods are symbols with an own local scope. They also always have a {@link #getEnclosing() parent scope}.
 * </p>
 *
 * @since 1.0.0
 */
public final class MethodSymbol extends BaseSymbol implements Scope {

    /**
     * Arguments of the method and the local variables.
     */
    private final Map<String, Symbol> orderedArgs = new LinkedHashMap<>();
    /**
     * Delegate for the local scope of a method.
     */
    private final Scope scope;

    /**
     * Dedicated constructor.
     *
     * @param name must not be {@code null} or empty
     * @param returnType must not be {@code null}
     * @param enclosingScope must not be {@code null}
     */
    public MethodSymbol(final String name, final Type returnType, final Scope enclosingScope) {
        super(name, returnType);
        this.scope = new LocalScope(enclosingScope);
    }

    @Override
    public Symbol resolve(final String name) {
        return scope.resolve(name);
    }

    @Override
    public void define(final Symbol sym) {
        scope.define(sym);
    }

    @Override
    public Scope getEnclosing() {
        return scope.getEnclosing();
    }

    @Override
    public boolean hasEnclosing() {
        return scope.hasEnclosing();
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
