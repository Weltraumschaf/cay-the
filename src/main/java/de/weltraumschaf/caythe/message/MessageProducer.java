package de.weltraumschaf.caythe.message;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public interface MessageProducer {
    public void addMessageListener(MessageListener listener);
    public void removeMessageListener(MessageListener listener);
    public void sendMessage(Message message);
}
