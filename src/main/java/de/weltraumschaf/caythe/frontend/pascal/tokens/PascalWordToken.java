package de.weltraumschaf.caythe.frontend.pascal.tokens;

import de.weltraumschaf.caythe.frontend.Source;
import de.weltraumschaf.caythe.frontend.pascal.PascalToken;
import de.weltraumschaf.caythe.frontend.pascal.PascalTokenType;
import static de.weltraumschaf.caythe.frontend.pascal.PascalTokenType.IDENTIFIER;
import static de.weltraumschaf.caythe.frontend.pascal.PascalTokenType.RESERVED_WORDS;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class PascalWordToken extends PascalToken {

    public PascalWordToken(Source source) throws Exception {
        super(source);
    }

    @Override
    protected void customExtraction() throws Exception {
	StringBuilder textBuffer = new StringBuilder();
	char currentChar = currentChar();

	// Get the word characters (letter or digit).
	// The scanner has already determined that the first
	// charatcer is a letter.
	while (Character.isLetterOrDigit(currentChar)) {
	    textBuffer.append(currentChar);
            nextChar();
	    currentChar = currentChar();
	}

	text = textBuffer.toString();

	// Is it a reserved word or an identifier?
	type = (RESERVED_WORDS.contains(text.toLowerCase()))
		? PascalTokenType.valueOf(text.toUpperCase())
		: IDENTIFIER;
    }


}
