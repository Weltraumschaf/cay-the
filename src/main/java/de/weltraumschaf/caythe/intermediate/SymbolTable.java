package de.weltraumschaf.caythe.intermediate;

import java.util.ArrayList;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public interface SymbolTable {

    public int getNestingLevel();

    public SymbolTableEntry enter(String name);

    public SymbolTableEntry lookup(String name);

    public ArrayList<SymbolTableEntry> sortedEntries();

}
