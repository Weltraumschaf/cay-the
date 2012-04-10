package de.weltraumschaf.caythe.frontend;

import de.weltraumschaf.caythe.message.Message;
import static de.weltraumschaf.caythe.message.MessageType.SYNTAX_ERROR;

/**
 * Generic error handler.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
abstract public class ErrorHandler {

    /**
     * Count of occured errors.
     */
    private static int errorCount = 0;

    /**
     * Designated method to signal an error.
     *
     * @param token        Token which caused the error.
     * @param errorMessage Error message
     * @param parser       The involved parser.
     */
    public void flag(Token token, String errorMessage, Parser parser) {
        errorCount++;
        parser.sendMessage(new Message(SYNTAX_ERROR, new Object[] {
	    token.getLineNumber(),
	    token.getPosition(),
	    token.getText(),
	    errorMessage
	}));
    }

    /**
     * Expects an {@link ErrorCode error code} enum to signal the error.
     *
     * @param token     Token which caused the error.
     * @param errorCode The error code.
     * @param parser    The involved parser.
     */
    public void flag(Token token, ErrorCode errorCode, Parser parser) {
	flag(token, errorCode.toString(), parser);
    }

    /**
     * Aborts program execution after sending message to the parser.
     *
     * @param errorCode
     * @param parser
     */
    public void abortTranslation(ErrorCode errorCode, Parser parser) {
        parser.sendMessage(new Message(SYNTAX_ERROR, new Object[] {
	    0,
	    0,
	    "",
	    "FATAL ERROR: " + errorCode.toString()
	}));
	System.exit(errorCode.getStatus());
    }

    /**
     * Returns the count of occurred errors.
     *
     * @return
     */
    public int getErrorCount() {
	return errorCount;
    }

}
