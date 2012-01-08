package de.weltraumschaf.caythe.backend.interpreter;

import de.weltraumschaf.caythe.intermediate.SymbolTableEntry;
import java.util.ArrayList;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public interface ActivationRecord {

    public SymbolTableEntry getRuntineId();

    public Cell getCell(String name);

    public ArrayList<String> getAllNames();

    public int getNestingLevel();

    public ActivationRecord linkedTo();

    public ActivationRecord makeLinkTo(ActivationRecord ar);
}
