package de.weltraumschaf.caythe.frontend.caythe.tokens;

import de.weltraumschaf.caythe.util.TokenFixture;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import de.weltraumschaf.caythe.util.SourceHelper;
import de.weltraumschaf.caythe.util.TokenFixture;
import static de.weltraumschaf.caythe.util.Assert.assertToken;
import static de.weltraumschaf.caythe.frontend.caythe.CayTheTokenType.*;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
@RunWith(Parameterized.class)
public class CayTheWordTokenTest {

    private final TokenFixture testData;

    public CayTheWordTokenTest(final TokenFixture data) {
        testData = data;
    }

    @Parameterized.Parameters
    public static Collection<TokenFixture[]> data() {
        TokenFixture[][] data = new TokenFixture[][] {
            {new TokenFixture(SourceHelper.createFrom("foo"), "foo", IDENTIFIER)},
            {new TokenFixture(SourceHelper.createFrom("foo bar"), "foo", IDENTIFIER)},
            {new TokenFixture(SourceHelper.createFrom("foo23 bar"), "foo23", IDENTIFIER)},
            {new TokenFixture(SourceHelper.createFrom("foo-1 bar"), "foo", IDENTIFIER)},
            {new TokenFixture(SourceHelper.createFrom("foo_1 bar"), "foo_1", IDENTIFIER)},
            {new TokenFixture(SourceHelper.createFrom("fooBar42 = bla"), "fooBar42", IDENTIFIER)},
            // key words
            {new TokenFixture(SourceHelper.createFrom("const foo"), "const", CONST)},
            {new TokenFixture(SourceHelper.createFrom("var foo"), "var", VAR)},
            {new TokenFixture(SourceHelper.createFrom("while foo"), "while", WHILE)},
            {new TokenFixture(SourceHelper.createFrom("for foo"), "for", FOR)},
            {new TokenFixture(SourceHelper.createFrom("in foo"), "in", IN)},
            {new TokenFixture(SourceHelper.createFrom("function foo"), "function", FUNCTION)},
            {new TokenFixture(SourceHelper.createFrom("if foo"), "if", IF)},
            {new TokenFixture(SourceHelper.createFrom("else foo"), "else", ELSE)},
        };
        return Arrays.asList(data);
    }

    @Test public void extract() throws Exception {
        CayTheWordToken token = new CayTheWordToken(testData.getSource());
        token.extract();
        assertToken(testData, token);
        token.extract();
        assertToken(testData, token);
    }

}
