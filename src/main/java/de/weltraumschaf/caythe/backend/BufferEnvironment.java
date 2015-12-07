package de.weltraumschaf.caythe.backend;

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
        out.append(output);
    }

    @Override
    public void stdErr(final String output) {
        err.append(output);
    }

    @Override
    public String stdIn() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
