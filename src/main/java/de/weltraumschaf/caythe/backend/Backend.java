package de.weltraumschaf.caythe.backend;

import de.weltraumschaf.caythe.intermediate.Code;
import de.weltraumschaf.caythe.intermediate.SymbolTableStack;
import de.weltraumschaf.caythe.message.Message;
import de.weltraumschaf.caythe.message.MessageHandlerImpl;
import de.weltraumschaf.caythe.message.MessageListener;
import de.weltraumschaf.caythe.message.MessageProducer;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public abstract class Backend implements MessageProducer {

    protected static MessageHandlerImpl messageHandler;

    static {
        messageHandler = new MessageHandlerImpl();
    }

    protected SymbolTableStack symbolTableStack;
    protected Code intermediateCode;

    public abstract void process(Code iCode, SymbolTableStack symTab) throws Exception;

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
