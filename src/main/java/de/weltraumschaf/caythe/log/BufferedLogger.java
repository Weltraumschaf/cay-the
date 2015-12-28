package de.weltraumschaf.caythe.log;

import de.weltraumschaf.caythe.CayThe;

/**
 */
final class BufferedLogger extends BaseLogger implements Logger {

    private final StringBuilder buffer = new StringBuilder();

    @Override
    public void log(final String msg, final Object... args) {
        buffer.append(format(msg, args)).append(CayThe.DEFAULT_NEWLINE);
    }

    @Override
    public String toString() {
        return buffer.toString();
    }

}
