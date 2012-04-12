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
            token = new EofToken(getSource());
        } else if (Character.isLetter(currentChar)) {
            token = new PascalWordToken(getSource());
        } else if (Character.isDigit(currentChar)) {
            token = new PascalNumberToken(getSource());
        } else if ('\'' == currentChar) {
            token = new PascalStringToken(getSource());
        } else if (PascalTokenType.SPECIAL_SYMBOLS.containsKey(Character.toString(currentChar))) {
            token = new PascalSpecialSymbolToken(getSource());
        } else {
            token = new PascalErrorToken(getSource(), INVALID_CHARACTER, Character.toString(currentChar));
        }

        token.extract();
        return token;
    }

    /**
     * Skips white spaces and comments.
     *
     * @throws Exception
     */
    private void skipWhitespace() throws Exception {
        char currentChar = currentChar();

        while (Character.isWhitespace(currentChar) || ( '{' == currentChar )) {
            // Start of a comment?
            if ('{' == currentChar) {
                do {
                    nextChar(); // Consume comment chars.
                    currentChar = currentChar();
                } while (( '}' != currentChar ) && ( EOF != currentChar ));

                // Found closing '}'?
                if ('}' == currentChar) {
                    nextChar(); // Consumes the '}'.
                    currentChar = currentChar();
                }
            } // Not a comment.
            else {
                nextChar();
                currentChar = currentChar();
            }
        }
    }
}
