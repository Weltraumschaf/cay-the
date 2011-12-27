package de.weltraumschaf.caythe.frontend.pascal.parsers;

import de.weltraumschaf.caythe.frontend.Scanner;
import de.weltraumschaf.caythe.frontend.pascal.PascalTopDownParser;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class ArrayTypeSpecificationParser extends TypeSpecificationParser {

    public ArrayTypeSpecificationParser(Scanner scanner) {
        super(scanner);
    }

    public ArrayTypeSpecificationParser(PascalTopDownParser parent) {
        super(parent);
    }

}
