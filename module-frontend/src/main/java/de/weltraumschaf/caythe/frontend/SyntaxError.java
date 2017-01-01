package de.weltraumschaf.caythe.frontend;

/**
 * Runtime exception to signal syntax errors.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class SyntaxError extends RuntimeException {
    public SyntaxError(final String message) {
        super(message);
    }
}
