package de.weltraumschaf.caythe.frontend.pascal;

import de.weltraumschaf.caythe.frontend.Parser;
import de.weltraumschaf.caythe.frontend.Token;
import static de.weltraumschaf.caythe.frontend.pascal.PascalErrorCode.TOO_MANY_ERRORS;
import de.weltraumschaf.caythe.message.Message;
import static de.weltraumschaf.caythe.message.MessageType.SYNTAX_ERROR;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class PascalErrorHandler {

    private static final int MAX_ERRORS = 25;

    private static int errorCount = 0;

    public void flag(Token token, String errorMessage, Parser parser) {
        parser.sendMessage(new Message(SYNTAX_ERROR, new Object[] {
	    token.getLineNumber(),
	    token.getPosition(),
	    token.getText(),
	    errorMessage
	}));

	if (++errorCount > MAX_ERRORS) {
	    abortTranslation(TOO_MANY_ERRORS, parser);
	}
    }

    public void flag(Token token, PascalErrorCode errorCode, Parser parser) {
	flag(token, errorCode.toString(), parser);
    }

    public void abortTranslation(PascalErrorCode errorCode, Parser parser) {
	String fatalText = "FATAL ERROR: " + errorCode.toString();
	parser.sendMessage(new Message(SYNTAX_ERROR, new Object[] {
	    0,
	    0,
	    "",
	    fatalText
	}));
	System.exit(errorCode.getStatus());
    }

    public int getErrorCount() {
	return errorCount;
    }

}
