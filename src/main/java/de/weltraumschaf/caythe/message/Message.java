package de.weltraumschaf.caythe.message;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class Message {
    private Object body;
    private MessageType type;

    public Message(MessageType type, Object body) {
        this.body = body;
        this.type = type;
    }

    public Object getBody() {
        return body;
    }

    public MessageType getType() {
        return type;
    }

}
