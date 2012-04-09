package de.weltraumschaf.caythe.frontend.caythe;

import de.weltraumschaf.caythe.frontend.Scanner;
import de.weltraumschaf.caythe.frontend.Source;
import de.weltraumschaf.caythe.frontend.Token;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class CayTheScanner extends Scanner {

    public CayTheScanner(Source source) {
        super(source);
    }

    @Override
    protected Token extractToken() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
