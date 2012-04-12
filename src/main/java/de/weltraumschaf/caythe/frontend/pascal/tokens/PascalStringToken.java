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
 */
public class PascalStringToken extends PascalToken {

    public PascalStringToken(Source source) throws Exception {
        super(source);
    }

    @Override
    protected void customExtraction() throws Exception {
	StringBuilder textBuffer  = new StringBuilder();
	StringBuilder valueBuffer = new StringBuilder();
        nextChar(); // consume initial quote.
	char currentChar = currentChar();
	textBuffer.append('\'');

	// Get string characters.
	do {
	    // Replace any white space character with a blank.
	    if (Character.isWhitespace(currentChar)) {
		currentChar = ' ';
	    }

	    if (('\'' != currentChar) && (EOF != currentChar)) {
		textBuffer.append(currentChar);
		valueBuffer.append(currentChar);
                nextChar(); // Consume character.
		currentChar = currentChar();
	    }

	    // Quote? Each pair of adjacent quotes represents a single-quote.
	    if ('\'' == currentChar) {
		while (('\'' == currentChar) && (peekChar() == '\'')) {
		    textBuffer.append("''");
		    valueBuffer.append(currentChar);
		    nextChar(); // Consume pair of quotes.
                    nextChar();
		    currentChar = currentChar();
		}
	    }
	} while (('\'' != currentChar) && (EOF != currentChar));

	if ('\'' == currentChar) {
	    nextChar(); // Consume final quote.
	    textBuffer.append('\'');

	    type  = STRING;
	    value = valueBuffer.toString();
	} else {
	    type  = ERROR;
	    value = UNEXPECTED_EOF;
	}

        text = textBuffer.toString();
    }

}
