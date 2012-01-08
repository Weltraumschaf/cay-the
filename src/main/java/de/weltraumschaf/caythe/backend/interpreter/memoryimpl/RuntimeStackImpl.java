package de.weltraumschaf.caythe.backend.interpreter.memoryimpl;

import de.weltraumschaf.caythe.backend.interpreter.MemoryFactory;
import de.weltraumschaf.caythe.backend.interpreter.ActivationRecord;
import de.weltraumschaf.caythe.backend.interpreter.RuntimeDisplay;
import de.weltraumschaf.caythe.backend.interpreter.RuntimeStack;
import java.util.ArrayList;

/**
 *
 * @todo Dont inherit fro mArratyList make composition.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class RuntimeStackImpl extends ArrayList<ActivationRecord> implements RuntimeStack {

    private RuntimeDisplay display;

    public RuntimeStackImpl() {
        display = MemoryFactory.createRuntimeDisplay();
    }

    @Override
    public ArrayList<ActivationRecord> records() {
        return this;
    }

    @Override
    public ActivationRecord getTopmost(int nestingLevel) {
        return display.getActivationRecord(nestingLevel);
    }

    @Override
    public int currentNestingLevel() {
        int topIndex = size() - 1;
        return topIndex >= 0 ? get(topIndex).getNestingLevel() : -1;
    }

    @Override
    public void push(ActivationRecord ar) {
        int nestingLevel = ar.getNestingLevel();
        add(ar);
        display.callUpdate(nestingLevel, ar);
    }

    @Override
    public ActivationRecord pop() {
        display.returnUpdate(currentNestingLevel());
        return remove(size() - 1);
    }

}
