package de.weltraumschaf.caythe.intermediate;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public interface SymbolTableStack {

    public int getCurrentNestingLevel();

    public SymbolTable getLocalSymTab();

    public SymbolTableEntry enterLocal(String name);

    public SymbolTableEntry lookupLocal(String name);

    public SymbolTableEntry lookup(String name);

    public void setProgramId(SymbolTableEntry entry);

    public SymbolTableEntry getProgramId();

    public SymbolTable push();

    public SymbolTable push(SymbolTable symbolTable);

    public SymbolTable pop();
}
