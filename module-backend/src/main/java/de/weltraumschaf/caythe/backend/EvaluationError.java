package de.weltraumschaf.caythe.backend;

import de.weltraumschaf.caythe.CayTheError;

/**
 * Error to signal errors during the interpretation.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class EvaluationError extends CayTheError {
    EvaluationError(final String message) {
        super(message);
    }

    public static EvaluationError newError(final String messageFormat, final Object... args) {
        return new EvaluationError(String.format(messageFormat, args));
    }
}
