package de.weltraumschaf.caythe.frontend;

import de.weltraumschaf.caythe.message.Message;
import de.weltraumschaf.caythe.message.MessageHandler;
import de.weltraumschaf.caythe.message.MessageHandlerImpl;
import de.weltraumschaf.caythe.message.MessageListener;
import de.weltraumschaf.caythe.message.MessageProducer;
import static de.weltraumschaf.caythe.message.MessageType.SOURCE_LINE;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Represents a peace of source code which is accessible character by character..
 *
 * This class will read the source from an {@link BufferedReader} line by line.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class Source implements MessageProducer {

    /**
     * End of line character.
     */
    public static final char EOL = '\n';
    /**
     * End of file character.
     */
    public static final char EOF = (char) 0;
    /**
     * Handles source messages.
     */
    protected MessageHandler messageHandler;
    /**
     * The input stream.
     */
    private BufferedReader reader;
    /**
     * The current line.
     */
    private String line;
    /**
     * The current line number.
     */
    private int lineNumber;
    /**
     * The current column in the line.
     */
    private int currentPos;

    /**
     * Constructs source with {@link MessageHandler} as handler.
     *
     * @param r
     */
    public Source(BufferedReader r) {
        this(r, new MessageHandlerImpl());
    }

    /**
     * Designated constructor.
     *
     * @param r
     * @param h
     */
    public Source(BufferedReader r, MessageHandler h) {
        reader         = r;
        lineNumber     = 0;
        currentPos     = -2;
        messageHandler = h;
    }

    /**
     * Gets the current column in the current line,
     *
     * @return
     */
    public int getCurrentPos() {
        return currentPos;
    }

    /**
     * Gets the current line number.
     *
     * @return
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * Gets the current character of the source.
     *
     * @return
     * @throws IOException
     */
    public char currentChar() throws IOException {
        // First time?
        if (-2 == currentPos) {
            readLine();
            return nextChar();
        } // At end of file?
        else if (null == line) {
            return EOF;
        } // At end of line?
        else if (( -1 == currentPos ) || ( line.length() == currentPos )) {
            return EOL;
        } // Need to read the next line?
        else if (currentPos > line.length()) {
            readLine();
            return nextChar();
        } // REturn th character at the current position.
        else {
            return line.charAt(currentPos);
        }
    }

    /**
     * Gets the next character by advancing current position.
     *
     * @return
     * @throws IOException
     */
    public char nextChar() throws IOException {
        ++currentPos;
        return currentChar();
    }

    /**
     * Look ahead one character.
     *
     * Gets the next character without advancing current position.
     *
     * @return
     * @throws IOException
     */
    public char peekChar() throws IOException {
        currentChar();

        if (null == line) {
            return EOF;
        }

        int nextPos = currentPos + 1;
        return nextPos < line.length() ? line.charAt(nextPos) : EOL;
    }

    /**
     * Reads next line from input stream.
     *
     * @throws IOException
     */
    private void readLine() throws IOException {
        line = reader.readLine(); // Null when at end of the source.
        currentPos = -1;

        if (null != line) {
            ++lineNumber;
            sendMessage(new Message(
                    SOURCE_LINE,
                    new Object[]{lineNumber, line}));
        }
    }

    /**
     * Closes the {@link BufferedReader}.
     *
     * @throws IOException
     */
    public void close() throws IOException {
        if (null != reader) {
            reader.close();
        }
    }

    @Override
    public void addMessageListener(MessageListener listener) {
        messageHandler.addListener(listener);
    }

    @Override
    public void removeMessageListener(MessageListener listener) {
        messageHandler.removeListener(listener);
    }

    @Override
    public void sendMessage(Message message) {
        messageHandler.sendMessage(message);
    }

    /**
     * Returns true if end of line reached.
     *
     * @return
     * @throws Exception
     */
    public boolean atEol() throws Exception {
        return ( line != null ) && ( currentPos == line.length() );
    }

    /**
     * Returns true if end of source reached.
     *
     * @return
     * @throws Exception
     */
    public boolean atEof() throws Exception {
        // First time?
        if (currentPos == -2) {
            readLine();
        }

        return line == null;
    }

    /**
     * Skips all characters until the next line.
     *
     * @throws Exception
     */
    public void skipToNextLine() throws Exception {
        if (line != null) {
            currentPos = line.length() + 1;
        }
    }
}
