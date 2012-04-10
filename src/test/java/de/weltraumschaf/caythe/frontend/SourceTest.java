package de.weltraumschaf.caythe.frontend;

import de.weltraumschaf.caythe.message.Message;
import de.weltraumschaf.caythe.message.MessageHandler;
import de.weltraumschaf.caythe.message.MessageType;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class SourceTest {

    private static final String FIXTURE_DIR = "/de/weltraumschaf/caythe/frontend";

    private Source createSourceFromFixture(String fixtureFile) throws FileNotFoundException, URISyntaxException {
        URL resource = getClass().getResource(FIXTURE_DIR + '/' + fixtureFile);
        return new Source(new BufferedReader(new FileReader(new File(resource.toURI()))));
    }

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

    @Test public void testReadWholeSource() throws Exception {
        Source sut = createSourceFromFixture("empty_source");
        assertEquals(-2, sut.getCurrentPos());
        assertEquals(0, sut.getLineNumber());
        assertEquals(Source.EOF, sut.currentChar());
        assertEquals(0, sut.getCurrentPos());
        assertEquals(0, sut.getLineNumber());

        sut = createSourceFromFixture("one_line_source");
        assertEquals(-2, sut.getCurrentPos());
        assertEquals(0, sut.getLineNumber());

        char[] chars = "Source with one line".toCharArray();
        assertEquals('S', sut.currentChar());
        assertEquals(0, sut.getCurrentPos());
        assertEquals(1, sut.getLineNumber());
        for (int i = 1; i < chars.length; ++i) {
            assertEquals("iteration " + i, chars[i], sut.nextChar());
            assertEquals("iteration " + i, chars[i], sut.currentChar());
            assertEquals(i, sut.getCurrentPos());
            assertEquals(1, sut.getLineNumber());

        }
        assertEquals(Source.EOL, sut.nextChar());
        assertEquals(Source.EOF, sut.nextChar());
        assertEquals(0, sut.getCurrentPos());
        assertEquals(1, sut.getLineNumber());
    }
}
