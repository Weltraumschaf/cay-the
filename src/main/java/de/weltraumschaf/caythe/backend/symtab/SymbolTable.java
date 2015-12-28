package de.weltraumschaf.caythe.backend.symtab;

/**
 * Holds the global scope and initializes build in types.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class SymbolTable {

    private final Scope globals = Scope.newGlobal();

    SymbolTable() {
        super();
    }

    public Scope getGlobals() {
        return globals;
    }

    @Override
    public String toString() {
        return globals.toString();
    }

    public static void init(final SymbolTable table) {
        table.globals.define((Symbol) BuildInTypeSymbol.NIL);
        table.globals.define((Symbol) BuildInTypeSymbol.BOOL);
        table.globals.define((Symbol) BuildInTypeSymbol.INT);
        table.globals.define((Symbol) BuildInTypeSymbol.FLOAT);
        table.globals.define((Symbol) BuildInTypeSymbol.STRING);
    }

    public static SymbolTable newTable() {
        final SymbolTable table = new SymbolTable();
        init(table);
        return table;
    }
}
