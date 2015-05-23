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

import com.beust.jcommander.internal.Maps;
import de.weltraumschaf.commons.validate.Validate;
import java.util.Collections;
import java.util.Map;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
enum TokenType {

    NEW_LINE, END_OF_FILE,
    BOOLEAN_VALUE, INTEGER_VALUE, FLOAT_VALUE, STRING_VALUE,
    IDENTIFIER,
    OP_ADD("+"),
    OP_SUBTRACT("-"),
    OP_MULTIPLY("*"),
    OP_DIVIDE("/"),
    OP_MODULO("%"),
    OP_INCREMENT("++"),
    OP_DECREMENT("--"),
    OP_ASSIGN("="),
    OP_LESS_THAN("<"),
    OP_GREATER_THAN(">"),
    OP_LESS_EQUAL("<="),
    OP_GREATER_EQUAL(">="),
    OP_EQUAL("=="),
    OP_NOT_EQUAL("!="),
    OP_AND("&"),
    OP_OR("|"),
    OP_NOT("!"),
    K_RETURN("return"),
    K_BREAK("break"),
    K_CONTINUE("continue"),
    K_IF("if"),
    K_ELSE("else"),
    K_SWITCH("switch"),
    K_CASE("case"),
    K_DEFAULT("default"),
    K_FOR("for");

    private static final Map<String, TokenType> OPERATORS;

    static {
        Map<String, TokenType> tmp = Maps.newHashMap();

        for (final TokenType type : values()) {
            if (type.name().startsWith("OP_")) {
                tmp.put(type.literal.toString(), type);
            }
        }

        OPERATORS = Collections.unmodifiableMap(tmp);
    }

    private static final Map<String, TokenType> KEYWORDS;

    static {
        Map<String, TokenType> tmp = Maps.newHashMap();

        for (final TokenType type : values()) {
            if (type.name().startsWith("K_")) {
                tmp.put(type.literal.toString(), type);
            }
        }

        KEYWORDS = Collections.unmodifiableMap(tmp);
    }

    private final Literal literal;

    private TokenType() {
        this(Literal.EMPTY);
    }

    private TokenType(final String literal) {
        this(new Literal(literal));
    }

    private TokenType(final Literal literal) {
        this.literal = Validate.notNull(literal, "literal");
    }

    @Override
    public String toString() {
        if (literal.toString().isEmpty()) {
            return name();
        }

        return literal.toString();
    }


    static boolean isOperator(final String str) {
        return OPERATORS.containsKey(Validate.notNull(str, "str"));
    }

    static TokenType operatorFor(final String str) {
        if (isOperator(str)) {
            return OPERATORS.get(str);
        }

        throw new IllegalArgumentException("Not an operator '" + str + "'!");
    }

    static boolean isKeyword(final String str) {
        return KEYWORDS.containsKey(Validate.notNull(str, "str"));
    }

    static TokenType keywordFor(final String str) {
        if (isKeyword(str)) {
            return KEYWORDS.get(str);
        }

        throw new IllegalArgumentException("Not an keyword '" + str + "'!");
    }

    private static final class Literal {

        static final Literal EMPTY = new Literal("");

        private final String literal;

        private Literal(final String literal) {
            super();
            this.literal = Validate.notNull(literal, "literal");
        }

        @Override
        public String toString() {
            return literal;
        }

    }
}
