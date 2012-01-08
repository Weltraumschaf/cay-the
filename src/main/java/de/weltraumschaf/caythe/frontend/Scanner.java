package de.weltraumschaf.caythe.frontend;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public abstract class Scanner {

    protected Source source;
    private Token currentToken;

    public Scanner(Source source) {
        this.source = source;
    }

    public Token currentToken() {
        return currentToken;
    }

    public Token nextToken() throws Exception {
        currentToken = extractToken();
        return currentToken;
    }

    protected abstract Token extractToken() throws Exception;

    public char currentChar() {
        try {
            return source.currentChar();
        }
        catch (IOException ex) {
            return Source.EOF;
        }
    }

    public char nextChar() {
        try {
            return source.nextChar();
        }
        catch (IOException ex) {
            return Source.EOF;
        }
    }
}
