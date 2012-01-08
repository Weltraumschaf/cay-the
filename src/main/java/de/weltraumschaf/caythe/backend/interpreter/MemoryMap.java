package de.weltraumschaf.caythe.backend.interpreter;

import java.util.ArrayList;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public interface MemoryMap {

    public Cell getCall(String name);

    public ArrayList<String> getAllNames();
}
