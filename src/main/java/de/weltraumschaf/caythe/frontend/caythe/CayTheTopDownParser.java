package de.weltraumschaf.caythe.frontend.caythe;

import de.weltraumschaf.caythe.frontend.Parser;
import de.weltraumschaf.caythe.frontend.Scanner;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class CayTheTopDownParser extends Parser {

    protected static CayTheErrorHandler errorHandler = new CayTheErrorHandler();

    public CayTheTopDownParser(Scanner scanner) {
        super(scanner);
    }

    public CayTheTopDownParser(CayTheTopDownParser parent) {
        super(parent.getScanner());
    }

    @Override
    public void parse() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getErrorCount() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
