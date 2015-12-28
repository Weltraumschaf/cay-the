package de.weltraumschaf.caythe.log;

/**
 * Implementations logs stuff.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public interface Logger {

    /**
     * Logs given message interpreted as format string.
     *
     * @param msg must not be {@code null} or empty
     * @param args optional arguments used in format string message
     */
    void log(final String msg, final Object... args);

}
