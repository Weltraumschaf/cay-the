package de.weltraumschaf.caythe.frontend;

import de.weltraumschaf.caythe.CayTheError;
import de.weltraumschaf.commons.validate.Validate;
import org.antlr.v4.runtime.Token;

/**
 * Runtime exception to signal semantic syntax errors during the parse tree transformation.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class SyntaxError extends CayTheError {
    private static final String UNSUPPORTED_OPERATOR = "Unsupported operator: '%s'!";

    /**
     * Use the factory methods.
     *
     * @param message mut not be {@code null} or empty
     */
    private SyntaxError(final String message) {
        super(message);
    }

    /**
     * Create an error for unsupported operator.
     *
     * @param operator must not be {@code null}
     * @return never {@code null}
     */
    public static SyntaxError newUnsupportedOperatorError(final Token operator) {
        Validate.notNull(operator, "operator");
        return newError(operator, UNSUPPORTED_OPERATOR, operator.getText());
    }

    /**
     * Create a generic error.
     *
     * @param messageFormat must not be {@code null} or empty
     * @param args optional format arguments for {@code messageFormat}
     * @return never {@code null}
     */
    public static SyntaxError newError(final String messageFormat, final Object... args) {
        return newError(null, messageFormat, args);
    }

    /**
     * Create a generic error with a position where it occurred.
     *
     * @param position may be {@code null}
     * @param messageFormat must not be {@code null} or empty
     * @param args optional format arguments for {@code messageFormat}
     * @return never {@code null}
     */
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
