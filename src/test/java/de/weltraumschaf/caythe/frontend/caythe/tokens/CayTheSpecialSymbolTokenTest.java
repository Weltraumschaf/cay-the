package de.weltraumschaf.caythe.frontend.caythe.tokens;

import static de.weltraumschaf.caythe.frontend.caythe.CayTheErrorCode.INVALID_CHARACTER;
import static de.weltraumschaf.caythe.frontend.caythe.CayTheTokenType.*;
import static de.weltraumschaf.caythe.util.Assert.assertToken;
import de.weltraumschaf.caythe.util.SourceHelper;
import de.weltraumschaf.caythe.util.TokenFixture;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
@RunWith(Parameterized.class)
public class CayTheSpecialSymbolTokenTest {

    private final TokenFixture testData;

    public CayTheSpecialSymbolTokenTest(final TokenFixture data) {
        testData = data;
    }

    @Parameters
    public static Collection<TokenFixture[]> data() {
        TokenFixture[][] data = new TokenFixture[][] {
            {new TokenFixture(SourceHelper.createFrom("+"), "+", PLUS)},
            {new TokenFixture(SourceHelper.createFrom("+23"), "+", PLUS)},
            {new TokenFixture(SourceHelper.createFrom("-"), "-", MINUS)},
            {new TokenFixture(SourceHelper.createFrom("-foo"), "-", MINUS)},
            {new TokenFixture(SourceHelper.createFrom("*"), "*", STAR)},
            {new TokenFixture(SourceHelper.createFrom("* 11"), "*", STAR)},
            {new TokenFixture(SourceHelper.createFrom("/"), "/", SLASH)},
            {new TokenFixture(SourceHelper.createFrom("/ snafu"), "/", SLASH)},
            {new TokenFixture(SourceHelper.createFrom("="), "=", ASSIGN)},
            {new TokenFixture(SourceHelper.createFrom("= foo"), "=", ASSIGN)},
            {new TokenFixture(SourceHelper.createFrom(","), ",", COMMA)},
            {new TokenFixture(SourceHelper.createFrom(", foo"), ",", COMMA)},
            {new TokenFixture(SourceHelper.createFrom("++"), "++", INC)},
            {new TokenFixture(SourceHelper.createFrom("++ foo"), "++", INC)},
            {new TokenFixture(SourceHelper.createFrom("--"), "--", DEC)},
            {new TokenFixture(SourceHelper.createFrom("-- foo"), "--", DEC)},
            {new TokenFixture(SourceHelper.createFrom(";"), ";", SEMICOLON)},
            {new TokenFixture(SourceHelper.createFrom("; "), ";", SEMICOLON)},
            {new TokenFixture(SourceHelper.createFrom("\""), "\"", DOUBLE_QUOTE)},
            {new TokenFixture(SourceHelper.createFrom("\"snafu"), "\"", DOUBLE_QUOTE)},
            {new TokenFixture(SourceHelper.createFrom("'"), "'", QUOTE)},
            {new TokenFixture(SourceHelper.createFrom("=="), "==", EQUALS)},
            {new TokenFixture(SourceHelper.createFrom("==true"), "==", EQUALS)},
            {new TokenFixture(SourceHelper.createFrom("!="), "!=", NOT_EQUALS)},
            {new TokenFixture(SourceHelper.createFrom("!= false"), "!=", NOT_EQUALS)},
            {new TokenFixture(SourceHelper.createFrom("<"), "<", LESS_THAN)},
            {new TokenFixture(SourceHelper.createFrom("< 5"), "<", LESS_THAN)},
            {new TokenFixture(SourceHelper.createFrom("<="), "<=", LESS_EQUALS)},
            {new TokenFixture(SourceHelper.createFrom("<= 42"), "<=", LESS_EQUALS)},
            {new TokenFixture(SourceHelper.createFrom(">"), ">", GREATER_THAN)},
            {new TokenFixture(SourceHelper.createFrom("> 11"), ">", GREATER_THAN)},
            {new TokenFixture(SourceHelper.createFrom(">="), ">=", GREATER_EQUALS)},
            {new TokenFixture(SourceHelper.createFrom(">= foo"), ">=", GREATER_EQUALS)},
            {new TokenFixture(SourceHelper.createFrom("("), "(", LEFT_PAREN)},
            {new TokenFixture(SourceHelper.createFrom("(!"), "(", LEFT_PAREN)},
            {new TokenFixture(SourceHelper.createFrom(")"), ")", RIGHT_PAREN)},
            {new TokenFixture(SourceHelper.createFrom(") =="), ")", RIGHT_PAREN)},
            {new TokenFixture(SourceHelper.createFrom("{"), "{", LEFT_BRACE)},
            {new TokenFixture(SourceHelper.createFrom("{snafu"), "{", LEFT_BRACE)},
            {new TokenFixture(SourceHelper.createFrom("}"), "}", RIGHT_BRACE)},
            {new TokenFixture(SourceHelper.createFrom("}, "), "}", RIGHT_BRACE)},
            {new TokenFixture(SourceHelper.createFrom("["), "[", LEFT_BRACKET)},
            {new TokenFixture(SourceHelper.createFrom("[foo"), "[", LEFT_BRACKET)},
            {new TokenFixture(SourceHelper.createFrom("]"), "]", RIGHT_BRACKET)},
            {new TokenFixture(SourceHelper.createFrom("],"), "]", RIGHT_BRACKET)},
            {new TokenFixture(SourceHelper.createFrom("&&"), "&&", AND)},
            {new TokenFixture(SourceHelper.createFrom("&&foo"), "&&", AND)},
            {new TokenFixture(SourceHelper.createFrom("||"), "||", OR)},
            {new TokenFixture(SourceHelper.createFrom("||foo"), "||", OR)},
            {new TokenFixture(SourceHelper.createFrom("!"), "!", NOT)},
            {new TokenFixture(SourceHelper.createFrom("!foo"), "!", NOT)},
            {new TokenFixture(SourceHelper.createFrom("foo"), "f", ERROR, INVALID_CHARACTER)},
        };
        return Arrays.asList(data);
    }

    @Test public void extract() throws Exception {
        testData.getSource().nextChar();
        CayTheSpecialSymbolToken token = new CayTheSpecialSymbolToken(testData.getSource());
        token.extract();
        assertToken(testData, token);
        token.extract();
        assertToken(testData, token);
    }
}
