package de.weltraumschaf.caythe.backend.symtab;

import de.weltraumschaf.commons.validate.Validate;
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
    public Symbol resolve(final String name) {
        Validate.notEmpty(name, "name");

        if (orderedArgs.containsKey(name)) {
            return orderedArgs.get(name);
        }

        return getEnclosing().resolve(name);
    }

    @Override
    public void define(final Symbol sym) {
        Validate.notNull(sym, "sym");
        orderedArgs.put(sym.getName(), sym);

        if (sym instanceof BaseSymbol) {
            ((BaseSymbol) sym).setScope(this); // Track the scope in each symbol.
        }
    }

    @Override
    public Scope getEnclosing() {
        return enclosingScope;
    }

    @Override
    public boolean hasEnclosing() {
        return Scope.NULL != enclosingScope;
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
