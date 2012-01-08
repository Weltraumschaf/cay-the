package de.weltraumschaf.caythe.frontend.pascal.tokens;

import de.weltraumschaf.caythe.frontend.Source;
import de.weltraumschaf.caythe.frontend.pascal.PascalToken;
import de.weltraumschaf.caythe.frontend.pascal.PascalTokenType;

import static de.weltraumschaf.caythe.frontend.pascal.PascalTokenType.RESERVED_WORDS;
import static de.weltraumschaf.caythe.frontend.pascal.PascalTokenType.IDENTIFIER;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class PascalWordToken extends PascalToken {

    public PascalWordToken(Source source) throws Exception {
        super(source);
    }

    @Override
    protected void extract() throws Exception {
	StringBuilder textBuffer = new StringBuilder();
	char currentChar = currentChar();

	// Get the word characters (letter or digit).
	// The scanner has already determined that the first
	// charatcer is a letter.
	while (Character.isLetterOrDigit(currentChar)) {
	    textBuffer.append(currentChar);
	    currentChar = nextChar();
	}

	text = textBuffer.toString();

	// Is it a reserved word or an identifier?
	type = (RESERVED_WORDS.contains(text.toLowerCase()))
		? PascalTokenType.valueOf(text.toUpperCase())
		: IDENTIFIER;
    }


}
