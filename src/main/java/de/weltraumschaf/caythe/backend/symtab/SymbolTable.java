package de.weltraumschaf.caythe.backend.symtab;

/**
 * @since 1.0.0
 */
public final class SymbolTable {

    private final Scope globals = new GlobalScope();

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
