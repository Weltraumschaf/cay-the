package de.weltraumschaf.caythe.frontend;

import java.io.IOException;

/**
 * Abstract base scanner class.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public abstract class Scanner {

    /**
     * The scanned source.
     */
    private Source source;
    /**
     * The current scanned token.
     */
    private Token currentToken;

    /**
     * Default constructor.
     *
     * @param source
     */
    public Scanner(Source source) {
        this.source = source;
    }

    /**
     * Gets the scanned source.
     *
     * @return
     */
    public Source getSource() {
        return source;
    }

    /**
     * Returns the current scaned token.
     *
     * @return
     */
    public Token currentToken() {
        return currentToken;
    }

    /**
     * Extracts the next token and set it as current token.
     *
     * @return Returns the extracted token.
     * @throws Exception
     */
    public Token nextToken() throws Exception {
        currentToken = extractToken();
        return currentToken;
    }

    /**
     * Language specific extraction method.
     *
     * @return
     * @throws Exception
     */
    protected abstract Token extractToken() throws Exception;

    /**
     * Returns the current character of the scanned source.
     *
     * Returns {@link Source.EOF} on IOExceptions.
     *
     * @return
     */
    public char currentChar() {
        return source.currentChar();
    }

    /**
     * Advances the current character by one and returns it.
     *
     * Returns {@link Source.EOF} on IOExceptions.
     *
     * @return
     */
    public void nextChar() throws IOException {
        source.nextChar();
    }

    /**
     * Returns the next character without advancing the current character by one.
     *
     * Returns {@link Source.EOF} on IOExceptions.
     *
     * @return
     */
    public char peakChar() throws IOException {
        return source.peekChar();
    }

    /**
     * Are we at end of line?
     *
     * @return
     * @throws Exception
     */
    public boolean atEol() throws Exception {
        return source.atEol();
    }

    /**
     * Are w at end of file?
     *
     * @return
     * @throws Exception
     */
    public boolean atEof() throws Exception {
        return source.atEof();
    }

    /**
     * Skip all characters until new line.
     *
     * @throws Exception
     */
    public void skipToNextLine() throws Exception {
        source.skipToNextLine();
    }
}
