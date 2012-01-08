package de.weltraumschaf.caythe.backend.interpreter;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public interface RuntimeDisplay {

    public ActivationRecord getActivationRecord(int nestingLevel);

    public void callUpdate(int nestingLevel, ActivationRecord ar);

    public void returnUpdate(int nestingLevel);
    
}
