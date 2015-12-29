package de.weltraumschaf.caythe.backend.symtab;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This describes methods and functions.
 * <p>
 Functions are symbols with an own local values. They also always have a {@link #getEnclosing() parent values}.
 * </p>
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class FunctionSymbol extends BaseSymbol implements Scope {

    /**
     * Arguments of the method and the local variables.
     */
    private final Map<String, Symbol> orderedArgs = new LinkedHashMap<>();
    /**
     * Delegate for the local values of a method.
     */
    private final Scope values;
    /**
     * Delegate for the local functions of a method.
     */
    private final Scope functions;

    /**
     * Dedicated constructor.
     *
     * @param name must not be {@code null} or empty
     * @param returnType must not be {@code null}
     * @param enclosingScope must not be {@code null}
     */
    public FunctionSymbol(final String name, final Type returnType, final Scope enclosingScope) {
        super(name, returnType);
        this.values = Scope.newLocal(enclosingScope);
        this.functions = Scope.newLocal(enclosingScope);
    }

    @Override
    public Symbol resolveValue(final String name) {
        return values.resolveValue(name);
    }

    @Override
    public void defineValue(final Symbol sym) {
        values.defineValue(sym);
    }

    @Override
    public boolean isValueDefined(final String identifier) {
        return values.isValueDefined(identifier);
    }

    @Override
    public void defineFunction(final Symbol sym) {
        functions.defineFunction(sym);
    }

    @Override
    public boolean isFunctionDefined(final String identifier) {
        return functions.isFunctionDefined(identifier);
    }

    @Override
    public Symbol resolveFunction(final String name) {
        return functions.resolveFunction(name);
    }

    @Override
    public Scope getEnclosing() {
        return values.getEnclosing();
    }

    @Override
    public boolean hasEnclosing() {
        return values.hasEnclosing();
    }

    @Override
    public void store(final Symbol symbol, final Value value) {
        values.store(symbol, value);
    }

    @Override
    public Value load(final Symbol symbol) {
        return values.load(symbol);
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
