package de.weltraumschaf.caythe.intermediate.symboltableimpl;

import de.weltraumschaf.caythe.intermediate.SymbolTable;
import de.weltraumschaf.caythe.intermediate.SymbolTableEntry;
import de.weltraumschaf.caythe.intermediate.SymbolTableKey;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class SymbolTableEntryImpl extends HashMap<SymbolTableKey, Object> implements SymbolTableEntry {

    private String name;                     // entry name
    private SymbolTable symbolTable;              // parent symbol table
    private ArrayList<Integer> lineNumbers;  // source line numbers

    public SymbolTableEntryImpl(String name, SymbolTable symTab) {
	this.name	 = name;
	this.symbolTable = symTab;
	this.lineNumbers = new ArrayList<Integer>();
    }

    @Override
    public String getName() {
	return name;
    }

    public SymbolTable getSymTab() {
	return symbolTable;
    }

    @Override
    public void appendLineNumber(int lineNumber) {
	lineNumbers.add(lineNumber);
    }

    @Override
    public ArrayList<Integer> getLineNumbers() {
	return lineNumbers;
    }

    @Override
    public void setAttribute(SymbolTableKey key, Object value) {
	put(key, value);
    }

    @Override
    public Object getAttribute(SymbolTableKey key) {
	return get(key);
    }

    @Override
    public SymbolTable getSymbolTable() {
	return symbolTable;
    }
    
}
