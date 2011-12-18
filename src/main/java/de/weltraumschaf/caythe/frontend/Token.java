package de.weltraumschaf.caythe.frontend;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class Token {

    protected TokenType type;
    protected String    text;
    protected Object    value;
    protected Source    source;
    protected int       lineNum;
    protected int       position;

    public Token(Source source) throws Exception {
        this.source = source;
        lineNum     = source.getLineNum();
        position    = source.getCurrentPos();
        extract();
    }

    protected void extract() throws Exception {
        text  = Character.toString(currentChar());
        value = null;
        nextChar();
    }

    protected char currentChar() throws Exception {
        return source.currentChar();
    }

    protected char nextChar() throws Exception {
        return source.nextChar();
    }

    protected char peekChar() throws Exception {
        return source.peekChar();
    }

    public int getLineNum() {
        return lineNum;
    }
    
}
