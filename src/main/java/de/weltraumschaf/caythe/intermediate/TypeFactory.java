package de.weltraumschaf.caythe.intermediate;

import de.weltraumschaf.caythe.intermediate.typeimpl.TypeSpecImpl;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class TypeFactory {

    public static TypeSpecImpl createType(TypeForm form) {
        return new TypeSpecImpl(form);
    }

    public static TypeSpecImpl createType(String value) {
        return new TypeSpecImpl(value);
    }
}
