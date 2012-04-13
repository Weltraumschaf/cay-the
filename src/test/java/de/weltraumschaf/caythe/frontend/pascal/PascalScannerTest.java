package de.weltraumschaf.caythe.frontend.pascal;

import de.weltraumschaf.caythe.frontend.EofToken;
import de.weltraumschaf.caythe.frontend.Source;
import de.weltraumschaf.caythe.frontend.pascal.tokens.PascalNumberToken;
import de.weltraumschaf.caythe.frontend.pascal.tokens.PascalSpecialSymbolToken;
import de.weltraumschaf.caythe.frontend.pascal.tokens.PascalWordToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class PascalScannerTest {

    private Source generateSource(String src) {
        return new Source(new BufferedReader(new StringReader(src)));
    }

    @Test public void currentAndNextChar() throws IOException {
        PascalScanner scanner = null;

        scanner = new PascalScanner(generateSource(""));
        scanner.nextChar();
        assertEquals(Source.EOF, scanner.currentChar());
        scanner.nextChar();
        assertEquals(Source.EOF, scanner.currentChar());
        scanner.nextChar();
        assertEquals(Source.EOF, scanner.currentChar());

        scanner = new PascalScanner(generateSource("e, k : enum;"));
        scanner.nextChar();
        assertEquals('e', scanner.currentChar());
        assertEquals('e', scanner.currentChar());
        scanner.nextChar();
        assertEquals(',', scanner.currentChar());
        assertEquals(',', scanner.currentChar());
        scanner.nextChar();
        assertEquals(' ', scanner.currentChar());
        assertEquals(' ', scanner.currentChar());
        scanner.nextChar();
        assertEquals('k', scanner.currentChar());
        assertEquals('k', scanner.currentChar());
        scanner.nextChar();
        assertEquals(' ', scanner.currentChar());
        assertEquals(' ', scanner.currentChar());
        scanner.nextChar();
        assertEquals(':', scanner.currentChar());
        assertEquals(':', scanner.currentChar());
        scanner.nextChar();
        assertEquals(' ', scanner.currentChar());
        assertEquals(' ', scanner.currentChar());
        scanner.nextChar();
        assertEquals('e', scanner.currentChar());
        assertEquals('e', scanner.currentChar());
        scanner.nextChar();
        assertEquals('n', scanner.currentChar());
        assertEquals('n', scanner.currentChar());
        scanner.nextChar();
        assertEquals('u', scanner.currentChar());
        assertEquals('u', scanner.currentChar());
        scanner.nextChar();
        assertEquals('m', scanner.currentChar());
        assertEquals('m', scanner.currentChar());
        scanner.nextChar();
        assertEquals(';', scanner.currentChar());
        assertEquals(';', scanner.currentChar());
        scanner.nextChar();
        assertEquals(Source.EOF, scanner.currentChar());
        assertEquals(Source.EOF, scanner.currentChar());
        scanner.nextChar();
        assertEquals(Source.EOF, scanner.currentChar());
        assertEquals(Source.EOF, scanner.currentChar());

        scanner = new PascalScanner(generateSource("write(a:2);"));
        scanner.nextChar();
        assertEquals('w', scanner.currentChar());
        assertEquals('w', scanner.currentChar());
        scanner.nextChar();
        assertEquals('r', scanner.currentChar());
        assertEquals('r', scanner.currentChar());
        scanner.nextChar();
        assertEquals('i', scanner.currentChar());
        assertEquals('i', scanner.currentChar());
        scanner.nextChar();
        assertEquals('t', scanner.currentChar());
        assertEquals('t', scanner.currentChar());
        scanner.nextChar();
        assertEquals('e', scanner.currentChar());
        assertEquals('e', scanner.currentChar());
        scanner.nextChar();
        assertEquals('(', scanner.currentChar());
        assertEquals('(', scanner.currentChar());
        scanner.nextChar();
        assertEquals('a', scanner.currentChar());
        assertEquals('a', scanner.currentChar());
        scanner.nextChar();
        assertEquals(':', scanner.currentChar());
        assertEquals(':', scanner.currentChar());
        scanner.nextChar();
        assertEquals('2', scanner.currentChar());
        assertEquals('2', scanner.currentChar());
        scanner.nextChar();
        assertEquals(')', scanner.currentChar());
        assertEquals(')', scanner.currentChar());
        scanner.nextChar();
        assertEquals(';', scanner.currentChar());
        assertEquals(';', scanner.currentChar());
        scanner.nextChar();
        assertEquals(Source.EOF, scanner.currentChar());
        assertEquals(Source.EOF, scanner.currentChar());
    }

    @Test public void currentAndNextToken() throws Exception {
        PascalScanner scanner;

        scanner = new PascalScanner(generateSource(""));
        scanner.nextChar();
        assertNull(scanner.currentToken());
        assertTrue(scanner.nextToken() instanceof EofToken);
        assertTrue(scanner.currentToken() instanceof EofToken);
        assertNull(scanner.currentToken().getType());
        assertNull(scanner.currentToken().getText());
        assertTrue(scanner.nextToken() instanceof EofToken);
        assertTrue(scanner.currentToken() instanceof EofToken);
        assertNull(scanner.currentToken().getType());
        assertNull(scanner.currentToken().getText());

        scanner = new PascalScanner(generateSource("e, k : enum;"));
        scanner.nextChar();
        assertNull(scanner.currentToken());
        assertTrue(scanner.nextToken() instanceof PascalWordToken);
        assertTrue(scanner.currentToken() instanceof PascalWordToken);
        assertEquals(PascalTokenType.IDENTIFIER, scanner.currentToken().getType());
        assertEquals("e", scanner.currentToken().getText());
        assertTrue(scanner.nextToken() instanceof PascalSpecialSymbolToken);
        assertTrue(scanner.currentToken() instanceof PascalSpecialSymbolToken);
        assertEquals(PascalTokenType.COMMA, scanner.currentToken().getType());
        assertEquals(",", scanner.currentToken().getText());
        assertTrue(scanner.nextToken() instanceof PascalWordToken);
        assertTrue(scanner.currentToken() instanceof PascalWordToken);
        assertEquals(PascalTokenType.IDENTIFIER, scanner.currentToken().getType());
        assertEquals("k", scanner.currentToken().getText());
        assertTrue(scanner.nextToken() instanceof PascalSpecialSymbolToken);
        assertTrue(scanner.currentToken() instanceof PascalSpecialSymbolToken);
        assertEquals(PascalTokenType.COLON, scanner.currentToken().getType());
        assertEquals(":", scanner.currentToken().getText());
        assertTrue(scanner.nextToken() instanceof PascalWordToken);
        assertTrue(scanner.currentToken() instanceof PascalWordToken);
        assertEquals(PascalTokenType.IDENTIFIER, scanner.currentToken().getType());
        assertEquals("enum", scanner.currentToken().getText());
        assertTrue(scanner.nextToken() instanceof PascalSpecialSymbolToken);
        assertTrue(scanner.currentToken() instanceof PascalSpecialSymbolToken);
        assertEquals(PascalTokenType.SEMICOLON, scanner.currentToken().getType());
        assertEquals(";", scanner.currentToken().getText());
        assertTrue(scanner.nextToken() instanceof EofToken);
        assertTrue(scanner.currentToken() instanceof EofToken);
        assertNull(scanner.currentToken().getType());
        assertNull(scanner.currentToken().getText());

        scanner = new PascalScanner(generateSource("write(a:2);"));
        scanner.nextChar();
        assertNull(scanner.currentToken());
        assertTrue(scanner.nextToken() instanceof PascalWordToken);
        assertTrue(scanner.currentToken() instanceof PascalWordToken);
        assertEquals(PascalTokenType.IDENTIFIER, scanner.currentToken().getType());
        assertEquals("write", scanner.currentToken().getText());
        assertTrue(scanner.nextToken() instanceof PascalSpecialSymbolToken);
        assertTrue(scanner.currentToken() instanceof PascalSpecialSymbolToken);
        assertEquals(PascalTokenType.LEFT_PAREN, scanner.currentToken().getType());
        assertEquals("(", scanner.currentToken().getText());
        assertTrue(scanner.nextToken() instanceof PascalWordToken);
        assertTrue(scanner.currentToken() instanceof PascalWordToken);
        assertEquals(PascalTokenType.IDENTIFIER, scanner.currentToken().getType());
        assertEquals("a", scanner.currentToken().getText());
        assertTrue(scanner.nextToken() instanceof PascalSpecialSymbolToken);
        assertTrue(scanner.currentToken() instanceof PascalSpecialSymbolToken);
        assertEquals(PascalTokenType.COLON, scanner.currentToken().getType());
        assertEquals(":", scanner.currentToken().getText());
        assertTrue(scanner.nextToken() instanceof PascalNumberToken);
        assertTrue(scanner.currentToken() instanceof PascalNumberToken);
        assertEquals(PascalTokenType.INTEGER, scanner.currentToken().getType());
        assertEquals("2", scanner.currentToken().getText());
        assertTrue(scanner.nextToken() instanceof PascalSpecialSymbolToken);
        assertTrue(scanner.currentToken() instanceof PascalSpecialSymbolToken);
        assertEquals(PascalTokenType.RIGHT_PAREN, scanner.currentToken().getType());
        assertEquals(")", scanner.currentToken().getText());
        assertTrue(scanner.nextToken() instanceof PascalSpecialSymbolToken);
        assertTrue(scanner.currentToken() instanceof PascalSpecialSymbolToken);
        assertEquals(PascalTokenType.SEMICOLON, scanner.currentToken().getType());
        assertEquals(";", scanner.currentToken().getText());
        assertTrue(scanner.nextToken() instanceof EofToken);
        assertTrue(scanner.currentToken() instanceof EofToken);
        assertNull(scanner.currentToken().getType());
        assertNull(scanner.currentToken().getText());
    }
}
