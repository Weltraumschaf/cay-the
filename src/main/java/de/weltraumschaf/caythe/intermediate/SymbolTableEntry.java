package de.weltraumschaf.caythe.intermediate;

import java.util.ArrayList;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public interface SymbolTableEntry {

    public String getName();

    public SymbolTable getSymbolTable();

    public void appendLineNumber(int lineNumber);

    public ArrayList<Integer> getLineNumbers();

    public void setAttribute(SymbolTableKey key, Object value);

    public Object getAttribute(SymbolTableKey key);

}
