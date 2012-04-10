package de.weltraumschaf.caythe.frontend;

import de.weltraumschaf.caythe.message.Message;
import de.weltraumschaf.caythe.message.MessageHandler;
import de.weltraumschaf.caythe.message.MessageListener;
import de.weltraumschaf.caythe.message.MessageType;
import org.junit.Ignore;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class SourceTest {

    @Test public void testMessageProducer() {
        Message msg1 = new Message(MessageType.CALL, "msg1");
        Message msg2 = new Message(MessageType.CALL, "msg1");
        Message msg3 = new Message(MessageType.CALL, "msg1");

        MessageHandler handler    = mock(MessageHandler.class);

        Source src = new Source(null, handler);
        src.sendMessage(msg1);
        src.sendMessage(msg2);
        src.sendMessage(msg3);

        verify(handler, times(1)).sendMessage(msg1);
        verify(handler, times(1)).sendMessage(msg2);
        verify(handler, times(1)).sendMessage(msg3);
    }

    @Ignore
    @Test public void testAtEol() {

    }

    @Ignore
    @Test public void testAtEof() {

    }

    @Ignore
    @Test public void testSkipToNextLine() {

    }

    @Ignore
    @Test public void testPeakChar() {

    }

    @Ignore
    @Test public void testReadWholeSource() {

    }
}
