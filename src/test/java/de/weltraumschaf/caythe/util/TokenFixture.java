package de.weltraumschaf.caythe.util;

import de.weltraumschaf.caythe.frontend.Source;
import de.weltraumschaf.caythe.frontend.TokenType;

/**
 * Used as fixture to test against in the unit tests.
 *
 * Holds a {@link Source} object and expected {@link TokenType}, string value
 * and value object.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class TokenFixture {

    /**
     * Source to test.
     */
    private final Source    source;
    /**
     * Expected token type.
     */
    private final TokenType type;
    /**
     * Expected token text.
     */
    private final String    text;
    /**
     * Expected token value.
     */
    private final Object    value;

    /**
     * Initializes only the source.
     *
     * All other members will be null.
     *
     * @param src
     */
    public TokenFixture(Source src) {
        this(src, null, null);
    }

    /**
     * Initializes source, text and type.
     *
     * Value will be null.
     *
     * @param source
     * @param text
     * @param type
     */
    public TokenFixture(Source source, String text, TokenType type) {
        this(source, text, type, null);
    }

    /**
     * Designated constructor.
     *
     * @param source
     * @param text
     * @param type
     * @param value
     */
    public TokenFixture(Source source, String text, TokenType type, Object value) {
        this.source = source;
        this.type   = type;
        this.text   = text;
        this.value  = value;
    }

    /**
     * Gets the source to test against.
     *
     * @return
     */
    public Source getSource() {
        return source;
    }

    /**
     * Gets the expected text.
     *
     * May be null.
     *
     * @return
     */
    public String getText() {
        return text;
    }

    /**
     * Gets the expected type.
     *
     * May be null.
     *
     * @return
     */
    public TokenType getType() {
        return type;
    }

    /**
     * Gets the expected value.
     *
     * May be null.
     *
     * @return
     */
    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Fixture{" + "src=" + source + ", type=" + type + ", text=" + text + ", value=" + value + '}';
    }
}
