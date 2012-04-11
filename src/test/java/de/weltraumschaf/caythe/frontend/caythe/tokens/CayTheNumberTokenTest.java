package de.weltraumschaf.caythe.frontend.caythe.tokens;

import de.weltraumschaf.caythe.util.SourceHelper;
import static de.weltraumschaf.caythe.frontend.caythe.CayTheErrorCode.*;
import org.junit.Test;
import static org.junit.Assert.*;
import static de.weltraumschaf.caythe.frontend.caythe.CayTheTokenType.*;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class CayTheNumberTokenTest {

    @Test public void extract() throws Exception {
        CayTheNumberToken token;

        token = new CayTheNumberToken(SourceHelper.createFrom("42"));
        token.extract();
        assertEquals(INTEGER, token.getType());
        assertEquals(42, token.getValue());
        assertEquals("42", token.getText());

        token = new CayTheNumberToken(SourceHelper.createFrom("23foo"));
        token.extract();
        assertEquals(INTEGER, token.getType());
        assertEquals(23, token.getValue());
        assertEquals("23", token.getText());

        token = new CayTheNumberToken(SourceHelper.createFrom("3.1415"));
        token.extract();
        assertEquals(REAL, token.getType());
        assertEquals(3.1415f, (Float)token.getValue(), 0.0001f);
        assertEquals("3.1415", token.getText());
        token.extract();
        assertEquals(REAL, token.getType());
        assertEquals(3.1415f, (Float)token.getValue(), 0.0001f);
        assertEquals("3.1415", token.getText());

        token = new CayTheNumberToken(SourceHelper.createFrom("foo321"));
        token.extract();
        assertEquals(ERROR, token.getType());
        assertEquals(INVALID_NUMBER, token.getValue());
        assertEquals("", token.getText());
    }

}
