package de.weltraumschaf.caythe.backend.symtab;

/**
 * Describes the scope of symbols.
 *
 * @since 1.0.0
 */
public interface Scope {

    /**
     * Null object pattern: USed instead of {@code nul}.
     */
    Scope NULL = new Scope() {

        @Override
        public String getScopeName() {
            return "";
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
        public void define(final Symbol sym) {
        }

        @Override
        public Symbol resolve(final String name) {
            return Symbol.NULL;
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
    void define(Symbol sym);

    /**
     * Look up name in this scope or in enclosing scope if not here.
     *
     * @param name must not be {@code null} or empty
     * @return
     */
    Symbol resolve(String name);
}
