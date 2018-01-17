package de.weltraumschaf.caythe.cli;

import de.weltraumschaf.commons.application.IO;
import de.weltraumschaf.commons.application.Version;
import de.weltraumschaf.commons.validate.Validate;
import lombok.Getter;

/**
 * Context to cary around things necessary in the context of the CLI.
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public final class DefaultCliContext implements CliContext {

    @Getter
    private final IO io;
    @Getter
    private final CliOptions options;
    @Getter
    private final Version version;

    DefaultCliContext(final IO io, final CliOptions options, final Version version) {
        super();
        this.io = Validate.notNull(io, "io");
        this.options = Validate.notNull(options, "options");
        this.version = version;
    }

}
