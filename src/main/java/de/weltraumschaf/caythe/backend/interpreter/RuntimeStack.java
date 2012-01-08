package de.weltraumschaf.caythe.backend.interpreter;

import java.util.ArrayList;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public interface RuntimeStack {

    public ArrayList<ActivationRecord> records();

    public ActivationRecord getTopmost(int nestingLevel);

    public int currentNestingLevel();

    public void push(ActivationRecord ar);

    public ActivationRecord pop();
}
