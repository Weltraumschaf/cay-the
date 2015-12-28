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
abstract class DefaultScope implements Scope {

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
    public final Symbol resolve(final String name) {
        Validate.notEmpty(name, "name");

        if (symbols.containsKey(name)) {
            return symbols.get(name);
        }

        if (hasEnclosing()) {
            return getEnclosing().resolve(name);
        }

        return Symbol.NULL;
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
    public final boolean isDefined(final String identifier) {
        return symbols.containsKey(identifier) || (hasEnclosing() && getEnclosing().isDefined(identifier));
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
    public void store(final Symbol symbol, final Value value) {
        Validate.notNull(symbol, "symbol");
        Validate.notNull(value, "value");

        if (!isDefined(symbol.getName())) {
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
    public final String toString() {
        return String.format("{%s:%s}", getScopeName(), symbols.keySet());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(scopeName, enclosingScope, symbols, constants, variables);
    }

    @Override
    public final boolean equals(final Object obj) {
        if (!(obj instanceof DefaultScope)) {
            return false;
        }

        final DefaultScope other = (DefaultScope) obj;
        return Objects.equals(scopeName, other.scopeName)
            && Objects.equals(enclosingScope, other.enclosingScope)
            && Objects.equals(symbols, other.symbols)
            && Objects.equals(constants, other.constants)
            && Objects.equals(variables, other.variables);
    }

}
