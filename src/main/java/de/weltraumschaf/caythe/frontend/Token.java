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
    protected int       lineNumber;
    protected int       position;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("<");

        if (null != type) {
            sb.append(type.toString())
              .append(", ");
        }

        if (null == text) {
            sb.append("null");
        } else {
            sb.append(String.format("'%s'", text));
        }

        return sb.append(">").toString();
    }

    public Token(Source source) throws Exception {
        this.source = source;
        lineNumber  = source.getLineNumber();
        position    = source.getCurrentPos();
    }

    /**
     * Default implementation which extracts one character.
     * 
     * @throws Exception
     */
    public void extract() throws Exception {
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

    public int getLineNumber() {
        return lineNumber;
    }

    public TokenType getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

    public int getPosition() {
        return position;
    }

}
