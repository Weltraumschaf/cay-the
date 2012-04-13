package de.weltraumschaf.caythe.frontend.caythe.tokens;

import de.weltraumschaf.caythe.frontend.Source;
import de.weltraumschaf.caythe.frontend.caythe.CayTheToken;
import de.weltraumschaf.caythe.frontend.caythe.CayTheTokenType;
import static de.weltraumschaf.caythe.frontend.caythe.CayTheTokenType.IDENTIFIER;
import static de.weltraumschaf.caythe.frontend.caythe.CayTheTokenType.RESERVED_WORDS;

/**
 * Extracts word tokens such as keywords or identifiers.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class CayTheWordToken extends CayTheToken {

    public CayTheWordToken(Source source) throws Exception {
        super(source);
    }

    /**
     * Whether the character is allowed in words.
     *
     * @param chr
     * @return
     */
    private static boolean isWordCharacter(char chr) {
        return Character.isLetterOrDigit(chr) || '_' == chr;
    }

    @Override
    public void customExtraction() throws Exception {
        StringBuilder textBuffer = new StringBuilder();
	char currentChar = currentChar();

	// Get the word characters (letter or digit).
	// The scanner has already determined that the first
	// charatcer is a letter.
	while (isWordCharacter(currentChar)) {
	    textBuffer.append(currentChar);
            nextChar();
	    currentChar = currentChar();
	}

	text = textBuffer.toString();

	// Is it a reserved word or an identifier?
	type = (RESERVED_WORDS.contains(text.toLowerCase()))
             ? CayTheTokenType.valueOf(text.toUpperCase())
             : IDENTIFIER;
    }
}
