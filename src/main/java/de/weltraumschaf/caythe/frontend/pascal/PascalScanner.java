package de.weltraumschaf.caythe.frontend.pascal;

import de.weltraumschaf.caythe.frontend.EofToken;
import de.weltraumschaf.caythe.frontend.Scanner;
import de.weltraumschaf.caythe.frontend.Source;
import static de.weltraumschaf.caythe.frontend.Source.EOF;
import de.weltraumschaf.caythe.frontend.Token;
import static de.weltraumschaf.caythe.frontend.pascal.PascalErrorCode.INVALID_CHARACTER;
import de.weltraumschaf.caythe.frontend.pascal.tokens.*;

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
        skipWhitespace();

        Token token;
        char currentChar = currentChar();

        // Construct the next token.
        // The current character determines the token type.
        if (EOF == currentChar) {
            token = new EofToken(source);
        } else if (Character.isLetter(currentChar)) {
            token = new PascalWordToken(source);
        } else if (Character.isDigit(currentChar)) {
            token = new PascalNumberToken(source);
        } else if ('\'' == currentChar) {
            token = new PascalStringToken(source);
        } else if (PascalTokenType.SPECIAL_SYMBOLS.containsKey(Character.toString(currentChar))) {
            token = new PascalSpecialSymbolToken(source);
        } else {
            token = new PascalErrorToken(source, INVALID_CHARACTER, Character.toString(currentChar));
        }

        return token;
    }

    private void skipWhitespace() throws Exception {
        char currentChar = currentChar();

        while (Character.isWhitespace(currentChar) || ( '{' == currentChar )) {
            // Start of a comment?
            if ('{' == currentChar) {
                do {
                    currentChar = nextChar(); // Consume comment chars.
                } while (( '}' != currentChar ) && ( EOF != currentChar ));

                // Found closing '}'?
                if ('}' == currentChar) {
                    currentChar = nextChar(); // Consumes the '}'.
                }
            } // Not a comment.
            else {
                currentChar = nextChar();
            }
        }
    }
}
