package de.weltraumschaf.caythe.backend.env;

import de.weltraumschaf.commons.application.IO;
import de.weltraumschaf.commons.validate.Validate;

/**
 * Redirects I/O to the given implementation of {@link IO}.
 *
 * @since 1.0.0
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 */
public final class DefaultEnvironmnet implements Environment {

    /**
     * Delegate for I/O.
     */
    private final IO io;

    public DefaultEnvironmnet(final IO io) {
        super();
        this.io = Validate.notNull(io, "io");
    }

    @Override
    public void stdOut(final String output) {
        io.print(output);
    }

    @Override
    public void stdErr(final String output) {
        io.error(output);
    }

    @Override
    public String stdIn() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
