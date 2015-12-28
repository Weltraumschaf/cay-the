package de.weltraumschaf.caythe.backend;

import de.weltraumschaf.commons.validate.Validate;
import org.antlr.v4.runtime.Token;

/**
 * Syntax error with token position.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class SyntaxError extends RuntimeException {

    /**
     * Dedicated constructor.
     *
     * @param message must not be {@code null} or empty
     * @param token must not be {@code null} or empty
     */
    public SyntaxError(final String message, final Token token) {
        super(String.format(
            "%s (at line %d, column %d)!",
            Validate.notEmpty(message, "message"),
            Validate.notNull(token, "token").getLine(),
            token.getCharPositionInLine() + 1));
    }

}
