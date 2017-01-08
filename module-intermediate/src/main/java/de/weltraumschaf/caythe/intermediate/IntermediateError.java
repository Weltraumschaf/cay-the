package de.weltraumschaf.caythe.intermediate;

import de.weltraumschaf.commons.validate.Validate;

/**
 * Used to wrap unhandled checked exceptions to not clutter up the public API.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class IntermediateError extends RuntimeException {
    /**
     * Copy constructor.
     * <p>
     * Maintains the message and the cause.
     * </p>
     * 
     * @param e
     *        must not be {@code null}
     */
    public IntermediateError(final Throwable e) {
        super(Validate.notNull(e, "e").getMessage(), e.getCause());
    }
}
