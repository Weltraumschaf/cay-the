package de.weltraumschaf.caythe.frontend.pascal.tokens;

import de.weltraumschaf.caythe.frontend.Source;
import de.weltraumschaf.caythe.frontend.pascal.PascalToken;

import static de.weltraumschaf.caythe.frontend.pascal.PascalTokenType.*;
import static de.weltraumschaf.caythe.frontend.pascal.PascalErrorCode.*;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class PascalSpecialSymbolToken extends PascalToken {

    public PascalSpecialSymbolToken(Source source) throws Exception {
        super(source);
    }

    @Override
    protected void extract() throws Exception {
	char currentChar = currentChar();

	text = Character.toString(currentChar);
	type = null;

	switch (currentChar) {
	    // Single-character special symbols.
	    case '+': case '-': case '*': case '/': case ',':
	    case ';': case '\'': case '=': case '(': case ')':
	    case '[': case ']': case '{': case '}': case '^': {
		nextChar();
		break;
	    }

	    // : or :=
	    case ':': {
		currentChar = nextChar(); // consumes :

		if ('=' == currentChar) {
		    text += currentChar;
		    nextChar(); // consumes =
		}
	    }
	    // < or <= or <>
	    case '<': {
		currentChar = nextChar(); // consumes <

		if ('=' == currentChar) {
		    text += currentChar;
		    nextChar(); // consumes =
		}
		else if ('>' == currentChar) {
		    text += currentChar;
		    nextChar(); // consumes >
		}

		break;
	    }
	    // > or >=
	    case '>': {
		currentChar = nextChar(); // consumes >

		if ('=' == currentChar) {
		    text += currentChar;
		    nextChar(); // consumes =
		}

		break;
	    }
	    // . or ..
	    case '.': {
		currentChar = nextChar(); // consumes .

		if ('.' == currentChar) {
		    text += currentChar;
		    nextChar(); // consumes .
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
