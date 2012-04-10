package de.weltraumschaf.caythe.message;

/**
 * Implementors of this interface produces messages and interested listeners
 * can subscribe to the producer.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public interface MessageProducer {
    /**
     * Subscribe an interested listener.
     *
     * @param listener
     */
    public void addMessageListener(MessageListener listener);
    /**
     * Remove a subscribed listener.
     *
     * @param listener
     */
    public void removeMessageListener(MessageListener listener);
    /**
     * Send a message to all subscribed listeners.
     *
     * @param message
     */
    public void sendMessage(Message message);
}
