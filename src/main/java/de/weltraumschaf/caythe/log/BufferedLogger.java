package de.weltraumschaf.caythe.log;

import de.weltraumschaf.caythe.CayThe;

/**
 * This implementation buffers all logged content (get the content via {@link #toString()}.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
final class BufferedLogger extends BaseLogger implements Logger {

    /**
     * Buffer for content.
     */
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
