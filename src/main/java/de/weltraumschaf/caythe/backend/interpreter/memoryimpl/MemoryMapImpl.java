package de.weltraumschaf.caythe.backend.interpreter.memoryimpl;

import java.util.Iterator;
import java.util.Set;
import de.weltraumschaf.caythe.backend.interpreter.Cell;
import de.weltraumschaf.caythe.backend.interpreter.MemoryFactory;
import de.weltraumschaf.caythe.backend.interpreter.MemoryMap;
import de.weltraumschaf.caythe.intermediate.Definition;
import de.weltraumschaf.caythe.intermediate.SymbolTable;
import de.weltraumschaf.caythe.intermediate.SymbolTableEntry;
import de.weltraumschaf.caythe.intermediate.TypeForm;
import de.weltraumschaf.caythe.intermediate.TypeSpecification;
import de.weltraumschaf.caythe.intermediate.typeimpl.TypeFormImpl;
import java.util.ArrayList;
import java.util.HashMap;

import static de.weltraumschaf.caythe.intermediate.symboltableimpl.DefinitionImpl.VARIABLE;
import static de.weltraumschaf.caythe.intermediate.symboltableimpl.DefinitionImpl.FUNCTION;
import static de.weltraumschaf.caythe.intermediate.symboltableimpl.DefinitionImpl.VALUE_PARAM;
import static de.weltraumschaf.caythe.intermediate.symboltableimpl.DefinitionImpl.FIELD;
import static de.weltraumschaf.caythe.intermediate.symboltableimpl.DefinitionImpl.VAR_PARAM;
import static de.weltraumschaf.caythe.intermediate.typeimpl.TypeKeyImpl.ARRAY_ELEMENT_COUNT;
import static de.weltraumschaf.caythe.intermediate.typeimpl.TypeKeyImpl.ARRAY_ELEMENT_TYPE;
import static de.weltraumschaf.caythe.intermediate.typeimpl.TypeKeyImpl.RECORD_SYMBOL_TABLE;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class MemoryMapImpl extends HashMap<String, Cell> implements MemoryMap {

    public MemoryMapImpl(SymbolTable symTab) {
        ArrayList<SymbolTableEntry> entries = symTab.sortedEntries();

        for (SymbolTableEntry entry : entries) {
            Definition defn = entry.getDefinition();

            // Not a VAR parameter: Alocate cells for the data type
            //                      in the hashmap.
            if (( defn == VARIABLE ) || ( defn == FUNCTION ) || ( defn == VALUE_PARAM ) || ( defn == FIELD )) {
                String name = entry.getName();
                TypeSpecification type = entry.getTypeSpecification();
                put(name, MemoryFactory.createCell(allocateCellValue(type)));
            } // VAR parameter: Allocate a simple cell to hold a reference
            //                in the hashmap.
            else if (defn == VAR_PARAM) {
                String name = entry.getName();
                put(name, MemoryFactory.createCell(null));
            }
        }
    }

    private Object allocateCellValue(TypeSpecification type) {
        TypeForm form = type.getForm();

        switch ((TypeFormImpl) form) {
            case ARRAY: {
                return allocateArrayCells(type);
            }
            case RECORD: {
                return allocateRecordMap(type);
            }
            default: {
                return null; // Uninitializes scalar value.
            }
        }
    }

    @Override
    public ArrayList<String> getAllNames() {
        ArrayList<String> list = new ArrayList<String>();
        Set<String> names = keySet();
        Iterator<String> it = names.iterator();

        while (it.hasNext()) {
            list.add(it.next());
        }

        return list;
    }

    @Override
    public Cell getCall(String name) {
        return get(name);
    }

    private Object allocateArrayCells(TypeSpecification type) {
        int elementCount = (Integer) type.getAttribute(ARRAY_ELEMENT_COUNT);
        TypeSpecification elementType = (TypeSpecification) type.getAttribute(ARRAY_ELEMENT_TYPE);
        Cell allocation[] = new Cell[elementCount];

        for (int i = 0; i < elementCount; ++i) {
            allocation[i] = MemoryFactory.createCell(allocateCellValue(elementType));
        }

        return allocation;
    }

    private Object allocateRecordMap(TypeSpecification type) {
        SymbolTable symTab  = (SymbolTable) type.getAttribute(RECORD_SYMBOL_TABLE);
        MemoryMap memoryMap = MemoryFactory.createMemoryMap(symTab);
        return memoryMap;
    }

}
