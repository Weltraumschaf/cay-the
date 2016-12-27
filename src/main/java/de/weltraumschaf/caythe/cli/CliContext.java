package de.weltraumschaf.caythe.cli;

import de.weltraumschaf.commons.application.IO;
import de.weltraumschaf.commons.validate.Validate;

/**
 * Context to cary around things necessary in the context of the CLI.
 */
final class CliContext {

    private final IO io;
    private final CliOptions options;

    CliContext(final IO io, final CliOptions options) {
        super();
        this.io = Validate.notNull(io, "io");
        this.options = Validate.notNull(options, "options");
    }

    IO getIo() {
        return io;
    }

    CliOptions getOptions() {
        return options;
    }
}
