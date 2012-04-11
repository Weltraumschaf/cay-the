package de.weltraumschaf.caythe.frontend.pascal.tokens;

import de.weltraumschaf.caythe.frontend.Source;
import static de.weltraumschaf.caythe.frontend.Source.EOF;
import static de.weltraumschaf.caythe.frontend.pascal.PascalErrorCode.UNEXPECTED_EOF;
import de.weltraumschaf.caythe.frontend.pascal.PascalToken;
import static de.weltraumschaf.caythe.frontend.pascal.PascalTokenType.ERROR;
import static de.weltraumschaf.caythe.frontend.pascal.PascalTokenType.STRING;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class PascalStringToken extends PascalToken {

    public PascalStringToken(Source source) throws Exception {
        super(source);
    }

    @Override
    protected void customExtraction() throws Exception {
	StringBuilder textBuffer  = new StringBuilder();
	StringBuilder valueBuffer = new StringBuilder();

	char currentChar = nextChar(); // consume initial quote.
	textBuffer.append('\'');

	// Get string characters.
	do {
	    // Replace any white space character wit ha blank.
	    if (Character.isWhitespace(currentChar)) {
		currentChar = ' ';
	    }

	    if (('\'' != currentChar) && (EOF != currentChar)) {
		textBuffer.append(currentChar);
		valueBuffer.append(currentChar);
		currentChar = nextChar(); // Consume character.
	    }

	    // Quote? Each pair of adjacent quotes represents a single-quote.
	    if ('\'' == currentChar) {
		while (('\'' == currentChar) && (peekChar() == '\'')) {
		    textBuffer.append("''");
		    valueBuffer.append(currentChar);
		    currentChar = nextChar(); // Consume pair of quotes.
		    currentChar = nextChar();
		}
	    }
	} while (('\'' != currentChar) && (EOF != currentChar));

	if ('\'' == currentChar) {
	    nextChar(); // Consume final quote.
	    textBuffer.append('\'');

	    type  = STRING;
	    value = valueBuffer.toString();
	} else {
	    type = ERROR;
	    value = UNEXPECTED_EOF;
	}
    }

}
