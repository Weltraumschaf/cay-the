package de.weltraumschaf.caythe.log;

/**
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
abstract class BaseLogger {

    final String format(final String msg, final Object... args) {
        return String.format(msg, args);
    }
}
