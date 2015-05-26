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
            token("", TokenType.END_OF_FILE, 3, 1));
    }

    @Test
    public void scanEmptyNewlinesWithSpaces() throws SyntaxException {
        expect("  \n  \n",
            token("\n", TokenType.NEW_LINE, 1, 3),
            token("\n", TokenType.NEW_LINE, 2, 3),
            token("", TokenType.END_OF_FILE, 3, 1));
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
            token("\n", TokenType.NEW_LINE, 1, 23),
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
    public void scanDeclareBooleanConstant() throws SyntaxException {
        expect("const foo = true\n",
            token("const", TokenType.K_CONST, 1, 1),
            token("foo", TokenType.IDENTIFIER, 1, 7),
            token("=", TokenType.OP_ASSIGN, 1, 11),
            token("true", TokenType.BOOLEAN_VALUE, 1, 13),
            token("\n", TokenType.NEW_LINE, 1, 17),
            token("", TokenType.END_OF_FILE, 2, 1));
    }

    @Test
    public void scanDeclareIntegerConstant() throws SyntaxException {
        expect("const foo = 42\n",
            token("const", TokenType.K_CONST, 1, 1),
            token("foo", TokenType.IDENTIFIER, 1, 7),
            token("=", TokenType.OP_ASSIGN, 1, 11),
            token("42", TokenType.INTEGER_VALUE, 1, 13),
            token("\n", TokenType.NEW_LINE, 1, 15),
            token("", TokenType.END_OF_FILE, 2, 1));
    }

    @Test
    public void scanDeclareFloatConstant() throws SyntaxException {
        expect("const foo = 3.14\n",
            token("const", TokenType.K_CONST, 1, 1),
            token("foo", TokenType.IDENTIFIER, 1, 7),
            token("=", TokenType.OP_ASSIGN, 1, 11),
            token("3.14", TokenType.FLOAT_VALUE, 1, 13),
            token("\n", TokenType.NEW_LINE, 1, 17),
            token("", TokenType.END_OF_FILE, 2, 1));
    }

    @Test
    public void scanDeclareStringConstant() throws SyntaxException {
        expect("const foo = \"hello world\"\n",
            token("const", TokenType.K_CONST, 1, 1),
            token("foo", TokenType.IDENTIFIER, 1, 7),
            token("=", TokenType.OP_ASSIGN, 1, 11),
            token("hello world", TokenType.STRING_VALUE, 1, 13),
            token("\n", TokenType.NEW_LINE, 1, 26),
            token("", TokenType.END_OF_FILE, 2, 1));
    }

    @Test
    public void scanSimpleFunctionDeclaration() throws SyntaxException {
        expect("function foo(integer bar) { }",
            token("function", TokenType.K_FUNCTION, 1, 1),
            token("foo", TokenType.IDENTIFIER, 1, 10),
            token("(", TokenType.OP_LPAREN, 1, 13),
            token("integer", TokenType.IDENTIFIER, 1, 14),
            token("bar", TokenType.IDENTIFIER, 1, 22),
            token(")", TokenType.OP_RPAREN, 1, 25),
            token("{", TokenType.OP_LBRACE, 1, 27),
            token("}", TokenType.OP_RBRACE, 1, 29),
            token("", TokenType.END_OF_FILE, 1, 30)
        );
    }

    @Test
    @Ignore
    public void scannSingleLinecomments() throws SyntaxException {
        expect("", new Token[0]);
    }

    @Test
    @Ignore
    public void scannMultiLinecomments() throws SyntaxException {
        expect("", new Token[0]);
    }

    @Test
    public void scanComplexCodeWithDeclarationsAndFunction() throws SyntaxException {
        expect(
            "var integer foo = 23\n"
            + "var integer bar\n"
            + "var integer baz = foo + bar\n"
            + "\n"
            + "const snafu = \"Hello !\"\n"
            + "\n"
            + "function doIt(integer i) {\n"
            + "    print(i)\n"
            + "}\n"
            + "\n"
            + "function integer doWhat(integer i, integer j) {\n"
            + "    return i + j\n"
            + "}\n"
            + "\n"
            + "var result = doWhat(foo, 42)\n",
            // Line 1.
            token("var", TokenType.K_VAR, 1, 1),
            token("integer", TokenType.IDENTIFIER, 1, 5),
            token("foo", TokenType.IDENTIFIER, 1, 13),
            token("=", TokenType.OP_ASSIGN, 1, 17),
            token("23", TokenType.INTEGER_VALUE, 1, 19),
            token("\n", TokenType.NEW_LINE, 1, 21),
            // Line 2.
            token("var", TokenType.K_VAR, 2, 1),
            token("integer", TokenType.IDENTIFIER, 2, 5),
            token("bar", TokenType.IDENTIFIER, 2, 13),
            token("\n", TokenType.NEW_LINE, 2, 16),
            // Line 3.
            token("var", TokenType.K_VAR, 3, 1),
            token("integer", TokenType.IDENTIFIER, 3, 5),
            token("baz", TokenType.IDENTIFIER, 3, 13),
            token("=", TokenType.OP_ASSIGN, 3, 17),
            token("foo", TokenType.IDENTIFIER, 3, 19),
            token("+", TokenType.OP_ADD, 3, 23),
            token("bar", TokenType.IDENTIFIER, 3, 25),
            token("\n", TokenType.NEW_LINE, 3, 28),
            // Line 4.
            token("\n", TokenType.NEW_LINE, 4, 1),
            // Line 5.
            token("const", TokenType.K_CONST, 5, 1),
            token("snafu", TokenType.IDENTIFIER, 5, 7),
            token("=", TokenType.OP_ASSIGN, 5, 13),
            token("Hello !", TokenType.STRING_VALUE, 5, 15),
            token("\n", TokenType.NEW_LINE, 5, 24),
            // Line 6.
            token("\n", TokenType.NEW_LINE, 6, 1),
            // Line 7.
            token("function", TokenType.K_FUNCTION, 7, 1),
            token("doIt", TokenType.IDENTIFIER, 7, 10),
            token("(", TokenType.OP_LPAREN, 7, 14),
            token("integer", TokenType.IDENTIFIER, 7, 15),
            token("i", TokenType.IDENTIFIER, 7, 23),
            token(")", TokenType.OP_RPAREN, 7, 24),
            token("{", TokenType.OP_LBRACE, 7, 26),
            token("\n", TokenType.NEW_LINE, 7, 27),
            // Line 8.
            token("print", TokenType.IDENTIFIER, 8, 5),
            token("(", TokenType.OP_LPAREN, 8, 10),
            token("i", TokenType.IDENTIFIER, 8, 11),
            token(")", TokenType.OP_RPAREN, 8, 12),
            token("\n", TokenType.NEW_LINE, 8, 13),
            // Line 9.
            token("}", TokenType.OP_RBRACE, 9, 1),
            token("\n", TokenType.NEW_LINE, 9, 2),
            // Line 10.
            token("\n", TokenType.NEW_LINE, 10, 1),
            // Line 11.
            token("function", TokenType.K_FUNCTION, 11, 1),
            token("integer", TokenType.IDENTIFIER, 11, 10),
            token("doWhat", TokenType.IDENTIFIER, 11, 18),
            token("(", TokenType.OP_LPAREN, 11, 24),
            token("integer", TokenType.IDENTIFIER, 11, 25),
            token("i", TokenType.IDENTIFIER, 11, 33),
            token(",", TokenType.OP_COMMA, 11, 34),
            token("integer", TokenType.IDENTIFIER, 11, 36),
            token("j", TokenType.IDENTIFIER, 11, 44),
            token(")", TokenType.OP_RPAREN, 11, 45),
            token("{", TokenType.OP_LBRACE, 11, 47),
            token("\n", TokenType.NEW_LINE, 11, 48),
            // Line 12.
            token("return", TokenType.K_RETURN, 12, 5),
            token("i", TokenType.IDENTIFIER, 12, 12),
            token("+", TokenType.OP_ADD, 12, 14),
            token("j", TokenType.IDENTIFIER, 12, 16),
            token("\n", TokenType.NEW_LINE, 12, 17),
            // Line 13.
            token("}", TokenType.OP_RBRACE, 13, 1),
            token("\n", TokenType.NEW_LINE, 13, 2),
            // Line 14.
            token("\n", TokenType.NEW_LINE, 14, 1),
            // Line 15.
            token("var", TokenType.K_VAR, 15, 1),
            token("result", TokenType.IDENTIFIER, 15, 5),
            token("=", TokenType.OP_ASSIGN, 15, 12),
            token("doWhat", TokenType.IDENTIFIER, 15, 14),
            token("(", TokenType.OP_LPAREN, 15, 20),
            token("foo", TokenType.IDENTIFIER, 15, 21),
            token(",", TokenType.OP_COMMA, 15, 24),
            token("42", TokenType.INTEGER_VALUE, 15, 26),
            token(")", TokenType.OP_RPAREN, 15, 28),
            token("\n", TokenType.NEW_LINE, 15, 29),
            // EOF
            token("", TokenType.END_OF_FILE, 16, 1)
        );
    }

}
