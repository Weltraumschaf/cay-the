package de.weltraumschaf.caythe.frontend.pascal.parsers;

import de.weltraumschaf.caythe.frontend.Scanner;
import de.weltraumschaf.caythe.frontend.pascal.PascalTopDownParser;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class ConstantDefinitionParser extends DeclarationsParser {

    public ConstantDefinitionParser(Scanner scanner) {
        super(scanner);
    }

    public ConstantDefinitionParser(PascalTopDownParser parent) {
        super(parent);
    }

}
