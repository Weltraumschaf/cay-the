package de.weltraumschaf.caythe.frontend.caythe;

import de.weltraumschaf.caythe.frontend.Source;
import de.weltraumschaf.caythe.frontend.Token;
import de.weltraumschaf.caythe.frontend.TokenType;
import de.weltraumschaf.caythe.frontend.caythe.tokens.CayTheWordToken;
import de.weltraumschaf.caythe.util.SourceHelper;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class CayTheScannerTest {

    public class MutableCayTheWordToken extends CayTheWordToken {

        public MutableCayTheWordToken() throws Exception {
            this(null);
        }

        public MutableCayTheWordToken(Source source) throws Exception {
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

    private static final String FIXTURE_DIR = "/de/weltraumschaf/caythe/frontend/caythe";

    private Source createSourceFromFixture(String fixtureFile) throws FileNotFoundException, URISyntaxException {
        URL resource = getClass().getResource(FIXTURE_DIR + '/' + fixtureFile);
        return SourceHelper.createFrom(resource.toURI());
    }

    private Token createToken(TokenType type) throws Exception {
        return createToken(type, type.getText());
    }
    private Token createToken(TokenType type, String text) throws Exception {
        return createToken(type, text, null);
    }

    private Token createToken(TokenType type, String text, Object value) throws Exception {
        MutableCayTheWordToken t = new MutableCayTheWordToken();
        t.setType(type);
        t.setText(text);
        t.setValue(value);
        return t;
    }

    private List<Token> createTokensForRealSource() throws Exception {
        List<Token> tokens = new ArrayList<Token>();
        tokens.add(createToken(CayTheTokenType.CONST));
        tokens.add(createToken(CayTheTokenType.IDENTIFIER, "I"));
        tokens.add(createToken(CayTheTokenType.ASSIGN));
        tokens.add(createToken(CayTheTokenType.INTEGER, "10", 10));
        tokens.add(createToken(CayTheTokenType.VAR));
        tokens.add(createToken(CayTheTokenType.IDENTIFIER, "x"));
        tokens.add(createToken(CayTheTokenType.ASSIGN));
        tokens.add(createToken(CayTheTokenType.REAL, "1.23", 1.23f));
        // while loop
        tokens.add(createToken(CayTheTokenType.WHILE));
        tokens.add(createToken(CayTheTokenType.IDENTIFIER, "x"));
        tokens.add(createToken(CayTheTokenType.LESS_THAN));
        tokens.add(createToken(CayTheTokenType.IDENTIFIER, "I"));
        tokens.add(createToken(CayTheTokenType.LEFT_BRACE));
        tokens.add(createToken(CayTheTokenType.IDENTIFIER, "echo"));
        tokens.add(createToken(CayTheTokenType.LEFT_PAREN));
        tokens.add(createToken(CayTheTokenType.IDENTIFIER, "x"));
        tokens.add(createToken(CayTheTokenType.RIGHT_PAREN));
        tokens.add(createToken(CayTheTokenType.IDENTIFIER, "x"));
        tokens.add(createToken(CayTheTokenType.INC));
        tokens.add(createToken(CayTheTokenType.RIGHT_BRACE));
        // iterate array
        tokens.add(createToken(CayTheTokenType.FOR));
        tokens.add(createToken(CayTheTokenType.IDENTIFIER, "item"));
        tokens.add(createToken(CayTheTokenType.IN));
        tokens.add(createToken(CayTheTokenType.LEFT_BRACKET));
        tokens.add(createToken(CayTheTokenType.INTEGER, "1", 1));
        tokens.add(createToken(CayTheTokenType.COMMA));
        tokens.add(createToken(CayTheTokenType.INTEGER, "22", 22));
        tokens.add(createToken(CayTheTokenType.COMMA));
        tokens.add(createToken(CayTheTokenType.INTEGER, "333", 333));
        tokens.add(createToken(CayTheTokenType.RIGHT_BRACKET));
        tokens.add(createToken(CayTheTokenType.LEFT_BRACE));
        tokens.add(createToken(CayTheTokenType.IDENTIFIER, "echo"));
        tokens.add(createToken(CayTheTokenType.LEFT_PAREN));
        tokens.add(createToken(CayTheTokenType.IDENTIFIER, "item"));
        tokens.add(createToken(CayTheTokenType.RIGHT_PAREN));
        tokens.add(createToken(CayTheTokenType.RIGHT_BRACE));
        // string is array of characters
        tokens.add(createToken(CayTheTokenType.FOR));
        tokens.add(createToken(CayTheTokenType.IDENTIFIER, "character"));
        tokens.add(createToken(CayTheTokenType.IN));
        tokens.add(createToken(CayTheTokenType.STRING, "\"a string\"", "a string"));
        tokens.add(createToken(CayTheTokenType.LEFT_BRACE));
        tokens.add(createToken(CayTheTokenType.IDENTIFIER, "echo"));
        tokens.add(createToken(CayTheTokenType.LEFT_PAREN));
        tokens.add(createToken(CayTheTokenType.IDENTIFIER, "character"));
        tokens.add(createToken(CayTheTokenType.RIGHT_PAREN));
        tokens.add(createToken(CayTheTokenType.RIGHT_BRACE));
        // define a function
        tokens.add(createToken(CayTheTokenType.FUNCTION));
        tokens.add(createToken(CayTheTokenType.IDENTIFIER, "foo"));
        tokens.add(createToken(CayTheTokenType.LEFT_PAREN));
        tokens.add(createToken(CayTheTokenType.RIGHT_PAREN));
        tokens.add(createToken(CayTheTokenType.LEFT_BRACE));
        tokens.add(createToken(CayTheTokenType.IDENTIFIER, "echo"));
        tokens.add(createToken(CayTheTokenType.LEFT_PAREN));
        tokens.add(createToken(CayTheTokenType.STRING, "\"foo\"", "foo"));
        tokens.add(createToken(CayTheTokenType.RIGHT_PAREN));
        tokens.add(createToken(CayTheTokenType.RIGHT_BRACE));
        // main function called bei runtime
        tokens.add(createToken(CayTheTokenType.FUNCTION));
        tokens.add(createToken(CayTheTokenType.IDENTIFIER, "main"));
        tokens.add(createToken(CayTheTokenType.LEFT_PAREN));
        tokens.add(createToken(CayTheTokenType.IDENTIFIER, "args"));
        tokens.add(createToken(CayTheTokenType.RIGHT_PAREN));
        tokens.add(createToken(CayTheTokenType.LEFT_BRACE));
        tokens.add(createToken(CayTheTokenType.VAR));
        tokens.add(createToken(CayTheTokenType.IDENTIFIER, "a"));
        tokens.add(createToken(CayTheTokenType.ASSIGN));
        tokens.add(createToken(CayTheTokenType.INTEGER, "3", 3));
        tokens.add(createToken(CayTheTokenType.IDENTIFIER, "b"));
        tokens.add(createToken(CayTheTokenType.ASSIGN));
        tokens.add(createToken(CayTheTokenType.INTEGER, "4", 4));
        tokens.add(createToken(CayTheTokenType.CONST));
        tokens.add(createToken(CayTheTokenType.IDENTIFIER, "A"));
        tokens.add(createToken(CayTheTokenType.ASSIGN));
        tokens.add(createToken(CayTheTokenType.CHARACTER, "'A'", 'A'));
        tokens.add(createToken(CayTheTokenType.IDENTIFIER, "B"));
        tokens.add(createToken(CayTheTokenType.ASSIGN));
        tokens.add(createToken(CayTheTokenType.CHARACTER, "'B'", 'B'));
        tokens.add(createToken(CayTheTokenType.IDENTIFIER, "PI"));
        tokens.add(createToken(CayTheTokenType.ASSIGN));
        tokens.add(createToken(CayTheTokenType.REAL, "3.1415", 3.1415f));
        tokens.add(createToken(CayTheTokenType.IF));
        tokens.add(createToken(CayTheTokenType.IDENTIFIER, "a"));
        tokens.add(createToken(CayTheTokenType.EQUALS));
        tokens.add(createToken(CayTheTokenType.INTEGER, "3", 3));
        tokens.add(createToken(CayTheTokenType.AND));
        tokens.add(createToken(CayTheTokenType.IDENTIFIER, "b"));
        tokens.add(createToken(CayTheTokenType.EQUALS));
        tokens.add(createToken(CayTheTokenType.INTEGER, "4", 4));
        tokens.add(createToken(CayTheTokenType.LEFT_BRACE));
        tokens.add(createToken(CayTheTokenType.IDENTIFIER, "foo"));
        tokens.add(createToken(CayTheTokenType.LEFT_PAREN));
        tokens.add(createToken(CayTheTokenType.RIGHT_PAREN));
        tokens.add(createToken(CayTheTokenType.RIGHT_BRACE));
        tokens.add(createToken(CayTheTokenType.FOR));
        tokens.add(createToken(CayTheTokenType.IDENTIFIER, "i"));
        tokens.add(createToken(CayTheTokenType.ASSIGN));
        tokens.add(createToken(CayTheTokenType.INTEGER, "0", 0));
        tokens.add(createToken(CayTheTokenType.SEMICOLON));
        tokens.add(createToken(CayTheTokenType.IDENTIFIER, "i"));
        tokens.add(createToken(CayTheTokenType.LESS_THAN));
        tokens.add(createToken(CayTheTokenType.IDENTIFIER, "a"));
        tokens.add(createToken(CayTheTokenType.SEMICOLON));
        tokens.add(createToken(CayTheTokenType.IDENTIFIER, "i"));
        tokens.add(createToken(CayTheTokenType.INC));
        tokens.add(createToken(CayTheTokenType.LEFT_BRACE));
        tokens.add(createToken(CayTheTokenType.IDENTIFIER, "foo"));
        tokens.add(createToken(CayTheTokenType.LEFT_PAREN));
        tokens.add(createToken(CayTheTokenType.RIGHT_PAREN));
        tokens.add(createToken(CayTheTokenType.RIGHT_BRACE));
        return tokens;
    }

    @Test public void scan() throws Exception {
        CayTheScanner scanner;
        scanner = new CayTheScanner(SourceHelper.createFrom("echo(x)"));
        scanner.nextChar();
        assertEquals(createToken(CayTheTokenType.IDENTIFIER, "echo"), scanner.extractToken());
        assertEquals(createToken(CayTheTokenType.LEFT_PAREN), scanner.extractToken());
        assertEquals(createToken(CayTheTokenType.IDENTIFIER, "x"), scanner.extractToken());
        assertEquals(createToken(CayTheTokenType.RIGHT_PAREN), scanner.extractToken());

        scanner = new CayTheScanner(SourceHelper.createFrom("a == 3 && b == 4"));
        scanner.nextChar();
        assertEquals(createToken(CayTheTokenType.IDENTIFIER, "a"), scanner.extractToken());
        assertEquals(createToken(CayTheTokenType.EQUALS), scanner.extractToken());
        assertEquals(createToken(CayTheTokenType.INTEGER, "3", 3), scanner.extractToken());
        assertEquals(createToken(CayTheTokenType.AND), scanner.extractToken());
        assertEquals(createToken(CayTheTokenType.IDENTIFIER, "b"), scanner.extractToken());
        assertEquals(createToken(CayTheTokenType.EQUALS), scanner.extractToken());
        assertEquals(createToken(CayTheTokenType.INTEGER, "4", 4), scanner.extractToken());

        scanner = new CayTheScanner(createSourceFromFixture("real_source.ct"));
        scanner.nextChar();
        for (Token t : createTokensForRealSource()) {
            assertEquals(t, scanner.extractToken());
        }
    }
}
