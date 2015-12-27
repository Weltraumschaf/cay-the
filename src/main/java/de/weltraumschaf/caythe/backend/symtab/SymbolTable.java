package de.weltraumschaf.caythe.backend.symtab;

/**
 */
public final class SymbolTable {

    private final Scope globals = new GlobalScope();

    public SymbolTable() {
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
}
