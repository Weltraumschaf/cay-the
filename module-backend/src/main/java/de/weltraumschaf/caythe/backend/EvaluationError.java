package de.weltraumschaf.caythe.backend;

import de.weltraumschaf.caythe.CayTheError;
import de.weltraumschaf.caythe.intermediate.experimental.Position;
import de.weltraumschaf.caythe.intermediate.experimental.ast.AstNode;

/**
 * Error to signal errors during the interpretation.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class EvaluationError extends CayTheError {
    private EvaluationError(final String message) {
        super(message);
    }

    public static EvaluationError newError(final AstNode position, final String messageFormat, final Object... args) {
        return newError(position.sourcePosition(), messageFormat, args);
    }

    public static EvaluationError newError(final String messageFormat, final Object... args) {
        return newError((Position) null, messageFormat, args);
    }

    public static EvaluationError newError(final Position position, final String messageFormat, final Object... args) {
        final StringBuilder buffer = new StringBuilder("[E] ");
        buffer.append(String.format(messageFormat, args));

        if (position != null) {
            buffer.append(" (at line ").append(position.getLine());
            buffer.append(" column ").append(position.getCharacter()).append(')');
        }

        return new EvaluationError(buffer.toString());
    }
}
