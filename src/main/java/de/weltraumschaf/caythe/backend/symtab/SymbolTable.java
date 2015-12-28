package de.weltraumschaf.caythe.backend.symtab;

/**
 * Holds the global scope and initializes build in types.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class SymbolTable {

    /**
     * The one and only global scope.
     */
    private final Scope globals = Scope.newGlobal();

    /**
     * Dedicated constructor.
     * <p>
     * Use {@link #newTable() factory method} to create instances.
     * </p>
     */
    SymbolTable() {
        super();
    }

    /**
     * Get the global scope.
     *
     * @return never {@code null}, always same instance
     */
    public Scope getGlobals() {
        return globals;
    }

    @Override
    public String toString() {
        return globals.toString();
    }

    /**
     * Define build in types.
     *
     * @param table must not be {@code null}
     */
    public static void init(final SymbolTable table) {
        table.globals.define((Symbol) BuildInTypeSymbol.NIL);
        table.globals.define((Symbol) BuildInTypeSymbol.BOOL);
        table.globals.define((Symbol) BuildInTypeSymbol.INT);
        table.globals.define((Symbol) BuildInTypeSymbol.FLOAT);
        table.globals.define((Symbol) BuildInTypeSymbol.STRING);
    }

    /**
     * Crete and initialize a new table.
     *
     * @return never {@code null}, always new instance
     */
    public static SymbolTable newTable() {
        final SymbolTable table = new SymbolTable();
        init(table);
        return table;
    }
}
