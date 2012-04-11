package de.weltraumschaf.caythe.frontend.caythe.tokens;

import de.weltraumschaf.caythe.frontend.Source;
import de.weltraumschaf.caythe.frontend.TokenType;
import static de.weltraumschaf.caythe.frontend.caythe.CayTheErrorCode.INVALID_CHARACTER;
import static de.weltraumschaf.caythe.frontend.caythe.CayTheTokenType.*;
import de.weltraumschaf.caythe.util.SourceHelper;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertEquals;
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

    public static final class Fixture {
        private final Source    source;
        private final TokenType type;
        private final String    text;
        private final Object    value;

        public Fixture(Source src) {
            this(src, null, null);
        }

        public Fixture(Source source, String text, TokenType type) {
            this(source, text, type, null);
        }

        public Fixture(Source source, String text, TokenType type, Object value) {
            this.source = source;
            this.type   = type;
            this.text   = text;
            this.value  = value;
        }

        public Source getSource() {
            return source;
        }

        public String getText() {
            return text;
        }

        public TokenType getType() {
            return type;
        }

        public Object getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "Fixture{" + "src=" + source + ", type=" + type + ", text=" + text + ", value=" + value + '}';
        }

    }

    private final Fixture data;

    public CayTheSpecialSymbolTokenTest(final Fixture data) {
        this.data = data;
    }

    @Parameters
    public static Collection<Fixture[]> data() {
        Fixture[][] data = new Fixture[][] {
            {new Fixture(SourceHelper.createFrom("+"), "+", PLUS)},
            {new Fixture(SourceHelper.createFrom("+23"), "+", PLUS)},
            {new Fixture(SourceHelper.createFrom("-"), "-", MINUS)},
            {new Fixture(SourceHelper.createFrom("-foo"), "-", MINUS)},
            {new Fixture(SourceHelper.createFrom("*"), "*", STAR)},
            {new Fixture(SourceHelper.createFrom("* 11"), "*", STAR)},
            {new Fixture(SourceHelper.createFrom("/"), "/", SLASH)},
            {new Fixture(SourceHelper.createFrom("/ snafu"), "/", SLASH)},
            {new Fixture(SourceHelper.createFrom("="), "=", ASSIGN)},
            {new Fixture(SourceHelper.createFrom("= foo"), "=", ASSIGN)},
            {new Fixture(SourceHelper.createFrom(","), ",", COMMA)},
            {new Fixture(SourceHelper.createFrom(", foo"), ",", COMMA)},
            {new Fixture(SourceHelper.createFrom(";"), ";", SEMICOLON)},
            {new Fixture(SourceHelper.createFrom("; "), ";", SEMICOLON)},
            {new Fixture(SourceHelper.createFrom("\""), "\"", DOUBLE_QUOTE)},
            {new Fixture(SourceHelper.createFrom("\"snafu"), "\"", DOUBLE_QUOTE)},
            {new Fixture(SourceHelper.createFrom("'"), "'", QUOTE)},
            {new Fixture(SourceHelper.createFrom("=="), "==", EQUALS)},
            {new Fixture(SourceHelper.createFrom("==true"), "==", EQUALS)},
            {new Fixture(SourceHelper.createFrom("!="), "!=", NOT_EQUALS)},
            {new Fixture(SourceHelper.createFrom("!= false"), "!=", NOT_EQUALS)},
            {new Fixture(SourceHelper.createFrom("<"), "<", LESS_THAN)},
            {new Fixture(SourceHelper.createFrom("< 5"), "<", LESS_THAN)},
            {new Fixture(SourceHelper.createFrom("<="), "<=", LESS_EQUALS)},
            {new Fixture(SourceHelper.createFrom("<= 42"), "<=", LESS_EQUALS)},
            {new Fixture(SourceHelper.createFrom(">"), ">", GREATER_THAN)},
            {new Fixture(SourceHelper.createFrom("> 11"), ">", GREATER_THAN)},
            {new Fixture(SourceHelper.createFrom(">="), ">=", GREATER_EQUALS)},
            {new Fixture(SourceHelper.createFrom(">= foo"), ">=", GREATER_EQUALS)},
            {new Fixture(SourceHelper.createFrom("("), "(", LEFT_PAREN)},
            {new Fixture(SourceHelper.createFrom("(!"), "(", LEFT_PAREN)},
            {new Fixture(SourceHelper.createFrom(")"), ")", RIGHT_PAREN)},
            {new Fixture(SourceHelper.createFrom(") =="), ")", RIGHT_PAREN)},
            {new Fixture(SourceHelper.createFrom("{"), "{", LEFT_BRACE)},
            {new Fixture(SourceHelper.createFrom("{snafu"), "{", LEFT_BRACE)},
            {new Fixture(SourceHelper.createFrom("}"), "}", RIGHT_BRACE)},
            {new Fixture(SourceHelper.createFrom("}, "), "}", RIGHT_BRACE)},
            {new Fixture(SourceHelper.createFrom("["), "[", LEFT_BRACKET)},
            {new Fixture(SourceHelper.createFrom("[foo"), "[", LEFT_BRACKET)},
            {new Fixture(SourceHelper.createFrom("]"), "]", RIGHT_BRACKET)},
            {new Fixture(SourceHelper.createFrom("],"), "]", RIGHT_BRACKET)},
            {new Fixture(SourceHelper.createFrom("foo"), "f", ERROR, INVALID_CHARACTER)},
        };
        return Arrays.asList(data);
    }

    private void assertToken(Fixture data, CayTheSpecialSymbolToken token) {
        assertEquals(data.getText(), token.getText());
        assertEquals(data.getValue(), token.getValue());
        assertEquals(data.getType(), token.getType());
    }

    @Test public void extract() throws Exception {
        CayTheSpecialSymbolToken token = new CayTheSpecialSymbolToken(data.getSource());
        token.extract();
        assertToken(data, token);
    }
}
