package de.weltraumschaf.caythe.frontend.caythe.tokens;

import de.weltraumschaf.caythe.frontend.Source;
import static de.weltraumschaf.caythe.frontend.Source.EOF;
import static de.weltraumschaf.caythe.frontend.caythe.CayTheErrorCode.UNEXPECTED_EOF;
import de.weltraumschaf.caythe.frontend.caythe.CayTheToken;
import static de.weltraumschaf.caythe.frontend.caythe.CayTheTokenType.ERROR;
import static de.weltraumschaf.caythe.frontend.caythe.CayTheTokenType.STRING;

/**
 * Extracts string literals as token delimited by double quotes.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class CayTheStringToken extends CayTheToken {

    public CayTheStringToken(Source source) throws Exception {
        super(source);
    }

    @Override
    public void customExtraction() throws Exception {
        StringBuilder textBuffer  = new StringBuilder();
	StringBuilder valueBuffer = new StringBuilder();

        nextChar(); // consume initial double quote.
	char currentChar = currentChar();
	textBuffer.append('"');

	// Get string characters.
	do {
            if (Character.isWhitespace(currentChar)) {
                currentChar = ' ';
            }

	    if (('"' != currentChar) && (EOF != currentChar)) {
                // Replace any white space character with a blank.
            	textBuffer.append(currentChar);
		valueBuffer.append(currentChar);
                nextChar(); // Consume character.
		currentChar = currentChar();
	    }

	    // Quote? Each pair of adjacent quotes represents a single-quote.
	    if ('"' == currentChar) {
		while (('"' == currentChar) && (peekChar() == '"')) {
                    textBuffer.append("\"\"");
                    valueBuffer.append(currentChar);
                    nextChar(); // Consume pair of quotes.
                    nextChar();
                    currentChar = currentChar();
		}
	    }
	} while (('"' != currentChar) && (EOF != currentChar));

	if ('"' == currentChar) {
	    nextChar(); // Consume final double quote.
	    textBuffer.append('"');

	    type  = STRING;
	    value = valueBuffer.toString();
	} else {
	    type  = ERROR;
	    value = UNEXPECTED_EOF;
	}

        text  = textBuffer.toString();
    }
}
