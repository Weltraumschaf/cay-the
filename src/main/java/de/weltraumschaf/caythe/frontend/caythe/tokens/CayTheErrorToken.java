package de.weltraumschaf.caythe.frontend.caythe.tokens;

import de.weltraumschaf.caythe.frontend.Source;
import de.weltraumschaf.caythe.frontend.caythe.CayTheErrorCode;
import de.weltraumschaf.caythe.frontend.caythe.CayTheToken;
import static de.weltraumschaf.caythe.frontend.caythe.CayTheTokenType.ERROR;

/**
 * Erroneous token.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class CayTheErrorToken extends CayTheToken {

    /**
     * Uses the passed tokenText as text and erorCode as value.
     *
     * The token type is always {@link CayTheTokenType.ERROR}.
     * 
     * @param source    The source the token belongs to.
     * @param errorCode Will be the token value.
     * @param tokenText Will be the token text.
     * @throws Exception
     */
    public CayTheErrorToken(Source source, CayTheErrorCode errorCode, String tokenText) throws Exception {
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
