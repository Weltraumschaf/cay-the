package de.weltraumschaf.caythe.backend.interpreter.memoryimpl;

import de.weltraumschaf.caythe.backend.interpreter.ActivationRecord;
import de.weltraumschaf.caythe.backend.interpreter.Cell;
import de.weltraumschaf.caythe.backend.interpreter.MemoryFactory;
import de.weltraumschaf.caythe.backend.interpreter.MemoryMap;
import de.weltraumschaf.caythe.intermediate.SymbolTable;
import de.weltraumschaf.caythe.intermediate.SymbolTableEntry;
import java.util.ArrayList;

import static de.weltraumschaf.caythe.intermediate.symboltableimpl.SymbolTableKeyImpl.ROUTINE_SYMBOL_TABLE;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class ActivationRecordImpl implements ActivationRecord {

    private SymbolTableEntry routineId;
    private ActivationRecord link;
    private int nestingLevel;
    private MemoryMap memoryMap;

    public ActivationRecordImpl(SymbolTableEntry routineId) {
        SymbolTable symTab = (SymbolTable) routineId.getAttribute(ROUTINE_SYMBOL_TABLE);
        this.routineId     = routineId;
        this.nestingLevel  = symTab.getNestingLevel();
        this.memoryMap     = MemoryFactory.createMemoryMap(symTab);
    }

    @Override
    public SymbolTableEntry getRuntineId() {
        return routineId;
    }

    @Override
    public Cell getCell(String name) {
        return memoryMap.getCall(name);
    }

    @Override
    public ArrayList<String> getAllNames() {
        return memoryMap.getAllNames();
    }

    @Override
    public int getNestingLevel() {
        return nestingLevel;
    }

    @Override
    public ActivationRecord linkedTo() {
        return link;
    }

    @Override
    public ActivationRecord makeLinkTo(ActivationRecord ar) {
        link = ar;
        return this;
    }

}
