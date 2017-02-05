package de.weltraumschaf.caythe;

/**
 * Base exception type for all modules.
 * <p>
 * It is a runtime exception because checked exceptions are avoided as much because they are considered not useful.
 * </p>
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public class CayTheError extends RuntimeException {
    public CayTheError(final String message) {
        super(message);
    }
}
