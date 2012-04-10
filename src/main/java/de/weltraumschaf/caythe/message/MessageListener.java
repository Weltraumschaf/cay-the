package de.weltraumschaf.caythe.message;

/**
 * Implementors can resive messages.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public interface MessageListener {

    /**
     * Receive the message from someone (e.g. {@link MessageHandler}.
     *
     * @param message
     */
    public void receiveMessage(Message message);
}
