package de.weltraumschaf.caythe.intermediate;

import de.weltraumschaf.caythe.intermediate.symboltableimpl.SymbolTableEntryImpl;
import de.weltraumschaf.caythe.intermediate.symboltableimpl.SymbolTableImpl;
import de.weltraumschaf.caythe.intermediate.symboltableimpl.SymbolTableStackImpl;

/**
 * Factory to create symbol tables.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class SymbolTableFactory {

    /**
     * Creates a symbol tables stack.
     *
     * @return
     */
    public static SymbolTableStack createSymbolTableStack() {
        return new SymbolTableStackImpl();
    }

    /**
     * Creates a symbol table.
     *
     * @param nestingLevel
     * @return
     */
    public static SymbolTable createSymbolTable(int nestingLevel) {
        return new SymbolTableImpl(nestingLevel);
    }

    /**
     * Creates a symbol table entry.
     *
     * @param name
     * @param symbolTable
     * @return
     */
    public static SymbolTableEntry createSymbolTableEntry(String name, SymbolTable symbolTable) {
        return new SymbolTableEntryImpl(name, symbolTable);
    }

}
