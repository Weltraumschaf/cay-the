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

    @Test
    public void scanEmpty() throws SyntaxException {
        final Scanner sut = new Scanner("");

        assertThat(sut.hasNext(), is(true));
        assertThat(sut.getNext(), is(token("", TokenType.END_OF_FILE, 0, 0)));
        assertThat(sut.hasNext(), is(false));
    }

    @Test
    public void scanSingleBooleanFalse() throws SyntaxException {
        final Scanner sut = new Scanner("false");

        assertThat(sut.hasNext(), is(true));
        assertThat(sut.getNext(), is(token("false", TokenType.BOOLEAN_VALUE, 1, 1)));
        assertThat(sut.hasNext(), is(true));
        assertThat(sut.getNext(), is(token("", TokenType.END_OF_FILE, 1, 6)));
        assertThat(sut.hasNext(), is(false));
    }

    @Test
    public void scanSingleBooleanTrue() throws SyntaxException {
        final Scanner sut = new Scanner("true");

        assertThat(sut.hasNext(), is(true));
        assertThat(sut.getNext(), is(token("true", TokenType.BOOLEAN_VALUE, 1, 1)));
        assertThat(sut.hasNext(), is(true));
        assertThat(sut.getNext(), is(token("", TokenType.END_OF_FILE, 1, 5)));
        assertThat(sut.hasNext(), is(false));
    }

    @Test
    public void scanSingleIntegerTrue() throws SyntaxException {
        final Scanner sut = new Scanner("42");

        assertThat(sut.hasNext(), is(true));
        assertThat(sut.getNext(), is(token("42", TokenType.INTEGER_VALUE, 1, 1)));
        assertThat(sut.hasNext(), is(true));
        assertThat(sut.getNext(), is(token("", TokenType.END_OF_FILE, 1, 3)));
        assertThat(sut.hasNext(), is(false));
    }

    @Test
    public void scanSingleFloatTrue() throws SyntaxException {
        final Scanner sut = new Scanner("3.14");

        assertThat(sut.hasNext(), is(true));
        assertThat(sut.getNext(), is(token("3.14", TokenType.FLOAT_VALUE, 1, 1)));
        assertThat(sut.hasNext(), is(true));
        assertThat(sut.getNext(), is(token("", TokenType.END_OF_FILE, 1, 5)));
        assertThat(sut.hasNext(), is(false));
    }

    @Test
    public void scanSingleString() throws SyntaxException {
        final Scanner sut = new Scanner("\"hello world\"");

        assertThat(sut.hasNext(), is(true));
        assertThat(sut.getNext(), is(token("hello world", TokenType.STRING_VALUE, 1, 1)));
        assertThat(sut.hasNext(), is(true));
        assertThat(sut.getNext(), is(token("", TokenType.END_OF_FILE, 1, 14)));
        assertThat(sut.hasNext(), is(false));
    }

    @Test
    public void scanDeclareBooleanVariabe() throws SyntaxException {
        final Scanner sut = new Scanner("var foo = true\n");

        assertThat(sut.hasNext(), is(true));
        assertThat(sut.getNext(), is(token("var", TokenType.K_VAR, 1, 1)));
        assertThat(sut.hasNext(), is(true));
        assertThat(sut.getNext(), is(token("foo", TokenType.IDENTIFIER, 1, 5)));
        assertThat(sut.hasNext(), is(true));
        assertThat(sut.getNext(), is(token("=", TokenType.OP_ASSIGN, 1, 9)));
        assertThat(sut.hasNext(), is(true));
        assertThat(sut.getNext(), is(token("true", TokenType.BOOLEAN_VALUE, 1, 11)));
        assertThat(sut.hasNext(), is(true));
        assertThat(sut.getNext(), is(token("\n", TokenType.NEW_LINE, 2, 1)));
        assertThat(sut.hasNext(), is(true));
        assertThat(sut.getNext(), is(token("", TokenType.END_OF_FILE, 2, 2)));
        assertThat(sut.hasNext(), is(false));
    }

    @Test
    public void scanDeclareIntegerVariabe() throws SyntaxException {
        final Scanner sut = new Scanner("var foo = 42\n");

        assertThat(sut.hasNext(), is(true));
        assertThat(sut.getNext(), is(token("var", TokenType.K_VAR, 1, 1)));
        assertThat(sut.hasNext(), is(true));
        assertThat(sut.getNext(), is(token("foo", TokenType.IDENTIFIER, 1, 5)));
        assertThat(sut.hasNext(), is(true));
        assertThat(sut.getNext(), is(token("=", TokenType.OP_ASSIGN, 1, 9)));
        assertThat(sut.hasNext(), is(true));
        assertThat(sut.getNext(), is(token("42", TokenType.INTEGER_VALUE, 1, 11)));
        assertThat(sut.hasNext(), is(true));
        assertThat(sut.getNext(), is(token("\n", TokenType.NEW_LINE, 2, 1)));
        assertThat(sut.hasNext(), is(true));
        assertThat(sut.getNext(), is(token("", TokenType.END_OF_FILE, 2, 2)));
        assertThat(sut.hasNext(), is(false));
    }

    @Test
    public void scanDeclareFloatVariabe() throws SyntaxException {
        final Scanner sut = new Scanner("var foo = 3.14\n");

        assertThat(sut.hasNext(), is(true));
        assertThat(sut.getNext(), is(token("var", TokenType.K_VAR, 1, 1)));
        assertThat(sut.hasNext(), is(true));
        assertThat(sut.getNext(), is(token("foo", TokenType.IDENTIFIER, 1, 5)));
        assertThat(sut.hasNext(), is(true));
        assertThat(sut.getNext(), is(token("=", TokenType.OP_ASSIGN, 1, 9)));
        assertThat(sut.hasNext(), is(true));
        assertThat(sut.getNext(), is(token("3.14", TokenType.FLOAT_VALUE, 1, 11)));
        assertThat(sut.hasNext(), is(true));
        assertThat(sut.getNext(), is(token("\n", TokenType.NEW_LINE, 2, 1)));
        assertThat(sut.hasNext(), is(true));
        assertThat(sut.getNext(), is(token("", TokenType.END_OF_FILE, 2, 2)));
        assertThat(sut.hasNext(), is(false));
    }

    @Test
    public void scanDeclareStringVariabe() {
        final Scanner sut = new Scanner("var foo = \"hello world\"\n");
    }

    @Test
    public void scanDeclareBooleanConstant() {
        final Scanner sut = new Scanner("const foo = true\n");
    }

    @Test
    public void scanDeclareIntegerConstant() {
        final Scanner sut = new Scanner("const foo = 42\n");
    }

    @Test
    public void scanDeclareFloatConstant() {
        final Scanner sut = new Scanner("const foo = 3.14\n");
    }

    @Test
    public void scanDeclareStringConstant() {
        final Scanner sut = new Scanner("const foo = \"hello world\"\n");
    }

}
