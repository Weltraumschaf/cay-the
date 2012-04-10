package de.weltraumschaf.caythe.frontend.pascal.tokens;

import de.weltraumschaf.caythe.frontend.Source;
import de.weltraumschaf.caythe.frontend.pascal.PascalErrorCode;
import de.weltraumschaf.caythe.frontend.pascal.PascalToken;
import static de.weltraumschaf.caythe.frontend.pascal.PascalTokenType.ERROR;

/**
 * Erroneous token.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class PascalErrorToken extends PascalToken {

    /**
     * Uses the passed tokenText as text and erorCode as value.
     *
     * The token type is always {@link PascalTokenType.ERROR}.
     *
     * @param source    The source the token belongs to.
     * @param errorCode Will be the token value.
     * @param tokenText Will be the token text.
     * @throws Exception
     */
    public PascalErrorToken(Source source, PascalErrorCode errorCode, String tokenText) throws Exception {
        super(source);
	this.text  = tokenText;
	this.type  = ERROR;
	this.value = errorCode;
    }

    /**
     * Extracts nothing.
     *
     * @throws Exception
     */
    @Override
    public void extract() throws Exception {
        // Nothing to extract on erroneous token.
    }

}
