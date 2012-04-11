package de.weltraumschaf.caythe.frontend.caythe.tokens;

import static de.weltraumschaf.caythe.frontend.caythe.CayTheErrorCode.UNEXPECTED_EOF;
import static de.weltraumschaf.caythe.frontend.caythe.CayTheTokenType.ERROR;
import static de.weltraumschaf.caythe.frontend.caythe.CayTheTokenType.STRING;
import de.weltraumschaf.caythe.util.SourceHelper;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class CayTheStringTokenTest {

    @Test public void extract() throws Exception {
        CayTheStringToken token;

        token = new CayTheStringToken(SourceHelper.createFrom("this is a string\""));
        token.extract();
        assertEquals(STRING, token.getType());
        assertEquals("this is a string", token.getValue());
        assertEquals("\"this is a string\"", token.getText());

        token = new CayTheStringToken(SourceHelper.createFrom("this is a string\" snafu"));
        token.extract();
        assertEquals(STRING, token.getType());
        assertEquals("this is a string", token.getValue());
        assertEquals("\"this is a string\"", token.getText());

        token = new CayTheStringToken(SourceHelper.createFrom("this is \"\"a\"\" string\""));
        token.extract();
        assertEquals(STRING, token.getType());
        assertEquals("this is \"a\" string", token.getValue());
        assertEquals("\"this is \"\"a\"\" string\"", token.getText());

        token = new CayTheStringToken(SourceHelper.createFrom("this is a string"));
        token.extract();
        assertEquals(ERROR, token.getType());
        assertEquals(UNEXPECTED_EOF, token.getValue());
        assertEquals("\"this is a string ", token.getText());
    }

}
