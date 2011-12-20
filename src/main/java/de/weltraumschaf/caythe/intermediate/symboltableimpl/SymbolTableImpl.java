package de.weltraumschaf.caythe.intermediate.symboltableimpl;

import de.weltraumschaf.caythe.intermediate.SymbolTable;
import de.weltraumschaf.caythe.intermediate.SymbolTableEntry;
import de.weltraumschaf.caythe.intermediate.SymbolTableFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class SymbolTableImpl extends TreeMap<String, SymbolTableEntry> implements SymbolTable {

    private int nestingLevel;

    public SymbolTableImpl(int nestingLevel) {
        this.nestingLevel = nestingLevel;
    }

    @Override
    public int getNestingLevel() {
        return nestingLevel;
    }

    @Override
    public SymbolTableEntry enter(String name) {
        SymbolTableEntry entry = SymbolTableFactory.createSymbolTableEntry(name, this);
        put(name, entry);

        return entry;
    }

    @Override
    public SymbolTableEntry lookup(String name) {
        return get(name);
    }

    @Override
    public ArrayList<SymbolTableEntry> sortedEntries() {
        Collection<SymbolTableEntry> entries = values();
        Iterator<SymbolTableEntry> iter      = entries.iterator();
        ArrayList<SymbolTableEntry> list     = new ArrayList<SymbolTableEntry>(size());

        // Iterate over the sorted entries and append them to the list.
        while (iter.hasNext()) {
            list.add(iter.next());
        }

        return list;  // sorted list of entries
    }
}
