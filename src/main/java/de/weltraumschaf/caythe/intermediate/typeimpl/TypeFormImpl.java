package de.weltraumschaf.caythe.intermediate.typeimpl;

import de.weltraumschaf.caythe.intermediate.TypeForm;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public enum TypeFormImpl implements TypeForm {
    SCALAR, ENUMERATION, SUBRANGE, ARRAY, RECORD;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

}
