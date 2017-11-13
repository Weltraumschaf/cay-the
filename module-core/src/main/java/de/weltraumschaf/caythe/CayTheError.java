package de.weltraumschaf.caythe;

import de.weltraumschaf.commons.validate.Validate;

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
    /**
     * Dedicated constructor.
     *
     * @param message mut not be {@code null} or empty
     */
    public CayTheError(final String message) {
        super(Validate.notEmpty(message, "message"));
    }
}
