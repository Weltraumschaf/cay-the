package de.weltraumschaf.caythe.message;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public interface MessageListener {
    public void messageReceived(Message message);
}
