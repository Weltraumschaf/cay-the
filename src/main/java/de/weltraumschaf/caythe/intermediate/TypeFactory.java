package de.weltraumschaf.caythe.intermediate;

import de.weltraumschaf.caythe.intermediate.typeimpl.TypeSpecificationImpl;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class TypeFactory {

    public static TypeSpecificationImpl createType(TypeForm form) {
        return new TypeSpecificationImpl(form);
    }

    public static TypeSpecificationImpl createType(String value) {
        return new TypeSpecificationImpl(value);
    }
}
