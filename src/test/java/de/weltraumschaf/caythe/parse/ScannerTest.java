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
    public void scannEmpty() throws SyntaxException {
        final Scanner sut = new Scanner("");

        assertThat(sut.hasNext(), is(true));
        assertThat(sut.getNext(), is(token("", TokenType.END_OF_FILE, 0, 0)));
        assertThat(sut.hasNext(), is(false));
    }

    @Test
    public void scannSingleBooleanFalse() throws SyntaxException {
        final Scanner sut = new Scanner("false");

        assertThat(sut.hasNext(), is(true));
        assertThat(sut.getNext(), is(token("false", TokenType.BOOLEAN_VALUE, 1, 1)));
        assertThat(sut.hasNext(), is(true));
        assertThat(sut.getNext(), is(token("", TokenType.END_OF_FILE, 1, 6)));
        assertThat(sut.hasNext(), is(false));
    }

    @Test
    public void scannSingleBooleanTrue() throws SyntaxException {
        final Scanner sut = new Scanner("true");

        assertThat(sut.hasNext(), is(true));
        assertThat(sut.getNext(), is(token("true", TokenType.BOOLEAN_VALUE, 1, 1)));
        assertThat(sut.hasNext(), is(true));
        assertThat(sut.getNext(), is(token("", TokenType.END_OF_FILE, 1, 5)));
        assertThat(sut.hasNext(), is(false));
    }

    @Test
    public void scannSingleIntegerTrue() throws SyntaxException {
        final Scanner sut = new Scanner("42");

        assertThat(sut.hasNext(), is(true));
        assertThat(sut.getNext(), is(token("42", TokenType.INTEGER_VALUE, 1, 1)));
        assertThat(sut.hasNext(), is(true));
        assertThat(sut.getNext(), is(token("", TokenType.END_OF_FILE, 1, 3)));
        assertThat(sut.hasNext(), is(false));
    }

    @Test
    public void scannSingleFloatTrue() throws SyntaxException {
        final Scanner sut = new Scanner("3.14");

        assertThat(sut.hasNext(), is(true));
        assertThat(sut.getNext(), is(token("3.14", TokenType.FLOAT_VALUE, 1, 1)));
        assertThat(sut.hasNext(), is(true));
        assertThat(sut.getNext(), is(token("", TokenType.END_OF_FILE, 1, 5)));
        assertThat(sut.hasNext(), is(false));
    }

    @Test
    public void scannSingleString() throws SyntaxException {
        final Scanner sut = new Scanner("\"hello world\"");

        assertThat(sut.hasNext(), is(true));
        assertThat(sut.getNext(), is(token("hello world", TokenType.STRING_VALUE, 1, 1)));
        assertThat(sut.hasNext(), is(true));
        assertThat(sut.getNext(), is(token("", TokenType.END_OF_FILE, 1, 14)));
        assertThat(sut.hasNext(), is(false));
    }

    @Test
    public void scannDeclareBooleanVariabe() throws SyntaxException {
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
    public void scannDeclareIntegerVariabe() {
        final Scanner sut = new Scanner("var foo = 42");
    }

    @Test
    public void scannDeclareFloatVariabe() {
        final Scanner sut = new Scanner("var foo = 3.14");
    }

    @Test
    public void scannDeclareStringVariabe() {
        final Scanner sut = new Scanner("var foo = \"hello world\"");
    }

    @Test
    public void scannDeclareBooleanConstant() {
        final Scanner sut = new Scanner("const foo = true");
    }

    @Test
    public void scannDeclareIntegerConstant() {
        final Scanner sut = new Scanner("const foo = 42");
    }

    @Test
    public void scannDeclareFloatConstant() {
        final Scanner sut = new Scanner("const foo = 3.14");
    }

    @Test
    public void scannDeclareStringConstant() {
        final Scanner sut = new Scanner("const foo = \"hello world\"");
    }

}
