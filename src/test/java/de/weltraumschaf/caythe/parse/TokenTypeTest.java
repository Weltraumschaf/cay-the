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

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class TokenTypeTest {

    @Test
    public void isOperator() {
        assertThat(TokenType.isOperator("+"), is(true));
        assertThat(TokenType.isOperator("-"), is(true));
        assertThat(TokenType.isOperator("*"), is(true));
        assertThat(TokenType.isOperator("/"), is(true));
        assertThat(TokenType.isOperator("%"), is(true));
        assertThat(TokenType.isOperator("++"), is(true));
        assertThat(TokenType.isOperator("--"), is(true));
        assertThat(TokenType.isOperator("="), is(true));
        assertThat(TokenType.isOperator("<"), is(true));
        assertThat(TokenType.isOperator(">"), is(true));
        assertThat(TokenType.isOperator("<="), is(true));
        assertThat(TokenType.isOperator(">="), is(true));
        assertThat(TokenType.isOperator("=="), is(true));
        assertThat(TokenType.isOperator("!="), is(true));
        assertThat(TokenType.isOperator("&"), is(true));
        assertThat(TokenType.isOperator("|"), is(true));
        assertThat(TokenType.isOperator("!"), is(true));
    }

    @Test
    public void operatorFor() {
        assertThat(TokenType.operatorFor("+"), is(TokenType.OP_ADD));
        assertThat(TokenType.operatorFor("-"), is(TokenType.OP_SUBTRACT));
        assertThat(TokenType.operatorFor("*"), is(TokenType.OP_MULTIPLY));
        assertThat(TokenType.operatorFor("/"), is(TokenType.OP_DIVIDE));
        assertThat(TokenType.operatorFor("%"), is(TokenType.OP_MODULO));
        assertThat(TokenType.operatorFor("++"), is(TokenType.OP_INCREMENT));
        assertThat(TokenType.operatorFor("--"), is(TokenType.OP_DECREMENT));
        assertThat(TokenType.operatorFor("="), is(TokenType.OP_ASSIGN));
        assertThat(TokenType.operatorFor("<"), is(TokenType.OP_LESS_THAN));
        assertThat(TokenType.operatorFor(">"), is(TokenType.OP_GREATER_THAN));
        assertThat(TokenType.operatorFor("<="), is(TokenType.OP_LESS_EQUAL));
        assertThat(TokenType.operatorFor(">="), is(TokenType.OP_GREATER_EQUAL));
        assertThat(TokenType.operatorFor("=="), is(TokenType.OP_EQUAL));
        assertThat(TokenType.operatorFor("!="), is(TokenType.OP_NOT_EQUAL));
        assertThat(TokenType.operatorFor("&"), is(TokenType.OP_AND));
        assertThat(TokenType.operatorFor("|"), is(TokenType.OP_OR));
        assertThat(TokenType.operatorFor("!"), is(TokenType.OP_NOT));
    }

    @Test
    public void isKeyword() {
        assertThat(TokenType.isKeyword("return"), is(true));
        assertThat(TokenType.isKeyword("break"), is(true));
        assertThat(TokenType.isKeyword("continue"), is(true));
        assertThat(TokenType.isKeyword("if"), is(true));
        assertThat(TokenType.isKeyword("else"), is(true));
        assertThat(TokenType.isKeyword("switch"), is(true));
        assertThat(TokenType.isKeyword("case"), is(true));
        assertThat(TokenType.isKeyword("default"), is(true));
        assertThat(TokenType.isKeyword("for"), is(true));
    }

    @Test
    public void keywordFor() {
        assertThat(TokenType.keywordFor("return"), is(TokenType.K_RETURN));
        assertThat(TokenType.keywordFor("break"), is(TokenType.K_BREAK));
        assertThat(TokenType.keywordFor("continue"), is(TokenType.K_CONTINUE));
        assertThat(TokenType.keywordFor("if"), is(TokenType.K_IF));
        assertThat(TokenType.keywordFor("else"), is(TokenType.K_ELSE));
        assertThat(TokenType.keywordFor("switch"), is(TokenType.K_SWITCH));
        assertThat(TokenType.keywordFor("case"), is(TokenType.K_CASE));
        assertThat(TokenType.keywordFor("default"), is(TokenType.K_DEFAULT));
        assertThat(TokenType.keywordFor("for"), is(TokenType.K_FOR));
    }
}
