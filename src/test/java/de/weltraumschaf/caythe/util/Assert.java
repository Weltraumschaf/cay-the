package de.weltraumschaf.caythe.util;

import de.weltraumschaf.caythe.frontend.Token;
import static org.junit.Assert.assertEquals;

/**
 * Custom assertions go here.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class Assert {

    /**
     * Assertion to check {@link Token} against a {@link @TokenFixtrue}.
     * 
     * @param data
     * @param token
     */
    static public void assertToken(TokenFixture data, Token token) {
        assertEquals(data.getText(), token.getText());
        assertEquals(data.getValue(), token.getValue());
        assertEquals(data.getType(), token.getType());
    }

}
