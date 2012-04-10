package de.weltraumschaf.caythe.message;

/**
 * Dispatch sent messages to added listeners.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public interface MessageHandler {

    /**
     * Subscribe a listener to this handler an any message send by this
     * handler will be forwarded to the listener.
     *
     * @param listener
     */
    void addListener(MessageListener listener);

    /**
     * Remove a listener and it will not get messages by this handler anymore.
     *
     * @param listener
     */
    void removeListener(MessageListener listener);

    /**
     * Forwards the passed message to all subscribed listeners.
     *
     * @param message
     */
    void sendMessage(Message message);

}
