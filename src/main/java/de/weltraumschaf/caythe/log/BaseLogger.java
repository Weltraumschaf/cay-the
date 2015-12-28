package de.weltraumschaf.caythe.log;

/**
 * @since 1.0.0
 */
abstract class BaseLogger {

    final String format(final String msg, final Object... args) {
        return String.format(msg, args);
    }
}
