package de.weltraumschaf.caythe.backend.interpreter.memoryimpl;

import de.weltraumschaf.caythe.backend.interpreter.ActivationRecord;
import de.weltraumschaf.caythe.backend.interpreter.RuntimeDisplay;
import java.util.ArrayList;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class RuntimeDisplayImpl extends ArrayList<ActivationRecord> implements RuntimeDisplay {

    public RuntimeDisplayImpl() {
        add(null); // Dummy element 0 (never used).
    }

    @Override
    public ActivationRecord getActivationRecord(int nestingLevel) {
        return get(nestingLevel);
    }

    @Override
    public void callUpdate(int nestingLevel, ActivationRecord ar) {
        // Next higher level: Append a new element at the top.
        if (nestingLevel >= size()) {
            add(ar);
        }
        // Existing nesting level: Set at the specified level.
        else {
            ActivationRecord previousAr = get(nestingLevel);
            set(nestingLevel, ar.makeLinkTo(previousAr));
        }
    }

    @Override
    public void returnUpdate(int nestingLevel) {
        int topIndex = size() - 1;
        ActivationRecord ar = get(nestingLevel);
        ActivationRecord previousAr = ar.linkedTo();

        // Point the element at that nesting level to the
        // previous activation record.
        if (null != previousAr) {
            set(nestingLevel, previousAr);
        }
        // The top element has become null, so remove it.
        else if (nestingLevel == topIndex) {
            remove(topIndex);
        }
    }

}
