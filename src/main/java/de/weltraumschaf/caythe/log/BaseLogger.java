package de.weltraumschaf.caythe.log;

import de.weltraumschaf.commons.validate.Validate;

/**
 * Common logic for logger.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
abstract class BaseLogger {

    /**
     * Formats the given string with the arguments.
     *
     * @param msg must not be {@code null} or empty
     * @param args optional
     * @return never {@code null} or empty
     */
    final String format(final String msg, final Object... args) {
        Validate.notEmpty(msg, "msg");
        return String.format(msg, args);
    }
}
