package de.weltraumschaf.caythe.backend.symtab;

import de.weltraumschaf.commons.validate.Validate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Common implementation for all scopes.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
final class DefaultScope implements Scope {

    /**
     * Name of the scope.
     */
    private final String scopeName;
    /**
     * {@code null} if global (outermost) scope.
     */
    private final Scope enclosingScope;
    /**
     * Symbols of constants and variables in this scope.
     */
    private final Map<String, Symbol> values = new LinkedHashMap<>();
    /**
     * Symbols of functions in this scope.
     */
    private final Map<String, Symbol> functions = new LinkedHashMap<>();
    /**
     * Holds the constants of this scope.
     */
    private final Map<Symbol, Value> constants = new LinkedHashMap<>();
    /**
     * Holds the variable of this scope.
     */
    private final Map<Symbol, Value> variables = new LinkedHashMap<>();

    /**
     * Dedicated constructor.
     *
     * @param scopeName must not be {@code null} or empty
     * @param enclosingScope must nit be {@code null}
     */
    public DefaultScope(final String scopeName, final Scope enclosingScope) {
        super();
        this.scopeName = Validate.notEmpty(scopeName, "scopeName");
        this.enclosingScope = Validate.notNull(enclosingScope, "enclosingScope");
    }

    @Override
    public Symbol resolveValue(final String name) {
        Validate.notEmpty(name, "name");

        if (values.containsKey(name)) {
            return values.get(name);
        }

        if (hasEnclosing()) {
            return getEnclosing().resolveValue(name);
        }

        return Symbol.NULL;
    }

    @Override
    public void defineValue(final Symbol sym) {
        Validate.notNull(sym, "sym");
        values.put(sym.getName(), sym);

        if (sym instanceof BaseSymbol) {
            ((BaseSymbol) sym).setScope(this); // Track the scope in each symbol.
        }
    }

    @Override
    public boolean isValueDefined(final String identifier) {
        return values.containsKey(identifier) || (hasEnclosing() && getEnclosing().isValueDefined(identifier));
    }

    @Override
    public Symbol resolveFunction(final String name) {
        Validate.notEmpty(name, "name");

        if (functions.containsKey(name)) {
            return functions.get(name);
        }

        if (hasEnclosing()) {
            return getEnclosing().resolveFunction(name);
        }

        return Symbol.NULL;
    }

    @Override
    public void defineFunction(final Symbol sym) {
        Validate.notNull(sym, "sym");
        functions.put(sym.getName(), sym);

        if (sym instanceof BaseSymbol) {
            ((BaseSymbol) sym).setScope(this); // Track the scope in each symbol.
        }
    }

    @Override
    public boolean isFunctionDefined(final String identifier) {
        return functions.containsKey(identifier) || (hasEnclosing() && getEnclosing().isFunctionDefined(identifier));
    }

    @Override
    public String getScopeName() {
        return scopeName;
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
    public void store(final Symbol symbol, final Value value) {
        Validate.notNull(symbol, "symbol");
        Validate.notNull(value, "value");

        if (!isValueDefined(symbol.getName())) {
            throw new IllegalStateException(
                String.format(
                    "Trying to store value %s to undefiend %s!",
                    value, symbol
                ));
        }

        if (symbol instanceof ConstantSymbol) {
            if (constants.containsKey(symbol)) {
                throw new IllegalStateException(
                    String.format(
                        "Cant set constant '%s' to value '%s' because it is alreaty set with value '%s'!",
                        symbol.getName(), value, load(symbol)
                    ));
            }

            constants.put(symbol, value);
        } else if (symbol instanceof VariableSymbol) {
            variables.put(symbol, value);
        }
    }

    @Override
    public Value load(final Symbol symbol) {
        Validate.notNull(symbol, "symbol");

        if (symbol instanceof ConstantSymbol) {
            return constants.containsKey(symbol)
                ? constants.get(symbol)
                : Value.NIL;
        } else if (symbol instanceof VariableSymbol) {
            return variables.containsKey(symbol)
                ? variables.get(symbol)
                : Value.NIL;
        } else {
            return Value.NIL;
        }
    }

    @Override
    public String toString() {
        return String.format("{%s:%s}", getScopeName(), values.keySet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(scopeName, enclosingScope, values, constants, variables);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof DefaultScope)) {
            return false;
        }

        final DefaultScope other = (DefaultScope) obj;
        return Objects.equals(scopeName, other.scopeName)
            && Objects.equals(enclosingScope, other.enclosingScope)
            && Objects.equals(values, other.values)
            && Objects.equals(constants, other.constants)
            && Objects.equals(variables, other.variables);
    }

}
