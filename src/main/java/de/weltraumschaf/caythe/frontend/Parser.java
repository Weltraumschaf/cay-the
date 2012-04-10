package de.weltraumschaf.caythe.frontend;

import de.weltraumschaf.caythe.intermediate.Code;
import de.weltraumschaf.caythe.intermediate.SymbolTableFactory;
import de.weltraumschaf.caythe.intermediate.SymbolTableStack;
import de.weltraumschaf.caythe.message.Message;
import de.weltraumschaf.caythe.message.MessageHandler;
import de.weltraumschaf.caythe.message.MessageListener;
import de.weltraumschaf.caythe.message.MessageProducer;

/**
 * Generic abstract parser.
 *
 * Holds a symbol table stack and a message handler. Also implements {@link MessageProducer}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public abstract class Parser implements MessageProducer {

    /**
     * Symbol table stack.
     */
    protected static SymbolTableStack symbolTableStack;

    /**
     * Handles all messages produced during parse time.
     */
    protected static MessageHandler messageHandler;

    static {
        symbolTableStack = SymbolTableFactory.createSymbolTableStack();
        messageHandler   = new MessageHandler();
    }

    /**
     * The lexical code scanner.
     */
    private Scanner scanner;
    /**
     * Intermediate code representation aka. abstract syntax tree.
     */
    protected Code intermediateCode;

    /**
     * Initializes the parser with the scanner.
     *
     * @param scanner
     */
    public Parser(Scanner scanner) {
        this.scanner          = scanner;
        this.intermediateCode = null;
    }

    /**
     * Generic parse method.
     *
     * @throws Exception
     */
    public abstract void parse() throws Exception;

    /**
     * Returns the number of errors during parsing.
     *
     * @return
     */
    public abstract int getErrorCount();

    /**
     * Returns the current token from the scanner.
     *
     * @return
     */
    public Token currentToken() {
        return scanner.currentToken();
    }

    /**
     * Advances to the next token of the scanner.
     *
     * @return
     * @throws Exception
     */
    public Token nextToken() throws Exception {
        return scanner.nextToken();
    }

    @Override
    public void addMessageListener(MessageListener listener) {
        messageHandler.addListener(listener);
    }

    @Override
    public void removeMessageListener(MessageListener listener) {
        messageHandler.removeListener(listener);
    }

    @Override
    public void sendMessage(Message message) {
        messageHandler.sendMessage(message);
    }

    /**
     * Gets the intermediate code (aka. abstract syntax tree).
     *
     * By default this is null.
     *
     * @return
     */
    public Code getIntermediateCode() {
        return intermediateCode;
    }

    /**
     * Gets a symbol table stack.
     *
     * Returns always a valid object.
     *
     * @return
     */
    public SymbolTableStack getSymbolTableStack() {
        return symbolTableStack;
    }

    /**
     * getter for the scanner.
     *
     * @return
     */
    public Scanner getScanner() {
        return scanner;
    }

}
