package de.weltraumschaf.caythe.backend.env;

import de.weltraumschaf.commons.validate.Validate;

/**
 * Uses internal buffers for I/O.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class BufferedEnvironment implements Environment {

    /**
     * Buffers the content for STDOUT.
     */
    private final StringBuilder out = new StringBuilder();
    /**
     * Buffers the content for STDERR.
     */
    private final StringBuilder err = new StringBuilder();

    @Override
    public void stdOut(final String output) {
        out.append(Validate.notNull(output, "output"));
    }

    @Override
    public void stdErr(final String output) {
        err.append(Validate.notNull(output, "output"));
    }

    @Override
    public String stdIn() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Get the buffered content for STDOUT.
     *
     * @return never {@code null}
     */
    public String getOut() {
        return out.toString();
    }

}