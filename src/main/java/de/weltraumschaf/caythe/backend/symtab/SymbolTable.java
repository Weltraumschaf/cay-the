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
        table.globals.define(BuildInTypeSymbol.NIL);
        table.globals.define(BuildInTypeSymbol.BOOL);
        table.globals.define(BuildInTypeSymbol.INT);
        table.globals.define(BuildInTypeSymbol.FLOAT);
        table.globals.define(BuildInTypeSymbol.STRING);
    }
}
