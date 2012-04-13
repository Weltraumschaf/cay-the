package de.weltraumschaf.caythe.frontend;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Generic token implementation.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class Token {

    protected TokenType type  = null;
    protected String    text  = null;
    protected Object    value = null;

    private Source   source;
    private int      lineNumber;
    private int      position;
    private boolean  wasExctracted = false;

    /**
     * Initializes lineNumber and position from current values of passed in source object,
     * because while extraction the internal cursor of the source object may be advanced.
     *
     * @param source
     * @throws Exception
     */
    public Token(Source source) throws Exception {
        this.source = source;

        if (null != source) {
            lineNumber = source.getLineNumber();
            position   = source.getCurrentPos();
        }
    }

    /**
     * Whether the token is already extracted.
     *
     * @return
     */
    public boolean wasExctracted() {
        return wasExctracted;
    }

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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        try {
            Token t = (Token)obj;
            return new EqualsBuilder().
                append(text, t.getText()).
                append(type, t.getType()).
                append(value, t.getValue()).
                isEquals();
        } catch (ClassCastException e){
            return false;
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(37, 53). // Two randomly chosne prime numbers.
                append(text).
                append(type).
                append(value).
                toHashCode();
    }

    /**
     * Extracts the token and ensures that is extracted only once.
     *
     * @throws Exception
     */
    public final void extract() throws Exception {
        if (wasExctracted) {
            return;
        }

        customExtraction();
        wasExctracted = true;
    }

    /**
     * Default implementation called by {@link Token#extract()}.
     *
     * This implementation does nothing.
     * Override this method in tour specific token implementations.
     *
     * @throws Exception
     */
    protected void customExtraction() throws Exception {

    }

    /**
     * Delegates to {@link Source#currentChar()} of internal {@link Source} object.
     *
     * @return
     * @throws Exception
     */
    protected char currentChar() throws Exception {
        return source.currentChar();
    }

    /**
     * Delegates to {@link Source#nextChar()} of internal {@link Source} object.
     *
     * @return
     * @throws Exception
     */
    protected void nextChar() throws Exception {
        source.nextChar();
    }

    /**
     * Delegates to {@link Source#peakChar()} of internal {@link Source} object.
     *
     * @return
     * @throws Exception
     */
    protected char peekChar() throws Exception {
        return source.peekChar();
    }

    /**
     * Returns the line number where the token in source begins.
     *
     * @return
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * Returns the token type.
     *
     * @return
     */
    public TokenType getType() {
        return type;
    }

    /**
     * Returns the computed value of a token.
     *
     * E.g. for extracted integers it should return an Integer object or such.
     *
     * @return
     */
    public Object getValue() {
        return value;
    }

    /**
     * Returns the token's raw text.
     *
     * @return
     */
    public String getText() {
        return text;
    }

    /**
     * Returns the column in the source where the token begins.
     *
     * @return
     */
    public int getPosition() {
        return position;
    }

}
