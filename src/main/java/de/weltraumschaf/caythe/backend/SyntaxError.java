package de.weltraumschaf.caythe.backend;

import org.antlr.v4.runtime.Token;

/**
 * Syntax error with token position.
 *
 * @since 1.0.0
 */
final class SyntaxError extends RuntimeException {

    public SyntaxError(final String message, final Token token) {
        super(String.format("%s (at line %d, column %d)!", message, token.getLine(), token.getCharPositionInLine() + 1));
    }

}
