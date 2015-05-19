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
import de.weltraumschaf.commons.parse.characters.CharacterStream;
import de.weltraumschaf.commons.parse.token.Position;
import de.weltraumschaf.commons.validate.Validate;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class Scanner {

    private final CharacterStream source;
    private int currentLine = 1;
    private int currentColumn = 1;

    Scanner(final String source) {
        this(new CharacterStream(source));
    }

    Scanner(final CharacterStream source) {
        super();
        this.source = Validate.notNull(source, "source");
    }

    private Position currentPosition() {
        return new Position(currentLine, currentColumn);
    }

    boolean hasNext() {
        return source.hasNext();
    }

    Token getNext() {
        while (source.hasNext()) {
            final char currentChar = source.next();
        }

        return new Token(currentPosition(), "foo", TokenType.NEW_LINE);
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
