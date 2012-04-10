package de.weltraumschaf.caythe.message;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles a collection of {@link MessageListener}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class MessageHandlerImpl implements MessageHandler {

    /**
     * Collection of subscribed listeners.
     */
    private List<MessageListener> listeners;

    /**
     * Initializes with an empty collection of listeners.
     */
    public MessageHandlerImpl() {
        listeners = new ArrayList<MessageListener>();
    }

    @Override
    public void addListener(MessageListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(MessageListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void sendMessage(Message message) {
        for (MessageListener listener : listeners) {
            listener.receiveMessage(message);
        }
    }
}
