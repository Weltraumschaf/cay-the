package de.weltraumschaf.caythe.frontend.pascal;

import de.weltraumschaf.caythe.intermediate.SymbolTableEntry;
import de.weltraumschaf.caythe.frontend.EofToken;
import de.weltraumschaf.caythe.frontend.Parser;
import de.weltraumschaf.caythe.frontend.Scanner;
import de.weltraumschaf.caythe.frontend.Token;
import de.weltraumschaf.caythe.frontend.TokenType;
import de.weltraumschaf.caythe.intermediate.SymbolTable;
import de.weltraumschaf.caythe.message.Message;
import de.weltraumschaf.caythe.message.MessageType;
import java.io.IOException;

import static de.weltraumschaf.caythe.frontend.pascal.PascalTokenType.*;
import static de.weltraumschaf.caythe.frontend.pascal.PascalErrorCode.*;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class PascalParserTD extends Parser {

    protected static PascalErrorHandler errorHandler = new PascalErrorHandler();

    public PascalParserTD(Scanner scanner) {
        super(scanner);
    }

    @Override
    public void parse() throws Exception {
        Token token;
        long startTime = System.currentTimeMillis();

        try {
            while ( ! ((token = nextToken()) instanceof EofToken)) {
                TokenType tokenType = token.getType();

                if (IDENTIFIER == tokenType) {
                    String name = token.getText().toLowerCase();
                    // If it's not already in the symbol table,
                    // create and enter a new entry for the identifier.
                    SymbolTableEntry entry = symbolTableStack.lookup(name);

                    if (null == entry) {
                        entry = symbolTableStack.enterLocal(name);
                    }

                    // Append current line number to the entry.
                    entry.appendLineNumber(token.getLineNumber());
                } else if (ERROR == tokenType) {
                    errorHandler.flag(token, (PascalErrorCode) token.getValue(), this);
                }
            }

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

    @Override
    public int getErrorCount() {
        return errorHandler.getErrorCount();
    }

}
