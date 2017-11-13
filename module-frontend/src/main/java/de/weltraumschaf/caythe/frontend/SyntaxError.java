package de.weltraumschaf.caythe.frontend;

import de.weltraumschaf.caythe.CayTheError;
import org.antlr.v4.runtime.Token;

/**
 * Runtime exception to signal syntax errors.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class SyntaxError extends CayTheError {
    private static final String UNSUPPORTED_OPERATOR = "Unsupported operator: '%s'!";

    private SyntaxError(final String message) {
        super(message);
    }

    public static SyntaxError newUnsupportedOperatorError(final Token operator) {
        return newError(operator, UNSUPPORTED_OPERATOR, operator.getText());
    }

    public static SyntaxError newError(final String messageFormat, final Object... args) {
        return newError(null, messageFormat, args);
    }

    public static SyntaxError newError(final Token position, final String messageFormat, final Object... args) {
        final StringBuilder buffer = new StringBuilder("[E] ");
        buffer.append(String.format(messageFormat, args));

        if (position != null) {
            buffer.append(" (at line ").append(position.getLine());
            buffer.append(" column ").append(position.getCharPositionInLine()).append(')');
        }

        return new SyntaxError(buffer.toString());
    }
}
