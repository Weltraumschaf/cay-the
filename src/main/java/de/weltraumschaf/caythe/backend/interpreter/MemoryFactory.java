package de.weltraumschaf.caythe.backend.interpreter;

import de.weltraumschaf.caythe.backend.interpreter.memoryimpl.ActivationRecordImpl;
import de.weltraumschaf.caythe.backend.interpreter.memoryimpl.CellImpl;
import de.weltraumschaf.caythe.backend.interpreter.memoryimpl.MemoryMapImpl;
import de.weltraumschaf.caythe.backend.interpreter.memoryimpl.RuntimeDisplayImpl;
import de.weltraumschaf.caythe.backend.interpreter.memoryimpl.RuntimeStackImpl;
import de.weltraumschaf.caythe.intermediate.SymbolTable;
import de.weltraumschaf.caythe.intermediate.SymbolTableEntry;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class MemoryFactory {

    public static RuntimeStack createRuntimeStack() {
        return new RuntimeStackImpl();
    }

    public static RuntimeDisplay createRuntimeDisplay() {
        return new RuntimeDisplayImpl();
    }

    public static ActivationRecord createActivationRecord(SymbolTableEntry routineId) {
        return new ActivationRecordImpl(routineId);
    }

    public static MemoryMap createMemoryMap(SymbolTable symTab) {
        return new MemoryMapImpl(symTab);
    }

    public static Cell createCell(Object value) {
        return new CellImpl(value);
    }

}
