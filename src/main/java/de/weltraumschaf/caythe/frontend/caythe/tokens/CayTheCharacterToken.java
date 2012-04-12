package de.weltraumschaf.caythe.frontend.caythe.tokens;

import de.weltraumschaf.caythe.frontend.Source;
import static de.weltraumschaf.caythe.frontend.caythe.CayTheErrorCode.UNEXPECTED_EOF;
import de.weltraumschaf.caythe.frontend.caythe.CayTheToken;
import static de.weltraumschaf.caythe.frontend.caythe.CayTheTokenType.CHARACTER;
import static de.weltraumschaf.caythe.frontend.caythe.CayTheTokenType.ERROR;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class CayTheCharacterToken extends CayTheToken {

    public CayTheCharacterToken(Source source) throws Exception {
        super(source);
    }

    @Override
    protected void customExtraction() throws Exception {
        StringBuilder textBuffer = new StringBuilder();

        nextChar(); // consume initial single quote.
	textBuffer.append('\'');
        value = currentChar();
        textBuffer.append(currentChar());
        nextChar();

        if ('\'' == currentChar()) { // consume last single quote.
	    nextChar(); // Consume final single quote.
	    textBuffer.append('\'');
	    type  = CHARACTER;
	} else {
	    type  = ERROR;
	    value = UNEXPECTED_EOF;
	}

        text  = textBuffer.toString();
    }
}
