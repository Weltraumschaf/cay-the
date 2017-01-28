package de.weltraumschaf.caythe.cli;

import de.weltraumschaf.commons.application.IO;
import de.weltraumschaf.commons.application.Version;
import de.weltraumschaf.commons.validate.Validate;

/**
 * Context to cary around things necessary in the context of the CLI.
 */
public final class CliContext {

    private final IO io;
    private final CliOptions options;
    private final Version version;

    CliContext(final IO io, final CliOptions options, final Version version) {
        super();
        this.io = Validate.notNull(io, "io");
        this.options = Validate.notNull(options, "options");
        this.version = version;
    }

    public IO getIo() {
        return io;
    }

    public CliOptions getOptions() {
        return options;
    }

    public Version getVersion() {
        return version;
    }
}
