package de.weltraumschaf.caythe.frontend.caythe.tokens;

import static de.weltraumschaf.caythe.util.Assert.assertToken;
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
public class CayTheCharacterTokenTest {

    private final TokenFixture testData;

    public CayTheCharacterTokenTest(final TokenFixture data) {
        testData = data;
    }

    @Parameterized.Parameters
    public static Collection<TokenFixture[]> data() {
        TokenFixture[][] data = new TokenFixture[][] {
            };
        return Arrays.asList(data);
    }

    @Test public void extract() throws Exception {
        CayTheCharacterToken token = new CayTheCharacterToken(testData.getSource());
        token.extract();
        assertToken(testData, token);
        token.extract();
        assertToken(testData, token);
    }

}
