package de.weltraumschaf.caythe.backend.interpreter;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public interface RuntimeDisplay {

    public ActivationRecord getActivationRecord();

    public void callUpdate();

    public void returnUpdate();
}
