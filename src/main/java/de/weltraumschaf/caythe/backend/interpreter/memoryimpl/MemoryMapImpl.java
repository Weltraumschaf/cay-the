package de.weltraumschaf.caythe.backend.interpreter.memoryimpl;

import de.weltraumschaf.caythe.backend.interpreter.Cell;
import de.weltraumschaf.caythe.backend.interpreter.MemoryMap;
import java.util.ArrayList;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class MemoryMapImpl implements MemoryMap {

    public MemoryMapImpl() {
    }

    @Override
    public ArrayList<String> getAllNames() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Cell getCall(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
