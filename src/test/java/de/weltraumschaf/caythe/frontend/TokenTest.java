package de.weltraumschaf.caythe.frontend;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class TokenTest {

    public class MutableToken extends Token {

        public MutableToken() throws Exception {
            this(null);
        }

        public MutableToken(Source source) throws Exception {
            super(source);
        }

        public void setText(String text) {
            this.text = text;
        }

        public void setType(TokenType type) {
            this.type = type;
        }

        public void setValue(Object value) {
            this.value = value;
        }

    }

    public enum TestTokenType implements TokenType {
        CONST, VAR;
    }

    @Test public void equality() throws Exception {
        // same
        Object val1 = new Object();
        MutableToken t1 = new MutableToken();
        t1.setText("foo");
        t1.setType(TestTokenType.CONST);
        t1.setValue(val1);
        assertFalse(t1.equals(null));
        assertFalse(t1.equals(val1));
        assertTrue(t1.equals(t1));

        MutableToken t2 = new MutableToken();
        t2.setText("foo");
        t2.setType(TestTokenType.CONST);
        t2.setValue(val1);
        assertFalse(t2.equals(null));
        assertFalse(t2.equals(val1));
        assertTrue(t2.equals(t2));
        assertTrue(t1.equals(t2));
        assertTrue(t2.equals(t1));

        // different
        MutableToken t3 = new MutableToken();
        t3.setText("bar");
        t3.setType(TestTokenType.CONST);
        MutableToken t4 = new MutableToken();
        t4.setText("foo");
        t4.setType(TestTokenType.VAR);
        MutableToken t5 = new MutableToken();
        t5.setText("bar");
        t5.setType(TestTokenType.VAR);

        assertFalse(t1.equals(t3));
        assertFalse(t1.equals(t4));
        assertFalse(t1.equals(t5));
        assertFalse(t2.equals(t3));
        assertFalse(t2.equals(t4));
        assertFalse(t2.equals(t5));
    }
}
