package de.weltraumschaf.caythe.frontend.pascal.parsers;

import de.weltraumschaf.caythe.frontend.Scanner;
import de.weltraumschaf.caythe.frontend.pascal.PascalTopDownParser;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class DeclarationsParser extends PascalTopDownParser {

    public DeclarationsParser(PascalTopDownParser parent) {
        super(parent);
    }

    public DeclarationsParser(Scanner scanner) {
        super(scanner);
    }

}
