package de.weltraumschaf.caythe.backend.symtab;

/**
 * Describes the scope of symbols and their values, if they have one.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public interface Scope {

    /**
     * Null object pattern: USed instead of {@code nul}.
     */
    Scope NULL = new Scope() {

        @Override
        public String getScopeName() {
            return "NULL";
        }

        @Override
        public Scope getEnclosing() {
            return this;
        }

        @Override
        public boolean hasEnclosing() {
            return false;
        }

        @Override
        public void defineValue(final Symbol sym) {
        }

        @Override
        public boolean isValueDefined(final String identifier) {
            return false;
        }

        @Override
        public Symbol resolveValue(final String name) {
            return Symbol.NULL;
        }

        @Override
        public void store(final Symbol symbol, final Value value) {
            // Ignore it.
        }

        @Override
        public Value load(final Symbol symbol) {
            return Value.NIL;
        }

        @Override
        public String toString() {
            return getScopeName();
        }

        @Override
        public void defineFunction(final FunctionSymbol sym) {
            // Ignore it.
        }

        @Override
        public boolean isFunctionDefined(final String identifier) {
            return false;
        }

        @Override
        public FunctionSymbol resolveFunction(final String name) {
            return new FunctionSymbol("", FunctionSymbol.VOID, FunctionSymbol.NOARGS, NULL);
        }

        @Override
        public void wipe() {
            // Ignore it.
        }

    };

    /**
     * Name of scope.
     *
     * @return never {@code null} or empty
     */
    String getScopeName();

    /**
     * Where to look next for symbols.
     *
     * @return may be {@code null}
     */
    Scope getEnclosing();

    /**
     * Whether this scope has an enclosing scope.
     *
     * @return {@code true} if it has one, else {@code false}
     */
    boolean hasEnclosing();

    /**
     * Define a symbol in the current scope.
     *
     * @param sym must not be {@code null}
     */
    void defineValue(Symbol sym);

    /**
     * Whether the symbol is defined in this scope or an enclosing one.
     *
     * @param identifier must not be {@code null} or empty
     * @return {@code true} if defined, else {@code false}
     */
    boolean isValueDefined(final String identifier);

    /**
     * Look up name in this scope or in enclosing scope if not here.
     *
     * @param name must not be {@code null} or empty
     * @return the found symbol, unless {@link Symbol#NULL}
     */
    Symbol resolveValue(String name);

    /**
     * Define a symbol in the current scope.
     *
     * @param sym must not be {@code null}
     */
    void defineFunction(FunctionSymbol sym);

    /**
     * Whether the symbol is defined in this scope or an enclosing one.
     *
     * @param identifier must not be {@code null} or empty
     * @return {@code true} if defined, else {@code false}
     */
    boolean isFunctionDefined(final String identifier);

    /**
     * Look up name in this scope or in enclosing scope if not here.
     *
     * @param name must not be {@code null} or empty
     * @return the found symbol, unless {@link Symbol#NULL}
     */
    FunctionSymbol resolveFunction(String name);

    /**
     * Stores a value for a constant/variable.
     * <p>
     * Will throw {@link IllegalSateException} if already set.
     * </p>
     *
     * @param symbol must not be {@code null}
     * @param value must not be {@code null}
     */
    void store(Symbol symbol, Value value);

    /**
     * Load a value for a constant/variable.
     *
     * @param symbol must not be {@code null}
     * @return never {@code null}, {@link Value.NIL} if not exist
     */
    Value load(Symbol symbol);

    void wipe();

    /**
     * Create new global scope.
     *
     * @return never {@code null}, always new instance
     */
    static Scope newGlobal() {
        return new DefaultScope("global", NULL);
    }

    /**
     * Create new global scope.
     *
     * @param enclosing must not be {@code null}
     * @return never {@code null}, always new instance
     */
    static Scope newLocal(final Scope enclosing) {
        return new DefaultScope("local", enclosing);
    }

}
