package de.weltraumschaf.caythe.backend.interpreter.memoryimpl;

import de.weltraumschaf.caythe.backend.interpreter.ActivationRecord;
import de.weltraumschaf.caythe.backend.interpreter.RuntimeStack;
import java.util.ArrayList;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class RuntimeStackImpl implements RuntimeStack {

    @Override
    public ArrayList<ActivationRecord> records() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ActivationRecord getTopmost(int nestingLevel) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int currentNestingLevel() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void push(ActivationRecord ar) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ActivationRecord pop() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
