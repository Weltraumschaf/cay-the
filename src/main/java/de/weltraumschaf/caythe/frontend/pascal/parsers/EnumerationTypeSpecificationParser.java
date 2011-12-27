package de.weltraumschaf.caythe.frontend.pascal.parsers;

import de.weltraumschaf.caythe.frontend.Scanner;
import de.weltraumschaf.caythe.frontend.pascal.PascalTopDownParser;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class EnumerationTypeSpecificationParser extends TypeSpecificationParser {

    public EnumerationTypeSpecificationParser(Scanner scanner) {
        super(scanner);
    }

    public EnumerationTypeSpecificationParser(PascalTopDownParser parent) {
        super(parent);
    }

}
