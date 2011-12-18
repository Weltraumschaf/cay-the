package de.weltraumschaf.caythe.backend;

import de.weltraumschaf.caythe.intermediate.IntermediateCode;
import de.weltraumschaf.caythe.intermediate.SymbolTable;
import de.weltraumschaf.caythe.message.Message;
import de.weltraumschaf.caythe.message.MessageHandler;
import de.weltraumschaf.caythe.message.MessageListener;
import de.weltraumschaf.caythe.message.MessageProducer;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public abstract class Backend implements MessageProducer {

    protected static MessageHandler messageHandler;

    static {
        messageHandler = new MessageHandler();
    }

    protected SymbolTable symTab;
    protected IntermediateCode iCode;

    public abstract void process(IntermediateCode iCode, SymbolTable symTab) throws Exception;

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
