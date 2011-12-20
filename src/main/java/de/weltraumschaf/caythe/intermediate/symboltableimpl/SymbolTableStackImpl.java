package de.weltraumschaf.caythe.intermediate.symboltableimpl;

import de.weltraumschaf.caythe.intermediate.SymbolTable;
import de.weltraumschaf.caythe.intermediate.SymbolTableEntry;
import de.weltraumschaf.caythe.intermediate.SymbolTableFactory;
import de.weltraumschaf.caythe.intermediate.SymbolTableStack;
import java.util.ArrayList;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class SymbolTableStackImpl extends ArrayList<SymbolTable> implements SymbolTableStack {

    private int currentNestingLevel;  // current scope nesting level

    public SymbolTableStackImpl() {
	this.currentNestingLevel = 0;
	add(SymbolTableFactory.createSymbolTable(currentNestingLevel));
    }

    @Override
    public int getCurrentNestingLevel() {
	return currentNestingLevel;
    }

    @Override
    public SymbolTable getLocalSymTab() {
	return get(currentNestingLevel);
    }

    @Override
    public SymbolTableEntry enterLocal(String name) {
	return get(currentNestingLevel).enter(name);
    }

    @Override
    public SymbolTableEntry lookupLocal(String name) {
	return get(currentNestingLevel).lookup(name);
    }

    @Override
    public SymbolTableEntry lookup(String name) {
	return lookupLocal(name);
    }
}
