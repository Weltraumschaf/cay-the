package de.weltraumschaf.caythe.backend.experimental;

import org.antlr.v4.runtime.Token;

public final class EvaluationError extends RuntimeException {

    private static final String UNSUPPORTED_OPERATOR = "Unsupported operator: '%s'!";

    private EvaluationError(final String message) {
        super(message);
    }

    public static EvaluationError newUnsupportedOperatorError(final Token operator) {
        return newError(operator, UNSUPPORTED_OPERATOR, operator.getText());
    }

    public static EvaluationError newError(final Token position, final String messageFormat, final Object... args) {
        final StringBuilder buffer = new StringBuilder("[E] ");
        buffer.append(String.format(messageFormat, args));

        if (position != null) {
            buffer.append(" (at line ").append(position.getLine());
            buffer.append(" column ").append(position.getCharPositionInLine()).append(')');
        }

        return new EvaluationError(buffer.toString());
    }
}
