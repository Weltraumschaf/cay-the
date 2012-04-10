package de.weltraumschaf.caythe.message;

/**
 * A message which has a type and a generic body.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class Message {

    /**
     * Generic body.
     *
     * @todo Make it a little more type safe.
     */
    private Object body;

    /**
     * The message type.
     */
    private MessageType type;

    /**
     * Initializes the message with body and type.
     *
     * @param type
     * @param body
     */
    public Message(MessageType type, Object body) {
        this.body = body;
        this.type = type;
    }

    /**
     * Returns the message body.
     *
     * @return
     */
    public Object getBody() {
        return body;
    }

    /**
     * Returns the type.
     *
     * @return
     */
    public MessageType getType() {
        return type;
    }

}
