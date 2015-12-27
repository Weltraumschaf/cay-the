package de.weltraumschaf.caythe.backend.symtab;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This describes methods and functions.
 * <p>
 * Functions are symbols with an own local scope. They also always have a {@link #getEnclosing() parent scope}.
 * </p>
 *
 * @since 1.0.0
 */
public final class FunctionSymbol extends BaseSymbol implements Scope {

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
    public FunctionSymbol(final String name, final Type returnType, final Scope enclosingScope) {
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
    public boolean isDefined(String identifier) {
        return scope.isDefined(identifier);
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
    public void store(final Symbol symbol, final Value value) {
        scope.store(symbol, value);
    }

    @Override
    public Value load(final Symbol symbol) {
        return scope.load(symbol);
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
