package de.weltraumschaf.caythe.frontend.caythe;

import de.weltraumschaf.caythe.frontend.Scanner;
import de.weltraumschaf.caythe.frontend.Source;
import static de.weltraumschaf.caythe.frontend.Source.EOF;
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

    /**
     * Skips white spaces and comments.
     *
     * @throws Exception
     */
    private void skipWhitespace() throws Exception {
        char currentChar = currentChar();

        while (Character.isWhitespace(currentChar) || ( '/' == currentChar )) {
            // Start of a comment?
            if ('/' == currentChar) {
                char nextChar = peakChar();

                if ('/' == nextChar) { // single line comment
                    skipToNextLine();
                }
                else if ('*' == nextChar) { // multi line comment
                    do {
                        currentChar = nextChar(); // Consume comment chars.
                    } while (('*' != currentChar) && ('/' != peakChar()) && (EOF != currentChar));

                    // Found closing '*/'?
                    if ('*' == currentChar) {
                        currentChar = nextChar(); // Consumes the '*'.
                        if ('/' == currentChar) {
                            currentChar = nextChar(); // Consumes the '/'.
                        }
                    }
                }
            } // Not a comment.
            else {
                currentChar = nextChar();
            }
        }
    }
}
