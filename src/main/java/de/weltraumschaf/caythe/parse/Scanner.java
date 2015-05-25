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

import de.weltraumschaf.commons.guava.Objects;
import de.weltraumschaf.commons.parse.characters.CharacterHelper;
import de.weltraumschaf.commons.parse.characters.CharacterStream;
import de.weltraumschaf.commons.parse.token.Position;
import de.weltraumschaf.commons.validate.Validate;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class Scanner {

    private final CharacterStream source;
    private Token last = new Token(Position.NULL, "", TokenType.NONE);

    Scanner(final String source) {
        this(new CharacterStream(source));
    }

    Scanner(final CharacterStream source) {
        super();
        this.source = Validate.notNull(source, "source");
    }

    private Position currentPosition() {
        return source.position();
    }

    boolean hasNext() {
        return last.type != TokenType.END_OF_FILE;
    }

    Token getNext() throws SyntaxException {
        if (source.isEmpty()) {
            last = new Token(currentPosition(), "", TokenType.END_OF_FILE);
            return last;
        }

        while (source.hasNext()) {
            final char currentChar = source.next();

            if (CharacterHelper.isSign(currentChar) && CharacterHelper.isNum(source.peek())) {
                last = scanNumber();
                return last;
            } else if (CharacterHelper.isNum(currentChar)) {
                last = scanNumber();
                return last;
            } else if (CharacterHelper.isAlpha(currentChar)) {
                last = scanLiteral(new StringBuilder(), currentPosition());
                return last;
            } else if (CharacterHelper.isOperator(currentChar)) {
                last = scanOperator();
                return last;
            } else if (CharacterHelper.isDoubleQuote(currentChar)) {
                last = scanString();
                return last;
            } else if ('\n' == currentChar) {
                last = new Token(currentPosition(), "\n", TokenType.NEW_LINE);
                return last;
            } else if (' ' == currentChar || '\t' == currentChar) {
                // Skip whitespaces.
            } else {
                throw new SyntaxException("Unrecognized character '" + currentChar + "'!");
            }
        }

        last = new Token(currentPosition().incColumn(), "", TokenType.END_OF_FILE);
        return last;
    }

    private Token scanNumber() {
        final StringBuilder value = new StringBuilder();
        final Position start = currentPosition();
        value.append(source.current());

        while (source.hasNext()) {
            final char currentChar = source.next();

            if (CharacterHelper.isWhiteSpace(currentChar)) {
                break;
            }

            if ('.' == currentChar) {
                return scanFloat(value, start);
            }

            if (!CharacterHelper.isNum(currentChar)) {
                return scanLiteral(value, start);
            }

            value.append(currentChar);
        }

        return new Token(start, value.toString(), TokenType.INTEGER_VALUE);
    }

    private Token scanLiteral(final StringBuilder value, final Position start) {
        value.append(source.current());

        while (source.hasNext()) {
            if (CharacterHelper.isWhiteSpace(source.peek())) {
                break;
            }

            value.append(source.next());
        }

        final String tokenString = value.toString();

        if ("true".equals(tokenString) || "false".equals(tokenString)) {
            return new Token(start, value.toString(), TokenType.BOOLEAN_VALUE);
        } else if (TokenType.isKeyword(tokenString)) {
            return new Token(start, value.toString(), TokenType.keywordFor(tokenString));
        } else {
            return new Token(start, value.toString(), TokenType.IDENTIFIER);
        }
    }

    private Token scanString() throws SyntaxException {
        final StringBuilder value = new StringBuilder();
        final Position start = currentPosition();
        final char startQuote = source.current();
        boolean terminated = false;

        while (source.hasNext()) {
            final char currentChar = source.next();

            if (currentChar == startQuote) {
                terminated = true;

                if (source.hasNext()) {
                    // Skip closing quote, if there are more characters.
                    source.next();
                }

                break;
            }

            value.append(currentChar);
        }

        if (!terminated) {
            throw new SyntaxException(String.format("Unterminated string '%s'!", value.toString()));
        }

        return new Token(start, value.toString(), TokenType.STRING_VALUE);
    }

    private Token scanFloat(final StringBuilder value, final Position start) {
        value.append(source.current());

        while (source.hasNext()) {
            final char currentChar = source.next();

            if (CharacterHelper.isWhiteSpace(currentChar)) {
                break;
            }

            if (!CharacterHelper.isNum(currentChar) && !isAllowedInFloat(currentChar)) {
                return scanLiteral(value, start);
            }

            value.append(currentChar);
        }

        return new Token(start, value.toString(), TokenType.FLOAT_VALUE);
    }

    /**
     * Whether an character is allowed in float literals.
     * <p>
     * Allowed are signs (+|-) and the exponential characters (E|e).
     * </p>
     *
     * @param c any character
     * @return {@code true} if c is allowed, else {@code false}
     */
    private boolean isAllowedInFloat(final char c) {
        return CharacterHelper.isSign(c) || 'e' == c || 'E' == c;
    }

    private Token scanOperator() {
        final StringBuilder value = new StringBuilder();
        final Position start = currentPosition();
        value.append(source.current());

        while (source.hasNext()) {
            final char currentChar = source.next();

            if (CharacterHelper.isWhiteSpace(currentChar)) {
                break;
            }

            value.append(currentChar);
        }

        return new Token(start, value.toString(), TokenType.operatorFor(value.toString()));
    }

    static final class Token implements de.weltraumschaf.commons.parse.token.Token {

        private final Position pos;
        private final String raw;
        private final TokenType type;

        public Token(final Position pos, final String raw, final TokenType type) {
            super();
            this.pos = Validate.notNull(pos, "pos");
            this.raw = Validate.notNull(raw, "raw");
            this.type = Validate.notNull(type, "type");
        }

        @Override
        public Position getPosition() {
            return pos;
        }

        @Override
        public String getRaw() {
            return raw;
        }

        public TokenType getType() {
            return type;
        }

        @Override
        public String toString() {
            return Objects.toStringHelper(this)
                .add("pos", pos)
                .add("raw", raw)
                .add("type", type)
                .toString();
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(pos, raw, type);
        }

        @Override
        public boolean equals(final Object obj) {
            if (!(obj instanceof Token)) {
                return false;
            }

            final Token other = (Token) obj;
            return Objects.equal(pos, other.pos)
                && Objects.equal(raw, other.raw)
                && Objects.equal(type, other.type);
        }

    }
}
