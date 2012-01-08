package de.weltraumschaf.caythe.backend.interpreter;

import java.util.ArrayList;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public interface ActivationRecord {

    public Cell getCell();

    public ArrayList getAllNames();

    public int getNestingLevel();

    public ActivationRecord linkedTo();

    public ActivationRecord makeLinkTo(ActivationRecord previous);
}
