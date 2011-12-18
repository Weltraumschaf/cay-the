package de.weltraumschaf.caythe.frontend;

import de.weltraumschaf.caythe.intermediate.ICode;
import de.weltraumschaf.caythe.intermediate.SymbolTable;
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
    protected static SymbolTable symTab;
    protected static MessageHandler messageHandler;

    static {
        symTab         = null;
        messageHandler = null;
    }

    protected Scanner scanner;
    protected ICode iCode;

    public Parser(Scanner scanner) {
        this.scanner = scanner;
        this.iCode   = null;
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


}
