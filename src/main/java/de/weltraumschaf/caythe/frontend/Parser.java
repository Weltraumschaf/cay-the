package de.weltraumschaf.caythe.frontend;

import de.weltraumschaf.caythe.intermediate.IntermediateCode;
import de.weltraumschaf.caythe.intermediate.SymbolTableFactory;
import de.weltraumschaf.caythe.intermediate.SymbolTableStack;
import de.weltraumschaf.caythe.message.Message;
import de.weltraumschaf.caythe.message.MessageProducer;
import de.weltraumschaf.caythe.message.MessageListener;
import de.weltraumschaf.caythe.message.MessageHandler;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public abstract class Parser implements MessageProducer {
    protected static SymbolTableStack symbolTableStack;
    protected static MessageHandler messageHandler;

    static {
        symbolTableStack = SymbolTableFactory.createSymbolTableStack();
        messageHandler   = new MessageHandler();
    }

    protected Scanner scanner;
    protected IntermediateCode intermediateCode;

    public Parser(Scanner scanner) {
        this.scanner = scanner;
        this.intermediateCode   = null;
    }

    public abstract void parse() throws Exception;
    public abstract int getErrorCount();

    public Token currentToken() {
        return scanner.currentToken();
    }

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

    public IntermediateCode getIntermediateCode() {
        return intermediateCode;
    }

    public SymbolTableStack getSymbolTableStack() {
        return symbolTableStack;
    }

}
