package de.weltraumschaf.caythe.frontend.pascal;

import de.weltraumschaf.caythe.frontend.EofToken;
import de.weltraumschaf.caythe.frontend.Scanner;
import de.weltraumschaf.caythe.frontend.Source;
import static de.weltraumschaf.caythe.frontend.Source.EOF;
import de.weltraumschaf.caythe.frontend.Token;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class PascalScanner extends Scanner {

    public PascalScanner(Source source) {
        super(source);
    }

    @Override
    protected Token extractToken() throws Exception {
        Token token;
        char currentChar = currentChar();

        // Construct the next token.
        // The current character determines the token type.
        if (EOF == currentChar) {
            token = new EofToken(source);
        } else {
            token = new Token(source);
        }

        return token;
    }


}
