package de.weltraumschaf.caythe.backend;

import de.weltraumschaf.commons.validate.Validate;

/**
 * Uses internal buffers for I/O.
 *
 * @since 1.0.0
 */
public final class BufferEnvironment implements Environment {

    private final StringBuilder out = new StringBuilder();
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

    public String getOut() {
        return out.toString();
    }

}
