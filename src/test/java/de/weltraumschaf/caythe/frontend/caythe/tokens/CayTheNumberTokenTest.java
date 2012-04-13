package de.weltraumschaf.caythe.frontend.caythe.tokens;

import static de.weltraumschaf.caythe.frontend.caythe.CayTheErrorCode.INVALID_NUMBER;
import static de.weltraumschaf.caythe.frontend.caythe.CayTheTokenType.*;
import static de.weltraumschaf.caythe.util.Assert.assertToken;
import de.weltraumschaf.caythe.util.SourceHelper;
import de.weltraumschaf.caythe.util.TokenFixture;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
@RunWith(Parameterized.class)
public class CayTheNumberTokenTest {

    private final TokenFixture testData;

    public CayTheNumberTokenTest(final TokenFixture data) {
        testData = data;
    }

    @Parameterized.Parameters
    public static Collection<TokenFixture[]> data() {
        TokenFixture[][] data = new TokenFixture[][] {
            {new TokenFixture(SourceHelper.createFrom("42"), "42", INTEGER, 42)},
            {new TokenFixture(SourceHelper.createFrom("23foo"), "23", INTEGER, 23)},
            {new TokenFixture(SourceHelper.createFrom("3.1415"), "3.1415", REAL, 3.1415f)},
            {new TokenFixture(SourceHelper.createFrom("foo321"), "", ERROR, INVALID_NUMBER)},
        };
        return Arrays.asList(data);
    }

    @Test public void extract() throws Exception {
        testData.getSource().nextChar();
        CayTheNumberToken token = new CayTheNumberToken(testData.getSource());
        token.extract();
        assertToken(testData, token);
        token.extract();
        assertToken(testData, token);
    }

}
