package de.weltraumschaf.caythe.frontend;

import de.weltraumschaf.caythe.message.Message;
import de.weltraumschaf.caythe.message.MessageHandler;
import de.weltraumschaf.caythe.message.MessageType;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.net.URL;
import static org.junit.Assert.*;
import org.junit.Test;
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

    @Test public void testAtEol() throws Exception {
        Source sut = createSourceFromFixture("empty_source");
        assertFalse(sut.atEol());
        sut.currentChar();
        assertFalse(sut.atEol());
        sut.close();

        sut = createSourceFromFixture("one_line_source");
        assertFalse(sut.atEol());
        for (int i = 0; i < 20; ++i) {
            sut.nextChar();
            assertFalse(sut.atEol());
        }
        sut.nextChar();
        assertTrue(sut.atEol());
        sut.close();

        sut = createSourceFromFixture("multi_line_source");
        assertEquals(0, sut.getLineNumber());
        assertFalse(sut.atEol());
        for (int i = 0; i < 10; ++i) {
            sut.nextChar();
            assertEquals(1, sut.getLineNumber());
            assertFalse(sut.atEol());
        }
        sut.nextChar();
        assertEquals(1, sut.getLineNumber());
        assertTrue(sut.atEol());

        for (int i = 0; i < 11; ++i) {
            sut.nextChar();
            assertEquals(2, sut.getLineNumber());
            assertFalse(sut.atEol());
        }
        sut.nextChar();
        assertEquals(2, sut.getLineNumber());
        assertTrue(sut.atEol());

        for (int i = 0; i < 7; ++i) {
            sut.nextChar();
            assertEquals(3, sut.getLineNumber());
            assertFalse(sut.atEol());
        }
        sut.nextChar();
        assertEquals(3, sut.getLineNumber());
        assertTrue(sut.atEol());
        sut.nextChar();
        assertEquals(4, sut.getLineNumber());
        assertTrue(sut.atEol());
        for (int i = 0; i < 14; ++i) {
            sut.nextChar();
            assertEquals(5, sut.getLineNumber());
            assertFalse(sut.atEol());
        }
        sut.nextChar();
        assertEquals(5, sut.getLineNumber());
        assertTrue(sut.atEol());

        sut.close();
    }

    @Test public void testAtEof() throws Exception {
        Source sut = createSourceFromFixture("empty_source");
        assertTrue(sut.atEof());
        sut.currentChar();
        assertTrue(sut.atEof());
        sut.close();

        sut = createSourceFromFixture("one_line_source");
        for (int i = 0; i < 22; ++i) {
            assertFalse(sut.atEof());
            sut.nextChar();
        }
        assertTrue(sut.atEof());
        sut.close();

        sut = createSourceFromFixture("multi_line_source");
        for (int i = 0; i < 85; ++i) {
            assertFalse(sut.atEof());
            sut.nextChar();
        }
        assertTrue(sut.atEof());
        sut.close();
    }

    @Test public void testSkipToNextLine() throws Exception {
        Source sut = createSourceFromFixture("multi_line_source");
        assertEquals(-2, sut.getCurrentPos());
        assertEquals(0, sut.getLineNumber());

        assertEquals('T', sut.currentChar());
        assertEquals(0, sut.getCurrentPos());
        assertEquals(1, sut.getLineNumber());

        assertEquals('h', sut.nextChar());
        assertEquals('h', sut.currentChar());
        assertEquals(1, sut.getCurrentPos());
        assertEquals(1, sut.getLineNumber());

        sut.skipToNextLine();
        assertEquals('m', sut.nextChar());
        assertEquals('m', sut.currentChar());
        assertEquals(0, sut.getCurrentPos());
        assertEquals(2, sut.getLineNumber());

        sut.close();
    }

    @Test public void testPeakChar() throws Exception {
        Source sut = createSourceFromFixture("one_line_source");
        assertEquals(-2, sut.getCurrentPos());
        assertEquals(0, sut.getLineNumber());

        assertEquals('S', sut.currentChar());
        assertEquals(0, sut.getCurrentPos());
        assertEquals(1, sut.getLineNumber());
        assertEquals('o', sut.peekChar());
        assertEquals('S', sut.currentChar());
        assertEquals(0, sut.getCurrentPos());
        assertEquals(1, sut.getLineNumber());

        sut.close();
    }

    @Test public void testReadWholeSource() throws Exception {
        Source sut = createSourceFromFixture("empty_source");
        assertEquals(-2, sut.getCurrentPos());
        assertEquals(0, sut.getLineNumber());
        assertEquals(Source.EOF, sut.currentChar());
        assertEquals(0, sut.getCurrentPos());
        assertEquals(0, sut.getLineNumber());
        sut.close();

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
        sut.close();
    }
}
