package de.weltraumschaf.caythe.message;

import java.util.ArrayList;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class MessageHandler {

    private Message message;
    private ArrayList<MessageListener> listeners;

    public MessageHandler() {
        listeners = new ArrayList<MessageListener>();
    }

    public void addListener(MessageListener listener) {
        listeners.add(listener);
    }

    public void removeListener(MessageListener listener) {
        listeners.remove(listener);
    }

    public void sendMessage(Message message) {
        this.message = message;
        notifyListeners();
    }

    private void notifyListeners() {
        for (MessageListener listener : listeners) {
            listener.messageReceived(message);
        }
    }
}
