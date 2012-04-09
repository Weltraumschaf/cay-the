package de.weltraumschaf.caythe.frontend.pascal;

import de.weltraumschaf.caythe.frontend.EofToken;
import de.weltraumschaf.caythe.frontend.Parser;
import de.weltraumschaf.caythe.frontend.Scanner;
import de.weltraumschaf.caythe.frontend.Token;
import static de.weltraumschaf.caythe.frontend.pascal.PascalErrorCode.*;
import de.weltraumschaf.caythe.frontend.pascal.parsers.ProgramParser;
import de.weltraumschaf.caythe.intermediate.symboltableimpl.Predefined;
import de.weltraumschaf.caythe.message.Message;
import de.weltraumschaf.caythe.message.MessageType;
import java.io.IOException;
import java.util.EnumSet;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class PascalTopDownParser extends Parser {

    protected static PascalErrorHandler errorHandler = new PascalErrorHandler();

    public PascalTopDownParser(Scanner scanner) {
        super(scanner);
    }

    public PascalTopDownParser(PascalTopDownParser parent) {
        super(parent.getScanner());
    }

    @Override
    public void parse() throws Exception {
        long startTime = System.currentTimeMillis();
        Predefined.initialize(symbolTableStack);

        try {
            Token token = nextToken();

            if (null == token.getType()) {
                errorHandler.abortTranslation(EMPTY_INPUT_ERROR, this);
                return;
            }

            ProgramParser programParser = new ProgramParser(this);
            programParser.parse(token, null);
            token = currentToken();

            // Send the parser summary message.
            float elapsedTime = (System.currentTimeMillis() - startTime) / 1000f;
            sendMessage(new Message(MessageType.PARSER_SUMMARY, new Number[] {
                token.getLineNumber(),
                getErrorCount(),
                elapsedTime
            }));
        } catch (IOException ex) {
            errorHandler.abortTranslation(IO_ERROR, this);
        }
    }

    public Token synchronize(EnumSet syncSet) throws Exception {
        Token token = currentToken();

        // If the current token is not in the synchronisation set set,
        // then it is unexpected and the parser must recover.
        if (!syncSet.contains(token.getType())) {
            errorHandler.flag(token, UNEXPECTED_TOKEN, this);
            // Recover by skipping tokens that are not in the sync set.
            do {
                token = nextToken();
            } while (!(token instanceof EofToken) &&
                     !syncSet.contains(token.getType()));
        }

        return token;
    }

    @Override
    public int getErrorCount() {
        return errorHandler.getErrorCount();
    }

    public PascalErrorHandler getErrorHandler() {
        return errorHandler;
    }

}
