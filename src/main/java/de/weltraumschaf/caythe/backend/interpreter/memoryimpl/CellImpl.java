package de.weltraumschaf.caythe.backend.interpreter.memoryimpl;

import de.weltraumschaf.caythe.backend.interpreter.Cell;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class CellImpl implements Cell {

    private Object value = null;

    public CellImpl(Object value) {
        this.value = value;
    }

    @Override
    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public Object getValue() {
        return value;
    }

}
