package de.weltraumschaf.caythe.intermediate;

import de.weltraumschaf.caythe.intermediate.symboltableimpl.*;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class SymbolTableFactory {

    public static SymbolTableStack createSymbolTableStack() {
        return new SymbolTableStackImpl();
    }

    public static SymbolTable createSymbolTable(int nestingLevel) {
        return new SymbolTableImpl(nestingLevel);
    }

    public static SymbolTableEntry createSymbolTableEntry(String name, SymbolTable symbolTable) {
        return new SymbolTableEntryImpl(name, symbolTable);
    }

}
