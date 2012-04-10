package de.weltraumschaf.caythe.frontend.caythe.tokens;

import de.weltraumschaf.caythe.frontend.Source;
import de.weltraumschaf.caythe.frontend.caythe.CayTheToken;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class CayTheNumberToken extends CayTheToken {

    private static final int MAX_EXPONENT = 37;

    public CayTheNumberToken(Source source) throws Exception {
        super(source);
    }

    @Override
    public void extract() throws Exception {
	StringBuilder textBuffer = new StringBuilder();  // token's characters
        extractNumber(textBuffer);
        text = textBuffer.toString();
    }

    protected void extractNumber(StringBuilder textBuffer) throws Exception {

    }
}
