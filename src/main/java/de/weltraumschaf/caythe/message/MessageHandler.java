package de.weltraumschaf.caythe.message;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles a collection of {@link MessageListener}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class MessageHandler {

    /**
     * Collection of subscribed listeners.
     */
    private List<MessageListener> listeners;

    /**
     * Initializes with an empty collection of listeners.
     */
    public MessageHandler() {
        listeners = new ArrayList<MessageListener>();
    }

    /**
     * Subscribe a listener to this handler an any message send by this
     * handler will be forwarded to the listener.
     *
     * @param listener
     */
    public void addListener(MessageListener listener) {
        listeners.add(listener);
    }

    /**
     * Remove a listener and it will not get messages by this handler anymore.
     *
     * @param listener
     */
    public void removeListener(MessageListener listener) {
        listeners.remove(listener);
    }

    /**
     * Forwards the passed message to all subscribed listeners.
     * 
     * @param message
     */
    public void sendMessage(Message message) {
        for (MessageListener listener : listeners) {
            listener.messageReceived(message);
        }
    }
}
