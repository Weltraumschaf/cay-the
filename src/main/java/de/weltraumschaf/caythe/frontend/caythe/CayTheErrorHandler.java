package de.weltraumschaf.caythe.frontend.caythe;

import de.weltraumschaf.caythe.frontend.ErrorHandler;
import de.weltraumschaf.caythe.frontend.Parser;
import de.weltraumschaf.caythe.frontend.Token;
import static de.weltraumschaf.caythe.frontend.caythe.CayTheErrorCode.TOO_MANY_ERRORS;

/**
 * Handles errors during parsing Cay-The code.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class CayTheErrorHandler extends ErrorHandler {

    /**
     * If more than this number of errors occurred translation will be aborted.
     */
    private static final int MAX_ERRORS = 5;

    /**
     * Aborts translation of source code if {@link CayTheErrorHandler#MAX_ERRORS} exceeded.
     *
     * @param token
     * @param errorMessage
     * @param parser
     */
    @Override
    public void flag(Token token, String errorMessage, Parser parser) {
        super.flag(token, errorMessage, parser);

	if (getErrorCount() > MAX_ERRORS) {
	    abortTranslation(TOO_MANY_ERRORS, parser);
	}
    }
}
