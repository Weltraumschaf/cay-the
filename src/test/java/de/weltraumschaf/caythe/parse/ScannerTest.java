/*
 *  LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" <weltraumschaf@googlemail.com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */
package de.weltraumschaf.caythe.parse;

import de.weltraumschaf.caythe.parse.Scanner.Token;
import de.weltraumschaf.commons.parse.token.Position;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Tests for {@link Scanner}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ScannerTest {

    private Token token(final String raw, final TokenType type, final int line, final int column) {
        return new Token(new Position(line, column), raw, type);
    }

    private void expect(final String source, Token... expectedTokens) throws SyntaxException {
        final Scanner sut = new Scanner(source);

        for (final Token t : expectedTokens) {
            assertThat(sut.hasNext(), is(true));
            assertThat(sut.getNext(), is(t));
        }

        assertThat(sut.hasNext(), is(false));
    }

    @Test
    public void scanEmpty() throws SyntaxException {
        expect("",
            token("", TokenType.END_OF_FILE, 0, 0));
    }

    @Test
    public void scanEmptyNewlines() throws SyntaxException {
        expect("\n\n",
            token("\n", TokenType.NEW_LINE, 1, 1),
            token("\n", TokenType.NEW_LINE, 2, 1),
            token("", TokenType.END_OF_FILE, 2, 2));
    }

    @Test
    public void scanEmptyNewlinesWithSpaces() throws SyntaxException {
        expect("  \n  \n",
            token("\n", TokenType.NEW_LINE, 1, 3),
            token("\n", TokenType.NEW_LINE, 2, 3),
            token("", TokenType.END_OF_FILE, 2, 4));
    }

    @Test
    public void scanSingleBooleanFalse() throws SyntaxException {
        expect("false",
            token("false", TokenType.BOOLEAN_VALUE, 1, 1),
            token("", TokenType.END_OF_FILE, 1, 6));
    }

    @Test
    public void scanSingleBooleanTrue() throws SyntaxException {
        expect("true",
            token("true", TokenType.BOOLEAN_VALUE, 1, 1),
            token("", TokenType.END_OF_FILE, 1, 5));
    }

    @Test
    public void scanMultiplebooleansInOneLine() throws SyntaxException {
        expect("true false  true\tfalse\n",
            token("true", TokenType.BOOLEAN_VALUE, 1, 1),
            token("false", TokenType.BOOLEAN_VALUE, 1, 6),
            token("true", TokenType.BOOLEAN_VALUE, 1, 13),
            token("false", TokenType.BOOLEAN_VALUE, 1, 18),
            token("\n", TokenType.NEW_LINE, 1, 22),
            token("", TokenType.END_OF_FILE, 2, 1));

    }

    @Test
    public void scanSingleIntegerTrue() throws SyntaxException {
        expect("42",
            token("42", TokenType.INTEGER_VALUE, 1, 1),
            token("", TokenType.END_OF_FILE, 1, 3));

    }

    @Test
    public void scanSingleFloatTrue() throws SyntaxException {
        expect("3.14",
            token("3.14", TokenType.FLOAT_VALUE, 1, 1),
            token("", TokenType.END_OF_FILE, 1, 5));

    }

    @Test
    public void scanSingleString() throws SyntaxException {
        expect("\"hello world\"",
            token("hello world", TokenType.STRING_VALUE, 1, 1),
            token("", TokenType.END_OF_FILE, 1, 14));

    }

    @Test
    @Ignore
    public void scanDeclareBooleanVariabe() throws SyntaxException {
        expect("var foo = true\n",
            token("var", TokenType.K_VAR, 1, 1),
            token("foo", TokenType.IDENTIFIER, 1, 5),
            token("=", TokenType.OP_ASSIGN, 1, 9),
            token("true", TokenType.BOOLEAN_VALUE, 1, 11),
            token("\n", TokenType.NEW_LINE, 1, 15),
            token("", TokenType.END_OF_FILE, 2, 1));

    }

    @Test
    @Ignore
    public void scanDeclareIntegerVariabe() throws SyntaxException {
        expect("var foo = 42\n",
            token("var", TokenType.K_VAR, 1, 1),
            token("foo", TokenType.IDENTIFIER, 1, 5),
            token("=", TokenType.OP_ASSIGN, 1, 9),
            token("42", TokenType.INTEGER_VALUE, 1, 11),
            token("\n", TokenType.NEW_LINE, 1, 13),
            token("", TokenType.END_OF_FILE, 2, 1));

    }

    @Test
    @Ignore
    public void scanDeclareFloatVariabe() throws SyntaxException {
        expect("var foo = 3.14\n",
            token("var", TokenType.K_VAR, 1, 1),
            token("foo", TokenType.IDENTIFIER, 1, 5),
            token("=", TokenType.OP_ASSIGN, 1, 9),
            token("3.14", TokenType.FLOAT_VALUE, 1, 11),
            token("\n", TokenType.NEW_LINE, 1, 15),
            token("", TokenType.END_OF_FILE, 2, 1));

    }

    @Test
    @Ignore
    public void scanDeclareStringVariabe() throws SyntaxException {
        expect("var foo = \"hello world\"\n",
            token("var", TokenType.K_VAR, 1, 1),
            token("foo", TokenType.IDENTIFIER, 1, 5),
            token("=", TokenType.OP_ASSIGN, 1, 9),
            token("hello world", TokenType.STRING_VALUE, 1, 11),
            token("\n", TokenType.NEW_LINE, 1, 24),
            token("", TokenType.END_OF_FILE, 2, 1));

    }

    @Test
    @Ignore
    public void scanDeclareBooleanConstant() throws SyntaxException {
        expect("const foo = true\n", new Token[0]);
    }

    @Test
    @Ignore
    public void scanDeclareIntegerConstant() throws SyntaxException {
        expect("const foo = 42\n", new Token[0]);
    }

    @Test
    @Ignore
    public void scanDeclareFloatConstant() throws SyntaxException {
        expect("const foo = 3.14\n", new Token[0]);
    }

    @Test
    @Ignore
    public void scanDeclareStringConstant() throws SyntaxException {
        expect("const foo = \"hello world\"\n", new Token[0]);
    }

}
