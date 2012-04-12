package de.weltraumschaf.caythe.frontend.caythe.tokens;

import static de.weltraumschaf.caythe.frontend.caythe.CayTheErrorCode.UNEXPECTED_EOF;
import static de.weltraumschaf.caythe.frontend.caythe.CayTheTokenType.ERROR;
import static de.weltraumschaf.caythe.frontend.caythe.CayTheTokenType.STRING;
import static de.weltraumschaf.caythe.util.Assert.assertToken;
import de.weltraumschaf.caythe.util.SourceHelper;
import de.weltraumschaf.caythe.util.TokenFixture;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
@RunWith(Parameterized.class)
public class CayTheStringTokenTest {

    private final TokenFixture testData;

    public CayTheStringTokenTest(final TokenFixture data) {
        testData = data;
    }

    @Parameterized.Parameters
    public static Collection<TokenFixture[]> data() {
        TokenFixture[][] data = new TokenFixture[][] {
            {new TokenFixture(SourceHelper.createFrom("this is a string\""), "\"this is a string\"", STRING, "this is a string")},
            {new TokenFixture(SourceHelper.createFrom("this is a string\" snafu"), "\"this is a string\"", STRING, "this is a string")},
            {new TokenFixture(SourceHelper.createFrom("this is a string"), "\"this is a string", ERROR, UNEXPECTED_EOF)},
            {new TokenFixture(SourceHelper.createFrom("this"), "\"this", ERROR, UNEXPECTED_EOF)},
        };
        return Arrays.asList(data);
    }

    @Test public void extract() throws Exception {
        CayTheStringToken token = new CayTheStringToken(testData.getSource());
        token.extract();
        assertToken(testData, token);
        token.extract();
        assertToken(testData, token);
    }

}
