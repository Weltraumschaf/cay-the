package de.weltraumschaf.caythe.frontend.caythe.tokens;

import de.weltraumschaf.caythe.frontend.Source;
import static de.weltraumschaf.caythe.frontend.caythe.CayTheErrorCode.INVALID_CHARACTER;
import de.weltraumschaf.caythe.frontend.caythe.CayTheToken;
import static de.weltraumschaf.caythe.frontend.caythe.CayTheTokenType.ERROR;
import static de.weltraumschaf.caythe.frontend.caythe.CayTheTokenType.SPECIAL_SYMBOLS;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class CayTheSpecialSymbolToken extends CayTheToken {

    public CayTheSpecialSymbolToken(Source source) throws Exception {
        super(source);
    }

    @Override
    public void customExtraction() throws Exception {
        char currentChar = currentChar();

        text = Character.toString(currentChar);
        type = null;

        switch (currentChar) {
            // Single-character special symbols.
            case '+':
            case '-':
            case '*':
            case '/':
            case ',':
            case ';':
            case '\'':
            case '"':
            case '(':
            case ')':
            case '[':
            case ']':
            case '{':
            case '}': {
                nextChar();
                break;
            }
            // ! or !=
            case '!':
            // = or ==
            case '=':
            // < or <=
            case '<':
            // > or >=
            case '>': {
                currentChar = nextChar(); // consumes ! or = or < or >

                if ('=' == currentChar) {
                    text += Character.toString(currentChar);
                    nextChar(); // consumes =
                }

                break;
            }
            default: {
                nextChar();
                type  = ERROR;
                value = INVALID_CHARACTER;
            }
        }

        if (null == type) {
            type = SPECIAL_SYMBOLS.get(text);
        }
    }
}
