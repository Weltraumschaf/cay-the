package de.weltraumschaf.caythe.backend.symtab;

/**
 * Describes a found symbol.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public interface Symbol {

    /**
     * Null object pattern: USed instead of {@code nul}.
     */
    Symbol NULL = new Symbol() {

        @Override
        public String getName() {
            return "nil";
        }

        @Override
        public Scope getScope() {
            return Scope.NULL;
        }

        @Override
        public Value load() {
            return Value.NIL;
        }

    };

    /**
     * Get the name of the symbol.
     *
     * @return never {@code null} or empty
     */
    String getName();

    /**
     * Get the scope of the symbol.
     *
     * @return never {@code null}
     */
    Scope getScope();

    /**
     * Short hand to load a value from scope pool.
     *
     * @return never {@code null}, maybe {@link Value.NIL}
     */
    Value load();
}
